package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ExerciseVO;
import br.com.nw51.common.vo.QuestionVO;

/**
 * DAO class for Question.
 * 
 * @author Josivan Silva
 *
 */
public class QuestionDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (QuestionDAO.class.getName());
	
	public QuestionDAO () {
		
	}
		
	/**
	 * Inserts a new question.
	 * 
	 * @param questionVO the question.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public int insert (QuestionVO questionVO) throws DataAccessException {
		Integer id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO QUESTION (EXERCISE_ID, ENUNCIATION, ALTERNATIVE_A, ALTERNATIVE_B, ALTERNATIVE_C, ALTERNATIVE_D, ALTERNATIVE_E, ALTERNATIVE_CORRECT) ");
		sbSql.append ("VALUES (" +  questionVO.getExerciseVO().getExerciseId() + ", ");
		sbSql.append ("'" + questionVO.getEnunciation() + "', ");
		sbSql.append ("'" +  questionVO.getAlternativeA() + "', ");
		sbSql.append ("'" +  questionVO.getAlternativeB() + "', ");
		sbSql.append ("'" +  questionVO.getAlternativeC() + "', ");
		sbSql.append ("'" +  questionVO.getAlternativeD() + "', ");
		sbSql.append ("'" +  questionVO.getAlternativeE() + "', ");
		sbSql.append ("'" +  questionVO.getAlternativeCorrect() + "')");
		logger.debug ("sql: " + sbSql.toString());		
		id = insertDbWithIntegerKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Updates an existing question.
	 * 
	 * @param questionVO the question.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (QuestionVO questionVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE QUESTION SET "); 
		sbSql.append ("EXERCISE_ID = ");
		sbSql.append (questionVO.getExerciseVO().getExerciseId() + ", ");
		sbSql.append ("ENUNCIATION = ");
		sbSql.append ("'" + questionVO.getEnunciation() + "', ");
		sbSql.append ("ALTERNATIVE_A = ");
		sbSql.append ("'" + questionVO.getAlternativeA() + "', ");
		sbSql.append ("ALTERNATIVE_B = ");
		sbSql.append ("'" + questionVO.getAlternativeB() + "', ");
		sbSql.append ("ALTERNATIVE_C = ");
		sbSql.append ("'" + questionVO.getAlternativeC() + "', ");
		sbSql.append ("ALTERNATIVE_D = ");
		sbSql.append ("'" + questionVO.getAlternativeD() + "', ");
		sbSql.append ("ALTERNATIVE_E = ");
		sbSql.append ("'" + questionVO.getAlternativeE() + "', ");
		sbSql.append ("ALTERNATIVE_CORRECT = ");
		sbSql.append ("'" + questionVO.getAlternativeCorrect() + "' ");
		whereClause = "WHERE QUESTION_ID = " + questionVO.getQuestionId();
		sbSql.append (whereClause);		
		logger.debug ("sql: " + sbSql.toString());		
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes a question.
	 * 
	 * @param questionVO the question.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (QuestionVO questionVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM QUESTION WHERE QUESTION_ID = " + questionVO.getQuestionId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a Class by its id.
	 * 
	 * @param questionVO the question.
	 * @return a question.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public QuestionVO findById (QuestionVO questionVO) throws DataAccessException {
		QuestionVO foundQuestionVO = null;
		String sql                 = null;
		List<Object> rowList       = null;
		sql = "SELECT QUESTION_ID, EXERCISE_ID, ENUNCIATION, ALTERNATIVE_A, ALTERNATIVE_B, ALTERNATIVE_C, ALTERNATIVE_D, ALTERNATIVE_E, ALTERNATIVE_CORRECT FROM QUESTION WHERE QUESTION_ID = " + questionVO.getQuestionId();
		logger.debug ("sql: " + sql.toString());		
		rowList = selectDb (sql, 9);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {				
				List<Object> columns      = (List<Object>)columnList;			
				Integer questionId        = (Integer) columns.get (0);
				Integer exerciseId        = (Integer) columns.get (1);
				String enunciation        = (String) columns.get (2);
				String alternativeA       = (String) columns.get (3);
				String alternativeB       = (String) columns.get (4);
				String alternativeC       = (String) columns.get (5);
				String alternativeD       = (String) columns.get (6);
				String alternativeE       = (String) columns.get (7);
				String alternativeCorrect = (String) columns.get (8);
								
				foundQuestionVO = new QuestionVO();
				foundQuestionVO.setQuestionId (questionId);
				
				ExerciseVO exerciseVO = new ExerciseVO ();
				exerciseVO.setExerciseId (exerciseId);
				
				foundQuestionVO.setExerciseVO (exerciseVO);
				foundQuestionVO.setEnunciation (enunciation);
				foundQuestionVO.setAlternativeA (alternativeA);
				foundQuestionVO.setAlternativeB (alternativeB);
				foundQuestionVO.setAlternativeC (alternativeC);
				foundQuestionVO.setAlternativeD (alternativeD);
				foundQuestionVO.setAlternativeE (alternativeE);
				foundQuestionVO.setAlternativeCorrect (alternativeCorrect);								
			}
		}
		return foundQuestionVO;
	}
	
	/**
	 * Finds the questions by exercise id.
	 * 
	 * @return a list of questions.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<QuestionVO> findByExerciseId (ExerciseVO exerciseVO) throws DataAccessException {
		String sql              = null;
		List<Object> rowList    = null;
		List<QuestionVO> questionList = new ArrayList<QuestionVO>();
		sql = "SELECT QUESTION_ID, EXERCISE_ID, ENUNCIATION, ALTERNATIVE_A, ALTERNATIVE_B, ALTERNATIVE_C, ALTERNATIVE_D, ALTERNATIVE_E, ALTERNATIVE_CORRECT FROM QUESTION WHERE EXERCISE_ID = " + exerciseVO.getExerciseId();
		logger.debug ("sql: " + sql);				
		rowList = selectDb (sql, 9);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;					
				Integer questionId        = (Integer) columns.get (0);
				Integer exerciseId        = (Integer) columns.get (1);
				String enunciation        = (String) columns.get (2);
				String alternativeA       = (String) columns.get (3);
				String alternativeB       = (String) columns.get (4);
				String alternativeC       = (String) columns.get (5);
				String alternativeD       = (String) columns.get (6);
				String alternativeE       = (String) columns.get (7);
				String alternativeCorrect = (String) columns.get (8);
								
				QuestionVO questionVO = new QuestionVO();
				questionVO.setQuestionId (questionId);
				
				ExerciseVO newExerciseVO = new ExerciseVO ();
				newExerciseVO.setExerciseId (exerciseId);
				
				questionVO.setExerciseVO (newExerciseVO);
				questionVO.setEnunciation (enunciation);
				questionVO.setAlternativeA (alternativeA);
				questionVO.setAlternativeB (alternativeB);
				questionVO.setAlternativeC (alternativeC);
				questionVO.setAlternativeD (alternativeD);
				questionVO.setAlternativeE (alternativeE);
				questionVO.setAlternativeCorrect (alternativeCorrect);
																					
				questionList.add (questionVO);
			}
		}
		return questionList;
	}
	
}
