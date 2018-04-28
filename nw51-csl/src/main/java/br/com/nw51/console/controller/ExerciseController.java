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

import br.com.nw51.common.util.ExerciseTypeEnum;
import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.ExerciseVO;
import br.com.nw51.common.vo.QuestionVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.ExerciseService;
import br.com.nw51.console.services.QuestionService;

/**
 * Exercise Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@RequestScoped
public class ExerciseController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (ExerciseController.class.getName());
	private ExerciseService exerciseService = new ExerciseService();
	private QuestionService questionService = new QuestionService();
	
	private Integer exerciseIdForm;
	private Integer classIdForm;
	private Integer exerciseTypeForm;
	private String nameForm;
		
	private List<QuestionVO> questionVOList = new ArrayList<QuestionVO>();
	
	private Map<String,Object> exerciseTypeMap;
	
	public Integer getExerciseIdForm() {
		return exerciseIdForm;
	}

	public void setExerciseIdForm(Integer exerciseIdForm) {
		this.exerciseIdForm = exerciseIdForm;
	}

	public Integer getClassIdForm() {
		return classIdForm;
	}

	public void setClassIdForm(Integer classIdForm) {
		this.classIdForm = classIdForm;
	}

	public Integer getExerciseTypeForm() {
		return exerciseTypeForm;
	}

	public void setExerciseTypeForm(Integer exerciseTypeForm) {
		this.exerciseTypeForm = exerciseTypeForm;
	}

	public String getNameForm() {
		return nameForm;
	}

	public void setNameForm(String nameForm) {
		this.nameForm = nameForm;
	}

	public List<QuestionVO> getQuestionVOList() {
		return questionVOList;
	}

	public void setQuestionVOList(List<QuestionVO> questionVOList) {
		this.questionVOList = questionVOList;
	}

	public Map<String, Object> getExerciseTypeMap() {
		return exerciseTypeMap;
	}

	public void setExerciseTypeMap(Map<String, Object> exerciseTypeMap) {
		this.exerciseTypeMap = exerciseTypeMap;
	}

	@PostConstruct
    public void init() {
		initExerciseTypeSelect();
    }	
	
	/**
	 * Redirects to class page.
	 */
	public String backToClass() {
		String toPage = "updateClass?id=" + this.classIdForm + "&faces-redirect=true";
	    return toPage;
	}
	
	/**
	 * Redirects to add question page.
	 */
	public String goToAddQuestion() {
		String toPage = "updateQuestion?exerciseId=" + this.exerciseIdForm + "&faces-redirect=true";
		logger.debug ("Starting goToAddQuestion.");
	    logger.debug ("exerciseIdForm: " + this.exerciseIdForm);
	    if (this.exerciseIdForm == null || this.exerciseIdForm == 0) {
			super.addWarnMessage ("formUpdateExercise:messagesUpdateExercise", " É necessário salvar o Exercício antes de adicionar uma Questão.");
			return null;
	    }
	    return toPage;
	}
	
	/**
	 * Performs the exercise save operation.
	 */
	public void save (ActionEvent actionEvent) {
		if (isValidForm ()) { 
			ExerciseVO exerciseVO = new ExerciseVO ();
			exerciseVO.setExerciseId (exerciseIdForm);
			
			ClassVO classVO = new ClassVO();
			classVO.setClassId (classIdForm);
						
			exerciseVO.setClassVO (classVO);
			exerciseVO.setExerciseType (exerciseTypeForm);
			exerciseVO.setName (nameForm);

			try {
				if (this.exerciseIdForm != null && this.exerciseIdForm > 0) {
					int affectedRows = exerciseService.update (exerciseVO);
					if (affectedRows > 0) {
						super.addInfoMessage ("formUpdateExercise:messagesUpdateExercise", " Exercício atualizado com sucesso.");
						logger.info ("The exercise [" + exerciseVO.getName() + "] has been successfully updated.");						
					}
				} else {					
					this.exerciseIdForm = exerciseService.insert (exerciseVO);
					if (this.exerciseIdForm > 0) {
						super.addInfoMessage ("formUpdateExercise:messagesUpdateExercise", " Exercício adicionado com sucesso.");
						logger.info ("The exercise [" + exerciseVO.getName() + "] has been successfully inserted.");
					}
				}
				this.resetForm();
			} catch (BusinessException e) {
				String error = "An error occurred while saving the exercise. " + e.getMessage();
				logger.error (error);
			}
			
		}		
	}
	
	/**
	 * Performs the exercise delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		logger.debug ("Starting delete");
		if (this.exerciseIdForm != null && this.exerciseIdForm > 0) {
			ExerciseVO exerciseVO = new ExerciseVO ();
			exerciseVO.setExerciseId (exerciseIdForm);
			
			ClassVO classVO = new ClassVO();
			classVO.setClassId (classIdForm);
			
			exerciseVO.setClassVO (classVO);
			
			try {
				int affectedRows = exerciseService.delete (exerciseVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateExercise:messagesUpdateExercise", " Exercício excluído com sucesso.");
					logger.info ("The exercise [" + exerciseVO.getName() + "] has been successfully deleted.");
				}
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the exercise. " + e.getMessage();
				logger.error (error);
			}
		}
		this.resetForm();
	}	
	
	/**
	 * Loads an exercise given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById.");
		ExerciseVO foundExerciseVO = null;
		if (this.exerciseIdForm != null && this.exerciseIdForm > 0) {
			ExerciseVO exerciseVO = new ExerciseVO ();
			exerciseVO.setExerciseId (exerciseIdForm);
	        try {
	        	foundExerciseVO = this.exerciseService.findById (exerciseVO);
	        	
	        	this.classIdForm = foundExerciseVO.getClassVO().getClassId();
	        		        		        	
	        	this.exerciseTypeForm = foundExerciseVO.getExerciseType();
	        	this.nameForm = foundExerciseVO.getName();
	        		        	
	        	findQuestionsByExerciseId (foundExerciseVO);
				
				logger.debug ("foundExerciseVO.getName() " + foundExerciseVO.getName());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the exercise by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Finds all the questions by exercise id.
	 */
	private void findQuestionsByExerciseId (ExerciseVO exerciseVO) {
		questionVOList = new ArrayList<QuestionVO>();
		try {
			questionVOList = questionService.findQuestionsByExerciseId (exerciseVO);
			logger.debug ("questionVOList.size() [" + questionVOList.size() + "]");
		} catch (BusinessException e) {
			String error = "An error occurred while find the questions by exercise id. " + e.getMessage();
			logger.error (error);
		}
	}
	
	/**
	 * Validates the exercise insert/update form.
	 * 
	 */
	private boolean isValidForm () {						
		if (!Utils.isNonEmpty (this.nameForm)
				|| this.exerciseTypeForm == null
				|| this.exerciseTypeForm == 0) {			
			super.addWarnMessage ("formUpdateExercise:messagesUpdateExercise", " Preencha os campos corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {		
		this.exerciseIdForm   = null;
		this.nameForm         = null;
    	this.exerciseTypeForm = null;
    	this.questionVOList   = null;
	}
	
	/**
	 * Initializes the exerciseType select.
	 */
	private void initExerciseTypeSelect() {
		this.exerciseTypeMap = new LinkedHashMap<String,Object>();
		exerciseTypeMap.put ("", ""); //label, value
		for (ExerciseTypeEnum exerciseTypeEnum : ExerciseTypeEnum.values()) {
			exerciseTypeMap.put (exerciseTypeEnum.getName(), exerciseTypeEnum.getId());
		}
	}
	
}
