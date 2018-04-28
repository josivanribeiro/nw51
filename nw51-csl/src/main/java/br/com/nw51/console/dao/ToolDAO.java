package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ToolVO;

/**
 * DAO Class for Tool.
 * 
 * @author Josivan Silva
 *
 */
public class ToolDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (ToolDAO.class.getName());
	
	public ToolDAO () {
		
	}
		
	/**
	 * Finds all the tools.
	 * 
	 * @return a list of tool.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ToolVO> findAll () throws DataAccessException {
		String sql           = null;
		List<Object> rowList = null;
		List<ToolVO> toolList = new ArrayList<ToolVO>();
		sql = "SELECT TOOL_ID, NAME FROM TOOL ORDER BY TOOL_ID";
		rowList = selectDb (sql, 2);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Integer toolId = (Integer) columns.get (0);				
				String name    = (String) columns.get (1);
								
				ToolVO toolVO = new ToolVO();
				toolVO.setToolId (toolId);
				toolVO.setName (name);
								
				toolList.add (toolVO);
			}
		}
		return toolList;
	}
	
}
