package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.ExerciseVO;

/**
 * DAO class for Exercise.
 * 
 * @author Josivan Silva
 *
 */
public class ExerciseDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (ExerciseDAO.class.getName());
	
	public ExerciseDAO () {
		
	}
		
	/**
	 * Inserts a new exercise.
	 * 
	 * @param exerciseVO the exercise.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public int insert (ExerciseVO exerciseVO) throws DataAccessException {
		Integer id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO EXERCISE (CLASS_ID, EXERCISE_TYPE, NAME, SEQUENCE_NUMBER) ");
		sbSql.append ("VALUES (" +  exerciseVO.getClassVO().getClassId() + ", ");
		sbSql.append (exerciseVO.getExerciseType() + ", ");
		sbSql.append ("'" +  exerciseVO.getName() + "', ");
		sbSql.append (exerciseVO.getSequenceNumber() + ")");
		logger.debug ("sql: " + sbSql.toString());		
		id = insertDbWithIntegerKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Updates an existing exercise.
	 * 
	 * @param exerciseVO the exercise.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (ExerciseVO exerciseVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE EXERCISE SET "); 
		sbSql.append ("CLASS_ID = ");
		sbSql.append (exerciseVO.getClassVO().getClassId() + ", ");
		sbSql.append ("EXERCISE_TYPE = ");
		sbSql.append (exerciseVO.getExerciseType() + ", ");
		sbSql.append ("NAME = ");
		sbSql.append ("'" + exerciseVO.getName() + "' ");
		whereClause = "WHERE CLASS_ID = " + exerciseVO.getExerciseId();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes a exercise.
	 * 
	 * @param exerciseVO the exercise.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (ExerciseVO exerciseVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM EXERCISE WHERE EXERCISE_ID = " + exerciseVO.getExerciseId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a Class by its id.
	 * 
	 * @param exerciseVO the exercise.
	 * @return a exercise.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public ExerciseVO findById (ExerciseVO exerciseVO) throws DataAccessException {
		ExerciseVO foundExerciseVO   = null;
		String sql             = null;
		List<Object> rowList   = null;
		
		sql = "SELECT EXERCISE_ID, CLASS_ID, EXERCISE_TYPE, NAME, SEQUENCE_NUMBER FROM EXERCISE WHERE EXERCISE_ID = " + exerciseVO.getExerciseId();
		rowList = selectDb (sql, 5);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns   = (List<Object>)columnList;			
				Integer exerciseId     = (Integer) columns.get (0);
				Integer classId        = (Integer) columns.get (1);
				Integer exerciseType   = (Integer) columns.get (2);
				String name            = (String) columns.get (3);
				Integer sequenceNumber = (Integer) columns.get (4);
								
				foundExerciseVO = new ExerciseVO();
				foundExerciseVO.setExerciseId (exerciseId);
				
				ClassVO classVO = new ClassVO ();
				classVO.setClassId (classId);
				
				foundExerciseVO.setClassVO (classVO);
				foundExerciseVO.setExerciseType (exerciseType);
				foundExerciseVO.setName (name);
				foundExerciseVO.setSequenceNumber (sequenceNumber);
			}
		}
		return foundExerciseVO;
	}
	
	/**
	 * Finds the exercises by class id.
	 * 
	 * @return a list of exercises.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ExerciseVO> findByClassId (ClassVO classVO) throws DataAccessException {
		String sql              = null;
		List<Object> rowList    = null;
		List<ExerciseVO> exerciseList = new ArrayList<ExerciseVO>();
		sql = "SELECT EXERCISE_ID, CLASS_ID, EXERCISE_TYPE, NAME, SEQUENCE_NUMBER FROM EXERCISE WHERE CLASS_ID = " + classVO.getClassId();
		logger.debug ("sql: " + sql);				
		rowList = selectDb (sql, 5);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;					
				Integer exerciseId     = (Integer) columns.get (0);
				Integer classId        = (Integer) columns.get (1);
				Integer exerciseType   = (Integer) columns.get (2);
				String name            = (String) columns.get (3);
				Integer sequenceNumber = (Integer) columns.get (4);
								
				ExerciseVO exerciseVO = new ExerciseVO();
				exerciseVO.setExerciseId (exerciseId);
				exerciseVO.setExerciseType (exerciseType);
								
				ClassVO newClassVO = new ClassVO ();
				newClassVO.setClassId (classId);				
				
				exerciseVO.setClassVO (newClassVO);
				exerciseVO.setName (name);
				exerciseVO.setSequenceNumber (sequenceNumber);
																									
				exerciseList.add (exerciseVO);
			}
		}
		return exerciseList;
	}
	
	/**
	 * Finds the next sequence number.
	 * 
	 * @return a sequence number.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public Integer findNextSequenceNumber () throws DataAccessException {
		Integer sequenceNumber = 0;
		String sql             = null;
		List<Object> rowList   = null;
		sql = "SELECT SEQUENCE_NUMBER AS SN FROM EXERCISE ORDER BY SEQUENCE_NUMBER DESC LIMIT 1";
		rowList = selectDb (sql, 1);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				sequenceNumber = (Integer) columns.get (0);
			}
		}
		return sequenceNumber;
	}
	
}
