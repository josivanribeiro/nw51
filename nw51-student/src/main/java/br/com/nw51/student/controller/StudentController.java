package br.com.nw51.student.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.StateIdEnum;
import br.com.nw51.common.vo.AddressVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.student.services.BusinessException;
import br.com.nw51.student.services.StudentService;
import br.com.nw51.student.util.SecurityUtils;
import br.com.nw51.student.util.Utils;

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
	
	private Long studentIdForm;
	private String emailForm;
	private String pwdForm;
	private String pwdConfirmForm;
	private String genderForm;
	private String fullNameForm;
	private String cpfForm;
	private String phoneForm;
	private String mobileForm;
	private Long addressIdForm;
	private String cepForm;
	private String address1Form;
	private String numberForm;
	private String address2Form;
	private String neighborhoodForm;
	private String cityForm;
	private int stateIdForm;
	private Date joinDateForm;
	private Date expirationDateForm;
	private Date lastUpdateDateForm;
	private boolean socialProgramForm;
	private boolean statusForm;
	private Map<String,Object> stateIdMap;
	
	public Long getStudentIdForm() {
		return studentIdForm;
	}
	public void setStudentIdForm(Long studentIdForm) {
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
	public String getPwdConfirmForm() {
		return pwdConfirmForm;
	}
	public void setPwdConfirmForm(String pwdConfirmForm) {
		this.pwdConfirmForm = pwdConfirmForm;
	}
	public String getGenderForm() {
		return genderForm;
	}
	public void setGenderForm(String genderForm) {
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
	public Long getAddressIdForm() {
		return addressIdForm;
	}
	public void setAddressIdForm(Long addressIdForm) {
		this.addressIdForm = addressIdForm;
	}
	public String getCepForm() {
		return cepForm;
	}
	public void setCepForm(String cepForm) {
		this.cepForm = cepForm;
	}
	public String getAddress1Form() {
		return address1Form;
	}
	public void setAddress1Form(String address1Form) {
		this.address1Form = address1Form;
	}
	public String getNumberForm() {
		return numberForm;
	}
	public void setNumberForm(String numberForm) {
		this.numberForm = numberForm;
	}
	public String getAddress2Form() {
		return address2Form;
	}
	public void setAddress2Form(String address2Form) {
		this.address2Form = address2Form;
	}
	public String getNeighborhoodForm() {
		return neighborhoodForm;
	}
	public void setNeighborhoodForm(String neighborhoodForm) {
		this.neighborhoodForm = neighborhoodForm;
	}
	public String getCityForm() {
		return cityForm;
	}
	public void setCityForm(String cityForm) {
		this.cityForm = cityForm;
	}
	public int getStateIdForm() {
		return stateIdForm;
	}
	public void setStateIdForm(int stateIdForm) {
		this.stateIdForm = stateIdForm;
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
	public boolean isSocialProgramForm() {
		return socialProgramForm;
	}
	public void setSocialProgramForm(boolean socialProgramForm) {
		this.socialProgramForm = socialProgramForm;
	}
	public boolean isStatusForm() {
		return statusForm;
	}
	public void setStatusForm(boolean statusForm) {
		this.statusForm = statusForm;
	}
	public Map<String, Object> getStateIdMap() {
		return stateIdMap;
	}
	public void setStateIdMap(Map<String, Object> stateIdMap) {
		this.stateIdMap = stateIdMap;
	}
	
	@PostConstruct
    public void init() {		
		initStateIdSelect();
    }
	
	/**
	 * Performs the student update operation.
	 */
	public void update (ActionEvent actionEvent) {
		logger.debug ("Starting update");
		StudentVO loggedStudent = super.getLoggedStudentFromSession();
		if (loggedStudent != null
				&& loggedStudent.getStudentId() != null
				&& loggedStudent.getStudentId() > 0) {
			if (isValidForm ()) {
				StudentVO studentVO = new StudentVO ();
				studentVO.setStudentId (studentIdForm);
				studentVO.setFullName (fullNameForm);
				studentVO.setEmail (emailForm);
				studentVO.setPwd (pwdForm);
				studentVO.setCpf (cpfForm);
				studentVO.setPhone (phoneForm);
				studentVO.setMobile (mobileForm);				
				AddressVO addressVO = new AddressVO ();
				addressVO.setAddressId (addressIdForm);
				addressVO.setCEP (cepForm);
				addressVO.setAddress1 (address1Form);
				addressVO.setNumber (numberForm);
				addressVO.setAddress2 (address2Form);
				addressVO.setNeighborhood (neighborhoodForm);
				addressVO.setCity (cityForm);
				addressVO.setStateId (stateIdForm);				
				studentVO.setAddressVO (addressVO);				
				try {
					int affectedRows = studentService.update (studentVO);
					if (affectedRows > 0) {
						super.addInfoMessage ("formProfile:messagesProfile", " Aluno atualizado com sucesso.");
						logger.info ("The student [" + studentVO.getEmail() + "] has been successfully updated.");
					}
				} catch (BusinessException e) {
					String error = "An error occurred while updating the student. " + e.getMessage();
					logger.error (error);
				}
			}
		}
		logger.debug ("Finishing update");
	}
	
	/**
	 * Loads a student given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		StudentVO foundStudentVO = null;
		StudentVO loggedStudent = super.getLoggedStudentFromSession();
		if (loggedStudent != null
				&& loggedStudent.getStudentId() != null
				&& loggedStudent.getStudentId() > 0) {
			this.studentIdForm = loggedStudent.getStudentId();
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
				this.addressIdForm = foundStudentVO.getAddressVO().getAddressId();
				this.cepForm = foundStudentVO.getAddressVO().getCEP();
				this.address1Form = foundStudentVO.getAddressVO().getAddress1();
				this.numberForm = foundStudentVO.getAddressVO().getNumber();
				this.address2Form = foundStudentVO.getAddressVO().getAddress2();
				this.neighborhoodForm = foundStudentVO.getAddressVO().getNeighborhood();
				this.cityForm = foundStudentVO.getAddressVO().getCity();
				this.stateIdForm = foundStudentVO.getAddressVO().getStateId();				
				this.joinDateForm = foundStudentVO.getJoinDate();
				this.expirationDateForm = foundStudentVO.getExpirationDate();
				this.lastUpdateDateForm = foundStudentVO.getLastUpdateDate();
				this.socialProgramForm = foundStudentVO.isSocialProgram();
				this.statusForm = foundStudentVO.getStatus();
				
				logger.debug ("foundStudentVO.getEmail() " + foundStudentVO.getEmail());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the student by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Validates the student insert/update form.
	 * 
	 */
	private boolean isValidForm () {						
		String clientId = "formProfile:messagesProfile";
		if (!Utils.isNonEmpty (this.fullNameForm)
				|| !Utils.isNonEmpty (this.emailForm)			
				|| !Utils.isNonEmpty (this.cpfForm)
				|| !Utils.isNonEmpty (this.phoneForm)
				|| !Utils.isNonEmpty (this.mobileForm)
				|| !Utils.isNonEmpty (this.cepForm)
				|| !Utils.isNonEmpty (this.address1Form)
				|| !Utils.isNonEmpty (this.numberForm)
				|| !Utils.isNonEmpty (this.neighborhoodForm)
				|| !Utils.isNonEmpty (this.cityForm)
				|| this.stateIdForm == 0) {
			super.addWarnMessage (clientId, " Os campos de senha são opcionais, os outros campos são obrigatórios.");
			return false;
		} else if (!Utils.isValidEmail (this.emailForm)) {
			super.addWarnMessage (clientId, " Preencha o Email corretamente.");
			return false;
		} else if (Utils.isNonEmpty (this.pwdForm) 
						&& !Utils.isNonEmpty (this.pwdConfirmForm)) {
			super.addWarnMessage (clientId, " É necessário confirmar a senha.");
			return false;
		} else if (Utils.isNonEmpty (this.pwdForm) 
						&& Utils.isNonEmpty (this.pwdConfirmForm)
						&& !this.pwdForm.equals (this.pwdConfirmForm)) {
			super.addWarnMessage (clientId, " A senha e confirmação da senha devem ser iguais.");
			return false;
		} else if (Utils.isNonEmpty (this.pwdForm)
						&& !SecurityUtils.isValidPwd (this.pwdForm)) {
			super.addWarnMessage (clientId, " A senha deve conter pelo menos um número, uma letra minúscula, "
					 + "uma letra maiúscula e um símbolo (@#$%). Mínimo 8 e máximo de 20 caracteres.");
			return false;
		} else if (!Utils.isCPF (Utils.getCPFAsNumber(this.cpfForm))) {
			super.addWarnMessage (clientId, " Preencha o CPF corretamente.");
			return false;
		} else if (!Utils.isValidPhone (this.phoneForm)) {
			super.addWarnMessage (clientId, " Preencha o Telefone corretamente. Exemplos: (11) 3333-2222, (11) 3333 2222, 11 3333 2222 e 1133332222.");
			return false;
		} else if (!Utils.isValidMobile (this.mobileForm)) {
			super.addWarnMessage (clientId, " Preencha o Celular corretamente. Exemplos: 51988884444, 5188884444, (51) 8888 4444 e (51) 98888-4444.");
			return false;
		} else if (!Utils.isValidCep (this.cepForm)) {
			super.addWarnMessage (clientId, " Preencha o CEP corretamente. Exemplo: 92030-030.");
			return false;
		} else if (!Utils.isNumber (this.numberForm)) {
			super.addWarnMessage (clientId, " Preencha o Número corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Initializes the stateId select.
	 */
	private void initStateIdSelect() {
		this.stateIdMap = new LinkedHashMap<String,Object>();
		stateIdMap.put ("", ""); //label, value
		for (StateIdEnum stateId : StateIdEnum.values()) {			
			stateIdMap.put (stateId.getName(), stateId.getId());						
		}
	}
	
}
