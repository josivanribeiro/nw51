package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.TeacherVO;
import br.com.nw51.common.vo.UserVO;

/**
 * DAO class for Teacher.
 * 
 * @author Josivan Silva
 *
 */
public class TeacherDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (TeacherDAO.class.getName());
	
	public TeacherDAO () {
		
	}
		
	/**
	 * Inserts a new teacher.
	 * 
	 * @param teacherVO the teacher.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public Integer insert (TeacherVO teacherVO) throws DataAccessException {
		Integer teacherId = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO TEACHER (USER_ID, FULL_NAME, PHONE, MOBILE) ");
		sbSql.append ("VALUES (" +  teacherVO.getUserVO().getUserId() + ", ");
		sbSql.append ("'" + teacherVO.getFullName() + "', ");
		sbSql.append ("'" +  teacherVO.getPhone() + "', ");
		sbSql.append ("'" +  teacherVO.getMobile() + "')");
		logger.debug("sql: " + sbSql.toString());
		teacherId = insertDbWithIntegerKey (sbSql.toString());
		return teacherId;
	}
		
	/**
	 * Updates an existing teacher.
	 * 
	 * @param teacherVO the teacher.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (TeacherVO teacherVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE TEACHER SET "); 
		sbSql.append ("USER_ID=");
		sbSql.append (teacherVO.getUserVO().getUserId() + ", ");
		
		sbSql.append ("FULL_NAME=");
		sbSql.append ("'" + teacherVO.getFullName() + "', ");
		sbSql.append ("PHONE=");
		sbSql.append ("'" + teacherVO.getPhone() + "', ");
		sbSql.append ("MOBILE=");
		sbSql.append ("'" + teacherVO.getMobile() + "' ");
		whereClause = "WHERE TEACHER_ID = " + teacherVO.getTeacherId();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes a teacher.
	 * 
	 * @param teacherVO the teacher.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (TeacherVO teacherVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM TEACHER WHERE TEACHER_ID = " + teacherVO.getTeacherId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a teacher by its id.
	 * 
	 * @param teacherVO the teacher.
	 * @return a teacher.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public TeacherVO findById (TeacherVO teacherVO) throws DataAccessException {
		TeacherVO foundTeacherVO = null;
		String sql               = null;
		List<Object> rowList     = null;
		sql = "SELECT TEACHER_ID, USER_ID, FULL_NAME, PHONE, MOBILE FROM TEACHER WHERE TEACHER_ID = " + teacherVO.getTeacherId();
		rowList = selectDb (sql, 5);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns = (List<Object>)columnList;					
				Integer teacherId   = (Integer) columns.get (0);
				Integer userId      = (Integer) columns.get (1);
				String fullName     = (String) columns.get (2);
				String phone        = (String) columns.get (3);
				String mobile       = (String) columns.get (4);
								
				foundTeacherVO = new TeacherVO();
				foundTeacherVO.setTeacherId (teacherId);
				
				UserVO userVO = new UserVO();
				userVO.setUserId (userId);
				
				foundTeacherVO.setUserVO (userVO);
				foundTeacherVO.setFullName (fullName);
				foundTeacherVO.setPhone (phone);
				foundTeacherVO.setMobile (mobile);
			}
		}
		return foundTeacherVO;
	}
	
	/**
	 * Find all the teachers.
	 * 
	 * @return a list of teachers.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<TeacherVO> findAll () throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<TeacherVO> teacherList = new ArrayList<TeacherVO>();
		sql = "SELECT T.TEACHER_ID, T.USER_ID, U.EMAIL, T.FULL_NAME, T.PHONE, T.MOBILE "
			+ "FROM TEACHER T "
			+ "INNER JOIN USER U ON U.USER_ID = T.USER_ID "	
			+ "ORDER BY T.TEACHER_ID";
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;					
				Integer teacherId = (Integer) columns.get (0);
				Integer userId    = (Integer) columns.get (1);
				String email      = (String) columns.get (2);
				String fullName   = (String) columns.get (3);
				String phone      = (String) columns.get (4);
				String mobile     = (String) columns.get (5);
				
				TeacherVO teacherVO = new TeacherVO();
				teacherVO.setTeacherId (teacherId);
								
				UserVO userVO = new UserVO();
				userVO.setUserId (userId);
				userVO.setEmail (email);
				
				teacherVO.setUserVO (userVO);
				teacherVO.setFullName (fullName);
				teacherVO.setPhone (phone);
				teacherVO.setMobile (mobile);
				
				teacherList.add (teacherVO);
			}
		}
		return teacherList;
	}
	
	/**
	 * Checks if already exists a teacher with this user associated.
	 * 
	 * @param teacherVO the teacher.
	 * @return the operation result.
	 * @throws DataAccessException
	 */
	public boolean hasAssociatedUser (TeacherVO teacherVO) throws DataAccessException {
		boolean exists     = false;
		String sql         = null;
		int count          = 0;
		String whereClause = "";
		if (teacherVO.getTeacherId() != null && teacherVO.getTeacherId() > 0) {
			whereClause = " AND TEACHER_ID <> " + teacherVO.getTeacherId();
		}
		sql = "SELECT COUNT(*) FROM TEACHER WHERE USER_ID = " + teacherVO.getUserVO().getUserId() + whereClause;
		count = selectRowCount (sql);
		exists = count > 0 ? true : false;
		return exists;
	}
	
	/**
	 * Gets the row count of teachers.
	 * 
	 * @return the row count.
	 * @throws DataAccessException
	 */
	public int rowCount () throws DataAccessException {
		int count = 0;
		String sql = null;
		sql = "SELECT COUNT(*) FROM TEACHER";
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		return count;
	}
	
}
