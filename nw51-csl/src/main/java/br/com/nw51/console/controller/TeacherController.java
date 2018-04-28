package br.com.nw51.console.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.TeacherVO;
import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.TeacherService;
import br.com.nw51.console.services.UserService;

/**
 * Teacher Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@RequestScoped
public class TeacherController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (TeacherController.class.getName());
	private TeacherService teacherService = new TeacherService();
	private UserService userService = new UserService();
	
	private List<TeacherVO> teacherVOList;
	private Integer teacherIdForm;
	private Integer userIdForm;
	private String fullNameForm;
	private String phoneForm;
	private String mobileForm;
	
	private Map<String,Object> userIdMap;

	public List<TeacherVO> getTeacherVOList() {
		return teacherVOList;
	}

	public void setTeacherVOList(List<TeacherVO> teacherVOList) {
		this.teacherVOList = teacherVOList;
	}

	public Integer getTeacherIdForm() {
		return teacherIdForm;
	}

	public void setTeacherIdForm(Integer teacherIdForm) {
		this.teacherIdForm = teacherIdForm;
	}

	public Integer getUserIdForm() {
		return userIdForm;
	}

	public void setUserIdForm(Integer userIdForm) {
		this.userIdForm = userIdForm;
	}

	public String getFullNameForm() {
		return fullNameForm;
	}

	public void setFullNameForm(String fullNameForm) {
		this.fullNameForm = fullNameForm;
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
	
	public Map<String, Object> getUserIdMap() {
		return userIdMap;
	}

	public void setUserIdMap(Map<String, Object> userIdMap) {
		this.userIdMap = userIdMap;
	}

	@PostConstruct
    public void init() {
		super.checkReadOnly ("papel_professor_manter");
		logger.debug("this.isReadOnly(): " + super.isReadOnly());
    }
	
	/**
	 * Finds all the teachers.
	 */
	public void findAll () {
		try {
			teacherVOList = teacherService.findAll();
			logger.debug ("teacherVOList.size() [" + teacherVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find all the teachers. " + e.getMessage();
			logger.error (error);
		}
	}

	/**
	 * Gets the count of teachers.
	 */
	public int getTeachersCount() { 
		int count = 0;
		try {
			count = teacherService.rowCount();
		} catch (BusinessException e) {
			String error = "An error occurred while getting the row count of teachers. " + e.getMessage();
			logger.error (error);
		}
		return count;
	}
	
	/**
	 * Redirects to add teacher page.
	 */
	public String goToAddTeacher() {
	    String toPage = "updateTeacher?faces-redirect=true";
	    logger.debug ("goToAddTeacher");	    
	    return toPage;
	}
	
	/**
	 * Redirects to teachers page.
	 */
	public String goToTeachers() {
		logger.debug ("Starting goToTeachers method");
		String toPage = "teachers?faces-redirect=true";
	    return toPage;
	}
		
	/**
	 * Performs the teacher save operation.
	 */
	public void save (ActionEvent actionEvent) {
		logger.debug ("Starting save method.");
		if (isValidForm ()) { 
			TeacherVO teacherVO = new TeacherVO ();
			teacherVO.setTeacherId (teacherIdForm);
			
			UserVO userVO = new UserVO();
			userVO.setUserId(userIdForm);
			
			teacherVO.setUserVO (userVO);
			teacherVO.setFullName (fullNameForm);
			teacherVO.setPhone (phoneForm);
			teacherVO.setMobile (mobileForm);	
			
			if (!this.isUserAlreadyAssociated (teacherVO)) {
				try {
					if (this.teacherIdForm != null && this.teacherIdForm > 0) {
						int affectedRows = teacherService.update (teacherVO);
						if (affectedRows > 0) {
							super.addInfoMessage ("formUpdateTeacher:messagesUpdateTeacher", " Professor atualizado com sucesso.");
							logger.info ("The teacher [" + teacherVO.getFullName() + "] has been successfully updated.");						
						}
					} else {					
						int affectedRows = teacherService.insert (teacherVO);
						if (affectedRows > 0) {
							super.addInfoMessage ("formUpdateTeacher:messagesUpdateTeacher", " Professor adicionado com sucesso.");
							logger.info ("The teacher [" + teacherVO.getFullName() + "] has been successfully inserted.");
						}
					}
					this.resetForm();
				} catch (BusinessException e) {
					String error = "An error occurred while saving the teacher. " + e.getMessage();
					logger.error (error);
				}
			}
		}
		logger.debug ("Finishing save method.");
	}
	
	/**
	 * Performs the teacher delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		logger.debug ("Starting delete");
		if (this.teacherIdForm != null && this.teacherIdForm > 0) {
			TeacherVO teacherVO = new TeacherVO ();
			teacherVO.setTeacherId (this.teacherIdForm);
			teacherVO.setFullName (this.fullNameForm);
			try {
				int affectedRows = teacherService.delete (teacherVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateTeacher:messagesUpdateTeacher", " Professor excluído com sucesso.");
					logger.info ("The teacher [" + teacherVO.getFullName() + "] has been successfully deleted.");
				}
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the student. " + e.getMessage();
				logger.error (error);
			}
		}
		this.resetForm();
	}	
	
	/**
	 * Loads a teacher given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		TeacherVO foundTeacherVO = null;
		if (this.teacherIdForm != null && this.teacherIdForm > 0) {
			TeacherVO teacherVO = new TeacherVO ();
			teacherVO.setTeacherId (this.teacherIdForm);
	        try {
	        	foundTeacherVO = this.teacherService.findById (teacherVO);
	        	this.teacherIdForm = foundTeacherVO.getTeacherId();
				this.userIdForm = foundTeacherVO.getUserVO().getUserId();
				this.fullNameForm = foundTeacherVO.getFullName();
				this.phoneForm = foundTeacherVO.getPhone();
				this.mobileForm = foundTeacherVO.getMobile();
				logger.debug ("this.teacherIdForm " + this.teacherIdForm);
			} catch (BusinessException e) {
				String error = "An error occurred while finding the teacher by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Validates the teacher insert/update form.
	 * 
	 */
	private boolean isValidForm () {						
		if (!Utils.isNonEmpty (this.fullNameForm)
				|| this.userIdForm == null
				|| this.userIdForm == 0
				|| !Utils.isNonEmpty (this.fullNameForm)
				|| !Utils.isNonEmpty (this.phoneForm)
				|| !Utils.isNonEmpty (this.mobileForm)) {			
			super.addWarnMessage ("formUpdateTeacher:messagesUpdateTeacher", " Preencha os campos corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {
		this.teacherIdForm = null;
		this.userIdForm    = null;
		this.fullNameForm  = "";
		this.phoneForm     = "";
		this.mobileForm    = "";		
	}
	
	/**
	 * Initializes the userId select.
	 */
	public void initUserIdSelect() {
		List<UserVO> userVOList = new ArrayList<UserVO>();
		this.userIdMap = new LinkedHashMap<String,Object>();
		userIdMap.put ("", ""); //label, value
		try {
			userVOList = userService.findAllWithTeacherProfile();
			for (UserVO userVO : userVOList) {			
				userIdMap.put (userVO.getEmail(), userVO.getUserId());						
			}
		} catch (BusinessException e) {
			String error = "An error occurred while find the users with teacher profile. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Checks if the user is already associated to other teacher.
	 * 
	 * @param teacherVO the teacher.
	 * @return the result operation.
	 */
	private boolean isUserAlreadyAssociated (TeacherVO teacherVO) {
		boolean isAlreadyAssociated = false;
		try {
			isAlreadyAssociated = teacherService.hasAssociatedUser (teacherVO);
			if (isAlreadyAssociated) {
				super.addWarnMessage ("formUpdateTeacher:messagesUpdateTeacher", " Usuário já está associado a outro Professor, selecione outro usuário.");
				logger.warn ("isUserAlreadyAssociated: " + isAlreadyAssociated);
			}
		} catch (BusinessException e) {
			String error = "An error occurred while checking if user is already associated. " + e.getMessage();
			logger.error (error);
		}
		return isAlreadyAssociated;
	}
	
}
