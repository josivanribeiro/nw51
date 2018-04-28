package br.com.nw51.student.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.student.services.BusinessException;
import br.com.nw51.student.services.StudentService;
import br.com.nw51.student.util.Constants;
import br.com.nw51.student.util.SecurityUtils;


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
	private StudentService studentService = new StudentService ();
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
			
	@PostConstruct
    public void init() {
		
    }
	
	/**
	 * Performs the user login.
	 * 
	 */	
	public void doLogin (ActionEvent actionEvent) {
		String redirectToPage = null;
		RequestContext context = RequestContext.getCurrentInstance();
		if (isValidLoginForm()) {
			StudentVO studentVO = new StudentVO();
			studentVO.setEmail (this.emailForm);
			studentVO.setPwd (this.pwdForm);
			try {
				isLogged = this.studentService.doLogin (studentVO);
				logger.debug ("isLogged [" + isLogged +"]");
				StudentVO foundStudent = this.studentService.findByEmail (studentVO);				
				if (foundStudent != null) {
				
					if (isLogged && foundStudent.getState() == Constants.STUDENT_STATE_LOGGED) {
						
						super.addErrorMessage ("formLogin:messagesLogin", " Outro aluno já está logado com esta conta.");
						logger.warn ("Another student is already logged in with this account.");
						
					} else if (isLogged && foundStudent.getState() == Constants.STUDENT_STATE_NOT_LOGGED) {
						
						FacesContext
							.getCurrentInstance()
								.getExternalContext()
									.getSessionMap().put (Constants.LOGGED_STUDENT, foundStudent);
						
						//foundStudent.setState (Constants.STUDENT_STATE_LOGGED);
						//studentService.updateState (foundStudent);
						redirectToPage = Constants.PROFILE_PAGE;
						
					} else if (foundStudent.getState() == Constants.STUDENT_STATE_BLOCKED) {
						
						super.addErrorMessage ("formLogin:messagesLogin", " Aluno bloqueado, contate nosso suporte.");
						logger.warn ("Blocked student, please contact our support.");
						
					} else if (foundStudent.getLoginAttempts() < 3) {
						
						super.addErrorMessage ("formLogin:messagesLogin", " Email e senha inválidos.");
						int loginAttempts = foundStudent.getLoginAttempts() == 0 ? 1 : foundStudent.getLoginAttempts() + 1;
						foundStudent.setLoginAttempts (loginAttempts);
						studentService.updateLoginAttempts (foundStudent);
						logger.debug ("loginAttempts: " + loginAttempts);
						
					} else if (foundStudent.getLoginAttempts() == 3) {
						
						super.addErrorMessage ("formLogin:messagesLogin", " Limite de tentativas de login excedido, sua conta está bloqueada.");
						foundStudent.setState (Constants.STUDENT_STATE_BLOCKED);
						studentService.updateState (foundStudent);
						logger.warn ("Student account blocked due login attempts exceeded.");						
					}
					
				} else {
					super.addErrorMessage ("formLogin:messagesLogin", " Email e senha inválidos.");
				}
				
				resetForm ();
				
			} catch (BusinessException e) {
				String error = "An error occurred while performing the student login. " + e.getMessage();
				logger.error (error);
			}
		}
		context.addCallbackParam ("isLogged", isLogged);
		context.addCallbackParam ("redirectToPage", redirectToPage);
    }
	
	/**
	 * Performs the student logout.
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
			super.addWarnMessage ("formLogin:messagesLogin", " Preencha o email corretamente.");
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
}
