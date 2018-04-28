package br.com.nw51.console.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.LogFilterVO;
import br.com.nw51.common.vo.LogVO;
import br.com.nw51.common.vo.StudentVO;

/**
 * DAO class for Log.
 * 
 * @author Josivan Silva
 *
 */
public class LogDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (LogDAO.class.getName());
	
	public LogDAO () {
		
	}
		
	/**
	 * Inserts a new log.
	 * 
	 * @param logVO the log.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public long insert (LogVO logVO) throws DataAccessException {
		long id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO LOG (STUDENT_ID, OPERATION, INPUT, OUTPUT, ERROR, MESSAGE, LOG_TIME, STATUS) ");
		sbSql.append ("VALUES (" +  logVO.getStudentVO().getStudentId() + ", ");
		sbSql.append ("'" + logVO.getOperation() + "', ");
		sbSql.append ("'" +  logVO.getInput() + "', ");
		sbSql.append ("'" +  logVO.getOutput() + "', ");
		sbSql.append ("'" +  logVO.getError() + "', ");
		sbSql.append ("'" +  logVO.getMessage() + "', ");
		sbSql.append ("CURRENT_TIMESTAMP(), ");
		sbSql.append ("'" +  logVO.getStatus() + "')");
		
		logger.debug ("sql: " + sbSql.toString());
		
		id = insertDbWithLongKey (sbSql.toString());
		return id;
	}
		
	/**
	 * Find a Log by its id.
	 * 
	 * @param logVO the log.
	 * @return a log.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public LogVO findById (LogVO logVO) throws DataAccessException {
		LogVO foundLogVO     = null;
		String sql           = null;
		List<Object> rowList = null;
		
		sql = "SELECT L.LOG_ID, L.STUDENT_ID, S.FULL_NAME, L.OPERATION, L.MESSAGE, L.INPUT, L.OUTPUT, L.ERROR, L.LOG_TIME, L.STATUS "
			+ "FROM LOG L "
			+ "LEFT OUTER JOIN STUDENT S ON S.STUDENT_ID = L.STUDENT_ID "
			+ "WHERE L.LOG_ID = " + logVO.getLogId();
		
		logger.debug ("sql: " + sql.toString());
		
		rowList = selectDb (sql, 10);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns      = (List<Object>)columnList;			
				Long logId         = (Long) columns.get (0);
				Long studentId     = (Long) columns.get (1);
				String studentName = (String) columns.get (2);
				String operation  = (String) columns.get (3);
				String message    = (String) columns.get (4);
				String input      = (String) columns.get (5);
				String output     = (String) columns.get (6);
				String error      = (String) columns.get (7);
				Timestamp logTime = (Timestamp) columns.get (8);
				int status        = (Integer) columns.get (9);
								
				foundLogVO = new LogVO();
				foundLogVO.setLogId (logId);
				
				if (studentId != null && studentId > 0) {
					StudentVO studentVO = new StudentVO();
					studentVO.setStudentId (studentId);
					studentVO.setFullName (studentName);
					foundLogVO.setStudentVO (studentVO);
				}
				
				foundLogVO.setOperation (operation);
				foundLogVO.setInput (input);
				foundLogVO.setOutput (output);
				foundLogVO.setError (error);
				foundLogVO.setMessage (message);
				foundLogVO.setLogTime (new Date(logTime.getTime()));
				foundLogVO.setStatus (status);
			}
		}
		return foundLogVO;
	}
	
	/**
	 * Finds the logs by filter.
	 * 
	 * @return a list of logs.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<LogVO> findByFilter (LogFilterVO filter) throws DataAccessException {
		String sql             = null;
		List<Object> rowList   = null;
		List<LogVO> logList    = new ArrayList<LogVO>();
		String whereClause     = "";
		boolean hasWhereClause = false;
		if (Utils.isNonEmpty (filter.getOperation())) {
			whereClause = "WHERE L.OPERATION LIKE '%" + filter.getOperation() + "%' ";
			hasWhereClause = true;
		}
		if (Utils.isNonEmpty (filter.getCpf())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "S.CPF = '" + filter.getCpf() + "' ";
		}
		if (Utils.isNonEmpty (filter.getStartDate()) 
				&& Utils.isNonEmpty (filter.getEndDate())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "L.LOG_TIME >= STR_TO_DATE ('" + filter.getStartDate() + " 00:00:00', '%d/%m/%Y %H:%i:%s') AND L.LOG_TIME <= STR_TO_DATE ('" + filter.getEndDate() + " 23:59:59', '%d/%m/%Y %H:%i:%s')";
		}
		if (filter.getStatus() != -1) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "L.STATUS = " + filter.getStatus();
		}
		sql = "SELECT L.LOG_ID, L.OPERATION, S.FULL_NAME, L.MESSAGE, L.ERROR, L.LOG_TIME, L.STATUS "
			+ "FROM LOG L "
			+ "LEFT OUTER JOIN STUDENT S ON S.STUDENT_ID = L.STUDENT_ID "
			+ whereClause + " ORDER BY L.LOG_ID";
				  
		logger.debug ("sql: " + sql);
				
		rowList = selectDb (sql, 7);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;
				Long logId         = (Long) columns.get (0);
				String operation   = (String) columns.get (1);
				String studentName = (String) columns.get (2);
				String message     = (String) columns.get (3);
				String error       = (String) columns.get (4);
				Timestamp logTime  = (Timestamp) columns.get (5);
				int status         = (Integer) columns.get (6);
				
				LogVO logVO = new LogVO();
				logVO.setLogId (logId);
				logVO.setOperation (operation);
				
				if (Utils.isNonEmpty (studentName)) {					
					StudentVO studentVO = new StudentVO();
					studentVO.setFullName (studentName);
					logVO.setStudentVO (studentVO);					
				}
				
				logVO.setMessage (message);
				logVO.setError (error);
				logVO.setLogTime (new Date(logTime.getTime()));
				logVO.setStatus (status);
													
				logList.add (logVO);
			}
		}
		return logList;
	}
	
	/**
	 * Gets the row count of logs.
	 * 
	 * @return the row count.
	 * @throws DataAccessException
	 */
	public int rowCount () throws DataAccessException {
		int count = 0;
		String sql = null;
		sql = "SELECT COUNT(*) FROM LOG";
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		return count;
	}
	
}
