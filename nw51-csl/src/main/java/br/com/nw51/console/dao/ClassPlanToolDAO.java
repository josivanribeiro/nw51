package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassPlanToolVO;
import br.com.nw51.common.vo.ClassPlanVO;
import br.com.nw51.common.vo.ToolVO;

/**
 * DAO Class for Class Plan Tool.
 * 
 * @author Josivan Silva
 *
 */
public class ClassPlanToolDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (ClassPlanToolDAO.class.getName());
	
	public ClassPlanToolDAO () {
		
	}
		
	/**
	 * Inserts a new classPlanTool.
	 * 
	 * @param classPlanToolVO the classPlanTool.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public Integer insert (ClassPlanToolVO classPlanToolVO) throws DataAccessException {
		Integer id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CLASS_PLAN_TOOL (CLASS_PLAN_ID, TOOL_ID) ");
		sbSql.append ("VALUES (" +  classPlanToolVO.getClassPlanVO().getClassPlanId() + ", ");
		sbSql.append (classPlanToolVO.getToolVO().getToolId() + ")");
		id = insertDbWithIntegerKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Deletes a classPlanTool.
	 * 
	 * @param classPlanlVO the classPlanlVO.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int deleteByClassPlan (ClassPlanVO classPlanlVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM CLASS_PLAN_TOOL WHERE CLASS_PLAN_ID = " + classPlanlVO.getClassPlanId();
		logger.debug ("sql: " + sql);
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Finds a list of classPlanTool by class plan id.
	 * 
	 * @param classPlanVO the classPlanVO.
	 * @return a list of classPlanTool.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ClassPlanToolVO> findByClassPlan (ClassPlanVO classPlanVO) throws DataAccessException {
		String sql           = null;
		List<Object> rowList = null;
		List<ClassPlanToolVO> classPlanToolList = new ArrayList<ClassPlanToolVO>();
		sql = "SELECT CLASS_PLAN_ID, TOOL_ID FROM CLASS_PLAN_TOOL WHERE CLASS_PLAN_ID = " + classPlanVO.getClassPlanId();
		rowList = selectDb (sql, 2);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Integer classPlanId = (Integer) columns.get (0);
				Integer toolId      = (Integer) columns.get (1);				
				
				ClassPlanVO newClassPlanVO = new ClassPlanVO();
				newClassPlanVO.setClassPlanId (classPlanId);
				
				ToolVO toolVO = new ToolVO();
				toolVO.setToolId (toolId);
				
				ClassPlanToolVO classPlanToolVO = new ClassPlanToolVO();
								
				classPlanToolVO.setClassPlanVO (newClassPlanVO);
				classPlanToolVO.setToolVO (toolVO);
				
				classPlanToolList.add (classPlanToolVO);
			}
		}
		return classPlanToolList;
	}
	
	/**
	 * Finds all the tools with the selected, given the classPlan.
	 * 
	 * @return a list of tools.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ToolVO> findAllWithSelected (ClassPlanVO classPlanVO) throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<ToolVO> toolList = new ArrayList<ToolVO>();
		sql = "SELECT T.TOOL_ID, T.NAME, "
			+ "(SELECT COUNT(*) FROM CLASS_PLAN_TOOL WHERE CLASS_PLAN_ID = " + classPlanVO.getClassPlanId() + " AND TOOL_ID = T.TOOL_ID) AS SELECTED "
		    + "FROM TOOL T "
			+ "ORDER BY T.TOOL_ID";
		rowList = selectDb (sql, 3);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
							
				List<Object> columns = (List<Object>)columnList;					
				Integer toolId = (Integer) columns.get (0);
				String name    = (String) columns.get (1);
				Long selected  = (Long) columns.get (2);
				
				ToolVO toolVO = new ToolVO();
				toolVO.setToolId (toolId);
				toolVO.setName (name);
				boolean isSelected = selected == 1 ? true : false;
				toolVO.setSelected (isSelected);
										
				toolList.add (toolVO);
			}
		}
		return toolList;
	}
	
}
