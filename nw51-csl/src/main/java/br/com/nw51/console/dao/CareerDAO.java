package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.CareerVO;

/**
 * DAO class for Career.
 * 
 * @author Josivan Silva
 *
 */
public class CareerDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (CareerDAO.class.getName());
	
	public CareerDAO () {
		
	}
	
	/**
	 * Inserts a new career.
	 * 
	 * @param careerVO the career.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public Integer insert (CareerVO careerVO) throws DataAccessException {
		Integer careerId = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO CAREER (NAME, CAREER_TYPE, DESCRIPTION, STATUS) ");
		sbSql.append ("VALUES ('" +  careerVO.getName() + "', ");
		sbSql.append (careerVO.getCareerType() + ", ");
		sbSql.append ("'" + careerVO.getDescription() + "', ");
		sbSql.append (careerVO.getStatus() + ")");
		logger.debug ("sql: " + sbSql.toString());
		careerId = insertDbWithIntegerKey (sbSql.toString());
		return careerId;
	}
	
	/**
	 * Updates an existing career.
	 * 
	 * @param careerVO the career.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (CareerVO careerVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE CAREER SET ");
		sbSql.append ("NAME=");
		sbSql.append ("'" + careerVO.getName() + "', ");
		sbSql.append ("CAREER_TYPE=");
		sbSql.append (careerVO.getCareerType() + ", ");
		sbSql.append ("DESCRIPTION=");
		sbSql.append ("'" + careerVO.getDescription() + "', ");
		sbSql.append ("STATUS=");
		sbSql.append (careerVO.getStatus() + " ");
		whereClause = "WHERE CAREER_ID = " + careerVO.getCareerId();
		sbSql.append (whereClause);
		logger.debug ("sql: " + sbSql.toString());
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes a career.
	 * 
	 * @param careerVO the career.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int delete (CareerVO careerVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM CAREER WHERE CAREER_ID = " + careerVO.getCareerId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Find a career by its id.
	 * 
	 * @param careerVO the career.
	 * @return a career.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public CareerVO findById (CareerVO careerVO) throws DataAccessException {
		CareerVO foundCareerVO = null;
		String sql         = null;
		List<Object> rowList = null;
		sql = "SELECT CAREER_ID, NAME, CAREER_TYPE, DESCRIPTION, STATUS FROM CAREER WHERE CAREER_ID = " + careerVO.getCareerId();
		rowList = selectDb (sql, 5);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;					
				Integer careerId   = (Integer) columns.get (0);
				String name        = (String) columns.get (1);
				Integer careerType = (Integer) columns.get (2);
				String description = (String) columns.get (3);
				Integer status     = (Integer) columns.get (4);
				
				foundCareerVO = new CareerVO();
				foundCareerVO.setCareerId (careerId);
				foundCareerVO.setName (name);
				foundCareerVO.setCareerType (careerType);
				foundCareerVO.setDescription (description);
				foundCareerVO.setStatus (status);
			}
		}
		return foundCareerVO;
	}
	
	/**
	 * Finds all the careers.
	 * 
	 * @return a list of careers.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<CareerVO> findAll () throws DataAccessException {
		String sql           = null;
		List<Object> rowList = null;
		List<CareerVO> careerList = new ArrayList<CareerVO>();
		sql = "SELECT CAREER_ID, NAME, CAREER_TYPE, DESCRIPTION, STATUS FROM CAREER ORDER BY CAREER_ID";
		rowList = selectDb (sql, 5);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {							
				List<Object> columns = (List<Object>)columnList;
				Integer careerId     = (Integer) columns.get (0);
				String name          = (String) columns.get (1);
				Integer carrerType   = (Integer) columns.get (2);
				String description   = (String) columns.get (3);
				Integer status       = (Integer) columns.get (4);
				
				CareerVO careerVO = new CareerVO();
				careerVO.setCareerId (careerId);
				careerVO.setName (name);
				careerVO.setCareerType (carrerType);
				careerVO.setDescription (description);
				careerVO.setStatus (status);
										
				careerList.add (careerVO);
			}
		}
		return careerList;
	}
	
	/**
	 * Gets the row count of careers.
	 * 
	 * @return the row count.
	 * @throws DataAccessException
	 */
	public int rowCount () throws DataAccessException {
		int count = 0;
		String sql = null;
		sql = "SELECT COUNT(*) FROM CAREER";
		logger.debug ("sql: " + sql);
		count = selectRowCount (sql);
		return count;
	}
	
}
