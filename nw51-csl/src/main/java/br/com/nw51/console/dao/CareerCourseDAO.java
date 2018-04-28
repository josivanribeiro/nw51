package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.CareerCourseVO;
import br.com.nw51.common.vo.CareerVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.TeacherVO;

/**
 * DAO Class for Class Plan Tool.
 * 
 * @author Josivan Silva
 *
 */
public class CareerCourseDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (CareerCourseDAO.class.getName());
	
	public CareerCourseDAO () {
		
	}
		
	/**
	 * Inserts a new careerCourse.
	 * 
	 * @param careerCourseVO the careerCourse.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public Integer insert (CareerCourseVO careerCourseVO) throws DataAccessException {
		Integer id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CAREER_COURSE (CAREER_ID, COURSE_ID, SEQUENCE_NUMBER) ");
		sbSql.append ("VALUES (" +  careerCourseVO.getCareerVO().getCareerId() + ", ");
		sbSql.append (careerCourseVO.getCourseVO().getCourseId() + ", ");
		sbSql.append (careerCourseVO.getSequenceNumber() + ")");
		logger.debug ("sql: " + sbSql.toString());
		id = insertDbWithIntegerKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Deletes a careerCourse.
	 * 
	 * @param careerVO the careerVO.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int deleteByCareer (CareerVO careerVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM CAREER_COURSE WHERE CAREER_ID = " + careerVO.getCareerId();
		logger.debug ("sql: " + sql);
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Finds a list of careerCourse by career id.
	 * 
	 * @param careerVO the careerVO.
	 * @return a list of careerCourse.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<CareerCourseVO> findByCareer (CareerVO careerVO) throws DataAccessException {
		String sql           = null;
		List<Object> rowList = null;
		List<CareerCourseVO> careerCourseList = new ArrayList<CareerCourseVO>();
		sql = "SELECT CC.CAREER_ID, C.NAME, CC.SEQUENCE_NUMBER, CC.COURSE_ID, C.COURSE_TYPE, T.FULL_NAME "
			+ "FROM CAREER_COURSE CC "
			+ "INNER JOIN COURSE C ON C.COURSE_ID = CC.COURSE_ID "
			+ "INNER JOIN TEACHER T ON C.TEACHER_ID = T.TEACHER_ID "
			+ "WHERE CAREER_ID = " + careerVO.getCareerId();
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Integer careerId       = (Integer) columns.get (0);
				String name            = (String) columns.get (1);
				Integer sequenceNumber = (Integer) columns.get (2);
				Integer courseId       = (Integer) columns.get (3);
				Integer courseType     = (Integer) columns.get (4);
				String teacherFullName = (String) columns.get (5);
				
				CareerCourseVO newCareerCourseVO = new CareerCourseVO();
				CareerVO newCareerVO = new CareerVO ();
				newCareerVO.setCareerId (careerId);
				newCareerCourseVO.setCareerVO (newCareerVO);
				newCareerCourseVO.setSequenceNumber (sequenceNumber);
				
				CourseVO courseVO = new CourseVO ();
				courseVO.setCourseId (courseId);
				courseVO.setName (name);
				courseVO.setCourseType (courseType);
				
				TeacherVO teacherVO = new TeacherVO();
				teacherVO.setFullName (teacherFullName);
				courseVO.setTeacherVO (teacherVO);
				
				newCareerCourseVO.setCourseVO (courseVO);
								
				careerCourseList.add (newCareerCourseVO);
			}
		}
		return careerCourseList;
	}
	
}
