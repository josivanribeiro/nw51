package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.CareerCourseVO;
import br.com.nw51.common.vo.CareerVO;
import br.com.nw51.console.dao.CareerCourseDAO;
import br.com.nw51.console.dao.CareerDAO;
import br.com.nw51.console.dao.DataAccessException;

/**
 * Business Service class for Career.
 * 
 * @author Josivan Silva
 *
 */
public class CareerService {

	static Logger logger = Logger.getLogger (CareerService.class.getName());
	
	private CareerDAO careerDAO = new CareerDAO();
	private CareerCourseDAO careerCourseDAO = new CareerCourseDAO();
			
		
	public CareerService() {
		
	}
	
	/**
	 * Inserts a new career.
	 * 
	 * @param careerVO the career.
	 * @return the career id.
	 * @throws BusinessException
	 */
	public Integer insert (CareerVO careerVO) throws BusinessException {
		Integer newCarrerId = 0;
		try {
			newCarrerId = careerDAO.insert (careerVO);
			logger.debug ("new newCarrerId [" + newCarrerId + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the career. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		try {			
			if (newCarrerId > 0) {
				careerVO.setCareerId (newCarrerId);
				this.updateCareerIdInCareerCourseVOList (careerVO);
				
				for (CareerCourseVO careerCourseVO : careerVO.getCareerCourseVOList()) {
					careerCourseDAO.insert (careerCourseVO);
				}				
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the careerCourses. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return newCarrerId;
	}
		
	
	/**
	 * Updates a career.
	 * 
	 * @param careerVO the career.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (CareerVO careerVO) throws BusinessException {
		int careerAffectedRows = 0;
		try {
			careerAffectedRows = careerDAO.update (careerVO);
			logger.debug ("careerAffectedRows [" + careerAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the career. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		try {
			if (careerAffectedRows > 0) {
				
				careerCourseDAO.deleteByCareer (careerVO);
				
				this.updateCareerIdInCareerCourseVOList (careerVO);
				
				for (CareerCourseVO careerCourseVO : careerVO.getCareerCourseVOList()) {
					careerCourseDAO.insert (careerCourseVO);
				}
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the career/careerCourses. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return careerAffectedRows;
	}
	
	/**
	 * Deletes a career.
	 * 
	 * @param careerVO the career.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (CareerVO careerVO) throws BusinessException {
		int careerAffectedRows = 0;
		try {
			careerAffectedRows = careerDAO.delete (careerVO);
			logger.debug ("careerAffectedRows [" + careerAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the career. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return careerAffectedRows;
	}
	
	/**
	 * Finds a career by its id.
	 * 
	 * @param CareerVO the career.
	 * @return the found career.
	 * @throws BusinessException
	 */
	public CareerVO findById (CareerVO careerVO) throws BusinessException {
		CareerVO foundCareerVO = null;
		try {
			foundCareerVO = careerDAO.findById (careerVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the career by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		try {
			if (foundCareerVO != null) {
				List<CareerCourseVO> careerCourseVOList = careerCourseDAO.findByCareer (foundCareerVO);
				foundCareerVO.setCareerCourseVOList (careerCourseVOList);
			}
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the careerCourses by career id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundCareerVO;
	}
	
	/**
	 * Finds all the careers.
	 * 
	 * @return a list of careers.
	 * @throws BusinessException
	 */
	public List<CareerVO> findAll () throws BusinessException {
		List<CareerVO> careerVOList = null;
		try {
			careerVOList = careerDAO.findAll();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding all the careers. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return careerVOList;
	}
	
	/**
	 * Gets the row count of careers.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = careerDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count of careers. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
	/**
	 * Updates the careerId in the careerCourseVO list.
	 * 
	 * @param careerVO the careerVO.
	 */
	private void updateCareerIdInCareerCourseVOList (CareerVO careerVO) {
		for (CareerCourseVO careerCourseVO : careerVO.getCareerCourseVOList()) {
			careerCourseVO.setCareerVO (careerVO);
		}
	}
	
}
