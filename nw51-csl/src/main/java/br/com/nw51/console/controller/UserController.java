package br.com.nw51.console.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.ProfileVO;
import br.com.nw51.common.vo.UserProfileVO;
import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.ProfileService;
import br.com.nw51.console.services.UserService;
import br.com.nw51.console.util.Constants;
import br.com.nw51.console.util.SecurityUtils;

/**
 * User Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@SessionScoped
public class UserController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (UserController.class.getName());
	private UserService userService = new UserService();
	private ProfileService profileService = new ProfileService();
	private List<UserVO> userVOList;
	private List<ProfileVO> profileVOList;
	private Integer userIdForm;
	private String emailForm;
	private String pwdForm;
	private String confirmationPwdForm;
	private Integer stateForm;
	private boolean statusForm = true;	
		
	public List<UserVO> getUserVOList() {
		return userVOList;
	}
	public void setUserVOList(List<UserVO> userVOList) {
		this.userVOList = userVOList;
	}
	public List<ProfileVO> getProfileVOList() {
		return profileVOList;
	}
	public void setProfileVOList(List<ProfileVO> profileVOList) {
		this.profileVOList = profileVOList;
	}
	public Integer getUserIdForm() {
		return userIdForm;
	}
	public void setUserIdForm(Integer userIdForm) {
		this.userIdForm = userIdForm;
	}
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
	public String getConfirmationPwdForm() {
		return confirmationPwdForm;
	}
	public void setConfirmationPwdForm(String confirmationPwdForm) {
		this.confirmationPwdForm = confirmationPwdForm;
	}
	public Integer getStateForm() {
		return stateForm;
	}
	public void setStateForm(Integer stateForm) {
		this.stateForm = stateForm;
	}
	public boolean isStatusForm() {
		return statusForm;
	}
	public void setStatusForm(boolean statusForm) {
		this.statusForm = statusForm;
	}
		
	@PostConstruct
    public void init() {
		super.checkReadOnly ("papel_usuario_manter");
		logger.debug ("this.isReadOnly(): " + super.isReadOnly());		
    }
	
	/**
	 * Finds all the users.
	 */
	public void findAll () {
		try {
			userVOList = userService.findAll();
			logger.info ("userList.size() [" + userVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find all the users. " + e.getMessage();
			logger.error (error);
		}				
	}
	
	/**
	 * Finds all the profiles.
	 */
	public void findAllProfile () {
		try {
			profileVOList = profileService.findAll();
			logger.debug ("profileVOList.size() [" + profileVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find all the profiles. " + e.getMessage();
			logger.error (error);
		}		
	}
	
	/**
	 * Gets the count of users.
	 */
	public int getUsersCount() { 
		int count = 0;
		try {
			count = userService.rowCount();
		} catch (BusinessException e) {
			String error = "An error occurred while getting the row count of users. " + e.getMessage();
			logger.error (error);
		}
		return count;
	}
	
	/**
	 * Redirects to add user page.
	 */
	public String goToAddUser() {
	    String toPage = "updateUser?faces-redirect=true";
	    logger.debug ("goToAddUser");
	    this.resetForm();
	    this.findAllProfile();
	    return toPage;
	}
	
	/**
	 * Redirects to users page.
	 */
	public String goToUsers() {
	    String toPage = "users?faces-redirect=true";
	    logger.debug ("goToUsers");
	    return toPage;
	}
	
	/**
	 * Performs the user save operation.
	 */
	public void save (ActionEvent actionEvent) {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
		String[] profileIdArr = request.getParameterValues ("chkProfileId"); 
		if (isValidForm ()) {
			UserVO userVO = new UserVO ();
			userVO.setUserId (this.userIdForm);
			userVO.setEmail (this.emailForm);
			userVO.setPwd (this.pwdForm);
			userVO.setState (this.stateForm);			
			userVO.setStatus (this.statusForm);
			if (!this.isDuplicatedEmail (userVO)) {
				try {
					List<UserProfileVO> userProfileVOList = this.getUserProfileVOList (profileIdArr);
					userVO.setUserProfileVOList (userProfileVOList);
					if (this.userIdForm != null && this.userIdForm > 0) {						
						if (this.stateForm == Constants.USER_STATE_NOT_LOGGED) {
							userVO.setLoginAttempts (0);
							userVO.setUnauthorizedAccessAttempts (0);
						}						
						int affectedRows = userService.update (userVO);
						if (affectedRows > 0) {							
							super.addInfoMessage ("formUpdateUser:messagesUpdateUser", " Usuário atualizado com sucesso.");
							logger.info ("The user [" + userVO.getEmail() + "] has been successfully updated.");						
						}
					} else {					
						int affectedRows = userService.insert (userVO);
						if (affectedRows > 0) {							
							super.addInfoMessage ("formUpdateUser:messagesUpdateUser", " Usuário adicionado com sucesso.");
							logger.info ("The user [" + userVO.getEmail() + "] has been successfully inserted.");
						}
					}
					this.resetForm ();
				} catch (BusinessException e) {
					String error = "An error occurred while saving or updating the user. " + e.getMessage();
					logger.error (error);
				}
			}
		}
	}
	
	/**
	 * Updates the user password.
	 */
	public void updatePassword (ActionEvent actionEvent) {
		if (isValidChangePwdForm ()) {
			UserVO userVO = new UserVO ();
			UserVO loggedUser = getLoggedUserFromSession();
			this.userIdForm = loggedUser.getUserId();
			this.emailForm = loggedUser.getEmail();
			userVO.setUserId (this.userIdForm);
			userVO.setEmail (emailForm);
			userVO.setPwd (this.pwdForm);
			try {
				int affectedRows = userService.updatePassword (userVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateUser:messagesUpdateUser", " Senha alterada com sucesso.");
					logger.info ("The user pwd of [" + userVO.getEmail() + "] has been successfully updated.");
				}
			} catch (BusinessException e) {
				String error = "An error occurred while updating the user pwd. " + e.getMessage();
				logger.error (error);
			}
		}
		this.resetChangePwdForm();
	}
	
	/**
	 * Performs the user delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		if (this.userIdForm != null && this.userIdForm > 0) {
			UserVO userVO = new UserVO ();
			userVO.setUserId (this.userIdForm);
			userVO.setEmail (this.emailForm);
			try {
				int affectedRows = userService.delete (userVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateUser:messagesUpdateUser", " Usuário excluído com sucesso.");
					logger.info ("The user [" + userVO.getEmail() + "] has been successfully deleted.");						
				}
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the user. " + e.getMessage();
				logger.error (error);
			}		
		}
		this.resetForm();		
	}
	
	/**
	 * Loads an user according with the specified id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById.");
		UserVO foundUserVO = null;						
		if (this.userIdForm != null && this.userIdForm > 0) {
			UserVO userVO = new UserVO ();
	        userVO.setUserId (this.userIdForm);
	        try {
	        	foundUserVO = this.userService.findById (userVO);
				this.userIdForm = foundUserVO.getUserId();
				this.emailForm = foundUserVO.getEmail();
				this.stateForm = foundUserVO.getState();
				this.setStatusForm (foundUserVO.isStatus());
				
				this.findAllProfileWithSelected (foundUserVO);
				
				logger.debug ("foundUserVO.getEmail() " + foundUserVO.getEmail());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the user by id. " + e.getMessage();
				logger.error (error);
			}
		}		
    }
	
	/**
	 * Finds all the profiles with the selected field.
	 */
	private void findAllProfileWithSelected (UserVO userVO) {
		try {
			profileVOList = userService.findAllWithSelected (userVO);
			logger.debug ("profileVOList.size() [" + profileVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find all the profiles with selected field. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Gets a list of userProfile, given an array of profileId.
	 * 
	 * @param profileIdArr the array of profileId
	 * @return a list of userProfile.
	 */
	private List<UserProfileVO>getUserProfileVOList (String[] profileIdArr) {
		List<UserProfileVO> userProfileVOList = new ArrayList<UserProfileVO>();
		for (String profileId : profileIdArr) {			
			ProfileVO profileVO = new ProfileVO();
			profileVO.setProfileId (new Integer (profileId));						
			UserProfileVO userProfileVO = new UserProfileVO ();			
			userProfileVO.setProfileVO (profileVO);						
			userProfileVOList.add (userProfileVO);
		}		
		return userProfileVOList;
	}
		
	/**
	 * Checks if the email is duplicated.
	 * 
	 * @param userVO the user containing the email.
	 * @return the result operation.
	 */
	private boolean isDuplicatedEmail (UserVO userVO) {
		boolean isDuplicated = false;
		try {
			int numberDuplicatedEmail = userService.findDuplicatedEmail (userVO);
			isDuplicated = (numberDuplicatedEmail > 0) ? true : false; 
			if (isDuplicated) {
				super.addWarnMessage ("formUpdateUser:messagesUpdateUser", " Email já existente, preencha outro email.");
				logger.warn ("isDuplicatedEmail: " + isDuplicated);
			}
		} catch (BusinessException e) {
			String error = "An error occurred while finding a duplicated email. " + e.getMessage();
			logger.error (error);
		}
		return isDuplicated;
	}
	
	/**
	 * Validates the user insert/update form.
	 * 
	 */
	private boolean isValidForm () {						
		if (!Utils.isNonEmpty (this.emailForm)
				|| !Utils.isNonEmpty (this.pwdForm)
				|| this.stateForm == -1) {
			super.addWarnMessage ("formUpdateUser:messagesUpdateUser", " Preencha todos os campos.");
			return false;			
		} else if (!Utils.isValidEmail (this.emailForm)) {
			super.addWarnMessage ("formUpdateUser:messagesUpdateUser", " Preencha o Email corretamente.");
			return false;
		} else if (!SecurityUtils.isValidPwd (this.pwdForm)) {
			super.addWarnMessage ("formUpdateUser:messagesUpdateUser", " A Senha deve conter pelo menos um número, uma letra minúscula, "
								 + "uma letra maiúscula e um símbolo (@#$%). Mínimo 10 e máximo de 20 caracteres.");
			return false;
		} else if (isEmptyProfiles()) {
			super.addWarnMessage ("formUpdateUser:messagesUpdateUser", " Selecione pelo menos um Perfil.");
			return false;
		}
		return true;
    }
	
	/**
	 * Validates the user change pwd form.
	 * 
	 */
	private boolean isValidChangePwdForm () {						
		if (!Utils.isNonEmpty (this.pwdForm)
				|| !Utils.isNonEmpty (this.confirmationPwdForm)) {
			super.addWarnMessage ("formChangeUserPwd:messagesChangeUserPwd", " Preencha todos os campos.");
			return false;
		} else if (!this.pwdForm.equals (confirmationPwdForm)) {
			super.addWarnMessage ("formChangeUserPwd:messagesChangeUserPwd", " As senhas devem ser iguais.");
			return false;
		} else if (!SecurityUtils.isValidPwd (this.pwdForm)) {
			super.addWarnMessage ("formChangeUserPwd:messagesChangeUserPwd", " A Senha deve conter pelo menos um número, uma letra minúscula, "
					 		     + "uma letra maiúscula e um símbolo (@#$%). Mínimo 10 e máximo de 20 caracteres.");
			return false;
		}
		return true;
    }
	
	/**
	 * Checks if at least one profile is selected.
	 * 
	 * @return the operation result.
	 */
	private boolean isEmptyProfiles() {
		boolean isEmpty = true;
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest(); 
		String[] profileIdArr = request.getParameterValues ("chkProfileId");
		if (profileIdArr != null && profileIdArr.length > 0) {
			isEmpty = false;
		}	
		return isEmpty;
	}
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {
		this.userIdForm    = null;
		this.emailForm     = "";
		this.pwdForm       = "";
		this.stateForm     = -1;
		this.statusForm    = true;
		this.profileVOList = null;
	}
	
	/**
	 * Resets the change pwd form.
	 */
	private void resetChangePwdForm () {
		this.userIdForm = null;
		this.emailForm  = "";
		this.pwdForm    = "";		
	}	
	
}
