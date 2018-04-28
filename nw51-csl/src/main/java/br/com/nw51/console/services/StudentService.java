package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.StudentFilterVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.StudentDAO;

/**
 * Business Service class for Student.
 * 
 * @author Josivan Silva
 *
 */
public class StudentService {

	static Logger logger = Logger.getLogger (StudentService.class.getName());
	private StudentDAO studentDAO = new StudentDAO();
		
	public StudentService() {
		
	}
	
	/**
	 * Inserts a new student.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int insert (StudentVO studentVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = studentDAO.insert (studentVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the student. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Updates a student.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (StudentVO studentVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = studentDAO.update (studentVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the student. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Updates a student status.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updateStatus (StudentVO studentVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = studentDAO.updateStatus (studentVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the student status. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Deletes a student.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (StudentVO studentVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = studentDAO.delete (studentVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the student. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds a student by its id.
	 * 
	 * @param StudentVO the student.
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
	
	/**
	 * Finds a duplicated student email.
	 * 
	 * @param studentVO the student.
	 * @return the count of duplicated student email.
	 * @throws BusinessException
	 */
	public int findDuplicatedEmail (StudentVO studentVO) throws BusinessException {
		int count = 0;
		try {
			count = studentDAO.findDuplicatedEmail (studentVO);
			logger.debug ("count [" + count + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the a duplicated email. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
	/**
	 * Finds a duplicated student cpf.
	 * 
	 * @param studentVO the student.
	 * @return the count of duplicated student cpf.
	 * @throws BusinessException
	 */
	public int findDuplicatedCpf (StudentVO studentVO) throws BusinessException {
		int count = 0;
		try {
			count = studentDAO.findDuplicatedCpf (studentVO);
			logger.debug ("count [" + count + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the a duplicated cpf. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
	/**
	 * Finds the students by filter.
	 * 
	 * @return a list of students.
	 * @throws BusinessException
	 */
	public List<StudentVO> findByFilter (StudentFilterVO studentFilterVO) throws BusinessException {
		List<StudentVO> studentVOList = null;
		try {
			studentVOList = studentDAO.findByFilter (studentFilterVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the students by filter. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return studentVOList;
	}
	
	/**
	 * Gets the row count of students.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = studentDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count of students. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
}
