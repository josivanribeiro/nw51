package br.com.nw51.student.controller;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.common.vo.UserVO;
import br.com.nw51.student.util.Constants;

/**
 * Abstract Controller.
 * 
 * @author Josivan Silva
 *
 */
public class AbstractController {
	
	/**
	 * Gets the logged student from the session.
	 * 
	 * @return an instance of student.
	 */
	protected StudentVO getLoggedStudentFromSession () {
		StudentVO loggedStudent = null;
		loggedStudent = (StudentVO) FacesContext
    							.getCurrentInstance()
    								.getExternalContext()
    									.getSessionMap()
    										.get (Constants.LOGGED_STUDENT);
		return loggedStudent;
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
