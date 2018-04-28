package br.com.nw51.student.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.StudentVO;
import br.com.nw51.common.vo.StudyPlanFilterVO;
import br.com.nw51.common.vo.StudyPlanVO;

/**
 * DAO class for StudyPlan.
 * 
 * @author Josivan Silva
 *
 */
public class StudyPlanDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (StudyPlanDAO.class.getName());
	
	public StudyPlanDAO () {
		
	}
	
	/**
	 * Inserts a new studyPlan.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public long insert (StudyPlanVO studyPlanVO) throws DataAccessException {
		long studyPlanId = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO STUDY_PLAN (STUDENT_ID, COURSE_ID, START_CLASS_DATETIME, END_CLASS_DATETIME) ");
		sbSql.append ("VALUES (" +  studyPlanVO.getStudentVO().getStudentId() + ", ");
		sbSql.append (studyPlanVO.getCourseVO().getCourseId() + ", ");		
		String startClassDatetime = new SimpleDateFormat ("dd/MM/yyyy HH:mm").format(studyPlanVO.getStartClassDatetime());
		String endClassDatetime = new SimpleDateFormat ("dd/MM/yyyy HH:mm").format(studyPlanVO.getEndClassDatetime());
		sbSql.append ("STR_TO_DATE ('" + startClassDatetime + ":00', '%d/%m/%Y %H:%i:%s'), ");
		sbSql.append ("STR_TO_DATE ('" + endClassDatetime + ":00', '%d/%m/%Y %H:%i:%s')) ");		
		logger.debug ("sql: " + sbSql.toString());
		studyPlanId = insertDbWithLongKey (sbSql.toString());
		return studyPlanId;
	}
	
	/**
	 * Updates an existing studyPlan.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (StudyPlanVO studyPlanVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE STUDY_PLAN SET ");
		sbSql.append ("STUDENT_ID=");
		sbSql.append (studyPlanVO.getStudentVO().getStudentId() + ", ");
		sbSql.append ("COURSE_ID=");
		sbSql.append (studyPlanVO.getCourseVO().getCourseId() + ", ");
		String startClassDatetime = new SimpleDateFormat ("dd/MM/yyyy HH:mm").format(studyPlanVO.getStartClassDatetime());
		String endClassDatetime = new SimpleDateFormat ("dd/MM/yyyy HH:mm").format(studyPlanVO.getEndClassDatetime());
		sbSql.append ("START_CLASS_DATETIME=");
		sbSql.append ("STR_TO_DATE ('" + startClassDatetime + ":00', '%d/%m/%Y %H:%i:%s'), ");
		sbSql.append ("END_CLASS_DATETIME=");
		sbSql.append ("STR_TO_DATE ('" + endClassDatetime + ":00', '%d/%m/%Y %H:%i:%s') ");
		whereClause = "WHERE STUDY_PLAN_ID = " + studyPlanVO.getStudyPlanId();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes a studyPlan given the studyPlanId.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (StudyPlanVO studyPlanVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM STUDY_PLAN WHERE STUDY_PLAN_ID = " + studyPlanVO.getStudyPlanId();
		affectedRows = updateDb (sql);
		return affectedRows;		
	}
	
	/**
	 * Deletes studyPlans given the courseId and studentId.
	 * 
	 * @param studentVO the student.
	 * @param courseVO the course.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int deleteByCourseAndStudent (StudentVO studentVO, CourseVO courseVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM STUDY_PLAN WHERE COURSE_ID "
			+ "= " + courseVO.getCourseId() + " "
			+ "AND STUDENT_ID = " + studentVO.getStudentId() ;
		logger.debug ("sql: " + sql);
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a studyPlan by its id.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return a studyPlan.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public StudyPlanVO findById (StudyPlanVO studyPlanVO) throws DataAccessException {
		StudyPlanVO foundStudyPlanVO = null;
		String sql                   = null;
		List<Object> rowList         = null;
		sql = "SELECT STUDY_PLAN_ID, STUDENT_ID, COURSE_ID, START_CLASS_DATETIME, END_CLASS_DATETIME FROM STUDY_PLAN WHERE STUDY_PLAN_ID = " + studyPlanVO.getStudyPlanId();
		rowList = selectDb (sql, 5);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns  = (List<Object>)columnList;					
				Long studyPlanId        = (Long) columns.get (0);
				Long studentId          = (Long) columns.get (1);
				Integer courseId        = (Integer) columns.get (2);
				Date startClassDatetime = (Date) columns.get (3);
				Date endClassDatetime   = (Date) columns.get (4);
				
				foundStudyPlanVO = new StudyPlanVO();
				foundStudyPlanVO.setStudyPlanId (studyPlanId);
				
				StudentVO studentVO = new StudentVO ();
				studentVO.setStudentId (studentId);
				
				foundStudyPlanVO.setStudentVO (studentVO);
				
				CourseVO courseVO = new CourseVO ();
				courseVO.setCourseId (courseId);
				
				foundStudyPlanVO.setCourseVO (courseVO);
				
				foundStudyPlanVO.setStartClassDatetime (startClassDatetime);
				foundStudyPlanVO.setEndClassDatetime (endClassDatetime);
			}
		}
		return foundStudyPlanVO;
	}
	
	/**
	 * Finds studyPlans by studentId and courseId.
	 * 
	 * @param studentVO the student.
	 * @param courseVO the course.
	 * @return a list of study plans.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<StudyPlanVO> findByStudentAndCourse (StudentVO studentVO, CourseVO courseVO) throws DataAccessException {
		String sql           = null;
		List<Object> rowList = null;
		List<StudyPlanVO> studyPlanList = new ArrayList<StudyPlanVO>();
		sql = "SELECT SP.STUDY_PLAN_ID, SP.STUDENT_ID, SP.COURSE_ID, C.NAME, SP.START_CLASS_DATETIME, SP.END_CLASS_DATETIME "
			+ "FROM STUDY_PLAN SP "
			+ "INNER JOIN COURSE C ON C.COURSE_ID = SP.COURSE_ID "
			+ "WHERE SP.STUDENT_ID = " + studentVO.getStudentId() + " "
			+ "AND SP.COURSE_ID = " + courseVO.getCourseId() + " "
			+ "ORDER BY START_CLASS_DATETIME";
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns    = (List<Object>)columnList;
				Long studyPlanId        = (Long) columns.get (0);
				Long studentId          = (Long) columns.get (1);
				Integer courseId        = (Integer) columns.get (2);
				String courseName       = (String) columns.get (3);
				Date startClassDatetime = (Date) columns.get (4);
				Date endClassDatetime   = (Date) columns.get (5);
				
				StudyPlanVO studyPlanVO = new StudyPlanVO();
				studyPlanVO.setStudyPlanId (studyPlanId);
				
				StudentVO newStudentVO = new StudentVO ();
				newStudentVO.setStudentId (studentId);
				
				studyPlanVO.setStudentVO (newStudentVO);
				
				CourseVO newCourseVO = new CourseVO ();
				newCourseVO.setCourseId (courseId);
				newCourseVO.setName (courseName);
				
				studyPlanVO.setCourseVO (newCourseVO);
				
				studyPlanVO.setStartClassDatetime (startClassDatetime);
				studyPlanVO.setEndClassDatetime (endClassDatetime);
										
				studyPlanList.add (studyPlanVO);
			}
		}
		return studyPlanList;
	}
	
	/**
	 * Checks if a class period already exists or not.
	 * 
	 * @param studyPlanVO the studyPlan.
	 * @return a flag indicating success or false.
	 * @throws DataAccessException
	 */
	public boolean existsClassPeriod (StudyPlanVO studyPlanVO) throws DataAccessException {
		boolean exists = false;
		int count      = 0;
		String sql     = null;		
		String startClassDatetime = new SimpleDateFormat ("dd/MM/yyyy HH:mm").format(studyPlanVO.getStartClassDatetime());
		String endClassDatetime = new SimpleDateFormat ("dd/MM/yyyy HH:mm").format(studyPlanVO.getEndClassDatetime());				
		sql = "SELECT COUNT(*) "
		    + "FROM STUDY_PLAN "
		    + "WHERE STUDENT_ID = " + studyPlanVO.getStudentVO().getStudentId() + " "
		    + "AND (START_CLASS_DATETIME BETWEEN STR_TO_DATE ('" + startClassDatetime + ":00', '%d/%m/%Y %H:%i:%s') "
		    + "AND STR_TO_DATE ('" + endClassDatetime + ":00', '%d/%m/%Y %H:%i:%s') "
		    + "OR END_CLASS_DATETIME BETWEEN STR_TO_DATE ('" + startClassDatetime + ":00', '%d/%m/%Y %H:%i:%s') "
		    + "AND STR_TO_DATE ('" + endClassDatetime + ":00', '%d/%m/%Y %H:%i:%s'))";		
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		exists = count > 0 ? true : false;
		return exists;
	}
	
}
