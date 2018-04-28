package br.com.nw51.console.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.UserProfileVO;
import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.ProfileService;
import br.com.nw51.console.services.UserService;
import br.com.nw51.console.util.Constants;
import br.com.nw51.console.util.SecurityUtils;

/**
 * Login Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@SessionScoped
public class LoginController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (LoginController.class.getName());
	private UserService userService = new UserService();
	private ProfileService profileService = new ProfileService();
	private UserVO loggedUser;
	private String emailForm;
	private String pwdForm;
	private boolean isLogged = false;	
	
	public String getEmailForm() {
		return emailForm;
	}
	public void setEmailForm(String emailForm) {
		this.emailForm = emailForm;
	}	
	public String getPwdForm() {
		return pwdForm;
	}
	public void setPwdForm(String pwdForm) {
		this.pwdForm = pwdForm;
	}
	public void setLogged(boolean isLogged) {
		this.isLogged = isLogged;
	}
	public boolean isLogged() {
		return isLogged;
	}	
	public UserVO getLoggedUser () {
		return loggedUser;
	}
		
	@PostConstruct
    public void init() {
		resetForm ();
    }
	
	/**
	 * From the logged user, initializes the email and roles.
	 */
	private void initUser () {
		if (isLogged) {
			this.emailForm = this.loggedUser.getEmail();
		}		
	}
	
	/**
	 * Performs the user login.
	 * 
	 */	
	public void doLogin (ActionEvent actionEvent) {	
		RequestContext context = RequestContext.getCurrentInstance();
		if (isValidLoginForm()) {
			UserVO userVO = new UserVO();
			userVO.setEmail (this.emailForm);
			userVO.setPwd (this.pwdForm);
			try {
				isLogged = this.userService.doLogin (userVO);
				logger.debug ("isLogged [" + isLogged +"]");
				UserVO foundUser = this.userService.findByEmail (userVO);
				
				if (isLogged 
						&& foundUser != null 
						&& foundUser.getState() == Constants.USER_STATE_LOGGED) {
					
					super.addErrorMessage ("formLogin:messagesLogin", " Outro Usuário já está logado com esta conta.");
					logger.warn ("Another user is already logged in with this account.");
					
				} else if (isLogged
								&& foundUser != null
								&& foundUser.getState() == Constants.USER_STATE_NOT_LOGGED) {
					
					FacesContext
						.getCurrentInstance()
							.getExternalContext()
								.getSessionMap().put (Constants.LOGGED_USER, foundUser);
					
					//getting the user profiles
					if (foundUser != null && foundUser.getUserId() > 0) {
						List<UserProfileVO> userProfileVOList = profileService.findProfilesByUser (foundUser);
						//setting the found userProfiles to the user
						if (userProfileVOList != null && userProfileVOList.size() > 0) {
							foundUser.setUserProfileVOList (userProfileVOList);
						}
					}
					
					this.loggedUser = foundUser;
					foundUser.setState (Constants.USER_STATE_LOGGED);
					userService.updateState (foundUser);
					initUser ();
					
				} else if (foundUser != null
								&& foundUser.getState() == Constants.USER_STATE_BLOCKED) {
					super.addErrorMessage ("formLogin:messagesLogin", " Usuário bloqueado, contate o Administrador do sistema.");
					logger.warn ("Blocked User, please contact the system administrator.");
				} else if (foundUser != null 
								&& foundUser.getLoginAttempts() < 3) {
					super.addErrorMessage ("formLogin:messagesLogin", " Email ou Senha inválidos.");
					int loginAttempts = foundUser.getLoginAttempts() == 0 ? 1 : foundUser.getLoginAttempts() + 1;
					foundUser.setLoginAttempts (loginAttempts);
					userService.updateLoginAttempts (foundUser);
					logger.debug ("loginAttempts: " + loginAttempts);
				} else if (foundUser != null 
								&& foundUser.getLoginAttempts() == 3) {
					super.addErrorMessage ("formLogin:messagesLogin", " Limite de tentativas de Login excedido, sua conta está bloqueada.");
					foundUser.setState (Constants.USER_STATE_BLOCKED);
					userService.updateState (foundUser);
					logger.warn ("User account blocked due login attempts exceeded.");
				}
				resetForm ();
			} catch (BusinessException e) {
				String error = "An error occurred while performing the user login. " + e.getMessage();
				logger.error (error);
			}
		}
		String redirectToPage = this.getPageBasedInRole ();
		context.addCallbackParam ("isLogged", isLogged);
		context.addCallbackParam ("redirectToPage", redirectToPage);
    }
	
	/**
	 * Performs the user logout.
	 */
	public String logout() {
		logger.debug ("Starting logout.");
		String toPage = "login?faces-redirect=true";
		this.isLogged = false;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		SecurityUtils.logout (request);
		logger.debug ("Finishing logout.");
		return toPage;
	}
	
	/**
	 * Validates the user login form.
	 * 
	 */
	private boolean isValidLoginForm () {
		if (!Utils.isNonEmpty (this.emailForm)
				|| !Utils.isNonEmpty (this.pwdForm)) {
			super.addWarnMessage ("formLogin:messagesLogin", " Preencha todos os campos.");
			return false;
		} else if (!Utils.isValidEmail (this.emailForm)) {
			super.addWarnMessage ("formLogin:messagesLogin", " Preencha o Email corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the login form.
	 */
	private void resetForm () {
		this.emailForm = "";
		this.pwdForm   = "";
	}
	
	/**
	 * Gets the page to redirect based in the user role.
	 * 
	 * @return the page to redirect.
	 */
	private String getPageBasedInRole () {
		String page = null;
		if (super.isUserInRole (Constants.ROLE_USER_READ)) {
			page = Constants.USERS_PAGE;
		} else if (super.isUserInRole (Constants.ROLE_COURSE_READ)) {
			page = Constants.COURSES_PAGE;
		}
		return page;
	}
	
}
