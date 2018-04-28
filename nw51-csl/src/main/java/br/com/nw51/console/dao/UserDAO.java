package br.com.nw51.console.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.util.Constants;
import br.com.nw51.console.util.SecurityUtils;

/**
 * DAO class for User.
 * 
 * @author Josivan Silva
 *
 */
public class UserDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (UserDAO.class.getName());
	
	public UserDAO () {
		
	}
	
	/**
	 * Inserts a new user.
	 * 
	 * @param userVO the user.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public Integer insert (UserVO userVO) throws DataAccessException {
		Integer userId = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO USER (EMAIL, PWD, STATE, STATUS) ");
		sbSql.append ("VALUES ('" + userVO.getEmail() + "', ");
		String pwdHash = SecurityUtils.getSHA512Password (userVO.getPwd(), userVO.getEmail());
		sbSql.append ("'" + pwdHash + "', ");
		sbSql.append (userVO.getState() + ", ");
		Integer status = (userVO.isStatus()) ? 1 : 0;
		sbSql.append (status.toString() + ")");
		logger.debug ("sql: " + sbSql.toString());
		userId = insertDbWithIntegerKey (sbSql.toString());
		return userId;
	}
	
	/**
	 * Updates an existing user.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (UserVO userVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE USER SET "); 
		sbSql.append ("EMAIL=");
		sbSql.append ("'" + userVO.getEmail() + "', ");
		sbSql.append ("PWD=");
		String pwdHash = SecurityUtils.getSHA512Password (userVO.getPwd(), userVO.getEmail());		
		sbSql.append ("'" + pwdHash + "', ");
		sbSql.append ("STATE=");
		sbSql.append (userVO.getState() + ", ");
		if (userVO.getState() == Constants.USER_STATE_NOT_LOGGED) {
			sbSql.append ("LOGIN_ATTEMPTS=");
			sbSql.append (userVO.getLoginAttempts() + ", ");
			sbSql.append ("UNAUTH_ACCESS_ATTEMPTS=");
			sbSql.append (userVO.getUnauthorizedAccessAttempts() + ", ");
		}
		Integer status = (userVO.isStatus()) ? 1 : 0;
		sbSql.append (" STATUS=");
		sbSql.append (status.toString());
		whereClause = " WHERE USER_ID = " + userVO.getUserId().toString();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		logger.debug ("sql: " + sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the user password.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updatePassword (UserVO userVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE USER SET "); 
		sbSql.append ("PWD=");
		String pwdHash = SecurityUtils.getSHA512Password (userVO.getPwd(), userVO.getEmail());
		sbSql.append ("'" + pwdHash + "' ");
		whereClause = "WHERE USER_ID = " + userVO.getUserId().toString();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the user state.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateState (UserVO userVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE USER SET "); 
		sbSql.append ("STATE = ");
		sbSql.append (userVO.getState() + " ");
		whereClause = "WHERE USER_ID = " + userVO.getUserId().toString();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the user login attempts.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateLoginAttempts (UserVO userVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE USER SET "); 
		sbSql.append ("LOGIN_ATTEMPTS = ");
		sbSql.append (userVO.getLoginAttempts() + " ");
		whereClause = "WHERE USER_ID = " + userVO.getUserId().toString();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the user unauthorized access attempts.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateUnauthorizedAccessAttempts (UserVO userVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE USER SET ");
		sbSql.append ("UNAUTH_ACCESS_ATTEMPTS = ");
		sbSql.append (userVO.getUnauthorizedAccessAttempts() + " ");
		whereClause = "WHERE USER_ID = " + userVO.getUserId().toString();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes an user.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (UserVO userVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM USER WHERE USER_ID = " + userVO.getUserId().toString();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find an user by its id.
	 * 
	 * @param userVO the user.
	 * @return an user.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public UserVO findById (UserVO userVO) throws DataAccessException {
		UserVO foundUser  = null;
		String sql        = null;
		List<Object> rowList = null;
		sql = "SELECT USER_ID, EMAIL, STATE, STATUS FROM USER WHERE USER_ID = " + userVO.getUserId().toString();
		rowList = selectDb (sql, 4);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Integer userId    = (Integer) columns.get (0);
				String email      = (String) columns.get (1);
				Integer state     = (Integer) columns.get (2);
				boolean status    = (Boolean) columns.get (3);
				foundUser = new UserVO();
				foundUser.setUserId (userId);
				foundUser.setEmail (email);
				foundUser.setState (state);
				foundUser.setStatus (status);
			}
		}
		return foundUser;
	}
	
	/**
	 * Find an user by its email.
	 * 
	 * @param userVO the user.
	 * @return an user.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public UserVO findByEmail (UserVO userVO) throws DataAccessException {
		UserVO foundUser  = null;
		String sql        = null;
		List<Object> rowList = null;
		sql = "SELECT USER_ID, EMAIL, STATE, LOGIN_ATTEMPTS, UNAUTH_ACCESS_ATTEMPTS, STATUS FROM USER WHERE EMAIL = '" + userVO.getEmail() + "'";
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns = (List<Object>)columnList;
				Integer userId    = (Integer) columns.get (0);
				String email      = (String) columns.get (1);
				int state         = (Integer) columns.get (2);
				int loginAttempts = 0;
				if (columns.get (3) != null) {
					loginAttempts = (Integer) columns.get (3);
				} else {
					loginAttempts = 0;
				}
				int unauthorizedAccessAttempts = 0;
				if (columns.get (4) != null) {
					unauthorizedAccessAttempts = (Integer) columns.get (4);
				} else {
					unauthorizedAccessAttempts = 0;
				}
				boolean status    = (Boolean) columns.get (5);
				foundUser = new UserVO();
				foundUser.setUserId (userId);
				foundUser.setEmail (email);
				foundUser.setState (state);
				foundUser.setLoginAttempts (loginAttempts);
				foundUser.setUnauthorizedAccessAttempts (unauthorizedAccessAttempts);
				foundUser.setStatus (status);
			}
		}
		return foundUser;
	}
	
	/**
	 * Finds a duplicated email.
	 * 
	 * @param userVO the user.
	 * @return the count of duplicated email.
	 * @throws DataAccessException
	 */
	public int findDuplicatedEmail (UserVO userVO) throws DataAccessException {
		String sql = null;
		String whereClause = "";
		int count = 0;
		if (userVO.getUserId() != null && userVO.getUserId() > 0) {
			whereClause = " AND USER_ID <> " + userVO.getUserId().toString();
		}
		sql = "SELECT COUNT(*) FROM USER WHERE EMAIL = '" + userVO.getEmail() + "'" + whereClause;
		logger.info("sql: " + sql);		
		count = selectRowCount (sql);
		return count;
	}
	
	/**
	 * Find all the users.
	 * 
	 * @return a list of users.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<UserVO> findAll () throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<UserVO> userList = new ArrayList<UserVO>();
		sql = "SELECT USER_ID, EMAIL, STATE, STATUS FROM USER ORDER BY USER_ID";
		rowList = selectDb (sql, 4);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;					
				Integer userId = (Integer) columns.get (0);
				String email   = (String) columns.get (1);
				Integer state  = (Integer) columns.get (2);
				boolean status = (Boolean) columns.get (3);				
				UserVO userVO = new UserVO();
				userVO.setUserId (userId);
				userVO.setEmail (email);
				userVO.setState (state);
				userVO.setStatus (status);
				userList.add (userVO);
			}
		}
		return userList;
	}
	
	/**
	 * Find all the users with teacher profile.
	 * 
	 * @return a list of users with teacher profile.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<UserVO> findAllWithTeacherProfile () throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<UserVO> userList = new ArrayList<UserVO>();
		sql = "SELECT U.USER_ID, U.EMAIL "
			+ "FROM USER U "
			+ "INNER JOIN USER_PROFILE UP ON UP.USER_ID = U.USER_ID "
			+ "INNER JOIN PROFILE P ON P.PROFILE_ID = UP.PROFILE_ID "
			+ "WHERE U.STATUS = 1 AND P.NAME = 'Professor' "
			+ "ORDER BY U.USER_ID";
		rowList = selectDb (sql, 2);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;
				Integer userId = (Integer) columns.get (0);
				String email   = (String) columns.get (1);
				
				UserVO userVO = new UserVO ();
				userVO.setUserId (userId);
				userVO.setEmail (email);
				
				userList.add (userVO);
			}
		}
		return userList;
	}
	
	/**
	 * Performs the user login.
	 * 
	 * @param userVO the user.
	 * @return the operation result.
	 * @throws DataAccessException
	 */
	public boolean doLogin (UserVO userVO) throws DataAccessException {
		logger.debug("Starting doLogin.");
		boolean isValid         = false;
		Connection conn         = null;
		PreparedStatement pstmt = null;
		ResultSet rs            = null;
		String sql              = null;
		int count               = 0;
		try {
			conn = this.getConnection();
			String pwdHash = SecurityUtils.getSHA512Password (userVO.getPwd(), userVO.getEmail());
			sql = "SELECT COUNT(*) FROM USER WHERE EMAIL = ? AND PWD = ? AND STATUS = 1";
			
			pstmt = conn.prepareStatement (sql);
			
			pstmt.setString (1, userVO.getEmail());
			pstmt.setString (2, pwdHash);
						
			rs = pstmt.executeQuery();
		    while (rs.next()) {		    	
		    	count = rs.getInt (1);
		    }
		    
		    logger.debug ("count: " + count);
		    
		} catch (SQLException e) {
		    String error = "An error occurred while executing the login sql statement. " + e.getMessage();
		    logger.error (error);
		    throw new DataAccessException (error);
		} finally {
			this.closeResultSet (rs);
			this.closePreparedStatement (pstmt);
			this.closeConnection (conn);
		}
		isValid = count > 0 ? true : false;
		logger.debug("Finishing doLogin.");
		return isValid;
	}
	
	/**
	 * Gets the row count of users.
	 * 
	 * @return the row count.
	 * @throws DataAccessException
	 */
	public int rowCount () throws DataAccessException {
		int count = 0;
		String sql = null;
		sql = "SELECT COUNT(*) FROM USER";
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		return count;
	}
	
}
