package br.com.nw51.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.AddressVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.student.util.SecurityUtils;

/**
 * DAO class for Student.
 * 
 * @author Josivan Silva
 *
 */
public class StudentDAO extends AbstractDAO {

	public StudentDAO() {
		
	}
	
	/**
	 * Updates an existing student.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (StudentVO studentVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE STUDENT SET "); 
		sbSql.append ("FULL_NAME=");
		sbSql.append ("'" + studentVO.getFullName() + "', ");
		sbSql.append ("EMAIL=");
		sbSql.append ("'" + studentVO.getEmail() + "', ");
		if (Utils.isNonEmpty (studentVO.getPwd())) {
			sbSql.append ("PWD=");
			String pwdHash = SecurityUtils.getSHA512Password (studentVO.getPwd(), studentVO.getEmail());
			sbSql.append ("'" + pwdHash + "', ");
		}
		sbSql.append ("CPF=");
		sbSql.append ("'" + studentVO.getCpf() + "', ");
		sbSql.append ("PHONE=");
		sbSql.append ("'" + studentVO.getPhone() + "', ");
		sbSql.append ("MOBILE=");
		sbSql.append ("'" + studentVO.getMobile() + "' ");
		whereClause = "WHERE STUDENT_ID = " + studentVO.getStudentId();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		logger.debug ("sql: " + sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Performs the user login.
	 * 
	 * @param studentVO the user.
	 * @return the operation result.
	 * @throws DataAccessException
	 */
	public boolean doLogin (StudentVO studentVO) throws DataAccessException {
		logger.debug("Starting doLogin.");
		boolean isValid         = false;
		Connection conn         = null;
		PreparedStatement pstmt = null;
		ResultSet rs            = null;
		String sql              = null;
		int count               = 0;
		try {
			conn = this.getConnection();
			String pwdHash = SecurityUtils.getSHA512Password (studentVO.getPwd(), studentVO.getEmail());
			sql = "SELECT COUNT(*) FROM STUDENT WHERE EMAIL = ? AND PWD = ? AND STATUS = 1";			
			pstmt = conn.prepareStatement (sql);			
			pstmt.setString (1, studentVO.getEmail());
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
	 * Updates the student state.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateState (StudentVO studentVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE STUDENT SET "); 
		sbSql.append ("STATE = ");
		sbSql.append (studentVO.getState() + " ");
		whereClause = "WHERE STUDENT_ID = " + studentVO.getStudentId().toString();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the student login attempts.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateLoginAttempts (StudentVO studentVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE STUDENT SET "); 
		sbSql.append ("LOGIN_ATTEMPTS = ");
		sbSql.append (studentVO.getLoginAttempts() + " ");
		whereClause = "WHERE STUDENT_ID = " + studentVO.getStudentId().toString();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Finds a student by its email.
	 * 
	 * @param studentVO the student.
	 * @return a student.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public StudentVO findByEmail (StudentVO studentVO) throws DataAccessException {
		StudentVO foundStudent = null;
		String sql             = null;
		List<Object> rowList   = null;
		sql = "SELECT STUDENT_ID, EMAIL, FULL_NAME, STATE, LOGIN_ATTEMPTS, STATUS FROM STUDENT WHERE EMAIL = '" + studentVO.getEmail() + "'";
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {			
				List<Object> columns = (List<Object>)columnList;
				Long studentId       = (Long) columns.get (0);
				String email         = (String) columns.get (1);
				String fullName      = (String) columns.get (2);
				int state            = (Integer) columns.get (3);
				int loginAttempts    = 0;
				if (columns.get (4) != null) {
					loginAttempts = (Integer) columns.get (4);
				} else {
					loginAttempts = 0;
				}
				int status    = (Integer) columns.get (5);
				foundStudent = new StudentVO();
				foundStudent.setStudentId (studentId);
				foundStudent.setEmail (email);
				foundStudent.setFullName (fullName);
				foundStudent.setState (state);
				foundStudent.setLoginAttempts (loginAttempts);
				boolean statusAsBoolean = status == 1 ? true : false;
				foundStudent.setStatus (statusAsBoolean);
			}
		}
		return foundStudent;
	}
	
	/**
	 * Finds a student by its id.
	 * 
	 * @param studentVO the student.
	 * @return a student.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public StudentVO findById (StudentVO studentVO) throws DataAccessException {
		StudentVO foundStudent = null;
		String sql             = null;
		List<Object> rowList   = null;
		sql = "SELECT S.STUDENT_ID, S.ADDRESS_ID, S.EMAIL, S.GENDER, S.FULL_NAME, S.CPF, S.PHONE, S.MOBILE, A.CEP, "
			+ "A.ADDRESS1, A.NUMBER, A.ADDRESS2, A.NEIGHBORHOOD, A.CITY, A.STATE_ID, S.JOIN_DATE, S.EXPIRATION_DATE, "
			+ "S.LAST_UPDATE_DATE, S.LAST_LOGIN_DATE, S.LAST_LOGIN_IP, S.SOCIAL_PROGRAM, S.STATUS "
			+ "FROM STUDENT S "
			+ "INNER JOIN ADDRESS A ON A.ADDRESS_ID = S.ADDRESS_ID "
			+ "WHERE S.STUDENT_ID = " + studentVO.getStudentId();
		rowList = selectDb (sql, 22);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {			
				List<Object> columns = (List<Object>)columnList;
				Long studentId       = (Long) columns.get (0);
				Long addressId       = (Long) columns.get (1);
				String email         = (String) columns.get (2);
				String gender        = (String) columns.get (3);
				String fullName      = (String) columns.get (4);
				String cpf           = (String) columns.get (5);
				String phone         = (String) columns.get (6);
				String mobile        = (String) columns.get (7);
				String cep           = (String) columns.get (8);
				String address1      = (String) columns.get (9);
				String number        = (String) columns.get (10);
				String address2      = (String) columns.get (11);
				String neighborhood  = (String) columns.get (12);
				String city          = (String) columns.get (13);
				Integer stateId      = (Integer) columns.get (14);
				Date joinDate        = (Date) columns.get (15);
				Date expirationDate  = (Date) columns.get (16);
				Date lastUpdateDate  = (Date) columns.get (17);
				Date lastLoginDate   = (Date) columns.get (18);
				String lastLoginIp   = (String) columns.get (19);
				int socialProgram    = (Integer) columns.get (20);
				int status           = (Integer) columns.get (21);
				
				foundStudent = new StudentVO();
				foundStudent.setStudentId (studentId);
				
				AddressVO addressVO = new AddressVO();
				addressVO.setAddressId (addressId);
				addressVO.setCEP (cep);
				addressVO.setAddress1 (address1);
				addressVO.setNumber (number);
				addressVO.setAddress2 (address2);
				addressVO.setNeighborhood (neighborhood);
				addressVO.setCity (city);
				addressVO.setStateId (stateId);
				
				foundStudent.setAddressVO (addressVO);
				
				foundStudent.setEmail (email);
				foundStudent.setGender (gender);
				foundStudent.setFullName (fullName);
				foundStudent.setCpf (cpf);
				foundStudent.setPhone (phone);
				foundStudent.setMobile (mobile);
				foundStudent.setJoinDate (joinDate);
				foundStudent.setExpirationDate (expirationDate);
				foundStudent.setLastUpdateDate (lastUpdateDate);
				foundStudent.setLastLoginDate (lastLoginDate);
				foundStudent.setLastLoginIp (lastLoginIp);
				boolean socialProgramAsBoolean = socialProgram == 1 ? true : false;
				boolean statusAsBoolean = status == 1 ? true : false;				
				foundStudent.setSocialProgram (socialProgramAsBoolean);
				foundStudent.setStatus (statusAsBoolean);
			}
		}
		return foundStudent;
	}

}
