package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.CourseFilterVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.TeacherVO;
import br.com.nw51.common.vo.TeachingPlanVO;

/**
 * DAO class for Course.
 * 
 * @author Josivan Silva
 *
 */
public class CourseDAO extends AbstractDAO {
	
	static Logger logger = Logger.getLogger (CourseDAO.class.getName());
	
	public CourseDAO () {
		
	}
		
	/**
	 * Inserts a new course.
	 * 
	 * @param courseVO the course.
	 * @return the course id.
	 * @throws DataAccessException
	 */
	public Integer insert (CourseVO courseVO) throws DataAccessException {
		Integer newCourseId = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO COURSE (TEACHER_ID, TEACHING_PLAN_ID, COURSE_TYPE, NAME, CREATION_DATE, STATUS) ");
		sbSql.append ("VALUES (" +  courseVO.getTeacherVO().getTeacherId() + ", ");
		sbSql.append (courseVO.getTeachingPlanVO().getTeachingPlanId() + ", ");
		sbSql.append (courseVO.getCourseType() + ", ");
		sbSql.append ("'" +  courseVO.getName() + "', ");
		sbSql.append ("NOW(), ");
		sbSql.append (courseVO.getStatus() + ")");
		logger.debug ("sql: " + sbSql.toString());
		newCourseId = insertDbWithIntegerKey (sbSql.toString());
		return newCourseId;
	}
	
