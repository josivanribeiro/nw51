package br.com.nw51.student.services;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.student.dao.AddressDAO;
import br.com.nw51.student.dao.DataAccessException;
import br.com.nw51.student.dao.StudentDAO;

/**
 * Business Service class for Student.
 * 
 * @author Josivan Silva
 *
 */
public class StudentService {

	static Logger logger = Logger.getLogger (StudentService.class.getName());	
	private StudentDAO studentDAO = new StudentDAO();
	private AddressDAO addressDAO = new AddressDAO();
	
	public StudentService() {
		
    }
	
	/**
	 * Updates the student.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (StudentVO studentVO) throws BusinessException {
		int studentAffectedRows = 0;
		int addressAffectedRows = 0;
		try {
			studentAffectedRows = studentDAO.update (studentVO);			
			if (studentAffectedRows > 0) {
				addressAffectedRows = addressDAO.update (studentVO.getAddressVO());
			}			
			logger.debug ("studentAffectedRows [" + studentAffectedRows + "]");
			logger.debug ("addressAffectedRows [" + addressAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the student and address. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return studentAffectedRows;
	}
	
	/**
	 * Performs the student login.
	 * 
	 * @param studentVO the student.
	 * @return a the result of the login operation.
	 * @throws BusinessException
	 */
	public boolean doLogin (StudentVO studentVO) throws BusinessException {
		boolean isLogged = false;
		try {
			isLogged = studentDAO.doLogin (studentVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while performing the login. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return isLogged;
	}	
	
	/**
	 * Updates the student state.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updateState (StudentVO studentVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = studentDAO.updateState (studentVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the student state. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Updates the student login attempts.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updateLoginAttempts (StudentVO studentVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = studentDAO.updateLoginAttempts (studentVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the student login attempts. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds a student by its email.
	 * 
	 * @param studentVO the student.
	 * @return the found student.
	 * @throws BusinessException
	 */
	public StudentVO findByEmail (StudentVO studentVO) throws BusinessException {
		StudentVO foundStudentVO = null;
		try {
			foundStudentVO = studentDAO.findByEmail (studentVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the student by email. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundStudentVO;
	}
	
	/**
	 * Finds a student by its id.
	 * 
	 * @param studentVO the student.
	 * @return the found student.
	 * @throws BusinessException
	 */
	public StudentVO findById (StudentVO studentVO) throws BusinessException {
		StudentVO foundStudentVO = null;
		try {
			foundStudentVO = studentDAO.findById (studentVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the student by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundStudentVO;
	}
	
}
