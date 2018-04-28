package br.com.nw51.student.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.common.vo.StudyPlanVO;
import br.com.nw51.student.services.BusinessException;
import br.com.nw51.student.services.CourseService;
import br.com.nw51.student.services.StudyPlanService;
import br.com.nw51.student.util.Utils;

/**
 * StudyPlan Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@ViewScoped
public class StudyPlanController extends AbstractController {
	
	static Logger logger = Logger.getLogger (StudyPlanController.class.getName());
	private StudyPlanService studyPlanService = new StudyPlanService();
	private CourseService courseService = new CourseService ();
	
	private Long studyPlanIdForm;
	private Long studentIdForm;
	private Integer courseIdForm;
	private Date startClassDatetimeForm;
	private Date endClassDatetimeForm;
	private boolean isNewForm = true;
	
	private StudyPlanVO selectedStudyPlanVO;
		
	private Map<String,Object> courseMap;
	private List<StudyPlanVO> studyPlanVOList = new ArrayList<StudyPlanVO>();
	private List<StudyPlanVO> studyPlanVOToRemoveList = new ArrayList<StudyPlanVO>();
	
	private final String MESSAGES_CLIENT_ID = "formStudyPlan:messagesStudyPlan";
	private boolean isVOListLoaded = false;
	
	public Long getStudyPlanIdForm() {
		return studyPlanIdForm;
	}
	
	public void setStudyPlanIdForm(Long studyPlanIdForm) {
		this.studyPlanIdForm = studyPlanIdForm;
	}

	public Long getStudentIdForm() {
		return studentIdForm;
	}

	public void setStudentIdForm(Long studentIdForm) {
		this.studentIdForm = studentIdForm;
	}

	public Integer getCourseIdForm() {
		return courseIdForm;
	}

	public void setCourseIdForm(Integer courseIdForm) {
		this.courseIdForm = courseIdForm;
	}

	public Date getStartClassDatetimeForm() {
		return startClassDatetimeForm;
	}

	public void setStartClassDatetimeForm(Date startClassDatetimeForm) {
		this.startClassDatetimeForm = startClassDatetimeForm;
	}

	public Date getEndClassDatetimeForm() {
		return endClassDatetimeForm;
	}

	public void setEndClassDatetimeForm(Date endClassDatetimeForm) {
		this.endClassDatetimeForm = endClassDatetimeForm;
	}
	
	public boolean isNewForm() {
		return isNewForm;
	}

	public void setNewForm(boolean isNewForm) {
		this.isNewForm = isNewForm;
	}

	public StudyPlanVO getSelectedStudyPlanVO() {
		return selectedStudyPlanVO;
	}

	public void setSelectedStudyPlanVO(StudyPlanVO selectedStudyPlanVO) {
		this.selectedStudyPlanVO = selectedStudyPlanVO;
	}

	public Map<String, Object> getCourseMap() {
		return courseMap;
	}

	public void setCourseMap(Map<String, Object> courseMap) {
		this.courseMap = courseMap;
	}
	
	public List<StudyPlanVO> getStudyPlanVOList() {
		return studyPlanVOList;
	}

	public void setStudyPlanVOList(List<StudyPlanVO> studyPlanVOList) {
		this.studyPlanVOList = studyPlanVOList;
	}

	@PostConstruct
    public void init() {
		initCourseSelect();
    }
	
	/**
	 * Finds all the studyPlans by studentId and courseId.
	 */
	public void findByStudentAndCourse (AjaxBehaviorEvent abe){
		try {
			StudentVO studentVO = getLoggedStudentFromSession();
			CourseVO courseVO = new CourseVO ();
			courseVO.setCourseId (this.courseIdForm);
			this.studyPlanVOList = studyPlanService.findByStudentAndCourse (studentVO, courseVO);
			if (this.studyPlanVOList != null && this.studyPlanVOList.size() > 0) {
				isVOListLoaded = true;
				logger.debug ("isVOListLoaded [" + isVOListLoaded + "]");
			}
			logger.debug ("studyPlanVOList.size() [" + studyPlanVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find all the studyPlans. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Adds or updates a new study plan.
	 * 
	 * @param actionEvent
	 */
	public void saveStudyPlan (ActionEvent actionEvent) {
		logger.debug ("Starting saveStudyPlan.");
		if (isValidForm ()) {
			if (this.isNewForm) {
				StudyPlanVO studyPlanVO = new StudyPlanVO ();
				Long nextId = this.getNextStudyPlanId (this.studyPlanVOList);
				studyPlanVO.setStudyPlanId (nextId);
				StudentVO loggedStudentVO = super.getLoggedStudentFromSession();
				studyPlanVO.setStudentVO (loggedStudentVO);
				CourseVO courseVO = new CourseVO ();
				courseVO.setCourseId (courseIdForm);
				String courseName = this.getCourseName (courseIdForm);
				courseVO.setName (courseName);		
				studyPlanVO.setCourseVO (courseVO);
				studyPlanVO.setStartClassDatetime (startClassDatetimeForm);
				studyPlanVO.setEndClassDatetime (endClassDatetimeForm);
				studyPlanVO.setNew (true);
				this.studyPlanVOList.add (studyPlanVO);
				super.addInfoMessage (MESSAGES_CLIENT_ID, " Plano de estudo adicionado com sucesso.");
				logger.debug ("studyPlanVOList.size(): " + studyPlanVOList.size());				
			} else {
				updateStudyPlanVOInList ();
				super.addInfoMessage (MESSAGES_CLIENT_ID, " Plano de estudo atualizado com sucesso.");				
			}
			resetStudyPlanForm ();	
		}		
        logger.debug ("Finishing saveStudyPlan.");
	}	
	
	/**
	 * Gets and load the selected studyPlan on row click event.
	 * 
	 * @param event the event.
	 */
	public void onRowSelect(SelectEvent event) {
		logger.debug ("Starting onRowSelect.");
		this.selectedStudyPlanVO = (StudyPlanVO) event.getObject();
		load ();
    }
	
	/**
	 * Unselects the studyPlan selected on row click event.
	 * 
	 * @param event the event.
	 */
	public void onRowUnselect(UnselectEvent event) {
		this.selectedStudyPlanVO = null;
    }
 
    /**
	 * Performs the studyPlan save operation.
	 */
	public void save (ActionEvent actionEvent) {
		logger.debug ("Starting save");
		boolean success = false;
		try {					
			if (isVOListLoaded) {
				StudentVO studentVO = super.getLoggedStudentFromSession();
				CourseVO courseVO = new CourseVO ();
				courseVO.setCourseId (courseIdForm);
				success = studyPlanService.update (this.studyPlanVOList, this.studyPlanVOToRemoveList, studentVO, courseVO);
				if (success) {
					String msg = this.studyPlanVOList.size() == 1 ? " Plano de estudo atualizado" : " Planos de estudo atualizados"; 
					super.addInfoMessage (MESSAGES_CLIENT_ID, msg + " com sucesso.");
					logger.info ("The studyPlans have been successfully updated.");
				}
			} else {
				success = studyPlanService.insert (this.studyPlanVOList);
				if (success) {
					String msg = this.studyPlanVOList.size() == 1 ? " Plano de estudo adicionado" : " Planos de estudo adicionados";
					super.addInfoMessage (MESSAGES_CLIENT_ID, msg + " com sucesso.");
					logger.info ("The studyPlans have been successfully inserted.");
				}
			}
		} catch (BusinessException e) {
			String error = "An error occurred while inserting or updating the studyPlans. " + e.getMessage();
			logger.error (error);
		}
		logger.debug ("Finishing save");
	}
	
	/**
	 * Performs the studyPlan delete (in memory) operation.
	 */
	public void delete (String studyPlanId) {
		logger.debug ("Starting delete");
		for (StudyPlanVO studyPlanVO : this.studyPlanVOList) {
			if (studyPlanVO.getStudyPlanId().toString().equals(studyPlanId)) {				
				//add to the removal list
				studyPlanVOToRemoveList.add (studyPlanVO);
				//remove from the main list
				this.studyPlanVOList.remove (studyPlanVO);
				super.addInfoMessage (MESSAGES_CLIENT_ID, " Plano de estudo excluído com sucesso.");
				logger.info ("The studyPlan with studyPlanId [" + studyPlanId + "] has been successfully deleted.");
				break;
			}
		}
		logger.debug ("Finishing delete");
	}
	
	/**
	 * Loads a studyPlan.
	 */
	public void load () {
		logger.debug ("Starting load");
		this.studyPlanIdForm        = this.selectedStudyPlanVO.getStudyPlanId();
    	this.studentIdForm          = this.selectedStudyPlanVO.getStudentVO().getStudentId();
    	this.courseIdForm           = this.selectedStudyPlanVO.getCourseVO().getCourseId();
    	this.startClassDatetimeForm = this.selectedStudyPlanVO.getStartClassDatetime();
    	this.endClassDatetimeForm   = this.selectedStudyPlanVO.getEndClassDatetime();
    	this.setNewForm (false);
		logger.debug ("loaded studyPlanId: " + this.selectedStudyPlanVO.getStudyPlanId());
		logger.debug ("Finishing load");
    }
	
	/**
	 * Validates the studyPlan insert/update form.
	 * 
	 */
	private boolean isValidForm () {
		if ((this.courseIdForm == null
				|| this.courseIdForm == 0)
				|| this.startClassDatetimeForm == null
				|| this.endClassDatetimeForm == null) {
			super.addWarnMessage (MESSAGES_CLIENT_ID, " Preencha todos os campos corretamente.");
			return false;
		} else if (startClassDatetimeForm.after(endClassDatetimeForm)) {
			super.addWarnMessage (MESSAGES_CLIENT_ID, " Data/hora inicial é maior do que data/hora final.");
			return false;
		} else if (startClassDatetimeForm.before(new Date())) {
			super.addWarnMessage (MESSAGES_CLIENT_ID, " Data/hora inicial deve ser maior do que data/hora atual.");
			return false;
		} else if (startClassDatetimeForm.equals(endClassDatetimeForm)) {
			super.addWarnMessage (MESSAGES_CLIENT_ID, " Data/hora inicial não deve ser igual à data/hora final.");
			return false;
		} else if (!Utils.isValidIntervalInHours (startClassDatetimeForm, endClassDatetimeForm, 8)) {
			super.addWarnMessage (MESSAGES_CLIENT_ID, " Um plano de estudo pode ter no máximo 8 horas.");
			return false;
		} else if (!Utils.isMinimumIntervalInHours (startClassDatetimeForm, endClassDatetimeForm, 1)) {
			super.addWarnMessage (MESSAGES_CLIENT_ID, " Um plano de estudo pode ter no mínimo 1 hora.");
			return false;
		} else if (this.existsClassPeriod(startClassDatetimeForm, endClassDatetimeForm)) {
			super.addWarnMessage (MESSAGES_CLIENT_ID, " Já existe um plano de estudo cadastrado neste horário.");
			return false;
		}
		return true;
    }
	
	/**
	 * Initializes the course select.
	 */
	private void initCourseSelect() {
		List<CourseVO> courseVOList = null;
		courseMap = new LinkedHashMap<String,Object>();
		courseMap.put ("", ""); //label, value
		try {
			courseVOList = this.courseService.findAll();			
			if (courseVOList != null && courseVOList.size() > 0) {
				for (CourseVO courseVO : courseVOList) {
					courseMap.put (courseVO.getName(), courseVO.getCourseId());
				}
			}
		} catch (BusinessException e) {
			String error = "An error occurred while finding all the courses. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Gets the course name by course id, from the courses map.
	 * 
	 * @param courseId the course id.
	 * @return the course name.
	 */
	private String getCourseName (Integer courseId) {
		String courseName = null;				
		for (Map.Entry<String, Object> entry : this.courseMap.entrySet()) {
			//System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
			if (entry.getValue() != null 
					&& entry.getValue() instanceof Integer) {
				Integer value = (Integer) entry.getValue();
				if (courseId == value) {
					courseName = entry.getKey();
					break;
				}
			}
		}
		return courseName;
	}
	
	/**
	 * Gets the id from the next item of the studyPlanVO list.
	 * 
	 * @param studyPlanVOList the the studyPlanVO list.
	 * @return the next id.
	 */
	private Long getNextStudyPlanId (List<StudyPlanVO> studyPlanVOList) {
		Long nextId = new Long (1);
		if (studyPlanVOList != null && studyPlanVOList.size() > 0) {
			int size = studyPlanVOList.size() - 1;
			StudyPlanVO lastStudyPlanVO = studyPlanVOList.get (size);
			nextId = lastStudyPlanVO.getStudyPlanId() + 1;
		}
		logger.debug ("nextId " + nextId);
		return nextId;
	}
	
	/**
	 * Updates the studyPlan in the list with new values.
	 */
	private void updateStudyPlanVOInList () {
		logger.debug ("Starting updateStudyPlanVOInList.");
		//search for existing studyPlanVO
		for (StudyPlanVO studyPlanVO : this.studyPlanVOList) {
			//found
			if (studyPlanVO.getStudyPlanId() == this.studyPlanIdForm) {
				//update it
				studyPlanVO.setStartClassDatetime (startClassDatetimeForm);
				studyPlanVO.setEndClassDatetime (endClassDatetimeForm);
				studyPlanVO.setChanged (true);
				break;
			}
		}
		logger.debug ("Finishing updateStudyPlanVOInList.");
	}
	
	/**
	 * Checks if a given period already exists or not.
	 * 
	 * @param startDate the start date.
	 * @param endDate the end date.
	 * @return flag indicating the success or not.
	 */
	private boolean existsClassPeriod (Date startDate, Date endDate) {
		boolean exists = false;
		try {
			StudyPlanVO studyPlanVO = new StudyPlanVO ();
			StudentVO studentVO = getLoggedStudentFromSession();
			studyPlanVO.setStudentVO (studentVO);
			studyPlanVO.setStartClassDatetime (startDate);
			studyPlanVO.setEndClassDatetime (endDate);
			exists = this.studyPlanService.existsClassPeriod (studyPlanVO);
		} catch (BusinessException e) {
			String error = "An error occurred while checking the class period. " + e.getMessage();
			logger.error (error);
		}
		return exists;
	}
	
	/**
	 * Resets the form. 
	 */
	private void resetStudyPlanForm () {
		this.studyPlanIdForm = null;
		this.startClassDatetimeForm = null;
		this.endClassDatetimeForm = null;
		this.isNewForm = true;
		this.selectedStudyPlanVO = null;
	}
	
}