	/**
	 * Updates an existing course.
	 * 
	 * @param courseVO the course.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (CourseVO courseVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE COURSE SET "); 
		sbSql.append ("TEACHER_ID = ");
		sbSql.append (courseVO.getTeacherVO().getTeacherId() + ", ");
		sbSql.append ("TEACHING_PLAN_ID = ");
		sbSql.append (courseVO.getTeachingPlanVO().getTeachingPlanId()+ ", ");
		sbSql.append ("COURSE_TYPE = ");
		sbSql.append (courseVO.getCourseType() + ", ");
		sbSql.append ("NAME = ");
		sbSql.append ("'" + courseVO.getName() + "', ");
		sbSql.append ("LAST_UPDATE_DATE = NOW(), ");
		sbSql.append ("STATUS = ");
		sbSql.append (courseVO.getStatus());
		whereClause = " WHERE COURSE_ID = " + courseVO.getCourseId();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the course last update date.
	 * 
	 * @param courseVO the course.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateLastUpdateDate (CourseVO courseVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE COURSE SET "); 
		sbSql.append ("LAST_UPDATE_DATE = NOW() ");
		whereClause = "WHERE COURSE_ID = " + courseVO.getCourseId();
		sbSql.append (whereClause);
		logger.debug ("sbl: " + sbSql.toString());		
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes a course.
	 * 
	 * @param courseVO the course.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (CourseVO courseVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM COURSE WHERE COURSE_ID = " + courseVO.getCourseId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a Student by its id.
	 * 
	 * @param courseVO the course.
	 * @return a course.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public CourseVO findById (CourseVO courseVO) throws DataAccessException {
		CourseVO foundCourseVO = null;
		String sql             = null;
		List<Object> rowList   = null;
		sql = "SELECT C.COURSE_ID, C.TEACHER_ID, C.TEACHING_PLAN_ID, TP.WORKLOAD_HOURS_THEORY_TYPE, TP.WORKLOAD_HOURS_PRACTICE_TYPE, "
			+ "C.COURSE_TYPE, C.NAME, C.CREATION_DATE, C.LAST_UPDATE_DATE, C.STATUS "
			+ "FROM COURSE C "
			+ "INNER JOIN TEACHING_PLAN TP ON C.TEACHING_PLAN_ID = TP.TEACHING_PLAN_ID "
			+ "WHERE C.COURSE_ID = " + courseVO.getCourseId();
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 10);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns   = (List<Object>)columnList;
				Integer courseId                  = (Integer) columns.get (0);
				Integer teacherId                 = (Integer) columns.get (1);
				Integer teachingPlanId            = (Integer) columns.get (2);
				Integer workloadHoursTheoryType   = (Integer) columns.get (3);
				Integer workloadHoursPracticeType = (Integer) columns.get (4);
				Integer courseType                = (Integer) columns.get (5);
				String name                       = (String) columns.get (6);
				Date creationDate                 = (Date) columns.get (7);
				Date lastUpdateDate               = (Date) columns.get (8);
				int status                        = (Integer) columns.get (9);
				
				foundCourseVO = new CourseVO();
				foundCourseVO.setCourseId (courseId);
				
				TeacherVO teacherVO = new TeacherVO();
				teacherVO.setTeacherId (teacherId);
				
				foundCourseVO.setTeacherVO (teacherVO);
				
				TeachingPlanVO teachingPlanVO = new TeachingPlanVO();
				teachingPlanVO.setTeachingPlanId (teachingPlanId);
				teachingPlanVO.setWorkloadHoursTheoryType (workloadHoursTheoryType);
				teachingPlanVO.setWorkloadHoursPracticeType (workloadHoursPracticeType);
				
				foundCourseVO.setTeachingPlanVO (teachingPlanVO);
				
				foundCourseVO.setCourseType (courseType);
				foundCourseVO.setName (name);
				foundCourseVO.setCreationDate (creationDate);
				foundCourseVO.setLastUpdateDate (lastUpdateDate);
				foundCourseVO.setStatus (status);
			}
		}
		return foundCourseVO;
	}	
	
	/**
	 * Finds the courses by filter.
	 * 
	 * @return a list of courses.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<CourseVO> findByFilter (CourseFilterVO filter) throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<CourseVO> courseList = new ArrayList<CourseVO>();
		String whereClause    = "";
		boolean hasWhereClause = false;
		if (Utils.isNonEmpty (filter.getName())) {
			whereClause = "WHERE C.NAME LIKE '%" + filter.getName() + "%' ";
			hasWhereClause = true;
		}
		if (filter.getCourseType() != null && filter.getCourseType() > 0) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "C.COURSE_TYPE = " + filter.getCourseType() + " ";
		}
		if (filter.getTeacherId() != null && filter.getTeacherId() > 0) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "C.TEACHER_ID = " + filter.getTeacherId() + " ";
		}
		if (Utils.isNonEmpty (filter.getStartDate()) 
				&& Utils.isNonEmpty (filter.getEndDate())) {
			if (hasWhereClause) {
				whereClause += "AND ";
			} else {
				whereClause += "WHERE ";
				hasWhereClause = true;
			}
			whereClause += "C.CREATION_DATE >= STR_TO_DATE ('" + filter.getStartDate() + " 00:00:00', '%d/%m/%Y %H:%i:%s') AND C.CREATION_DATE <= STR_TO_DATE ('" + filter.getEndDate() + " 23:59:59', '%d/%m/%Y %H:%i:%s')";
		}
		
		sql = "SELECT C.COURSE_ID, C.TEACHER_ID, T.FULL_NAME, C.TEACHING_PLAN_ID, C.COURSE_TYPE, "
			+ "C.NAME, C.CREATION_DATE, C.LAST_UPDATE_DATE, C.STATUS "
			+ "FROM COURSE C "
			+ "INNER JOIN TEACHER T ON T.TEACHER_ID = C.TEACHER_ID "
			+ whereClause + " ORDER BY C.COURSE_ID";
		
		logger.debug ("sql " + sql);
				
		rowList = selectDb (sql, 9);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;					
				Integer courseId       = (Integer) columns.get (0);
				Integer teacherId      = (Integer) columns.get (1);
				String teacherFullName = (String) columns.get (2);
				Integer teachingPlanId = (Integer) columns.get (3);
				Integer courseType     = (Integer) columns.get (4);
				String name            = (String) columns.get (5);
				Date creationDate      = (Date) columns.get (6);
				Date lastUpdateDate    = (Date) columns.get (7);
				int status             = (Integer) columns.get (8);
				
				CourseVO courseVO = new CourseVO();
				courseVO.setCourseId (courseId);
				
				TeacherVO teacherVO = new TeacherVO();
				teacherVO.setTeacherId (teacherId);
				teacherVO.setFullName (teacherFullName);
				
				courseVO.setTeacherVO (teacherVO);
				
				TeachingPlanVO teachingPlanVO = new TeachingPlanVO();
				teachingPlanVO.setTeachingPlanId (teachingPlanId);
				
				courseVO.setTeachingPlanVO (teachingPlanVO);
				
				courseVO.setCourseType (courseType);
				courseVO.setName (name);
				courseVO.setCreationDate (creationDate);
				courseVO.setLastUpdateDate (lastUpdateDate);
				courseVO.setStatus (status);
													
				courseList.add (courseVO);
			}
		}
		return courseList;
	}
	
	/**
	 * Finds all the courses.
	 * 
	 * @return a list of courses.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<CourseVO> findAll () throws DataAccessException {
		String sql                = null;
		List<Object> rowList      = null;
		List<CourseVO> courseList = new ArrayList<CourseVO>();
				
		sql = "SELECT COURSE_ID, TEACHER_ID, COURSE_TYPE, "
			+ "NAME, CREATION_DATE, LAST_UPDATE_DATE, STATUS "
			+ "FROM COURSE ORDER BY COURSE_ID";
		
		logger.debug ("sql " + sql);
				
		rowList = selectDb (sql, 7);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;					
				Integer courseId     = (Integer) columns.get (0);
				Integer teacherId    = (Integer) columns.get (1);
				Integer courseType   = (Integer) columns.get (2);
				String name          = (String) columns.get (3);
				Date creationDate    = (Date) columns.get (4);
				Date lastUpdateDate  = (Date) columns.get (5);
				int status           = (Integer) columns.get (6);
				
				CourseVO courseVO = new CourseVO();
				courseVO.setCourseId (courseId);
				
				TeacherVO teacherVO = new TeacherVO();
				teacherVO.setTeacherId (teacherId);
								
				courseVO.setTeacherVO (teacherVO);
								
				courseVO.setCourseType (courseType);
				courseVO.setName (name);
				courseVO.setCreationDate (creationDate);
				courseVO.setLastUpdateDate (lastUpdateDate);
				courseVO.setStatus (status);
													
				courseList.add (courseVO);
			}
		}
		return courseList;
	}
	
	/**
	 * Gets the row count of courses.
	 * 
	 * @return the row count.
	 * @throws DataAccessException
	 */
	public int rowCount () throws DataAccessException {
		int count = 0;
		String sql = null;
		sql = "SELECT COUNT(*) FROM COURSE";
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		return count;
	}
	
}
