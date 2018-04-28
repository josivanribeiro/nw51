package br.com.nw51.console.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.CareerTypeEnum;
import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.CareerCourseVO;
import br.com.nw51.common.vo.CareerVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.CareerService;
import br.com.nw51.console.services.CourseService;

/**
 * Career Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@ViewScoped
public class CareerController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (CareerController.class.getName());
	private CareerService careerService = new CareerService();
	private CourseService courseService = new CourseService();
	
	private Integer careerIdForm;
	private String nameForm;
	private Integer careerTypeForm;
	private String descriptionForm;
	private boolean statusForm;	
	private List<CareerVO> careerVOList;
	private List<CareerCourseVO> careerCourseVOList;
	
	//Course
	private Integer courseIdForm;
	
	private Map<String,Object> careerTypeMap;
	private Map<String,Object> courseMap;
	
	private boolean isCourseAddedOrDeleted = false;
	
	public Integer getCareerIdForm() {
		return careerIdForm;
	}

	public void setCareerIdForm(Integer careerIdForm) {
		this.careerIdForm = careerIdForm;
	}

	public String getNameForm() {
		return nameForm;
	}

	public void setNameForm(String nameForm) {
		this.nameForm = nameForm;
	}
	
	public Integer getCareerTypeForm() {
		return careerTypeForm;
	}

	public void setCareerTypeForm(Integer careerTypeForm) {
		this.careerTypeForm = careerTypeForm;
	}

	public String getDescriptionForm() {
		return descriptionForm;
	}

	public void setDescriptionForm(String descriptionForm) {
		this.descriptionForm = descriptionForm;
	}

	public boolean isStatusForm() {
		return statusForm;
	}

	public void setStatusForm(boolean statusForm) {
		this.statusForm = statusForm;
	}
	
	public List<CareerVO> getCareerVOList() {
		return careerVOList;
	}

	public void setCareerVOList(List<CareerVO> careerVOList) {
		this.careerVOList = careerVOList;
	}

	public List<CareerCourseVO> getCareerCourseVOList() {
		return careerCourseVOList;
	}

	public void setCareerCourseVOList(List<CareerCourseVO> careerCourseVOList) {
		this.careerCourseVOList = careerCourseVOList;
	}
	
	public Integer getCourseIdForm() {
		return courseIdForm;
	}

	public void setCourseIdForm(Integer courseIdForm) {
		this.courseIdForm = courseIdForm;
	}

	public Map<String, Object> getCourseMap() {
		return courseMap;
	}

	public void setCourseMap(Map<String, Object> courseMap) {
		this.courseMap = courseMap;
	}
	
	public Map<String, Object> getCareerTypeMap() {
		return careerTypeMap;
	}

	public void setCareerTypeMap(Map<String, Object> careerTypeMap) {
		this.careerTypeMap = careerTypeMap;
	}

	@PostConstruct
    public void init() {
		super.checkReadOnly ("papel_profissao_manter");
		logger.debug("this.isReadOnly(): " + super.isReadOnly());
		this.initCareerTypeSelect();
		this.initCourseSelect();
    }
	
	/**
	 * Finds all the careers.
	 */
	public void findAll () {
		careerVOList = new ArrayList<CareerVO>();
		try {
			careerVOList = careerService.findAll ();
			logger.debug ("careerVOList.size() [" + careerVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find all the careers. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Gets the count of careers.
	 */
	public int getCareersCount() {
		int count = 0;
		try {
			count = careerService.rowCount();
		} catch (BusinessException e) {
			String error = "An error occurred while getting the row count of careers. " + e.getMessage();
			logger.error (error);
		}
		return count;
	}
	
	/**
	 * Redirects to add profile page.
	 */
	public String goToAddCareer() {
	    String toPage = "updateCareer?faces-redirect=true";
	    logger.debug ("Starting goToAddProfile.");
	    return toPage;
	}
	
	/**
	 * Redirects to careers page.
	 */
	public String goToCareers() {
		logger.debug ("Starting goToCareers.");
		String toPage = "careers?faces-redirect=true";
	    this.findAll();
	    return toPage;
	}
	
	/**
	 * Saves a new course.
	 */
	public void saveCourseListener (ActionEvent event) {
		logger.debug ("Starting saveCourseListener().");		
		if (this.courseIdForm != null && this.courseIdForm > 0) {			
			if (!courseExists (this.courseIdForm)) {
				
				String courseName = this.getCourseNameById (this.courseIdForm);
				
				CourseVO courseVO = new CourseVO();
				courseVO.setCourseId (courseIdForm);
				courseVO.setName (courseName);
				
				CareerCourseVO careerCourseVO =  new CareerCourseVO();
				careerCourseVO.setCourseVO (courseVO);
				Integer sequenceNumber = this.calculateCourseSequenceNumber (); 
				careerCourseVO.setSequenceNumber (sequenceNumber);			
				
				if (this.careerCourseVOList == null) {
					this.careerCourseVOList = new ArrayList<CareerCourseVO>();
				}
				
				this.careerCourseVOList.add (careerCourseVO);
				logger.info ("The course [" + courseVO.getName() + "] has been successfully inserted.");
				this.isCourseAddedOrDeleted = true;
				this.courseIdForm = null;
				
			} else {
				logger.warn ("Selected course already exists in careerCourse list.");
			}			
		}
		logger.debug ("Finishing saveCourseListener().");
		
	}
	
	/**
	 * Deletes a course given its courseId.
	 * 
	 * @param courseId the courseId.
	 */
	public void deleteCourse (Integer courseId) {
		logger.debug ("Starting deleteCourse().");
		logger.debug ("courseId to delete: " + courseId);
		ListIterator<CareerCourseVO> listIterator = this.careerCourseVOList.listIterator();
		while (listIterator.hasNext()) {
			CareerCourseVO careerCourseVO = (CareerCourseVO) listIterator.next();
			if (careerCourseVO.getCourseVO().getCourseId() == courseId) {
				listIterator.remove();
				this.isCourseAddedOrDeleted = true;
				logger.debug ("isCourseAddedOrDeleted: " + isCourseAddedOrDeleted);
			}
		}
		logger.debug ("Finishing deleteCourse().");
	}	
	
	/**
	 * Performs the careers save operation.
	 */
	public void save (ActionEvent actionEvent) {
		if (isValidForm ()) {
			CareerVO careerVO = new CareerVO ();
			careerVO.setCareerId (careerIdForm);
			careerVO.setName (this.nameForm);
			careerVO.setCareerType (this.careerTypeForm);
			careerVO.setDescription (this.descriptionForm);
			int status = this.statusForm ? 1 : 0;
			careerVO.setStatus (status);
			try {
				careerVO.setCareerCourseVOList (this.careerCourseVOList);					
				if (this.careerIdForm != null && this.careerIdForm > 0) {
					int affectedRows = careerService.update (careerVO);
					if (affectedRows > 0) {
						super.addInfoMessage ("formUpdateCareer:messagesUpdateCareer", " Profissão atualizada com sucesso.");
						logger.info ("The career [" + careerVO.getName() + "] has been successfully updated.");						
					}
				} else {
					int affectedRows = careerService.insert (careerVO);
					if (affectedRows > 0) {
						super.addInfoMessage ("formUpdateCareer:messagesUpdateCareer", " Profissão adicionada com sucesso.");
						logger.info ("The career [" + careerVO.getName() + "] has been successfully inserted.");
					}
				}
			} catch (BusinessException e) {
				String error = "An error occurred while saving or updating the career. " + e.getMessage();
				logger.error (error);
			}
			this.resetForm();
			
		}
	}
		
	/**
	 * Performs the career delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		logger.info ("Starting delete.");
		if (this.careerIdForm != null && this.careerIdForm > 0) {
			CareerVO careerVO = new CareerVO ();
			careerVO.setCareerId (this.careerIdForm);
			careerVO.setName (this.nameForm);
			try {
				int affectedRows = careerService.delete (careerVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateCareer:messagesUpdateCareer", " Profissão excluída com sucesso.");
					logger.info ("The career [" + careerVO.getName() + "] has been successfully deleted.");						
				}
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the career. " + e.getMessage();
				logger.error (error);
			}
			this.resetForm();
		}			
	}
	
	/**
	 * Loads a career according with the specified id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		CareerVO foundCareerVO = null;
		if (this.careerIdForm != null && this.careerIdForm > 0) {
			CareerVO careerVO = new CareerVO ();
			careerVO.setCareerId (this.careerIdForm);
	        try {
	        	foundCareerVO = this.careerService.findById (careerVO);
				this.careerIdForm = foundCareerVO.getCareerId();
				this.nameForm = foundCareerVO.getName();
				this.careerTypeForm = foundCareerVO.getCareerType();
				this.descriptionForm = foundCareerVO.getDescription();
				
				if (!isCourseAddedOrDeleted) {
					this.careerCourseVOList = foundCareerVO.getCareerCourseVOList();
				}				
				
				this.statusForm = foundCareerVO.getStatus() == 1 ? true : false;
				logger.debug ("foundCareerVO.getName() " + foundCareerVO.getName());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the career by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Validates the career insert/update form.
	 * 
	 */
	private boolean isValidForm () {						
		if (!Utils.isNonEmpty (this.nameForm)
				|| this.careerTypeForm == null
				|| this.careerTypeForm == 0
				|| !Utils.isNonEmpty (this.descriptionForm)
				|| this.careerCourseVOList == null
				|| this.careerCourseVOList.size() == 0) {
			super.addWarnMessage ("formUpdateCareer:messagesUpdateCareer", " Preencha todos os campos corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {
		this.careerIdForm           = null;
		this.nameForm               = null;
		this.careerTypeForm         = null;
		this.descriptionForm        = null;
		this.statusForm             = true;	
		this.careerVOList           = null;
		this.careerCourseVOList     = null;
		this.isCourseAddedOrDeleted = false;
	}
	
	/**
	 * Initializes the careerType select.
	 */
	public void initCareerTypeSelect() {
		this.careerTypeMap = new LinkedHashMap<String,Object>();
		careerTypeMap.put ("", ""); //label, value
		for (CareerTypeEnum careerType : CareerTypeEnum.values()) {			
			careerTypeMap.put (careerType.getName(), careerType.getId());						
		}
	}
	
	/**
	 * Initializes the course select.
	 */
	public void initCourseSelect() {
		List<CourseVO> courseVOList = new ArrayList<CourseVO>();
		this.courseMap = new LinkedHashMap<String,Object>();
		try {
			courseVOList = this.courseService.findAll();
		} catch (BusinessException e) {
			String error = "An error occurred while finding the all the courses. " + e.getMessage();
			logger.error (error);
		}
		this.courseMap.put ("", ""); //label, value
		for (CourseVO courseVO : courseVOList) {
			courseMap.put (courseVO.getName(), courseVO.getCourseId());
		}
	}
	
	/**
	 * Gets the course name, given its id.
	 * 
	 * @param id the course id.
	 * @return the course name.
	 */
	@SuppressWarnings("rawtypes")
	private String getCourseNameById (Integer id) {
	    String name = null;
		Iterator<Entry<String, Object>> it = this.courseMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();	        
	        if (pair.getValue() instanceof Integer) {
    			Integer courseId = (Integer) pair.getValue();
    			if (courseId == id) {
    				name = (String) pair.getKey();    				
    				break;
    			}        			
    		}	        	        
	    }	    
	    return name;
	}
	
	/**
	 * Checks if course already exists on careerCourse list.
	 * 
	 * @param courseId the course id.
	 * @return the result operation.
	 */
	private boolean courseExists (Integer courseId) {
		boolean exists = false;
		if (this.careerCourseVOList != null && this.careerCourseVOList.size() > 0) {
			for (CareerCourseVO careerCourseVO : this.careerCourseVOList) {
				if (careerCourseVO.getCourseVO().getCourseId() == courseId) {
					exists = true;
					break;
				}
			}
		}		
		return exists;
	}
	
	/**
	 * Calculates the next course sequence number.
	 * 
	 * @return the next sequence number.
	 */
	private Integer calculateCourseSequenceNumber () {
		Integer sequenceNumber = 0;
		if (this.careerCourseVOList == null || this.careerCourseVOList.size() == 0) {
			sequenceNumber = 1;
		} else {
			sequenceNumber = this.careerCourseVOList.size() + 1;
		}		
		return sequenceNumber;
	}
	
}
