package br.com.nw51.student.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.TeacherVO;

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
	
}
