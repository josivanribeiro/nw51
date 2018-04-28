package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ProfileRoleVO;
import br.com.nw51.common.vo.ProfileVO;
import br.com.nw51.common.vo.RoleVO;
import br.com.nw51.common.vo.UserProfileVO;
import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.ProfileDAO;
import br.com.nw51.console.dao.ProfileRoleDAO;
import br.com.nw51.console.dao.UserProfileDAO;

/**
 * Business Service class for Profile.
 * 
 * @author Josivan Silva
 *
 */
public class ProfileService {

	static Logger logger = Logger.getLogger (ProfileService.class.getName());
	private ProfileDAO profileDAO = new ProfileDAO();
	private ProfileRoleDAO profileRoleDAO = new ProfileRoleDAO();
	private UserProfileDAO userProfileDAO = new UserProfileDAO();
		
	public ProfileService() {
		
	}
	
	/**
	 * Inserts a new profile.
	 * 
	 * @param profileVO the profile.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public Integer insert (ProfileVO profileVO) throws BusinessException {
		Integer profileId = 0;
		try {
			profileId = profileDAO.insert (profileVO);
			logger.debug ("new profileId [" + profileId + "]");
			if (profileId != null && profileId > 0) {				
				for (ProfileRoleVO profileRoleVO : profileVO.getProfileRoleVOList()) {					
					
					ProfileVO newProfileVO = new ProfileVO();
					newProfileVO.setProfileId (profileId);
					
					profileRoleVO.setProfileVO (newProfileVO);
					
					int affectedRows = profileRoleDAO.insert (profileRoleVO);
					if (affectedRows > 0) {
						logger.debug ("role id [" + profileRoleVO.getRoleVO().getRoleId() + "] inserted successfully.");
					}
				}
			}
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the profile or profileRole. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return profileId;
	}
	
	/**
	 * Updates a profile.
	 * 
	 * @param profileVO the profile.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (ProfileVO profileVO) throws BusinessException {
		int profileAffectedRows = 0;
		try {
			profileAffectedRows = profileDAO.update (profileVO);
			logger.debug ("profileAffectedRows [" + profileAffectedRows + "]");			
			if (profileAffectedRows > 0) {				
				int profileRoleDeletedRows = profileRoleDAO.deleteByProfileId (profileVO);
				logger.debug ("profileRoleDeletedRows [" + profileRoleDeletedRows + "]");				
				for (ProfileRoleVO profileRoleVO : profileVO.getProfileRoleVOList()) {
					profileRoleVO.setProfileVO (profileVO);					
					profileRoleDAO.insert (profileRoleVO);
					logger.debug ("inserted profileRole with profileId "
					+ profileRoleVO.getProfileVO().getProfileId() + " and roleId " + profileRoleVO.getRoleVO().getRoleId());
				}				
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the profile with role. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return profileAffectedRows;
	}
	
	/**
	 * Deletes a profile.
	 * 
	 * @param ProfileVO the profile.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (ProfileVO ProfileVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = profileDAO.delete (ProfileVO);
			logger.info ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the profile. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds a profile by its id.
	 * 
	 * @param ProfileVO the profile.
	 * @return the found profile.
	 * @throws BusinessException
	 */
	public ProfileVO findById (ProfileVO profileVO) throws BusinessException {
		ProfileVO foundProfileVO = null;
		try {
			foundProfileVO = profileDAO.findById (profileVO);
			List<ProfileRoleVO> profileRoleVOList = profileRoleDAO.findByProfileId (profileVO);
			if (profileRoleVOList != null && profileRoleVOList.size() > 0) {
				foundProfileVO.setProfileRoleVOList (profileRoleVOList);
			}
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the profile by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundProfileVO;
	}
	
	/**
	 * Finds a duplicated profile name.
	 * 
	 * @param profileVO the profile.
	 * @return the count of duplicated profile name.
	 * @throws BusinessException
	 */
	public int findDuplicatedName (ProfileVO profileVO) throws BusinessException {
		int count = 0;
		try {
			count = profileDAO.findDuplicatedName (profileVO);
			logger.debug ("count [" + count + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the a duplicated name. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
	/**
	 * Finds all the profiles.
	 * 
	 * @return a list of profiles.
	 * @throws BusinessException
	 */
	public List<ProfileVO> findAll () throws BusinessException {
		List<ProfileVO> profileVOList = null;
		try {
			profileVOList = profileDAO.findAll();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the all the profiles. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return profileVOList;
	}
	
	/**
	 * Finds all the roles with the selected, given the profile.
	 * 
	 * @return a list of profiles.
	 * @throws BusinessException
	 */
	public List<RoleVO> findAllWithSelected (ProfileVO profileVO) throws BusinessException {
		List<RoleVO> roleVOList = null;
		try {
			roleVOList = profileRoleDAO.findAllWithSelected (profileVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the all roles with selected field. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return roleVOList;
	}
	
	/**
	 * Finds all the userProfiles given the user id.
	 * 
	 * @return a list of userProfiles.
	 * @throws BusinessException
	 */
	public List<UserProfileVO> findProfilesByUser (UserVO userVO) throws BusinessException {
		List<UserProfileVO> userProfileList = null;
		try {
			userProfileList = userProfileDAO.findByUserId (userVO);			
			if (userProfileList != null && userProfileList.size() > 0) {				
				for (UserProfileVO userProfileVO : userProfileList) {					
					List<ProfileRoleVO> profileRoleVOList = profileRoleDAO.findByProfileId (userProfileVO.getProfileVO());					
					if (profileRoleVOList != null && profileRoleVOList.size() > 0) {
						userProfileVO.getProfileVO().setProfileRoleVOList (profileRoleVOList);
					}
				}
			}
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the all profiles by user. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return userProfileList;
	}
	
	/**
	 * Gets the row count of profiles.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = profileDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count of profiles. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
}
