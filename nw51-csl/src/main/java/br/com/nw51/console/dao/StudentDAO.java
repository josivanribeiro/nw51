package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.StudentFilterVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.console.util.SecurityUtils;

/**
 * DAO class for Student.
 * 
 * @author Josivan Silva
 *
 */
public class StudentDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (StudentDAO.class.getName());
	
	public StudentDAO () {
		
	}
		
	/**
	 * Inserts a new student.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int insert (StudentVO studentVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO STUDENT (EMAIL, PWD, GENDER, FULL_NAME, CPF, PHONE, MOBILE, IMAGE_PROFILE, JOIN_DATE, EXPIRATION_DATE, STATUS) ");
		sbSql.append ("VALUES ('" +  studentVO.getEmail() + "', ");
		String pwdHash = SecurityUtils.getSHA512Password (studentVO.getPwd(), studentVO.getEmail());
		sbSql.append ("'" + pwdHash + "', ");
		sbSql.append ("'" +  studentVO.getGender() + "', ");
		sbSql.append ("'" +  studentVO.getFullName() + "', ");
		sbSql.append ("'" +  studentVO.getCpf() + "', ");
		sbSql.append ("'" +  studentVO.getPhone() + "', ");
		sbSql.append ("'" +  studentVO.getMobile() + "', ");
		sbSql.append ("null, ");
		sbSql.append ("NOW(), ");
		sbSql.append ("'" +  studentVO.getExpirationDate() + "', ");
		sbSql.append (studentVO.getStatus() + ")");
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates an existing student status.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateStatus (StudentVO studentVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE STUDENT SET "); 
		sbSql.append ("LAST_UPDATE_DATE=NOW(), ");
		sbSql.append ("STATUS=");
		sbSql.append (studentVO.getStatus());
		whereClause = " WHERE STUDENT_ID = " + studentVO.getStudentId();
		sbSql.append (whereClause);
		
		logger.debug ("sql: " + sbSql.toString());
		
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
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
		sbSql.append ("EMAIL=");
		sbSql.append ("'" + studentVO.getEmail() + "', ");
		sbSql.append ("PWD=");
		String pwdHash = SecurityUtils.getSHA512Password (studentVO.getPwd(), studentVO.getEmail());
		sbSql.append ("'" + pwdHash + "', ");
		sbSql.append ("GENDER=");
		sbSql.append ("'" + studentVO.getGender() + "', ");
		sbSql.append ("FULL_NAME=");
		sbSql.append ("'" + studentVO.getFullName() + "', ");
		sbSql.append ("CPF=");
		sbSql.append ("'" + studentVO.getCpf() + "', ");
		sbSql.append ("PHONE=");
		sbSql.append ("'" + studentVO.getPhone() + "', ");
		sbSql.append ("MOBILE=");
		sbSql.append ("'" + studentVO.getMobile() + "', ");
		sbSql.append ("IMAGE_PROFILE=null, ");
		sbSql.append ("EXPIRATION_DATE=");
		sbSql.append ("'" + studentVO.getExpirationDate() + "', ");
		sbSql.append ("LAST_UPDATE_DATE=NOW(), ");
		sbSql.append ("STATUS=");
		sbSql.append (studentVO.getStatus());
		whereClause = " WHERE STUDENT_ID = " + studentVO.getStudentId();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes a student.
	 * 
	 * @param studentVO the student.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (StudentVO studentVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM STUDENT WHERE STUDENT_ID = " + studentVO.getStudentId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a Student by its id.
	 * 
	 * @param studentVO the student.
	 * @return a student.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public StudentVO findById (StudentVO studentVO) throws DataAccessException {
		StudentVO foundStudentVO = null;
		String sql         = null;
		List<Object> rowList = null;
		sql = "SELECT STUDENT_ID, EMAIL, GENDER, FULL_NAME, CPF, PHONE, MOBILE, IMAGE_PROFILE, JOIN_DATE, EXPIRATION_DATE, LAST_UPDATE_DATE, LAST_LOGIN_DATE, LAST_LOGIN_IP, STATUS FROM STUDENT WHERE STUDENT_ID = " + studentVO.getStudentId();
		rowList = selectDb (sql, 14);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns = (List<Object>)columnList;					
				Long studentId      = (Long) columns.get (0);
				String email        = (String) columns.get (1);
				String gender       = (String) columns.get (2);
				String fullName     = (String) columns.get (3);
				String cpf          = (String) columns.get (4);
				String phone        = (String) columns.get (5);
				String mobile       = (String) columns.get (6);
				Byte[] imageProfile = (Byte[]) columns.get (7);
				Date joinDate       = (Date) columns.get (8);
				Date expirationDate = (Date) columns.get (9);
				Date lastUpdateDate = (Date) columns.get (10);
				Date lastLoginDate  = (Date) columns.get (11);
				String lastLoginIp  = (String) columns.get (12);
				int status          = (Integer) columns.get (13);
				
				foundStudentVO = new StudentVO();
				foundStudentVO.setStudentId (studentId);
				foundStudentVO.setEmail (email);
				foundStudentVO.setGender (gender);
				foundStudentVO.setFullName (fullName);
				foundStudentVO.setCpf (cpf);
				foundStudentVO.setPhone (phone);
				foundStudentVO.setMobile (mobile);
				foundStudentVO.setImageProfile (imageProfile);
				foundStudentVO.setJoinDate (joinDate);
				foundStudentVO.setExpirationDate (expirationDate);				
				foundStudentVO.setLastUpdateDate (lastUpdateDate);
				foundStudentVO.setLastLoginDate (lastLoginDate);
				foundStudentVO.setLastLoginIp (lastLoginIp);
				boolean statusAsBoolean = status == 1 ? true : false;
				foundStudentVO.setStatus (statusAsBoolean);
			}
		}
		return foundStudentVO;
	}
	
	/**
	 * Finds a duplicated student email.
	 * 
	 * @param studentVO the student.
	 * @return the count of duplicated student email.
	 * @throws DataAccessException
	 */
	public int findDuplicatedEmail (StudentVO studentVO) throws DataAccessException {
		String sql = null;
		String whereClause = "";
		int count = 0;
		if (studentVO.getStudentId() != null && studentVO.getStudentId() > 0) {
			whereClause = " AND STUDENT_ID <> " + studentVO.getStudentId();
		}
		sql = "SELECT COUNT(*) FROM STUDENT WHERE EMAIL = '" + studentVO.getEmail() + "'" + whereClause;
		count = selectRowCount (sql);
		return count;
	}
	
	/**
	 * Finds a duplicated student cpf.
	 * 
	 * @param studentVO the student.
	 * @return the count of duplicated student cpf.
	 * @throws DataAccessException
	 */
	public int findDuplicatedCpf (StudentVO studentVO) throws DataAccessException {
		String sql = null;
		String whereClause = "";
		int count = 0;
		if (studentVO.getStudentId() != null && studentVO.getStudentId() > 0) {
			whereClause = " AND STUDENT_ID <> " + studentVO.getStudentId();
		}
		sql = "SELECT COUNT(*) FROM STUDENT WHERE CPF = '" + studentVO.getCpf() + "'" + whereClause;
		count = selectRowCount (sql);
		return count;
	}
	
	/**
	 * Finds the students by filter.
	 * 
	 * @return a list of students.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<StudentVO> findByFilter (StudentFilterVO filter) throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<StudentVO> studentList = new ArrayList<StudentVO>();
		String whereClause    = "";
		boolean hasWhereClause = false;
		if (Utils.isNonEmpty (filter.getFullName())) {
			whereClause = "WHERE FULL_NAME LIKE '%" + filter.getFullName() + "%' ";
			hasWhereClause = true;
		}
		if (Utils.isNonEmpty (filter.getStartDate()) 
				&& Utils.isNonEmpty (filter.getEndDate())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "JOIN_DATE >= STR_TO_DATE ('" + filter.getStartDate() + " 00:00:00', '%d/%m/%Y %H:%i:%s') AND JOIN_DATE <= STR_TO_DATE ('" + filter.getEndDate() + " 23:59:59', '%d/%m/%Y %H:%i:%s')";
		}
		if (Utils.isNonEmpty (filter.getEmail())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "EMAIL = '" + filter.getEmail() + "'";
		}
		if (Utils.isNonEmpty (filter.getCpf())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";				
			}
			whereClause += "CPF = '" + filter.getCpf() + "'";
		}
		sql = "SELECT STUDENT_ID, EMAIL, GENDER, FULL_NAME, CPF, JOIN_DATE, EXPIRATION_DATE, LAST_UPDATE_DATE, LAST_LOGIN_DATE, LAST_LOGIN_IP, STATUS FROM STUDENT " + whereClause + " ORDER BY STUDENT_ID";
		
		logger.debug ("sql: " + sql);
				
		rowList = selectDb (sql, 11);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;					
				Long studentId      = (Long) columns.get (0);
				String email        = (String) columns.get (1);
				String gender       = (String) columns.get (2);
				String fullName     = (String) columns.get (3);
				String cpf          = (String) columns.get (4);
				Date joinDate       = (Date) columns.get (5);
				Date expirationDate = (Date) columns.get (6);
				Date lastUpdateDate = (Date) columns.get (7);
				Date lastLoginDate  = (Date) columns.get (8);
				String lastLoginIp  = (String) columns.get (9);
				int status          = (Integer) columns.get (10);
				
				StudentVO studentVO = new StudentVO();
				studentVO.setStudentId (studentId);
				studentVO.setEmail (email);
				studentVO.setGender (gender);
				studentVO.setFullName (fullName);
				studentVO.setCpf (cpf);
				studentVO.setJoinDate (joinDate);
				studentVO.setExpirationDate (expirationDate);
				studentVO.setLastUpdateDate (lastUpdateDate);
				studentVO.setLastLoginDate (lastLoginDate);
				studentVO.setLastLoginIp (lastLoginIp);
				boolean statusAsBoolean = status == 1 ? true : false;
				studentVO.setStatus (statusAsBoolean);
													
				studentList.add (studentVO);
			}
		}
		return studentList;
	}
	
	/**
	 * Gets the row count of students.
	 * 
	 * @return the row count.
	 * @throws DataAccessException
	 */
	public int rowCount () throws DataAccessException {
		int count = 0;
		String sql = null;
		sql = "SELECT COUNT(*) FROM STUDENT";
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		return count;
	}
	
}
