package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassPlanVO;
import br.com.nw51.common.vo.ToolVO;
import br.com.nw51.console.dao.ClassPlanToolDAO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.ToolDAO;

/**
 * Business Service class for Tool.
 * 
 * @author Josivan Silva
 *
 */
public class ToolService {

	static Logger logger = Logger.getLogger (ToolService.class.getName());
	private ToolDAO toolDAO = new ToolDAO();
	private ClassPlanToolDAO classPlanToolDAO = new ClassPlanToolDAO();
	
		
	public ToolService() {
		
	}
		
	/**
	 * Finds all the tools.
	 * 
	 * @return a list of profiles.
	 * @throws BusinessException
	 */
	public List<ToolVO> findAll () throws BusinessException {
		List<ToolVO> toolVOList = null;
		try {
			toolVOList = toolDAO.findAll();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the all the tools. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return toolVOList;
	}
	
	/**
	 * Finds all the tools with the selected, given the profile.
	 * 
	 * @return a list of tools.
	 * @throws BusinessException
	 */
	public List<ToolVO> findAllWithSelected (ClassPlanVO classPlanVO) throws BusinessException {
		List<ToolVO> toolVOList = null;
		try {
			toolVOList = classPlanToolDAO.findAllWithSelected (classPlanVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the all tools with selected field. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return toolVOList;
	}	
	
}
