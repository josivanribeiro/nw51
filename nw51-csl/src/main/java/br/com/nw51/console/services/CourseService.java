package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.ContentItemVO;
import br.com.nw51.common.vo.CourseFilterVO;
import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.common.vo.TeachingPlanVO;
import br.com.nw51.console.dao.ClassDAO;
import br.com.nw51.console.dao.ContentItemDAO;
import br.com.nw51.console.dao.CourseDAO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.TeachingPlanDAO;

/**
 * Business Service class for Course.
 * 
 * @author Josivan Silva
 *
 */
public class CourseService {
	
	static Logger logger = Logger.getLogger (CourseService.class.getName());
	private CourseDAO courseDAO = new CourseDAO();
	private TeachingPlanDAO teachingPlanDAO = new TeachingPlanDAO();
	private ClassDAO classDAO = new ClassDAO();
	private ContentItemDAO contentItemDAO = new ContentItemDAO();
		
	public CourseService() {
		
	}
	
	/**
	 * Inserts a new course.
	 * 
	 * @param courseVO the course.
	 * @return the course id.
	 * @throws BusinessException
	 */
	public Integer insert (CourseVO courseVO) throws BusinessException {
		Integer newCourseId = 0;
		Integer teachingPlanId = 0;		
		try {
			teachingPlanId = teachingPlanDAO.insert (courseVO.getTeachingPlanVO());
			logger.debug ("new teachingPlanId [" + teachingPlanId + "]");			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the teachingPlan. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		try {			
			if (teachingPlanId > 0) {
				courseVO.getTeachingPlanVO().setTeachingPlanId (teachingPlanId);
				newCourseId = courseDAO.insert (courseVO);
				
				updateTeachingPlanIdInContentItemVOList (courseVO.getTeachingPlanVO());
				
				for (ContentItemVO contentItemVO : courseVO.getTeachingPlanVO().getContentItemVOList()) {
					contentItemDAO.insert (contentItemVO);
				}
				
				logger.debug ("newCourseId [" + newCourseId + "]");
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the course/contentItems. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return newCourseId;
	}
		
	
	/**
	 * Updates a course.
	 * 
	 * @param courseVO the course.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (CourseVO courseVO) throws BusinessException {
		int teachingPlanAffectedRows = 0;
		int courseAffectedRows = 0;
		try {
			teachingPlanAffectedRows = teachingPlanDAO.update (courseVO.getTeachingPlanVO());
			logger.debug ("teachingPlanAffectedRows [" + teachingPlanAffectedRows + "]");			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the teachingPlan. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		try {
			if (teachingPlanAffectedRows > 0) {
				
				contentItemDAO.deleteByTeachingPlan (courseVO.getTeachingPlanVO());
				
				updateTeachingPlanIdInContentItemVOList (courseVO.getTeachingPlanVO());
				
				for (ContentItemVO contentItemVO : courseVO.getTeachingPlanVO().getContentItemVOList()) {
					contentItemDAO.insert (contentItemVO);
				}
				
				courseAffectedRows = courseDAO.update (courseVO);
				logger.debug ("courseAffectedRows [" + courseAffectedRows + "]");
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the course/contentItems. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return courseAffectedRows;
	}
	
	/**
	 * Deletes a course.
	 * 
	 * @param courseVO the course.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (CourseVO courseVO) throws BusinessException {
		int courseAffectedRows = 0;
		int teachingPlanAffectedRows = 0;
		try {
			courseAffectedRows = courseDAO.delete (courseVO);
			logger.debug ("courseAffectedRows [" + courseAffectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the course. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		try {
			if (courseAffectedRows > 0) {
				
				contentItemDAO.deleteByTeachingPlan (courseVO.getTeachingPlanVO());
				
				teachingPlanAffectedRows = teachingPlanDAO.delete (courseVO.getTeachingPlanVO());
				logger.debug ("teachingPlanAffectedRows [" + teachingPlanAffectedRows + "]");
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the teachingPlan/contentItems. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return courseAffectedRows;
	}
	
	/**
	 * Finds a course by its id.
	 * 
	 * @param CourseVO the course.
	 * @return the found course.
	 * @throws BusinessException
	 */
	public CourseVO findById (CourseVO courseVO) throws BusinessException {
		CourseVO foundCourseVO = null;
		TeachingPlanVO foundTeachingPlanVO = null;
		try {
			foundCourseVO = courseDAO.findById (courseVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the course by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		try {
			foundTeachingPlanVO = teachingPlanDAO.findById (foundCourseVO.getTeachingPlanVO());
			if (foundTeachingPlanVO != null) {				
				List<ContentItemVO> contentItemVOList = contentItemDAO.findByTeachingPlan (foundTeachingPlanVO);
				foundTeachingPlanVO.setContentItemVOList (contentItemVOList);				
				foundCourseVO.setTeachingPlanVO (foundTeachingPlanVO);
			}
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the teachingPlan by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundCourseVO;
	}
	
	/**
	 * Finds the courses by filter.
	 * 
	 * @return a list of courses.
	 * @throws BusinessException
	 */
	public List<CourseVO> findByFilter (CourseFilterVO courseFilterVO) throws BusinessException {
		List<CourseVO> courseVOList = null;
		try {
			courseVOList = courseDAO.findByFilter (courseFilterVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the courses by filter. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return courseVOList;
	}
	
	/**
	 * Finds all the courses.
	 * 
	 * @return a list of courses.
	 * @throws BusinessException
	 */
	public List<CourseVO> findAll () throws BusinessException {
		List<CourseVO> courseVOList = null;
		try {
			courseVOList = courseDAO.findAll ();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding all the courses. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return courseVOList;
	}
	
	/**
	 * Finds the classes by course id.
	 * 
	 * @return a list of classes.
	 * @throws BusinessException
	 */
	public List<ClassVO> findClassesByCourseId (CourseVO courseVO) throws BusinessException {
		List<ClassVO> classVOList = null;
		try {
			classVOList = classDAO.findByCourseId (courseVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the classes by course id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return classVOList;
	}
	
	/**
	 * Gets the row count of courses.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = courseDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count of courses. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
	/**
	 * Updates the teachingPlanId in the contentItemVO list.
	 * @param teachingPlanVO the teachingPlan.
	 */
	private void updateTeachingPlanIdInContentItemVOList (TeachingPlanVO teachingPlanVO) {
		for (ContentItemVO contentItemVO : teachingPlanVO.getContentItemVOList()) {
			contentItemVO.setTeachingPlanVO (teachingPlanVO);
		}
	}
	
}
