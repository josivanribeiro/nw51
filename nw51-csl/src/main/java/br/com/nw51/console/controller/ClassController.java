package br.com.nw51.console.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.AttachmentVO;
import br.com.nw51.common.vo.ClassPlanToolVO;
import br.com.nw51.common.vo.ClassPlanVO;
import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.ContentItemVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.ExerciseVO;
import br.com.nw51.common.vo.SlideVO;
import br.com.nw51.common.vo.ToolVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.ClassService;
import br.com.nw51.console.services.ExerciseService;
import br.com.nw51.console.services.ToolService;

/**
 * Class Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@SessionScoped
public class ClassController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (ClassController.class.getName());
	private ClassService classService = new ClassService();
	private ToolService toolService = new ToolService();
	private ExerciseService exerciseService = new ExerciseService();
	
	private Integer classIdForm;
	private Integer courseIdForm;
	/*Class Plan*/
	private Integer classPlanIdForm;
	private String[] selectedToolArr;
	private Float averageTimeForm;
	private String skillsForm;
	
	private String nameForm;
	private String descriptionForm;
	private String notesForm;
	
	/*Course Params*/
	private Integer contentItemIdForm;
	private String contentItemNumerationForm;
	private String contentItemNameForm;
	
	private List<SlideVO> slideVOList = new ArrayList<SlideVO>();
	private List<ExerciseVO> exerciseVOList = new ArrayList<ExerciseVO>();
	private List<AttachmentVO> attachmentVOList = new ArrayList<AttachmentVO>();
			
	private boolean isFileUploaded = false;
	
	public Integer getClassIdForm() {
		return classIdForm;
	}

	public void setClassIdForm(Integer classIdForm) {
		this.classIdForm = classIdForm;
	}

	public Integer getCourseIdForm() {
		return courseIdForm;
	}

	public void setCourseIdForm(Integer courseIdForm) {
		this.courseIdForm = courseIdForm;
	}
	
	public Integer getClassPlanIdForm() {
		return classPlanIdForm;
	}
	
	public void setClassPlanIdForm(Integer classPlanIdForm) {
		this.classPlanIdForm = classPlanIdForm;
	}
	
	public Float getAverageTimeForm() {
		return averageTimeForm;
	}

	public String[] getSelectedToolArr() {
		return selectedToolArr;
	}

	public void setSelectedToolArr(String[] selectedToolArr) {
		this.selectedToolArr = selectedToolArr;
	}

	public void setAverageTimeForm (Float averageTimeForm) {
		this.averageTimeForm = averageTimeForm;
	}

	public String getSkillsForm() {
		return skillsForm;
	}

	public void setSkillsForm(String skillsForm) {
		this.skillsForm = skillsForm;
	}

	public String getNameForm() {
		return nameForm;
	}

	public void setNameForm(String nameForm) {
		this.nameForm = nameForm;
	}

	public String getDescriptionForm() {
		return descriptionForm;
	}

	public void setDescriptionForm(String descriptionForm) {
		this.descriptionForm = descriptionForm;
	}

	public String getNotesForm() {
		return notesForm;
	}

	public void setNotesForm (String notesForm) {
		this.notesForm = notesForm;
	}

	public List<ExerciseVO> getExerciseVOList() {
		return exerciseVOList;
	}
	
	public List<SlideVO> getSlideVOList() {
		return slideVOList;
	}

	public void setSlideVOList(List<SlideVO> slideVOList) {
		this.slideVOList = slideVOList;
	}

	public void setExerciseVOList(List<ExerciseVO> exerciseVOList) {
		this.exerciseVOList = exerciseVOList;
	}
	
	public List<AttachmentVO> getAttachmentVOList() {
		return attachmentVOList;
	}

	public void setAttachmentVOList(List<AttachmentVO> attachmentVOList) {
		this.attachmentVOList = attachmentVOList;
	}
	
	public Integer getContentItemIdForm() {
		return contentItemIdForm;
	}

	public void setContentItemIdForm(Integer contentItemIdForm) {
		this.contentItemIdForm = contentItemIdForm;
	}

	public String getContentItemNumerationForm() {
		return contentItemNumerationForm;
	}

	public void setContentItemNumerationForm(String contentItemNumerationForm) {
		this.contentItemNumerationForm = contentItemNumerationForm;
	}

	public String getContentItemNameForm() {
		return contentItemNameForm;
	}

	public void setContentItemNameForm(String contentItemNameForm) {
		this.contentItemNameForm = contentItemNameForm;
	}

	@PostConstruct
    public void init() {
		this.resetForm();
    }
	
	/**
	 * Redirects to course page.
	 */
	public String backToCourse() {
		String toPage = "updateCourse?id=" + this.courseIdForm + "&faces-redirect=true";
		this.resetForm();
	    return toPage;
	}
	
	/**
	 * Redirects to add slide page.
	 */
	public String goToAddSlide() {
		logger.debug ("Starting goToAddSlide method");
		String toPage = "updateSlide?classId=" + this.classIdForm + "&faces-redirect=true";
		logger.debug ("classIdForm " + this.classIdForm);
	    if (this.classIdForm == null || this.classIdForm == 0) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " É necessário salvar a Aula antes de adicionar Slide.");
			return null;
	    }
	    return toPage;
	}
	
	/**
	 * Redirects to add exercise page.
	 */
	public String goToAddExercise() {
		String toPage = "updateExercise?classId=" + this.classIdForm + "&faces-redirect=true";
		logger.debug ("Starting goToAddExercise method");
	    logger.debug ("classIdForm " + this.classIdForm);
	    if (this.classIdForm == null || this.classIdForm == 0) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " É necessário salvar a Aula antes de adicionar Exercício.");
			return null;
	    }
	    return toPage;
	}
	
	/**
	 * Sets the class name.
	 */
	public void setClassName () {
		if (Utils.isNonEmpty (this.contentItemNumerationForm) 
				&& Utils.isNonEmpty (this.contentItemNameForm)) {
			try {
				this.nameForm = URLDecoder.decode (this.contentItemNumerationForm, "UTF-8") 
						      + " " + URLDecoder.decode (this.contentItemNameForm, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				String error = "An error occurred while decoding the URL. " + e.getMessage();
				logger.error (error);
			}
		}
	}
	
	/**
	 * Performs the class save operation.
	 */
	public void save (ActionEvent actionEvent) {
		if (isValidForm ()) { 
			ClassVO classVO = new ClassVO ();
			classVO.setClassId (classIdForm);
			
			CourseVO courseVO = new CourseVO();
			courseVO.setCourseId (courseIdForm);
			
			classVO.setCourseVO(courseVO);
			classVO.setName (nameForm);
			classVO.setDescription (descriptionForm);
						
			ClassPlanVO classPlanVO = new ClassPlanVO ();
			
			classPlanVO.setClassPlanId (classPlanIdForm);
			classPlanVO.setAverageTime (averageTimeForm);
			classPlanVO.setSkills (skillsForm);
			
			classVO.setClassPlanVO (classPlanVO);
			classVO.setNotes (notesForm);
			
			classVO.setAttachmentVOList (attachmentVOList);
			
			ContentItemVO contentItemVO = new ContentItemVO();
			contentItemVO.setContentItemId (contentItemIdForm);
			
			classVO.setContentItemVO (contentItemVO);
			
			try {
				List<ClassPlanToolVO> classPlanToolVOList = this.getClassPlanToolVOList (this.selectedToolArr);
				classVO.getClassPlanVO().setClassPlanToolVOList (classPlanToolVOList);
				if (this.classIdForm != null && this.classIdForm > 0) {
					int affectedRows = classService.update (classVO);
					if (affectedRows > 0) {
						this.classService.updateAverageTime (classVO.getCourseVO(), this.averageTimeForm);
						super.addInfoMessage ("formUpdateClass:messagesUpdateClass", " Aula atualizada com sucesso.");
						logger.info ("The class [" + classVO.getName() + "] has been successfully updated.");						
					}
				} else {					
					this.classIdForm = classService.insert (classVO);
					if (this.classIdForm > 0) {
						this.classService.updateAverageTime (classVO.getCourseVO(), this.averageTimeForm);
						super.addInfoMessage ("formUpdateClass:messagesUpdateClass", " Aula adicionada com sucesso.");
						logger.info ("The class [" + classVO.getName() + "] has been successfully inserted.");
					}
				}
				resetForm();
			} catch (BusinessException e) {
				String error = "An error occurred while saving the class. " + e.getMessage();
				logger.error (error);
			}			
		}		
	}
	
	/**
	 * Performs the class delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		logger.debug ("Starting delete");
		if (this.classIdForm != null && this.classIdForm > 0) {
			ClassVO classVO = new ClassVO ();
			classVO.setClassId (classIdForm);
			
			CourseVO courseVO = new CourseVO();
			courseVO.setCourseId (courseIdForm);
			
			classVO.setCourseVO (courseVO);
			
			ClassPlanVO classPlanVO = new ClassPlanVO ();
			classPlanVO.setClassPlanId (classPlanIdForm);
			
			classVO.setClassPlanVO (classPlanVO);
			
			ContentItemVO contentItemVO = new ContentItemVO();
			contentItemVO.setContentItemId (contentItemIdForm);
			
			classVO.setContentItemVO (contentItemVO);
			
			try {
				int affectedRows = classService.delete (classVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateClass:messagesUpdateClass", " Aula excluída com sucesso.");
					logger.info ("The class [" + classVO.getName() + "] has been successfully deleted.");
				}
				this.resetForm();
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the class. " + e.getMessage();
				logger.error (error);
			}
		}		
	}	
	
	/**
	 * Loads a class given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		logger.debug ("this.classIdForm: " + this.classIdForm);
		ClassVO foundClassVO = null;
		if (this.classIdForm != null && this.classIdForm > 0) {
			ClassVO classVO = new ClassVO ();
			classVO.setClassId (this.classIdForm);
	        try {
	        	foundClassVO = this.classService.findById (classVO);
	        	
	        	this.courseIdForm = foundClassVO.getCourseVO().getCourseId();
	        	this.classPlanIdForm = foundClassVO.getClassPlanVO().getClassPlanId();
	        		        	
	        	this.setSelectedTools (foundClassVO.getClassPlanVO());
	        	
	        	this.averageTimeForm = foundClassVO.getClassPlanVO().getAverageTime();
	        	this.skillsForm = foundClassVO.getClassPlanVO().getSkills();
				
	        	this.nameForm = foundClassVO.getName();
	        	this.descriptionForm = foundClassVO.getDescription();
	        	this.notesForm = foundClassVO.getNotes();
	        	
	        	findSlidesByClassId (foundClassVO);
	        		        	
	        	findExercisesByClassId (foundClassVO);
	        	
	        	logger.debug ("isFileUploaded: " + isFileUploaded);
	        		        	
	        	if (foundClassVO.getAttachmentVOList() != null 
	        			&& foundClassVO.getAttachmentVOList().size() > 0
	        			&& !isFileUploaded) {
	        		this.attachmentVOList = foundClassVO.getAttachmentVOList();
	        	}
	        	
	        	logger.debug ("this.classIdForm " + this.classIdForm);
				logger.debug ("foundClassVO.getName() " + foundClassVO.getName());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the class by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Checks if the Slide container should be enabled or not.
	 * 
	 * @return the operation result.
	 */
	public boolean isSlideEnabled () {		
		boolean isEnabled = false;
		if (this.selectedToolArr != null && this.selectedToolArr.length > 0) {
			for (String id : this.selectedToolArr) {
				Integer selectedId = new Integer (id);
				if (selectedId == 1 || selectedId == 2) {
					isEnabled = true;
					break;
				}				
			}
		}
		return isEnabled;
	}
	
	/**
	 * Checks if the Exercise container should be enabled or not.
	 * 
	 * @return the operation result.
	 */
	public boolean isExerciseEnabled () {
		boolean isEnabled = false;
		if (this.selectedToolArr != null && this.selectedToolArr.length > 0) {
			for (String id : this.selectedToolArr) {
				Integer selectedId = new Integer (id);
				if (selectedId == 3 || selectedId == 4) {
					isEnabled = true;
					break;
				}
			}
		}
		return isEnabled;
	}
	
	/**
	 * Uploads an attachment.
	 */
	public void doAttachmentUpload (FileUploadEvent fileUploadEvent) { 
        logger.debug ("Starting doAttachmentUpload");		
		UploadedFile uploadedFile = fileUploadEvent.getFile();
        String filename = uploadedFile.getFileName();
        String fileSize = Utils.convertToStringRepresentation (uploadedFile.getSize());
        AttachmentVO attachmentVO = new AttachmentVO();
        attachmentVO.setName (filename);
        attachmentVO.setSize (fileSize);
        attachmentVO.setType (uploadedFile.getContentType());
        try {
			attachmentVO.setFile (uploadedFile.getInputstream());
		} catch (IOException e) {
			e.printStackTrace();
			String error = "An I/O error occurred while getting the attachment input stream. " + e.getMessage();
			logger.error (error);
		}
        if (attachmentVOList == null) {
        	attachmentVOList = new ArrayList<AttachmentVO>();
        }
               
        this.attachmentVOList.add (attachmentVO);
        isFileUploaded = true;
        
        logger.debug ("isFileUploaded: " + isFileUploaded);
        logger.debug ("this.attachmentVOList.size(): " + this.attachmentVOList.size());
	}
	
	/**
	 * Download an attachment.
	 */
	public StreamedContent doAttachmentDownload (Integer attachmentId) {
		logger.debug ("Starting doAttachmentDownload");
		StreamedContent file = null;		
		for (AttachmentVO attachmentVO : this.attachmentVOList) {
			if (attachmentVO.getAttachmentId() == attachmentId) {
				logger.debug ("attachmentVO.getAttachmentId(): " + attachmentVO.getAttachmentId());
				InputStream stream = attachmentVO.getFile();
		        file = new DefaultStreamedContent (stream, attachmentVO.getType(), attachmentVO.getName());
				break;
			}
		}
		return file;
	}
	
	/**
	 * Deletes an attachment.
	 * 
	 * @param attachmentId the attachment id.
	 */
	public void doAttachmentDelete (Integer attachmentId) {
		logger.debug ("Starting doAttachmentDelete");
		try {
			AttachmentVO attachmentVO = new AttachmentVO ();
			attachmentVO.setAttachmentId (attachmentId);
			
			CourseVO courseVO = new CourseVO ();
			courseVO.setCourseId (courseIdForm);
			
			classService.deleteAttachment (attachmentVO, courseVO);			
			
			ClassVO classVO = new ClassVO ();
			classVO.setClassId (classIdForm);			
			this.findAttachmentsByClassId (classVO);			
		} catch (BusinessException e) {
			String error = "An error occurred while deleting the attachment. " + e.getMessage();
			logger.error (error);
		}
	}
		
	/**
	 * Finds the class average time.
	 */
	public void findClassAverageTime () {
		try {
			ClassVO classVO = new ClassVO();			
			CourseVO courseVO = new CourseVO();
			courseVO.setCourseId (courseIdForm);			
			classVO.setCourseVO (courseVO);			
			this.averageTimeForm = classService.calculateAverageTime (classVO);			
		} catch (BusinessException e) {
			String error = "An error occurred while find the class average time. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Sets the selected tools.
	 */
	private void setSelectedTools (ClassPlanVO classPlanVO) {
		logger.debug ("Starting setSelectedTools.");
		List<String> selectedToolList = new ArrayList<String> (); 
		try {
			List<ToolVO> toolVOList = toolService.findAllWithSelected (classPlanVO);
			logger.debug ("toolVOList.size(): " + toolVOList.size());			
			for (ToolVO toolVO : toolVOList) {
				if (toolVO.isSelected()) {
					selectedToolList.add (toolVO.getToolId().toString());
				}
			}		
			if (selectedToolList.size() > 0) {
				this.selectedToolArr = selectedToolList.toArray (new String[selectedToolList.size()]);
			}
			logger.debug ("selectedToolList.size() [" + selectedToolList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find all the tools with selected field. " + e.getMessage();
			logger.error (error);
		}
		logger.debug ("Finishing setSelectedTools.");
	}
	
	/**
	 * Gets a list of classPlanTool, given an array of toolId.
	 * 
	 * @param toolIdArr the array of toolId
	 * @return a list of classPlanToolVO.
	 */
	private List<ClassPlanToolVO>getClassPlanToolVOList (String[] toolIdArr) {
		List<ClassPlanToolVO> classPlanToolVOList = new ArrayList<ClassPlanToolVO>();
		for (String toolId : toolIdArr) {
			ToolVO toolVO = new ToolVO();
			toolVO.setToolId (new Integer (toolId));		
			ClassPlanToolVO classPlanToolVO = new ClassPlanToolVO ();
			classPlanToolVO.setToolVO (toolVO);
			classPlanToolVOList.add (classPlanToolVO);
		}
		return classPlanToolVOList;
	}
	
	/**
	 * Finds all the attachments by class id.
	 */
	private void findAttachmentsByClassId (ClassVO classVO) {
		attachmentVOList = new ArrayList<AttachmentVO>();
		try {
			attachmentVOList = classService.findAttachmentsByClass (classVO);
			logger.debug ("attachmentVOList.size() [" + attachmentVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find the attachments by class id. " + e.getMessage();
			logger.error (error);
		}
	}	
	
	/**
	 * Finds all the slides by class id.
	 */
	private void findSlidesByClassId (ClassVO classVO) {
		slideVOList = new ArrayList<SlideVO>();
		try {
			slideVOList = classService.findSlidesByClassId (classVO);
			logger.debug ("slideVOList.size() [" + slideVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find the slides by class id. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Finds all the exercises by class id.
	 */
	private void findExercisesByClassId (ClassVO classVO) {
		exerciseVOList = new ArrayList<ExerciseVO>();
		try {
			exerciseVOList = exerciseService.findExercisesByClassId (classVO);
			logger.debug ("exerciseVOList.size() [" + exerciseVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find the exercises by class id. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Checks if at least one tool is selected.
	 * 
	 * @return the operation result.
	 */
	private boolean isEmptyTools() {
		boolean isEmpty = true;
		if (this.selectedToolArr != null && this.selectedToolArr.length > 0) {
			isEmpty = false;
		}
		return isEmpty;
	}
	
	/**
	 * Checks if the slide list is empty.
	 * 
	 * @return the operation result.
	 */
	private boolean isEmptySlides () {
		boolean isEmpty = false;
		if (this.classIdForm != null
				&& this.classIdForm > 0
				&& this.isSlideEnabled ()
				&& (this.slideVOList == null || this.slideVOList.size() == 0)) {
			isEmpty = true;
		}
		return isEmpty;
	}
	
	/**
	 * Checks if the exercise type 3 is checked and it exists on list.
	 * 
	 * @return the operation result.
	 */
	private boolean isEmptyExerciseType3 () {
		boolean isEmpty = false;
		if (this.classIdForm != null
				&& this.classIdForm > 0
				&& this.isExerciseChecked ("3")
				&& (this.exerciseVOList == null 
						|| this.exerciseVOList.size() == 0
						|| !existsExerciseByType (3))) {
			isEmpty = true;
		}
		return isEmpty;
	}
	
	/**
	 * Checks if the exercise type 4 is checked and it exists on list.
	 * 
	 * @return the operation result.
	 */
	private boolean isEmptyExerciseType4 () {
		boolean isEmpty = false;
		if (this.classIdForm != null
				&& this.classIdForm > 0
				&& this.isExerciseChecked ("4")
				&& (this.exerciseVOList == null 
						|| this.exerciseVOList.size() == 0
						|| !existsExerciseByType (4))) {
			isEmpty = true;
		}
		return isEmpty;
	}	
	
	/**
	 * Checks if an slide is checked or not.
	 * 
	 * @return the operation result.
	 */
	private boolean isSlideChecked (String type) {
		boolean isChecked = false;
		if (this.selectedToolArr != null && this.selectedToolArr.length > 0) {
			for (String toolId : this.selectedToolArr) {
				if (toolId.equals (type)) {
					isChecked = true;
					break;
				}
			}
		}
		return isChecked;
	}
	
	/**
	 * Checks if an exercise is checked or not.
	 * 
	 * @return the operation result.
	 */
	private boolean isExerciseChecked (String type) {
		boolean isChecked = false;
		if (this.selectedToolArr != null && this.selectedToolArr.length > 0) {
			for (String toolId : this.selectedToolArr) {
				if (toolId.equals (type)) {
					isChecked = true;
					break;
				}
			}
		}
		return isChecked;
	}
	
	/**
	 * Checks if exists an exercise by its type.
	 * 
	 * @return the operation result.
	 */
	private boolean existsExerciseByType (Integer type) {
		boolean exists = false;
		if (this.exerciseVOList != null && this.exerciseVOList.size() > 0) {
			for (ExerciseVO exerciseVO : this.exerciseVOList) {
				if (exerciseVO.getExerciseType() == type) {
					exists = true;
					break;					
				}
			}
		}
		return exists;
	}
	
	/**
	 * Validates the class insert/update form.
	 * 
	 */
	private boolean isValidForm () {
		if (!Utils.isNonEmpty (this.nameForm)
				|| !Utils.isNonEmpty (this.descriptionForm)
				|| this.averageTimeForm == null
				|| this.averageTimeForm == 0
				|| !Utils.isNonEmpty (this.skillsForm)
				|| contentItemIdForm == null
				|| contentItemIdForm == 0) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " Preencha os campos corretamente.");
			return false;
		} else if (isEmptyTools()) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " Selecione pelo menos uma Ferramenta.");
			return false;
		} else if (isEmptySlides ()) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " É necessário adicionar pelo menos um Slide.");
			return false;
		} else if (isEmptyExerciseType3 ()) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " É necessário adicionar pelo menos um Exercício.");
			return false;
		} else if (isEmptyExerciseType4 ()) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " É necessário adicionar pelo menos um Desafio.");
			return false;
		} else if (!this.isSlideChecked ("1")
						&& !this.isSlideChecked ("2")
						&& this.slideVOList != null
						&& this.slideVOList.size() > 0) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " Selecione pelo menos uma ferramenta (Slide ou Videoaula).");
			return false;
		} else if (!this.isExerciseChecked ("3") 
						&& this.existsExerciseByType (3)) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " Selecione a Ferramenta Exercício.");
			return false;
		} else if (!this.isExerciseChecked ("4") 
				&& this.existsExerciseByType (4)) {
			super.addWarnMessage ("formUpdateClass:messagesUpdateClass", " Selecione a Ferramenta Desafio.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {		
		this.classIdForm = null;
    	
		this.classPlanIdForm = null;
		this.selectedToolArr = null;
    	this.averageTimeForm = null;
    	this.skillsForm = null;
		
    	this.nameForm = null;
    	this.descriptionForm = null;
    	this.notesForm = null;
    	
    	this.slideVOList = null;
    	this.exerciseVOList = null;
    	this.attachmentVOList = null;
    	
    	this.contentItemIdForm = null;
    	this.contentItemNumerationForm = null;
    	this.contentItemNameForm = null;
    	
    	this.isFileUploaded = false;
	}
	
}
