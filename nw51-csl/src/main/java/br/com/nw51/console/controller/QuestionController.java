package br.com.nw51.console.controller;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.ExerciseVO;
import br.com.nw51.common.vo.QuestionVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.QuestionService;

/**
 * Question Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@RequestScoped
public class QuestionController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (QuestionController.class.getName());
	private QuestionService questionService = new QuestionService();
	
	private Integer questionIdForm;
	private Integer exerciseIdForm;
	private String enunciationForm;
	private String alternativeAForm;
	private String alternativeBForm;
	private String alternativeCForm;
	private String alternativeDForm;
	private String alternativeEForm;
	private String alternativeCorrectForm;
		
	private Map<String,Object> alternativeMap;
	
	public Integer getQuestionIdForm() {
		return questionIdForm;
	}

	public void setQuestionIdForm(Integer questionIdForm) {
		this.questionIdForm = questionIdForm;
	}

	public Integer getExerciseIdForm() {
		return exerciseIdForm;
	}

	public void setExerciseIdForm(Integer exerciseIdForm) {
		this.exerciseIdForm = exerciseIdForm;
	}

	public String getEnunciationForm() {
		return enunciationForm;
	}

	public void setEnunciationForm(String enunciationForm) {
		this.enunciationForm = enunciationForm;
	}

	public String getAlternativeAForm() {
		return alternativeAForm;
	}

	public void setAlternativeAForm(String alternativeAForm) {
		this.alternativeAForm = alternativeAForm;
	}

	public String getAlternativeBForm() {
		return alternativeBForm;
	}

	public void setAlternativeBForm(String alternativeBForm) {
		this.alternativeBForm = alternativeBForm;
	}

	public String getAlternativeCForm() {
		return alternativeCForm;
	}

	public void setAlternativeCForm(String alternativeCForm) {
		this.alternativeCForm = alternativeCForm;
	}

	public String getAlternativeDForm() {
		return alternativeDForm;
	}

	public void setAlternativeDForm(String alternativeDForm) {
		this.alternativeDForm = alternativeDForm;
	}

	public String getAlternativeEForm() {
		return alternativeEForm;
	}

	public void setAlternativeEForm(String alternativeEForm) {
		this.alternativeEForm = alternativeEForm;
	}

	public String getAlternativeCorrectForm() {
		return alternativeCorrectForm;
	}

	public void setAlternativeCorrectForm(String alternativeCorrectForm) {
		this.alternativeCorrectForm = alternativeCorrectForm;
	}

	public Map<String, Object> getAlternativeMap() {
		return alternativeMap;
	}

	public void setAlternativeMap(Map<String, Object> alternativeMap) {
		this.alternativeMap = alternativeMap;
	}

	@PostConstruct
    public void init() {
		initAlternativeSelect();
    }	
	
	/**
	 * Redirects to exercise page.
	 */
	public String backToExercise() {
		String toPage = "updateExercise?id=" + this.exerciseIdForm + "&faces-redirect=true";
	    return toPage;
	}
	
	/**
	 * Performs the question save operation.
	 */
	public void save (ActionEvent actionEvent) {
		if (isValidForm ()) { 
			QuestionVO questionVO = new QuestionVO ();
			questionVO.setQuestionId (questionIdForm);
			
			ExerciseVO exerciseVO = new ExerciseVO ();
			exerciseVO.setExerciseId (exerciseIdForm);
			
			questionVO.setExerciseVO (exerciseVO);
			questionVO.setEnunciation (enunciationForm);
			questionVO.setAlternativeA (alternativeAForm);
			questionVO.setAlternativeB (alternativeBForm);
			questionVO.setAlternativeC (alternativeCForm);
			questionVO.setAlternativeD (alternativeDForm);
			questionVO.setAlternativeE (alternativeEForm);
			questionVO.setAlternativeCorrect (alternativeCorrectForm);			

			try {
				if (this.questionIdForm != null && this.questionIdForm > 0) {
					int affectedRows = questionService.update (questionVO);
					if (affectedRows > 0) {
						super.addInfoMessage ("formUpdateQuestion:messagesUpdateQuestion", " Questão atualizada com sucesso.");
						logger.info ("The question [" + questionVO.getQuestionId() + "] has been successfully updated.");						
					}
				} else {					
					this.questionIdForm = questionService.insert (questionVO);
					if (this.questionIdForm > 0) {
						super.addInfoMessage ("formUpdateQuestion:messagesUpdateQuestion", " Questão adicionada com sucesso.");
						logger.info ("The question [" + questionVO.getQuestionId() + "] has been successfully inserted.");
					}
				}
				this.resetForm();
			} catch (BusinessException e) {
				String error = "An error occurred while saving the question. " + e.getMessage();
				logger.error (error);
			}
			
		}		
	}
	
	/**
	 * Performs the question delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		logger.debug ("Starting delete.");
		if (this.questionIdForm != null && this.questionIdForm > 0) {
			QuestionVO questionVO = new QuestionVO ();
			questionVO.setQuestionId (questionIdForm);
			try {
				int affectedRows = questionService.delete (questionVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateQuestion:messagesUpdateQuestion", " Questão excluída com sucesso.");
					logger.info ("The question [" + questionVO.getQuestionId() + "] has been successfully deleted.");
				}
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the question. " + e.getMessage();
				logger.error (error);
			}
		}
		this.resetForm();
	}	
	
	/**
	 * Loads a question given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		QuestionVO foundQuestionVO = null;
		if (this.questionIdForm != null && this.questionIdForm > 0) {
			QuestionVO questionVO = new QuestionVO ();
			questionVO.setQuestionId (questionIdForm);
	        try {
	        	foundQuestionVO = this.questionService.findById (questionVO);
	        	
	        	this.exerciseIdForm = foundQuestionVO.getExerciseVO().getExerciseId();
	        	
	        	this.enunciationForm = foundQuestionVO.getEnunciation();
	        	this.alternativeAForm = foundQuestionVO.getAlternativeA();
	        	this.alternativeBForm = foundQuestionVO.getAlternativeB();
	        	this.alternativeCForm = foundQuestionVO.getAlternativeC();
	        	this.alternativeDForm = foundQuestionVO.getAlternativeD();
	        	this.alternativeEForm = foundQuestionVO.getAlternativeE();
	        	this.alternativeCorrectForm = foundQuestionVO.getAlternativeCorrect();
	        				
				logger.info ("foundQuestionVO.getQuestionId() " + foundQuestionVO.getQuestionId());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the question by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Validates the question insert/update form.
	 * 
	 */
	private boolean isValidForm () {						
		if ((this.exerciseIdForm == null 
				|| this.exerciseIdForm == 0)
				|| !Utils.isNonEmpty (this.enunciationForm)
				|| !Utils.isNonEmpty (this.alternativeAForm)
				|| !Utils.isNonEmpty (this.alternativeBForm)
				|| !Utils.isNonEmpty (this.alternativeCForm)
				|| !Utils.isNonEmpty (this.alternativeDForm)
				|| !Utils.isNonEmpty (this.alternativeEForm)
				|| !Utils.isNonEmpty (this.alternativeCorrectForm)) {			
			super.addWarnMessage ("formUpdateQuestion:messagesUpdateQuestion", " Preencha os campos corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {		
		this.questionIdForm = null;
		this.exerciseIdForm = null;
		this.enunciationForm = null;
    	this.alternativeAForm = null;
    	this.alternativeBForm = null;
    	this.alternativeCForm = null;
    	this.alternativeDForm = null;
    	this.alternativeEForm = null;
    	this.alternativeCorrectForm = null;		
	}
	
	/**
	 * Initializes the alternative select.
	 */
	private void initAlternativeSelect() {
		this.alternativeMap = new LinkedHashMap<String,Object>();
		alternativeMap.put ("", ""); //label, value
		alternativeMap.put ("A", "A");
		alternativeMap.put ("B", "B");
		alternativeMap.put ("C", "C");
		alternativeMap.put ("D", "D");
		alternativeMap.put ("E", "E");		
	}
	
}
