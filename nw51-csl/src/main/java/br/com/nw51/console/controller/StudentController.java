package br.com.nw51.console.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.StudentFilterVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.StudentService;

/**
 * Student Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@RequestScoped
public class StudentController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (StudentController.class.getName());
	private StudentService studentService = new StudentService();
	
	private String fullNameFilter;
	private String emailFilter;
	private String cpfFilter;
	private String startJoinDateFilter;
	private String endJoinDateFilter;	
	
	private List<StudentVO> studentVOList;
	private Long studentIdForm;
	private String emailForm;
	private String pwdForm;
	private String genderForm;
	private String fullNameForm;
	private String cpfForm;
	private String phoneForm;
	private String mobileForm;
	private Date joinDateForm;
	private Date expirationDateForm;	
	private Date lastUpdateDateForm;
	private Date lastLoginDateForm;
	private String lastLoginIpForm;
	private boolean statusForm;
		
	public String getFullNameFilter() {
		return fullNameFilter;
	}

	public void setFullNameFilter(String fullNameFilter) {
		this.fullNameFilter = fullNameFilter;
	}

	public String getEmailFilter() {
		return emailFilter;
	}

	public void setEmailFilter(String emailFilter) {
		this.emailFilter = emailFilter;
	}

	public String getCpfFilter() {
		return cpfFilter;
	}

	public void setCpfFilter(String cpfFilter) {
		this.cpfFilter = cpfFilter;
	}

	public String getStartJoinDateFilter() {
		return startJoinDateFilter;
	}

	public void setStartJoinDateFilter(String startJoinDateFilter) {
		this.startJoinDateFilter = startJoinDateFilter;
	}

	public String getEndJoinDateFilter() {
		return endJoinDateFilter;
	}

	public void setEndJoinDateFilter(String endJoinDateFilter) {
		this.endJoinDateFilter = endJoinDateFilter;
	}

	public List<StudentVO> getStudentVOList() {
		return studentVOList;
	}

	public void setStudentVOList(List<StudentVO> studentVOList) {
		this.studentVOList = studentVOList;
	}

	public Long getStudentIdForm() {
		return studentIdForm;
	}

	public void setStudentIdForm (Long studentIdForm) {
		this.studentIdForm = studentIdForm;
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

	public String getGenderForm() {
		return genderForm;
	}

	public void setGenderForm (String genderForm) {
		this.genderForm = genderForm;
	}

	public String getFullNameForm() {
		return fullNameForm;
	}

	public void setFullNameForm(String fullNameForm) {
		this.fullNameForm = fullNameForm;
	}

	public String getCpfForm() {
		return cpfForm;
	}

	public void setCpfForm(String cpfForm) {
		this.cpfForm = cpfForm;
	}

	public String getPhoneForm() {
		return phoneForm;
	}

	public void setPhoneForm(String phoneForm) {
		this.phoneForm = phoneForm;
	}

	public String getMobileForm() {
		return mobileForm;
	}

	public void setMobileForm(String mobileForm) {
		this.mobileForm = mobileForm;
	}

	public Date getJoinDateForm() {
		return joinDateForm;
	}

	public void setJoinDateForm(Date joinDateForm) {
		this.joinDateForm = joinDateForm;
	}
	
	public Date getExpirationDateForm() {
		return expirationDateForm;
	}

	public void setExpirationDateForm(Date expirationDateForm) {
		this.expirationDateForm = expirationDateForm;
	}

	public Date getLastUpdateDateForm() {
		return lastUpdateDateForm;
	}

	public void setLastUpdateDateForm(Date lastUpdateDateForm) {
		this.lastUpdateDateForm = lastUpdateDateForm;
	}

	public Date getLastLoginDateForm() {
		return lastLoginDateForm;
	}

	public void setLastLoginDateForm(Date lastLoginDateForm) {
		this.lastLoginDateForm = lastLoginDateForm;
	}

	public String getLastLoginIpForm() {
		return lastLoginIpForm;
	}

	public void setLastLoginIpForm(String lastLoginIpForm) {
		this.lastLoginIpForm = lastLoginIpForm;
	}

	public boolean getStatusForm() {
		return statusForm;
	}

	public void setStatusForm (boolean statusForm) {
		this.statusForm = statusForm;
	}
	
	@PostConstruct
    public void init() {
		super.checkReadOnly ("papel_aluno_manter");
		logger.debug("this.isReadOnly(): " + super.isReadOnly());		
    }
	
	/**
	 * Finds all the students.
	 */
	public void findByFilter () {
		StudentFilterVO studentFilterVO = new StudentFilterVO();
		studentVOList = new ArrayList<StudentVO>();
		if (isValidFilter()) {
			studentFilterVO.setFullName (this.fullNameFilter);
			studentFilterVO.setStartDate (this.startJoinDateFilter);
			studentFilterVO.setEndDate (this.endJoinDateFilter);
			studentFilterVO.setEmail (this.emailFilter);
			studentFilterVO.setCpf (this.getCpfFilter());
			try {
				studentVOList = studentService.findByFilter (studentFilterVO);
				logger.debug ("studentVOList.size() [" + studentVOList.size() + "]");
			} catch (BusinessException e) {
				String error = "An error occurred while find the students by filter. " + e.getMessage();
				logger.error (error);
			}
		}	
	}
		
	/**
	 * Validates the search filter.
	 * 
	 * @return the operation result.
	 */
	private boolean isValidFilter() {
		boolean isValid = true;
		if (Utils.isNonEmpty (startJoinDateFilter) 
				&& Utils.isNonEmpty (endJoinDateFilter)) {			
			if (!Utils.isValidDateInterval (startJoinDateFilter, endJoinDateFilter)) {
				isValid = false;
				super.addWarnMessage ("messagesStudents", " Início Data de Matrícula é maior do que Fim Data de Matrícula.");
				logger.warn ("The start date is greater than the end date.");
			}			
		} else if (Utils.isNonEmpty (emailFilter)) {
			if (!Utils.isValidEmail(emailFilter)) {
				isValid = false;
				super.addWarnMessage ("messagesStudents", " Email inválido.");
				logger.warn ("Invalid Email.");
			}
		} else if (Utils.isNonEmpty (cpfFilter)) {
			String cpf = Utils.removeCpfMask (cpfFilter);
			if (!Utils.isCPF (cpf)) {
				isValid = false;
				super.addWarnMessage ("messagesStudents", " CPF inválido.");
				logger.warn ("Invalid CPF.");
			}
		}
		return isValid;
	}

	/**
	 * Gets the count of students.
	 */
	public int getStudentsCount() { 
		int count = 0;
		try {
			count = studentService.rowCount();
		} catch (BusinessException e) {
			String error = "An error occurred while getting the row count of students. " + e.getMessage();
			logger.error (error);
		}
		return count;
	}
	
	/**
	 * Redirects to students page.
	 */
	public String goToStudents() {
		logger.debug ("\nStarting goToStudents method");
		String toPage = "students?faces-redirect=true";
	    this.findByFilter();
	    return toPage;
	}
		
	/**
	 * Performs the student save operation.
	 */
	public void save (ActionEvent actionEvent) {
		if (isValidForm ()) { 
			StudentVO studentVO = new StudentVO ();
			studentVO.setStudentId (studentIdForm);
			studentVO.setEmail (emailForm);
			studentVO.setPwd (pwdForm);
			studentVO.setGender (genderForm);
			studentVO.setFullName (fullNameForm);
			studentVO.setCpf (cpfForm);
			studentVO.setPhone (phoneForm);
			studentVO.setMobile (mobileForm);
			studentVO.setExpirationDate (expirationDateForm);
			studentVO.setStatus (statusForm);
			if (!this.isDuplicatedEmail (studentVO) && !this.isDuplicatedCpf (studentVO)) {
				try {
					if (this.studentIdForm != null && this.studentIdForm > 0) {
						int affectedRows = studentService.update (studentVO);
						if (affectedRows > 0) {
							super.addInfoMessage ("formUpdateStudent:messagesUpdateStudent", "Aluno atualizado com sucesso.");
							logger.info ("The student [" + studentVO.getEmail() + "] has been successfully updated.");						
						}
					} else {					
						int affectedRows = studentService.insert (studentVO);
						if (affectedRows > 0) {
							super.addInfoMessage ("formUpdateStudent:messagesUpdateStudent", "Aluno adicionado com sucesso.");
							logger.info ("The student [" + studentVO.getEmail() + "] has been successfully inserted.");
						}
					}
				} catch (BusinessException e) {
					String error = "An error occurred while saving the student. " + e.getMessage();
					logger.error (error);
				}
			}
		}	
	}
	
	/**
	 * Performs the student save status operation.
	 */
	public void saveStatus (ActionEvent actionEvent) {				
		try {
			if (this.studentIdForm != null && this.studentIdForm > 0) {
				StudentVO studentVO = new StudentVO ();
				studentVO.setStudentId (studentIdForm);
				studentVO.setStatus (statusForm);
				int affectedRows = studentService.updateStatus (studentVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateStudent:messagesUpdateStudent", " Status do Aluno atualizado com sucesso.");
					logger.info ("The student status [" + studentVO.getEmail() + "] has been successfully updated.");
				}
			}
		} catch (BusinessException e) {
			String error = "An error occurred while saving the student status. " + e.getMessage();
			logger.error (error);
		}
	}
		
	/**
	 * Performs the student delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		logger.debug ("Starting delete");
		if (this.studentIdForm != null && this.studentIdForm > 0) {
			StudentVO studentVO = new StudentVO ();
			studentVO.setStudentId (this.studentIdForm);
			studentVO.setEmail (this.emailForm);
			try {
				int affectedRows = studentService.delete (studentVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateStudent:messagesUpdateStudent", "Estudante excluído com sucesso.");
					logger.info ("The student [" + studentVO.getEmail() + "] has been successfully deleted.");
				}
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the student. " + e.getMessage();
				logger.error (error);
			}
		}
		this.resetForm();
	}	
	
	/**
	 * Loads a student given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		StudentVO foundStudentVO = null;
		if (this.studentIdForm != null && this.studentIdForm > 0) {
			StudentVO studentVO = new StudentVO ();
			studentVO.setStudentId (this.studentIdForm);
	        try {
	        	foundStudentVO = this.studentService.findById (studentVO);
	        	
	        	this.studentIdForm = foundStudentVO.getStudentId();
				this.emailForm = foundStudentVO.getEmail();
				this.genderForm = foundStudentVO.getGender();
				this.fullNameForm = foundStudentVO.getFullName();
				this.cpfForm = foundStudentVO.getCpf();
				this.phoneForm = foundStudentVO.getPhone();
				this.mobileForm = foundStudentVO.getMobile();
				this.joinDateForm = foundStudentVO.getJoinDate();
				this.expirationDateForm = foundStudentVO.getExpirationDate();
				this.lastUpdateDateForm = foundStudentVO.getLastUpdateDate();
				this.lastLoginDateForm = foundStudentVO.getLastLoginDate();
				this.lastLoginIpForm = foundStudentVO.getLastLoginIp();
				this.statusForm = foundStudentVO.getStatus();
				
				logger.debug ("foundStudentVO.getEmail() " + foundStudentVO.getEmail());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the student by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Performs the search according with the specified filter and pagination.
	 * 
	 * @param event the action listener event.
	 */
	public void findByFilterListener (ActionEvent event) {
		logger.debug ("Start executing the method findByFilterListener().");
		findByFilter ();
		logger.debug ("Finish executing the method findByFilterListener().");
	}
	
	/**
	 * Checks if the email is duplicated.
	 * 
	 * @param studentVO the student containing the email.
	 * @return the result operation.
	 */
	private boolean isDuplicatedEmail (StudentVO studentVO) {
		boolean isDuplicated = false;
		try {
			int numberDuplicatedEmail = studentService.findDuplicatedEmail (studentVO);
			isDuplicated = (numberDuplicatedEmail > 0) ? true : false; 
			if (isDuplicated) {
				super.addWarnMessage ("formUpdateStudent:messagesUpdateStudent", "Email já existente, preencha outro email.");
				logger.warn ("isDuplicatedEmail: " + isDuplicated);
			}
		} catch (BusinessException e) {
			String error = "An error occurred while finding a duplicated email. " + e.getMessage();
			logger.error (error);
		}
		return isDuplicated;		
	}
	
	/**
	 * Checks if the cpf is duplicated.
	 * 
	 * @param studentVO the student containing the cpf.
	 * @return the result operation.
	 */
	private boolean isDuplicatedCpf (StudentVO studentVO) {
		boolean isDuplicated = false;
		try {
			int numberDuplicatedCpf = studentService.findDuplicatedCpf (studentVO);
			isDuplicated = (numberDuplicatedCpf > 0) ? true : false; 
			if (isDuplicated) {
				super.addWarnMessage ("formUpdateStudent:messagesUpdateStudent", "CPF já existente, preencha outro CPF.");
				logger.warn ("isDuplicatedCpf: " + isDuplicated);
			}
		} catch (BusinessException e) {
			String error = "An error occurred while finding a duplicated cpf. " + e.getMessage();
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
				|| this.genderForm == null
				|| !Utils.isNonEmpty (this.fullNameForm)
				|| !Utils.isNonEmpty (this.cpfForm)) {			
			super.addWarnMessage ("formUpdateStudent:messagesUpdateStudent", "Preencha os campos corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {
		this.studentIdForm 		= null;
		this.emailForm 			= "";
		this.genderForm 		= null;
		this.fullNameForm 		= "";
		this.cpfForm            = "";
		this.phoneForm          = "";
		this.mobileForm         = "";
		this.joinDateForm       = null;
		this.expirationDateForm = null;
		this.lastUpdateDateForm = null;
		this.lastLoginDateForm  = null;
		this.lastLoginIpForm    = "";
		this.statusForm         = true;
	}	
	
}
