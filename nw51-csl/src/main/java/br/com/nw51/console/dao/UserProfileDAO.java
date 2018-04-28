package br.com.nw51.console.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ProfileVO;
import br.com.nw51.common.vo.UserProfileVO;
import br.com.nw51.common.vo.UserVO;

/**
 * DAO class for ProfileRole.
 * 
 * @author Josivan Silva
 *
 */
public class UserProfileDAO extends AbstractDAO {

	static Logger logger = Logger.getLogger (UserProfileDAO.class.getName());
	
	public UserProfileDAO () {
		
	}
	
	/**
	 * Inserts a new userProfile.
	 * 
	 * @param userProfileVO the profile.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int insert (UserProfileVO userProfileVO) throws DataAccessException {
		int affectedRows = 0;
		StringBuilder sbSql = null;
		sbSql = new StringBuilder();
		sbSql.append ("INSERT INTO USER_PROFILE (USER_ID, PROFILE_ID) ");
		sbSql.append ("VALUES (" +  userProfileVO.getUserVO().getUserId() + ", ");
		sbSql.append (userProfileVO.getProfileVO().getProfileId() + ")");
		affectedRows = updateDb (sbSql.toString());
		return affectedRows;
	}
	
	/**
	 * Deletes an userProfile.
	 * 
	 * @param userVO the userVO.
	 * @return the number of affected rows.
	 * @throws DataAccessException
	 */
	public int deleteByUserId (UserVO userVO) throws DataAccessException {
		String sql       = null;
		int affectedRows = 0;
		sql = "DELETE FROM USER_PROFILE WHERE USER_ID = " + userVO.getUserId();
		affectedRows = updateDb (sql);
		return affectedRows;
	}
	
	/**
	 * Finds a list of userProfile by user id.
	 * 
	 * @param userVO the user.
	 * @return a list of userProfile.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<UserProfileVO> findByUserId (UserVO userVO) throws DataAccessException {
		String sql           = null;
		List<Object> rowList = null;
		List<UserProfileVO> userProfileList = new ArrayList<UserProfileVO>();
		sql = "SELECT USER_ID, PROFILE_ID FROM USER_PROFILE WHERE USER_ID = " + userVO.getUserId();
		rowList = selectDb (sql, 2);
		if (!rowList.isEmpty() && rowList.size() > 0) {			
			for (Object columnList : rowList) {
				List<Object> columns = (List<Object>)columnList;
				Integer userId    = (Integer) columns.get (0);
				Integer profileId = (Integer) columns.get (1);				
				
				UserVO foundUserVO = new UserVO();
				foundUserVO.setUserId (userId);
				
				ProfileVO profileVO = new ProfileVO();
				profileVO.setProfileId (profileId);
				
				UserProfileVO userProfileVO = new UserProfileVO();
								
				userProfileVO.setProfileVO (profileVO);
				userProfileVO.setUserVO (foundUserVO);
				
				userProfileList.add (userProfileVO);
			}
		}
		return userProfileList;
	}
	
	/**
	 * Finds all the profiles with the selected, given the user.
	 * 
	 * @return a list of profiles.
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	public List<ProfileVO> findAllWithSelected (UserVO userVO) throws DataAccessException {
		String sql            = null;
		List<Object> rowList  = null;
		List<ProfileVO> profileList = new ArrayList<ProfileVO>();
		sql = "SELECT P.PROFILE_ID, P.NAME, "
			+ "(SELECT COUNT(*) FROM USER_PROFILE WHERE USER_ID = " + userVO.getUserId() + " AND PROFILE_ID = P.PROFILE_ID) AS SELECTED "
		    + "FROM PROFILE P "
			+ "ORDER BY P.PROFILE_ID";
		rowList = selectDb (sql, 3);
		if (!rowList.isEmpty() && rowList.size() > 0) {
			for (Object columnList : rowList) {	
							
				List<Object> columns = (List<Object>)columnList;					
				Integer profileId = (Integer) columns.get (0);
				String name       = (String) columns.get (1);
				Long selected     = (Long) columns.get (2);
				
				ProfileVO profileVO = new ProfileVO();
				profileVO.setProfileId (profileId);
				profileVO.setName (name);
				boolean isSelected = selected == 1 ? true : false;
				profileVO.setSelected (isSelected);
										
				profileList.add (profileVO);
			}
		}
		return profileList;
	}
	
}
