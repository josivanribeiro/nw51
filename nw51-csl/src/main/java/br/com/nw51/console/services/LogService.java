package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.LogFilterVO;
import br.com.nw51.common.vo.LogVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.LogDAO;
import br.com.nw51.console.util.Constants;

/**
 * Business Service class for Log.
 * 
 * @author Josivan Silva
 *
 */
public class LogService {

	static Logger logger = Logger.getLogger (LogService.class.getName());
	private LogDAO logDAO = new LogDAO();
		
	public LogService() {
		
	}
	
	/**
	 * Inserts a success log entry.
	 * 
	 * @param studentId the student id.
	 * @param operation the operation name.
	 * @param input the input (JSON)
	 * @param output the output (JSON)
	 * @param message the message.
	 * @return the new inserted log id.
	 * @throws BusinessException
	 */
	public long success (Long studentId, String operation, String input, String output, String message) throws BusinessException {
		long newLogId = 0;
		try {
			LogVO logVO = new LogVO();
			
			if (studentId != null && studentId > 0) {
				StudentVO studentVO = new StudentVO();
				studentVO.setStudentId (studentId);
				logVO.setStudentVO (studentVO);
			}
			
			logVO.setOperation (operation);
			logVO.setInput (input);
			logVO.setOutput (output);
			logVO.setMessage (message);
			logVO.setStatus (Constants.LOG_STATUS_SUCCESS);
						
			newLogId = logDAO.insert (logVO);
			logger.debug ("newLogId [" + newLogId + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the success log entry. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return newLogId;
	}
	
	/**
	 * Inserts an error log entry.
	 * 
	 * @param studentId the student id.
	 * @param operation the operation name.
	 * @param input the input (JSON)
	 * @param output the output (JSON)
	 * @param message the message.
	 * @return the new inserted log id.
	 * @throws BusinessException
	 */
	public long error (Long studentId, String operation, String input, String output, String error, String message) throws BusinessException {
		long newLogId = 0;
		try {
			LogVO logVO = new LogVO();
			
			if (studentId != null && studentId > 0) {
				StudentVO studentVO = new StudentVO();
				studentVO.setStudentId (studentId);
				logVO.setStudentVO (studentVO);
			}
			
			logVO.setOperation (operation);
			logVO.setInput (input);
			logVO.setOutput (output);
			logVO.setMessage (message);
			logVO.setError (error);
			logVO.setStatus (Constants.LOG_STATUS_ERROR);
						
			newLogId = logDAO.insert (logVO);
			logger.debug ("newLogId [" + newLogId + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the error log entry. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return newLogId;
	}
		
	/**
	 * Finds a log by its id.
	 * 
	 * @param LogVO the log.
	 * @return the found log.
	 * @throws BusinessException
	 */
	public LogVO findById (LogVO logVO) throws BusinessException {
		LogVO foundLogVO = null;
		try {
			foundLogVO = logDAO.findById (logVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the log by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundLogVO;
	}
	
	/**
	 * Finds the logs by filter.
	 * 
	 * @return a list of logs.
	 * @throws BusinessException
	 */
	public List<LogVO> findByFilter (LogFilterVO logFilterVO) throws BusinessException {
		List<LogVO> logVOList = null;
		try {
			logVOList = logDAO.findByFilter (logFilterVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the logs by filter. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return logVOList;
	}
	
	/**
	 * Gets the row count of logs.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = logDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count of logs. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
}
