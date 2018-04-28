package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.SlideVO;

/**
 * DAO Class for Slide.
 * 
 * @author Josivan Silva
 *
 */
public class SlideDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (SlideDAO.class.getName());
	
	public SlideDAO () {
		
	}
		
	/**
	 * Inserts a new slide.
	 * 
	 * @param slideVO the slide.
	 * @return the inserted id.
	 * @throws DataAccessException
	 */
	public Integer insert (SlideVO slideVO) throws DataAccessException {
		Integer id = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO SLIDE (CLASS_ID, NAME, CONTENT, STATUS, SEQUENCE_NUMBER) ");
		sbSql.append ("VALUES (" +  slideVO.getClassVO().getClassId() + ", ");
		sbSql.append ("'" + slideVO.getName() + "', ");
		sbSql.append ("'" + slideVO.getContent() + "', ");
		sbSql.append (slideVO.getStatus() + ", ");
		sbSql.append (slideVO.getSequenceNumber() + ")");
		logger.debug ("sql: " + sbSql.toString());
		id = insertDbWithIntegerKey (sbSql.toString());
		return id;
	}
	
	/**
	 * Updates an existing slide.
	 * 
	 * @param slideVO the slide.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (SlideVO slideVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE SLIDE SET ");
		sbSql.append ("CLASS_ID = ");
		sbSql.append (slideVO.getClassVO().getClassId() + ", ");
		sbSql.append ("NAME = ");
		sbSql.append ("'" + slideVO.getName() + "', ");
		sbSql.append ("CONTENT = ");
		sbSql.append ("'" + slideVO.getContent() + "', ");
		sbSql.append ("STATUS = ");
		sbSql.append (slideVO.getStatus() + " ");
		whereClause = "WHERE SLIDE_ID = " + slideVO.getSlideId();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		logger.debug ("sql: " + sbSql.toString());		
		return affectedRows;
	}
	
	/**
	 * Deletes a slide.
	 * 
	 * @param slideVO the slide.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (SlideVO slideVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM SLIDE WHERE SLIDE_ID = " + slideVO.getSlideId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a slide by its id.
	 * 
	 * @param slideVO the slide.
	 * @return a slide.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public SlideVO findById (SlideVO slideVO) throws DataAccessException {
		SlideVO foundSlideVO = null;
		String sql                   = null;
		List<Object> rowList         = null;		
		sql = "SELECT SLIDE_ID, CLASS_ID, NAME, CONTENT, STATUS, SEQUENCE_NUMBER FROM SLIDE WHERE SLIDE_ID = " + slideVO.getSlideId();
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
				List<Object> columns = (List<Object>)columnList;
				Integer slideId = (Integer) columns.get (0);
				Integer classId = (Integer) columns.get (1);
				String name     = (String) columns.get (2);
				String content  = (String) columns.get (3);
				int status      = (Integer) columns.get (4);
								
				foundSlideVO = new SlideVO();
				foundSlideVO.setSlideId (slideId);
				
				ClassVO classVO = new ClassVO();
				classVO.setClassId (classId);
				
				foundSlideVO.setClassVO (classVO);				
				foundSlideVO.setName (name);
				foundSlideVO.setContent (content);
				foundSlideVO.setStatus (status);	
			}
		}
		return foundSlideVO;
	}
	
	/**
	 * Finds the slides by class.
	 * 
	 * @return a list of slides.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<SlideVO> findByClass (ClassVO classVO) throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<SlideVO> slideVOList = new ArrayList<SlideVO>();
		sql = "SELECT SLIDE_ID, CLASS_ID, NAME, CONTENT, STATUS, SEQUENCE_NUMBER FROM SLIDE WHERE CLASS_ID = " + classVO.getClassId();
		logger.debug ("sql: " + sql);
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
							
				List<Object> columns = (List<Object>)columnList;					
				Integer slideId        = (Integer) columns.get (0);
				Integer classId        = (Integer) columns.get (1);
				String name            = (String) columns.get (2);
				String content         = (String) columns.get (3);
				int status             = (Integer) columns.get (4);
				Integer sequenceNumber = (Integer) columns.get (5);
								
				SlideVO slideVO = new SlideVO();
				slideVO.setSlideId (slideId);
				
				ClassVO newClassVO = new ClassVO();
				newClassVO.setClassId (classId);
				
				slideVO.setClassVO (newClassVO);				
				slideVO.setName (name);
				slideVO.setContent (content);
				slideVO.setStatus (status);
				slideVO.setSequenceNumber (sequenceNumber);
																		
				slideVOList.add (slideVO);
			}
		}
		return slideVOList;
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
		sql = "SELECT SEQUENCE_NUMBER AS SN FROM SLIDE ORDER BY SEQUENCE_NUMBER DESC LIMIT 1";
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
