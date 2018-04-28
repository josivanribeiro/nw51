package br.com.nw51.console.services;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.SlideVO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.SlideDAO;

/**
 * Business Service class for Slide.
 * 
 * @author Josivan Silva
 *
 */
public class SlideService {

	static Logger logger = Logger.getLogger (SlideService.class.getName());
	
	private SlideDAO slideDAO = new SlideDAO();
	
	private ClassService classService = new ClassService();
			
	public SlideService() {
		
	}
	
	/**
	 * Inserts a new slide.
	 * 
	 * @param slideVO the slide.
	 * @return the slide id.
	 * @throws BusinessException
	 */
	public Integer insert (SlideVO slideVO) throws BusinessException {
		Integer newSlideId = 0;
		try {			
			Integer sequenceNumber = classService.findNextSequenceNumber();
			slideVO.setSequenceNumber (sequenceNumber);			
			
			newSlideId = slideDAO.insert (slideVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the slide. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return newSlideId;
	}
	
	/**
	 * Updates a slide.
	 * 
	 * @param slideVO the slide.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (SlideVO slideVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = slideDAO.update (slideVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the slide. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Deletes a slide.
	 * 
	 * @param slideVO the slide.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (SlideVO slideVO) throws BusinessException {
		int affectedRows = 0;
		try {	
			affectedRows = slideDAO.delete (slideVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the slide. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds an slide by its id.
	 * 
	 * @param SlideVO the slide.
	 * @return the found slide.
	 * @throws BusinessException
	 */
	public SlideVO findById (SlideVO slideVO) throws BusinessException {
		SlideVO foundSlideVO = null;
		try {
			foundSlideVO = slideDAO.findById (slideVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the slide by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return foundSlideVO;
	}
	
}
