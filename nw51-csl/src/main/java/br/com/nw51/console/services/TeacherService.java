package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.TeacherVO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.TeacherDAO;

/**
 * Business Service class for Teacher.
 * 
 * @author Josivan Silva
 *
 */
public class TeacherService {

	static Logger logger = Logger.getLogger (TeacherService.class.getName());
	private TeacherDAO teacherDAO = new TeacherDAO();
		
	public TeacherService() {
		
	}
	
	/**
	 * Inserts a new teacher.
	 * 
	 * @param teacherVO the teacher.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public Integer insert (TeacherVO teacherVO) throws BusinessException {
		Integer teacherId = 0;
		try {
			teacherId = teacherDAO.insert (teacherVO);
			logger.debug ("teacherId [" + teacherId + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the teacher. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return teacherId;
	}
	
	/**
	 * Updates a teacher.
	 * 
	 * @param teacherVO the teacher.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (TeacherVO teacherVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = teacherDAO.update (teacherVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the teacher. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Deletes a teacher.
	 * 
	 * @param teacherVO the teacher.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (TeacherVO teacherVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = teacherDAO.delete (teacherVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the teacher. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds a teacher by its id.
	 * 
	 * @param TeacherVO the teacher.
	 * @return the found teacher.
	 * @throws BusinessException
	 */
	public TeacherVO findById (TeacherVO teacherVO) throws BusinessException {
		TeacherVO foundTeacherVO = null;
		try {
			foundTeacherVO = teacherDAO.findById (teacherVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the teacher by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundTeacherVO;
	}
	
	/**
	 * Finds all the teachers.
	 * 
	 * @return a list of teachers.
	 * @throws BusinessException
	 */
	public List<TeacherVO> findAll () throws BusinessException {
		List<TeacherVO> teacherVOList = null;
		try {
			teacherVOList = teacherDAO.findAll ();			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding all the teachers. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return teacherVOList;
	}
	
	/**
	 * Checks if already exist an associated user to the teacher.
	 * 
	 * @param TeacherVO the teacher.
	 * @return the operation result.
	 * @throws BusinessException
	 */
	public boolean hasAssociatedUser (TeacherVO teacherVO) throws BusinessException {
		boolean exists = false;
		try {
			exists = teacherDAO.hasAssociatedUser (teacherVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while checking the teacher associated user. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return exists;
	}
	
	/**
	 * Gets the row count of teachers.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = teacherDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count of teachers. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
}
