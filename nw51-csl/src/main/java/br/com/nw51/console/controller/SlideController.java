package br.com.nw51.console.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.SlideVO;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.SlideService;

/**
 * Slide Controller.
 * 
 * @author Josivan Silva
 *
 */
@ManagedBean
@RequestScoped
public class SlideController extends AbstractController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static Logger logger = Logger.getLogger (SlideController.class.getName());
	private SlideService slideService = new SlideService();
	
	private Integer slideIdForm;
	private Integer classIdForm;
	private String nameForm;
	private String contentForm;
	private boolean statusForm = true;
		
	public Integer getSlideIdForm() {
		return slideIdForm;
	}

	public void setSlideIdForm(Integer slideIdForm) {
		this.slideIdForm = slideIdForm;
	}

	public Integer getClassIdForm() {
		return classIdForm;
	}

	public void setClassIdForm(Integer classIdForm) {
		this.classIdForm = classIdForm;
	}

	public String getNameForm() {
		return nameForm;
	}

	public void setNameForm(String nameForm) {
		this.nameForm = nameForm;
	}

	public String getContentForm() {
		return contentForm;
	}

	public void setContentForm(String contentForm) {
		this.contentForm = contentForm;
	}

	public boolean getStatusForm() {
		return statusForm;
	}

	public void setStatusForm(boolean statusForm) {
		this.statusForm = statusForm;
	}

	@PostConstruct
    public void init() {
		
    }	
	
	/**
	 * Redirects to class page.
	 */
	public String backToClass() {
		String toPage = "updateClass?id=" + this.classIdForm + "&faces-redirect=true";
	    return toPage;
	}
	
	/**
	 * Performs the slide save operation.
	 */
	public void save (ActionEvent actionEvent) {
		if (isValidForm ()) { 
			SlideVO slideVO = new SlideVO ();
			slideVO.setSlideId (slideIdForm);
			
			ClassVO classVO = new ClassVO ();
			classVO.setClassId (classIdForm);
			
			slideVO.setClassVO (classVO);
			slideVO.setName (nameForm);
			slideVO.setContent (contentForm);
			int status = (this.statusForm) ? 1 : 0; 
			slideVO.setStatus (status);
			try {
				if (this.slideIdForm != null && this.slideIdForm > 0) {
					int affectedRows = slideService.update (slideVO);
					if (affectedRows > 0) {
						super.addInfoMessage ("formUpdateSlide:messagesUpdateSlide", " Slide atualizado com sucesso.");
						logger.info ("The slide [" + slideVO.getSlideId() + "] has been successfully updated.");
					}
				} else {					
					this.slideIdForm = slideService.insert (slideVO);
					if (this.slideIdForm > 0) {
						super.addInfoMessage ("formUpdateSlide:messagesUpdateSlide", " Slide adicionado com sucesso.");
						logger.info ("The slide [" + slideVO.getSlideId() + "] has been successfully inserted.");
					}
				}
				this.resetForm();
			} catch (BusinessException e) {
				String error = "An error occurred while saving the slide. " + e.getMessage();
				logger.error (error);
			}
		}
	}
	
	/**
	 * Performs the slide delete operation.
	 */
	public void delete (ActionEvent actionEvent) {
		logger.debug ("Starting delete");
		if (this.slideIdForm != null && this.slideIdForm > 0) {
			SlideVO slideVO = new SlideVO ();
			slideVO.setSlideId (slideIdForm);
			try {
				int affectedRows = slideService.delete (slideVO);
				if (affectedRows > 0) {
					super.addInfoMessage ("formUpdateSlide:messagesUpdateSlide", " Slide excluído com sucesso.");
					logger.info ("The slide with id [" + slideVO.getSlideId() + "] has been successfully deleted.");
				}
			} catch (BusinessException e) {
				String error = "An error occurred while deleting the slide. " + e.getMessage();
				logger.error (error);
			}
		}
		this.resetForm();
	}	
	
	/**
	 * Loads a slide given its id.
	 */
	public void loadById () {
		logger.debug ("Starting loadById");
		SlideVO foundSlideVO = null;
		if (this.slideIdForm != null && this.slideIdForm > 0) {
			SlideVO slideVO = new SlideVO ();
			slideVO.setSlideId (slideIdForm);
	        try {
	        	foundSlideVO = this.slideService.findById (slideVO);
	        	
	        	this.slideIdForm = foundSlideVO.getSlideId();
	        	this.classIdForm = foundSlideVO.getClassVO().getClassId(); 
				this.nameForm = foundSlideVO.getName();
				this.contentForm = foundSlideVO.getContent();
				this.statusForm = (foundSlideVO.getStatus() == 1) ? true : false;	        	
	        				
				logger.debug ("foundSlideVO.getSlideId(): " + foundSlideVO.getSlideId());
			} catch (BusinessException e) {
				String error = "An error occurred while finding the slide by id. " + e.getMessage();
				logger.error (error);
			}
		}
    }
	
	/**
	 * Validates the slide insert/update form.
	 * 
	 */
	private boolean isValidForm () {
		if ((this.classIdForm == null || this.classIdForm == 0)
				|| !Utils.isNonEmpty (this.nameForm)
				|| !Utils.isNonEmpty (this.contentForm)) {
			super.addWarnMessage ("formUpdateSlide:messagesUpdateSlide", " Preencha os campos corretamente.");
			return false;
		}
		return true;
    }
	
	/**
	 * Resets the insert/update form.
	 */
	private void resetForm () {		
		this.slideIdForm = null;
    	this.nameForm    = null;
    	this.contentForm = null;
    	this.statusForm  = true;
	}
	
}
