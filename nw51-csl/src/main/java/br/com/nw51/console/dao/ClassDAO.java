package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassPlanVO;
import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.CourseVO;

/**
 * DAO class for Class.
 * 
 * @author Josivan Silva
 *
 */
public class ClassDAO extends AbstractDAO {
	
	static Logger logger = Logger.getLogger (ClassDAO.class.getName());
	
	public ClassDAO () {
		
	}
		
	/**
	 * Inserts a new class.
	 * 
	 * @param classVO the class.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public int insert (ClassVO classVO) throws DataAccessException {
		Integer id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CLASS (COURSE_ID, CLASS_PLAN_ID, NAME, DESCRIPTION, NOTES) ");
		sbSql.append ("VALUES (" +  classVO.getCourseVO().getCourseId() + ", ");
		sbSql.append (classVO.getClassPlanVO().getClassPlanId() + ", ");
		sbSql.append ("'" +  classVO.getName() + "', ");
		sbSql.append ("'" +  classVO.getDescription() + "', ");
		sbSql.append ("'" +  classVO.getNotes() + "')");
		id = insertDbWithIntegerKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Updates an existing class.
	 * 
	 * @param classVO the class.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (ClassVO classVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE CLASS SET "); 
		sbSql.append ("COURSE_ID = ");
		sbSql.append (classVO.getCourseVO().getCourseId() + ", ");
		sbSql.append ("CLASS_PLAN_ID = ");
		sbSql.append (classVO.getClassPlanVO().getClassPlanId() + ", ");
		sbSql.append ("NAME = ");
		sbSql.append ("'" + classVO.getName() + "', ");
		sbSql.append ("DESCRIPTION = ");
		sbSql.append ("'" + classVO.getDescription() + "', ");
		sbSql.append ("NOTES = ");
		sbSql.append ("'" + classVO.getNotes() + "' ");
		whereClause = "WHERE CLASS_ID = " + classVO.getClassId();
		sbSql.append (whereClause);				
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
		
	/**
	 * Deletes a class.
	 * 
	 * @param classVO the class.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (ClassVO classVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM CLASS WHERE CLASS_ID = " + classVO.getClassId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a Class by its id.
	 * 
	 * @param classVO the class.
	 * @return a class.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public ClassVO findById (ClassVO classVO) throws DataAccessException {
		ClassVO foundClassVO = null;
		String sql           = null;
		List<Object> rowList = null;
		sql = "SELECT CLASS_ID, COURSE_ID, CLASS_PLAN_ID, NAME, DESCRIPTION, NOTES FROM CLASS WHERE CLASS_ID = " + classVO.getClassId();
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {		
				List<Object> columns = (List<Object>)columnList;			
				Integer classId      = (Integer) columns.get (0);
				Integer courseId     = (Integer) columns.get (1);
				Integer classPlanId  = (Integer) columns.get (2);
				String name          = (String) columns.get (3);
				String description   = (String) columns.get (4);
				String notes         = (String) columns.get (5);
								
				foundClassVO = new ClassVO();
				foundClassVO.setClassId (classId);
				
				CourseVO courseVO = new CourseVO ();
				courseVO.setCourseId (courseId);
				
				foundClassVO.setCourseVO (courseVO);
				
				ClassPlanVO classPlanVO = new ClassPlanVO();
				classPlanVO.setClassPlanId (classPlanId);
				
				foundClassVO.setClassPlanVO (classPlanVO);
								
				foundClassVO.setName (name);
				foundClassVO.setDescription (description);
				foundClassVO.setNotes (notes);				
			}
		}
		return foundClassVO;
	}
	
	/**
	 * Finds the classes by course id.
	 * 
	 * @return a list of classes.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ClassVO> findByCourseId (CourseVO courseVO) throws DataAccessException {
		String sql              = null;
		List<Object> rowList    = null;
		List<ClassVO> classList = new ArrayList<ClassVO>();		
		sql = "SELECT C.CLASS_ID, C.COURSE_ID, C.CLASS_PLAN_ID, C.NAME, C.DESCRIPTION, C.NOTES "
			  + "FROM CLASS C "
			  + "INNER JOIN CLASS_PLAN CP ON CP.CLASS_PLAN_ID = C.CLASS_PLAN_ID "
			  + "WHERE C.COURSE_ID = " + courseVO.getCourseId();				
		logger.debug ("sql " + sql);				
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;
				Integer classId      = (Integer) columns.get (0);
				Integer courseId     = (Integer) columns.get (1);
				Integer classPlanId  = (Integer) columns.get (2);
				String name          = (String) columns.get (3);
				String description   = (String) columns.get (4);
				String notes         = (String) columns.get (5);
								
				ClassVO classVO = new ClassVO();
				classVO.setClassId (classId);
				
				CourseVO newCourseVO = new CourseVO ();
				newCourseVO.setCourseId (courseId);
				
				classVO.setCourseVO (newCourseVO);
				
				ClassPlanVO classPlanVO = new ClassPlanVO();
				classPlanVO.setClassPlanId (classPlanId);
								
				classVO.setClassPlanVO (classPlanVO);
								
				classVO.setName (name);
				classVO.setDescription (description);
				classVO.setNotes (notes);
																									
				classList.add (classVO);
			}
		}
		return classList;
	}
	
	/**
	 * Gets the count of classes given a course.
	 * 
	 * @param classVO the class.
	 * @return the count of classes.
	 * @throws DataAccessException
	 */
	public int countClassesByCourse (ClassVO classVO) throws DataAccessException {
		int count = 0;
		String sql = null;		
		sql = "SELECT COUNT(*) FROM CLASS WHERE COURSE_ID = " + classVO.getCourseVO().getCourseId();
		count = selectRowCount (sql);
		return count;
	}
	
}
