package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.ExerciseVO;
import br.com.nw51.console.dao.ClassDAO;
import br.com.nw51.console.dao.CourseDAO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.ExerciseDAO;

/**
 * Business Service exercise for Class.
 * 
 * @author Josivan Silva
 *
 */
public class ExerciseService {

	static Logger logger = Logger.getLogger (ExerciseService.class.getName());
	private CourseDAO courseDAO = new CourseDAO();
	private ClassDAO classDAO = new ClassDAO();
	private ExerciseDAO exerciseDAO = new ExerciseDAO();
	
	private ClassService classService = new ClassService();
			
	public ExerciseService() {
		
	}
	
	/**
	 * Inserts a new exercise.
	 * 
	 * @param exerciseVO the exercise.
	 * @return the exercise id.
	 * @throws BusinessException
	 */
	public Integer insert (ExerciseVO exerciseVO) throws BusinessException {
		Integer newExerciseId = 0;
		try {
			
			Integer sequenceNumber = classService.findNextSequenceNumber();
			exerciseVO.setSequenceNumber (sequenceNumber);
			
			newExerciseId = exerciseDAO.insert (exerciseVO);
			
			ClassVO classVO = classDAO.findById (exerciseVO.getClassVO());
					
			courseDAO.updateLastUpdateDate (classVO.getCourseVO());
			
		} catch (DataAccessException e) {
				String errorMessage = "A business exception error occurred while inserting the exercise. " + e.getMessage();
				logger.error (errorMessage);
				throw new BusinessException (errorMessage, e.getCause());
		}
		return newExerciseId;
	}
	
	/**
	 * Updates an exercise.
	 * 
	 * @param exerciseVO the exercise.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (ExerciseVO exerciseVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = exerciseDAO.update (exerciseVO);
			
			ClassVO classVO = classDAO.findById (exerciseVO.getClassVO());
			
			courseDAO.updateLastUpdateDate (classVO.getCourseVO());
			
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the exercise. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Deletes an exercise.
	 * 
	 * @param exerciseVO the exercise.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (ExerciseVO exerciseVO) throws BusinessException {
		int affectedRows = 0;
		try {	
			affectedRows = exerciseDAO.delete (exerciseVO);
			
			ClassVO classVO = classDAO.findById (exerciseVO.getClassVO());
			
			courseDAO.updateLastUpdateDate (classVO.getCourseVO());
			
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the exercise. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds an exercise by its id.
	 * 
	 * @param ExerciseVO the exercise.
	 * @return the found exercise.
	 * @throws BusinessException
	 */
	public ExerciseVO findById (ExerciseVO exerciseVO) throws BusinessException {
		ExerciseVO foundExerciseVO = null;
		try {
			foundExerciseVO = exerciseDAO.findById (exerciseVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the exercise by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return foundExerciseVO;
	}
	
	/**
	 * Finds the exercises by class id.
	 * 
	 * @return a list of exercises.
	 * @throws BusinessException
	 */
	public List<ExerciseVO> findExercisesByClassId (ClassVO classVO) throws BusinessException {
		List<ExerciseVO> exerciseVOList = null;
		try {
			exerciseVOList = exerciseDAO.findByClassId (classVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the exercises by class id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return exerciseVOList;
	}
	
}
