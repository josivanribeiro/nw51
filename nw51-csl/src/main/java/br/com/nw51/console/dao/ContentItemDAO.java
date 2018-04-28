package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.ClassVO;
import br.com.nw51.common.vo.ContentItemVO;
import br.com.nw51.common.vo.TeachingPlanVO;

/**
 * DAO class for ContentItem.
 * 
 * @author Josivan Silva
 *
 */
public class ContentItemDAO extends AbstractDAO {
	
	static Logger logger = Logger.getLogger (ContentItemDAO.class.getName());
	
	public ContentItemDAO () {
		
	}
	
	/**
	 * Inserts a new contentItem.
	 * 
	 * @param contentItemVO the contentItem.
	 * @return the new id.
	 * @throws DataAccessException
	 */
	public Integer insert (ContentItemVO contentItemVO) throws DataAccessException {
		Integer contentItemId = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CONTENT_ITEM (NUMERATION_FATHER, TEACHING_PLAN_ID, CLASS_ID, NUMERATION, NAME) ");
		
		if (contentItemVO.getContentItemFather() != null
				&& Utils.isNonEmpty (contentItemVO.getContentItemFather().getNumeration())) {
			sbSql.append ("VALUES ('" +  contentItemVO.getContentItemFather().getNumeration() + "', ");	
		} else {
			sbSql.append ("VALUES (NULL, ");
		}
		
		sbSql.append (contentItemVO.getTeachingPlanVO().getTeachingPlanId() + ", ");
		if (contentItemVO.getClassVO() != null 
				&& contentItemVO.getClassVO().getClassId() != null
				&& contentItemVO.getClassVO().getClassId() > 0) {
			sbSql.append (contentItemVO.getClassVO().getClassId() + ", ");
		} else {
			sbSql.append ("NULL, ");
		}		
		
		sbSql.append ("'" + contentItemVO.getNumeration() + "', ");
		sbSql.append ("'" + contentItemVO.getName() + "')");
		logger.debug ("sql: " + sbSql.toString());
		contentItemId = insertDbWithIntegerKey (sbSql.toString());
		return contentItemId;
	}
	
	/**
	 * Updates an existing contentItem with the classId.
	 * 
	 * @param contentItemVO the contentItem.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int updateClass (ContentItemVO contentItemVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE CONTENT_ITEM SET ");
		sbSql.append ("CLASS_ID = ");
		if (contentItemVO.getClassVO() != null 
				&& contentItemVO.getClassVO().getClassId() != null
				&& contentItemVO.getClassVO().getClassId() > 0) {
			sbSql.append (contentItemVO.getClassVO().getClassId() + " ");
		} else {
			sbSql.append ("NULL ");
		}
		whereClause = "WHERE CONTENT_ITEM_ID = " + contentItemVO.getContentItemId();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes all the contentItems given a teachingPlan.
	 * 
	 * @param teachingPlanVO the teachingPlan.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int deleteByTeachingPlan (TeachingPlanVO teachingPlanVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM CONTENT_ITEM WHERE TEACHING_PLAN_ID = " + teachingPlanVO.getTeachingPlanId();
		logger.debug ("sql: " + sql);
		affectedRows = updateDb (sql);
		return affectedRows;
	}
			
	
	/**
	 * Find a contentItem by its id.
	 * 
	 * @param contentItemVO the contentItem.
	 * @return a contentItem.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public ContentItemVO findById (ContentItemVO contentItemVO) throws DataAccessException {
		ContentItemVO foundContentItemVO = null;
		String sql           = null;
		List<Object> rowList = null;
		sql = "SELECT CONTENT_ITEM_ID, NUMERATION_FATHER, TEACHING_PLAN_ID, CLASS_ID, NUMERATION, NAME FROM CONTENT_ITEM WHERE CONTENT_ITEM_ID = " + contentItemVO.getContentItemId();
		rowList = selectDb (sql, 6);	
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {				
				List<Object> columns = (List<Object>)columnList;					
				Integer contentItemId = (Integer) columns.get (0);
				String numerationFather = (String) columns.get (1);
				Integer teachingPlanId = (Integer) columns.get (2);
				Integer classId = (Integer) columns.get (3);
				String numeration = (String) columns.get (4);
				String name    = (String) columns.get (5);
				
				foundContentItemVO = new ContentItemVO();
				foundContentItemVO.setContentItemId (contentItemId);
				
				if (Utils.isNonEmpty (numerationFather)) {
					ContentItemVO contentItemFatherVO = new ContentItemVO();
					contentItemFatherVO.setNumeration (numerationFather);
					foundContentItemVO.setContentItemFather (contentItemFatherVO);
				}
				
				TeachingPlanVO teachingPlanVO = new TeachingPlanVO();
				teachingPlanVO.setTeachingPlanId (teachingPlanId);
				foundContentItemVO.setTeachingPlanVO (teachingPlanVO);
				
				if (classId != null && classId > 0) {
					ClassVO classVO = new ClassVO();
					classVO.setClassId (classId);
					foundContentItemVO.setClassVO (classVO);
				}
				
				foundContentItemVO.setNumeration (numeration);
				foundContentItemVO.setName (name);
			}
		}
		return foundContentItemVO;
	}
		
	/**
	 * Finds the contentItems by teachingPlan.
	 * 
	 * @return a list of contentItems.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ContentItemVO> findByTeachingPlan (TeachingPlanVO teachingPlanVO) throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<ContentItemVO> contentItemList = new ArrayList<ContentItemVO>();
		sql = "SELECT CONTENT_ITEM_ID, NUMERATION_FATHER, TEACHING_PLAN_ID, CLASS_ID, NUMERATION, NAME FROM CONTENT_ITEM WHERE TEACHING_PLAN_ID = " + teachingPlanVO.getTeachingPlanId();				
		logger.debug ("sql: " + sql);		
		rowList = selectDb (sql, 6);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
							
				List<Object> columns = (List<Object>)columnList;					
				Integer contentItemId = (Integer) columns.get (0);
				String numerationFather = (String) columns.get (1);
				Integer teachingPlanId = (Integer) columns.get (2);
				Integer classId = (Integer) columns.get (3);
				String numeration = (String) columns.get (4);
				String name    = (String) columns.get (5);
				
				ContentItemVO contentItemVO = new ContentItemVO();
				contentItemVO.setContentItemId (contentItemId);
				
				if (Utils.isNonEmpty (numerationFather)) {					
					ContentItemVO contentItemFatherVO = new ContentItemVO();
					contentItemFatherVO.setNumeration (numerationFather);
					contentItemVO.setContentItemFather (contentItemFatherVO);
				}
				
				TeachingPlanVO newTeachingPlanVO = new TeachingPlanVO();
				newTeachingPlanVO.setTeachingPlanId (teachingPlanId);
				contentItemVO.setTeachingPlanVO (newTeachingPlanVO);
				
				if (classId != null && classId > 0) {
					ClassVO classVO = new ClassVO();
					classVO.setClassId (classId);
					contentItemVO.setClassVO (classVO);
				}
				
				contentItemVO.setNumeration (numeration);
				contentItemVO.setName (name);
																		
				contentItemList.add (contentItemVO);
			}
		}
		return contentItemList;
	}
	
}
