package br.com.nw51.console.controller;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.util.Constants;
import br.com.nw51.console.util.SecurityUtils;

/**
 * Abstract Controller.
 * 
 * @author Josivan Silva
 *
 */
public class AbstractController {
	
	private boolean isReadOnly = false;
	
	public boolean isReadOnly() {
		return isReadOnly;
	}
	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	
	/**
	 * Checks if the form fields/buttons should be read only or not.
	 */
	protected void checkReadOnly (String role) {
		this.isReadOnly = !this.isUserInRole (role);
	}
	
	/**
	 * Checks if the logged user has the given role.
	 * 
	 * @param name the role name.
	 * @return the operation result.
	 */
	public boolean isUserInRole (String name) {
		UserVO loggedUser = getLoggedUserFromSession ();
		return SecurityUtils.isUserInRole (name, loggedUser);
	}
	
	/**
	 * Gets the logged user from the session.
	 * 
	 * @return an instance of user.
	 */
	protected UserVO getLoggedUserFromSession () {
		UserVO loggedUser = null;
		loggedUser = (UserVO) FacesContext
    							.getCurrentInstance()
    								.getExternalContext()
    									.getSessionMap()
    										.get (Constants.LOGGED_USER); 
		return loggedUser;
	}
	
	/** 
	 * Adds a info message to the faces message.
	 * 
	 * @param clientId the client id. 
	 * @param message the message.
	 */
	protected void addInfoMessage (String clientId, String message) {
		this.addFacesMessage (clientId, FacesMessage.SEVERITY_INFO, "Info", message);
    }
    
	/** 
	 * Adds a warn message to the faces message.
	 * 
	 * @param clientId the client id.
	 * @param message the message.
	 */
	protected void addWarnMessage (String clientId, String message) {
        this.addFacesMessage (clientId, FacesMessage.SEVERITY_WARN, "Warning!", message);
    }
	
	/** 
	 * Adds an error message to the faces message.
	 *
	 * @param clientId the client id. 
	 * @param message the message.
	 */
	protected void addErrorMessage (String clientId, String message) {
        this.addFacesMessage (clientId, FacesMessage.SEVERITY_ERROR, "Error!", message);
    }
    
    /** 
	 * Adds a fatal message to the faces message.
	 * 
	 * @param clientId the client id.
	 * @param message the message.
	 */
	protected void addFatalMessage (String clientId, String message) {
		this.addFacesMessage (clientId, FacesMessage.SEVERITY_FATAL, "Fatal!", message);
    }
	
	/**
	 * Utility method for adding FacesMessages to specified component.
	 *
	 * @param clientId - the client id.
	 * @param message - message to add.
	 */
	private void addFacesMessage (String clientId, Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage (clientId, new FacesMessage (severity, summary, detail));
	}
	
}
