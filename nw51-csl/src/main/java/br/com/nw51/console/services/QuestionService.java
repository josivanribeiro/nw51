package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.ExerciseVO;
import br.com.nw51.common.vo.QuestionVO;
import br.com.nw51.console.dao.ClassDAO;
import br.com.nw51.console.dao.CourseDAO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.ExerciseDAO;
import br.com.nw51.console.dao.QuestionDAO;

/**
 * Business Service class for Question.
 * 
 * @author Josivan Silva
 *
 */
public class QuestionService {

	static Logger logger = Logger.getLogger (QuestionService.class.getName());
	
	private CourseDAO courseDAO = new CourseDAO();
	private ClassDAO classDAO = new ClassDAO();
	private ExerciseDAO exerciseDAO = new ExerciseDAO();
	private QuestionDAO questionDAO = new QuestionDAO();	
			
	public QuestionService() {
		
	}
	
	/**
	 * Inserts a new question.
	 * 
	 * @param questionVO the question.
	 * @return the question id.
	 * @throws BusinessException
	 */
	public Integer insert (QuestionVO questionVO) throws BusinessException {
		Integer newQuestionId = 0;
		try {
			newQuestionId = questionDAO.insert (questionVO);
			
			ExerciseVO exerciseVO = exerciseDAO.findById (questionVO.getExerciseVO());
			
			ClassVO classVO = classDAO.findById (exerciseVO.getClassVO());
			
			courseDAO.updateLastUpdateDate (classVO.getCourseVO());
			
		} catch (DataAccessException e) {
				String errorMessage = "A business exception error occurred while inserting the question. " + e.getMessage();
				logger.error (errorMessage);
				throw new BusinessException (errorMessage, e.getCause());
		}
		return newQuestionId;
	}
	
	/**
	 * Updates a question.
	 * 
	 * @param questionVO the question.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (QuestionVO questionVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = questionDAO.update (questionVO);
			
			ExerciseVO exerciseVO = exerciseDAO.findById (questionVO.getExerciseVO());
			
			ClassVO classVO = classDAO.findById (exerciseVO.getClassVO());
			
			courseDAO.updateLastUpdateDate (classVO.getCourseVO());
			
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the question. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Deletes an question.
	 * 
	 * @param questionVO the question.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (QuestionVO questionVO) throws BusinessException {
		int affectedRows = 0;
		try {	
			affectedRows = questionDAO.delete (questionVO);
			
			ExerciseVO exerciseVO = exerciseDAO.findById (questionVO.getExerciseVO());
			
			ClassVO classVO = classDAO.findById (exerciseVO.getClassVO());
			
			courseDAO.updateLastUpdateDate (classVO.getCourseVO());
			
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the question. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds an question by its id.
	 * 
	 * @param QuestionVO the question.
	 * @return the found question.
	 * @throws BusinessException
	 */
	public QuestionVO findById (QuestionVO questionVO) throws BusinessException {
		QuestionVO foundQuestionVO = null;
		try {
			foundQuestionVO = questionDAO.findById (questionVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the question by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}		
		return foundQuestionVO;
	}
	
	/**
	 * Finds the questions by exercise id.
	 * 
	 * @return a list of questions.
	 * @throws BusinessException
	 */
	public List<QuestionVO> findQuestionsByExerciseId (ExerciseVO exerciseVO) throws BusinessException {
		List<QuestionVO> questionVOList = null;
		try {
			questionVOList = questionDAO.findByExerciseId (exerciseVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the questions by exercise id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return questionVOList;
	}
	
}
