package br.com.nw51.student.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.CourseVO;
import br.com.nw51.student.dao.CourseDAO;
import br.com.nw51.student.dao.DataAccessException;

/**
 * Business Service class for Course.
 * 
 * @author Josivan Silva
 *
 */
public class CourseService {
	
	static Logger logger = Logger.getLogger (CourseService.class.getName());
	private CourseDAO courseDAO = new CourseDAO();
			
	public CourseService() {
		
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
	
}
