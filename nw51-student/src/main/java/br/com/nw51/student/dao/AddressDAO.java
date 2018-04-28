package br.com.nw51.student.dao;

import br.com.nw51.common.vo.AddressVO;

/**
 * DAO class for Address.
 * 
 * @author Josivan Silva
 *
 */
public class AddressDAO extends AbstractDAO {

	public AddressDAO() {
		
	}
	
	/**
	 * Updates an existing address.
	 * 
	 * @param addressVO the address.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int update (AddressVO adressVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		String whereClause  = null;
		sbSql = new StringBuilder();
		sbSql.append ("UPDATE ADDRESS SET "); 
		sbSql.append ("CEP=");
		sbSql.append ("'" + adressVO.getCEP() + "', ");
		sbSql.append ("ADDRESS1=");
		sbSql.append ("'" + adressVO.getAddress1() + "', ");
		sbSql.append ("NUMBER=");
		sbSql.append ("'" + adressVO.getNumber() + "', ");
		sbSql.append ("ADDRESS2=");
		sbSql.append ("'" + adressVO.getAddress2() + "', ");
		sbSql.append ("NEIGHBORHOOD=");
		sbSql.append ("'" + adressVO.getNeighborhood() + "', ");
		sbSql.append ("CITY=");
		sbSql.append ("'" + adressVO.getCity() + "', ");
		sbSql.append ("STATE_ID=");
		sbSql.append (adressVO.getStateId() + " ");
		whereClause = "WHERE ADDRESS_ID = " + adressVO.getAddressId();
		sbSql.append (whereClause);
		affectedRows = updateDb (sbSql.toString());
		logger.debug ("sql: " + sbSql.toString());
		return affectedRows;
	}	

}
