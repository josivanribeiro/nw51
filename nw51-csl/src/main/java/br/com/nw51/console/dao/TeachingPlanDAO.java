package br.com.nw51.console.dao;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.TeachingPlanVO;

/**
 * DAO Class for TeachingPlan.
 * 
 * @author Josivan Silva
 *
 */
public class TeachingPlanDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (TeachingPlanDAO.class.getName());
	
	public TeachingPlanDAO () {
		
	}
		
	/**
	 * Inserts a new teachingPlan.
	 * 
	 * @param teachingPlanVO the teachingPlan.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public Integer insert (TeachingPlanVO teachingPlanVO) throws DataAccessException {
		Integer id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO TEACHING_PLAN (WORKLOAD_HOURS_THEORY_TYPE, WORKLOAD_HOURS_PRACTICE_TYPE, SUMMARY, GENERAL_GOAL, SPECIFIC_GOALS, TEACHING_STRATEGY_TYPE, RESOURCES, EVALUATION, BIBLIOGRAPHY) ");
		sbSql.append ("VALUES (" +  teachingPlanVO.getWorkloadHoursTheoryType() + ", ");
		sbSql.append (teachingPlanVO.getWorkloadHoursPracticeType() + ", ");
		sbSql.append ("'" +  teachingPlanVO.getSummary() + "', ");
		sbSql.append ("'" +  teachingPlanVO.getGeneralGoal() + "', ");
		sbSql.append ("'" +  teachingPlanVO.getSpecificGoals() + "', ");
		sbSql.append (teachingPlanVO.getTeachingStrategyType() + ", ");
		sbSql.append ("'" +  teachingPlanVO.getResources() + "', ");
		sbSql.append ("'" +  teachingPlanVO.getEvaluation() + "', ");
		sbSql.append ("'" +  teachingPlanVO.getBibliography() + "')");
		id = insertDbWithIntegerKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Updates an existing teachingPlan.
	 * 
	 * @param teachingPlanVO the teachingPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (TeachingPlanVO teachingPlanVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE TEACHING_PLAN SET ");
		sbSql.append ("WORKLOAD_HOURS_THEORY_TYPE = ");
		sbSql.append (teachingPlanVO.getWorkloadHoursTheoryType() + ", ");
		sbSql.append ("WORKLOAD_HOURS_PRACTICE_TYPE = ");
		sbSql.append (teachingPlanVO.getWorkloadHoursPracticeType() + ", ");
		sbSql.append ("SUMMARY = ");
		sbSql.append ("'" + teachingPlanVO.getSummary() + "', ");
		sbSql.append ("GENERAL_GOAL = ");
		sbSql.append ("'" + teachingPlanVO.getGeneralGoal() + "', ");
		sbSql.append ("SPECIFIC_GOALS = ");
		sbSql.append ("'" + teachingPlanVO.getSpecificGoals() + "', ");
		sbSql.append ("TEACHING_STRATEGY_TYPE = ");
		sbSql.append (teachingPlanVO.getTeachingStrategyType() + ", ");
		sbSql.append ("RESOURCES = ");
		sbSql.append ("'" + teachingPlanVO.getResources() + "', ");
		sbSql.append ("EVALUATION = ");
		sbSql.append ("'" + teachingPlanVO.getEvaluation() + "', ");
		sbSql.append ("BIBLIOGRAPHY = ");
		sbSql.append ("'" + teachingPlanVO.getBibliography() + "' ");		
		whereClause = " WHERE TEACHING_PLAN_ID = " + teachingPlanVO.getTeachingPlanId();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		logger.debug ("sql: " + sbSql.toString());		
		return affectedRows;
	}
	
	/**
	 * Deletes a teachingPlan.
	 * 
	 * @param teachingPlanVO the teachingPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (TeachingPlanVO teachingPlanVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM TEACHING_PLAN WHERE TEACHING_PLAN_ID = " + teachingPlanVO.getTeachingPlanId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a teachingPlan by its id.
	 * 
	 * @param teachingPlanVO the teachingPlan.
	 * @return a teachingPlan.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public TeachingPlanVO findById (TeachingPlanVO teachingPlanVO) throws DataAccessException {
		TeachingPlanVO foundTeachingPlanVO = null;
		String sql                   = null;
		List<Object> rowList         = null;		
		sql = "SELECT TEACHING_PLAN_ID, WORKLOAD_HOURS_THEORY_TYPE, WORKLOAD_HOURS_PRACTICE_TYPE, SUMMARY, "
			+ "GENERAL_GOAL, SPECIFIC_GOALS, TEACHING_STRATEGY_TYPE, RESOURCES, EVALUATION, BIBLIOGRAPHY "
			+ "FROM TEACHING_PLAN WHERE TEACHING_PLAN_ID = " + teachingPlanVO.getTeachingPlanId();
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 10);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns    = (List<Object>)columnList;			
				Integer teachingPlanId        = (Integer) columns.get (0);
				int workloadHoursTheoryType   = (Integer) columns.get (1);
				int workloadHoursPracticeType = (Integer) columns.get (2);
				String summary                = (String) columns.get (3);
				String generalGoal            = (String) columns.get (4);
				String specificGoals          = (String) columns.get (5);
				int teachingStrategyType      = (Integer) columns.get (6);
				String resources              = (String) columns.get (7);
				String evaluation             = (String) columns.get (8);
				String bibliography           = (String) columns.get (9);
				
				foundTeachingPlanVO = new TeachingPlanVO();
				foundTeachingPlanVO.setTeachingPlanId (teachingPlanId);
				foundTeachingPlanVO.setWorkloadHoursTheoryType (workloadHoursTheoryType);
				foundTeachingPlanVO.setWorkloadHoursPracticeType (workloadHoursPracticeType);
				foundTeachingPlanVO.setSummary (summary);
				foundTeachingPlanVO.setGeneralGoal (generalGoal);
				foundTeachingPlanVO.setSpecificGoals (specificGoals);
				foundTeachingPlanVO.setTeachingStrategyType (teachingStrategyType);
				foundTeachingPlanVO.setResources (resources);
				foundTeachingPlanVO.setEvaluation (evaluation);
				foundTeachingPlanVO.setBibliography (bibliography);					
			}
		}
		return foundTeachingPlanVO;
	}
	
}
