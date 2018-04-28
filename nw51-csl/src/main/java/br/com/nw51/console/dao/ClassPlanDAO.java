package br.com.nw51.console.dao;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassPlanVO;

/**
 * DAO Class for Class Plan.
 * 
 * @author Josivan Silva
 *
 */
public class ClassPlanDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (ClassPlanDAO.class.getName());
	
	public ClassPlanDAO () {
		
	}
		
	/**
	 * Inserts a new classPlan.
	 * 
	 * @param classPlanVO the classPlan.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public Integer insert (ClassPlanVO classPlanVO) throws DataAccessException {
		Integer id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CLASS_PLAN (CLASS_PLAN_ID, AVERAGE_TIME, SKILLS) ");
		sbSql.append ("VALUES (" +  classPlanVO.getClassPlanId() + ", ");
		sbSql.append (classPlanVO.getAverageTime() + ", ");
		sbSql.append ("'" +  classPlanVO.getSkills() + "')");
		id = insertDbWithIntegerKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Updates an existing classPlan.
	 * 
	 * @param classPlanVO the classPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (ClassPlanVO classPlanVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE CLASS_PLAN SET ");
		sbSql.append ("AVERAGE_TIME = ");
		sbSql.append (classPlanVO.getAverageTime() + ", ");
		sbSql.append ("SKILLS = ");
		sbSql.append ("'" + classPlanVO.getSkills() + "' ");
		whereClause = " WHERE CLASS_PLAN_ID = " + classPlanVO.getClassPlanId();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		logger.debug ("sql: " + sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Updates the average time of a class plan.
	 * 
	 * @param classPlanVO the classPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateAverageTime (ClassPlanVO classPlanVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE CLASS_PLAN SET ");
		sbSql.append ("AVERAGE_TIME = ");
		sbSql.append (classPlanVO.getAverageTime());
		whereClause = " WHERE CLASS_PLAN_ID = " + classPlanVO.getClassPlanId();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		logger.debug ("sql: " + sbSql.toString());		
		return affectedRows;
	}
	
	/**
	 * Deletes a classPlan.
	 * 
	 * @param classPlanVO the classPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (ClassPlanVO classPlanVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM CLASS_PLAN WHERE CLASS_PLAN_ID = " + classPlanVO.getClassPlanId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a classPlan by its id.
	 * 
	 * @param classPlanVO the classPlan.
	 * @return a classPlan.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public ClassPlanVO findById (ClassPlanVO classPlanVO) throws DataAccessException {
		ClassPlanVO foundClassPlanVO = null;
		String sql                   = null;
		List<Object> rowList         = null;		
		sql = "SELECT CLASS_PLAN_ID, AVERAGE_TIME, SKILLS FROM CLASS_PLAN WHERE CLASS_PLAN_ID = " + classPlanVO.getClassPlanId();
		logger.debug ("sql: " + sql);		
		rowList = selectDb (sql, 3);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {				
				List<Object> columns = (List<Object>)columnList;			
				Integer classPlanId  = (Integer) columns.get (0);
				Float averageTime    = (Float) columns.get (1);
				String skills        = (String) columns.get (2);
								
				foundClassPlanVO = new ClassPlanVO();
				foundClassPlanVO.setClassPlanId (classPlanId);
				foundClassPlanVO.setAverageTime (averageTime);
				foundClassPlanVO.setSkills (skills);
			}
		}
		return foundClassPlanVO;
	}
	
}
