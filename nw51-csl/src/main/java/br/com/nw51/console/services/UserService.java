package br.com.nw51.console.services;

import java.util.List;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.ProfileVO;
import br.com.nw51.common.vo.UserProfileVO;
import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.dao.DataAccessException;
import br.com.nw51.console.dao.UserDAO;
import br.com.nw51.console.dao.UserProfileDAO;

/**
 * Business Service class for User.
 * 
 * @author Josivan Silva
 *
 */
public class UserService {

	static Logger logger = Logger.getLogger (UserService.class.getName());	
	private UserDAO userDAO = new UserDAO();
	private UserProfileDAO userProfileDAO = new UserProfileDAO();
	
	public UserService() {
		
	}
	
	/**
	 * Inserts a new user.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public Integer insert (UserVO userVO) throws BusinessException {
		Integer userId = 0;
		try {
			userId = userDAO.insert (userVO);
			logger.debug ("new userId [" + userId + "]");
			if (userId != null && userId > 0) {				
				for (UserProfileVO userProfileVO : userVO.getUserProfileVOList()) {					
					UserVO newUserVO = new UserVO();
					newUserVO.setUserId (userId);
					userProfileVO.setUserVO (newUserVO);
					int affectedRows = userProfileDAO.insert (userProfileVO);
					if (affectedRows > 0) {
						logger.debug ("profile id [" + userProfileVO.getProfileVO().getProfileId() + "] inserted successfully.");
					}
				}				
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while inserting the user or userProfile. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return userId;
	}
	
	/**
	 * Updates an user.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int update (UserVO userVO) throws BusinessException {
		int userAffectedRows = 0;
		try {
			userAffectedRows = userDAO.update (userVO);
			logger.debug ("userAffectedRows [" + userAffectedRows + "]");			
			if (userAffectedRows > 0) {				
				int userProfileDeletedRows = userProfileDAO.deleteByUserId (userVO);
				logger.debug ("userProfileDeletedRows [" + userProfileDeletedRows + "]");				
				for (UserProfileVO userProfileVO : userVO.getUserProfileVOList()) {
					userProfileVO.setUserVO (userVO);
					userProfileDAO.insert (userProfileVO);
					logger.debug ("inserted userProfile with userId "
					+ userProfileVO.getUserVO().getUserId() + " and profileId " + userProfileVO.getProfileVO().getProfileId());					
				}				
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the user with profile. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return userAffectedRows;
	}
	
	/**
	 * Updates the user password.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updatePassword (UserVO userVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = userDAO.updatePassword (userVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the user password. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Updates the user state.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updateState (UserVO userVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = userDAO.updateState (userVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the user state. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Updates the user login attempts.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updateLoginAttempts (UserVO userVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = userDAO.updateLoginAttempts (userVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the user login attempts. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Updates the user unauthorized access attempts.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int updateUnauthorizedAccessAttempts (UserVO userVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = userDAO.updateUnauthorizedAccessAttempts (userVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while updating the user unauthorized access attempts. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
		
	/**
	 * Deletes an user.
	 * 
	 * @param userVO the user.
	 * @return the number of affected rows.
	 * @throws BusinessException
	 */
	public int delete (UserVO userVO) throws BusinessException {
		int affectedRows = 0;
		try {
			affectedRows = userDAO.delete (userVO);
			logger.debug ("affectedRows [" + affectedRows + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while deleting the user. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return affectedRows;
	}
	
	/**
	 * Finds an user by its id.
	 * 
	 * @param userVO the user.
	 * @return the found user.
	 * @throws BusinessException
	 */
	public UserVO findById (UserVO userVO) throws BusinessException {
		UserVO foundUserVO = null;
		try {
			foundUserVO = userDAO.findById (userVO);			
			List<UserProfileVO> userProfileVOList = userProfileDAO.findByUserId (userVO);
			if (userProfileVOList != null && userProfileVOList.size() > 0) {
				foundUserVO.setUserProfileVOList (userProfileVOList);
			}			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the user by id. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundUserVO;
	}
	
	/**
	 * Finds an user by its email.
	 * 
	 * @param userVO the user.
	 * @return the found user.
	 * @throws BusinessException
	 */
	public UserVO findByEmail (UserVO userVO) throws BusinessException {
		UserVO foundUserVO = null;
		try {
			foundUserVO = userDAO.findByEmail (userVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the user by email. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return foundUserVO;
	}
	
	/**
	 * Finds a duplicated email.
	 * 
	 * @param userVO the user.
	 * @return the count of duplicated email.
	 * @throws BusinessException
	 */
	public int findDuplicatedEmail (UserVO userVO) throws BusinessException {
		int count = 0;
		try {
			count = userDAO.findDuplicatedEmail (userVO);
			logger.debug ("count [" + count + "]");
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the a duplicated email. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
	/**
	 * Finds all the users.
	 * 
	 * @return a list of users.
	 * @throws BusinessException
	 */
	public List<UserVO> findAll () throws BusinessException {
		List<UserVO> userVOList = null;
		try {
			userVOList = userDAO.findAll ();			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding all the users. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return userVOList;
	}
	
	/**
	 * Finds all the users with teacher profile.
	 * 
	 * @return a list of users with teacher profile.
	 * @throws BusinessException
	 */
	public List<UserVO> findAllWithTeacherProfile () throws BusinessException {
		List<UserVO> userVOList = null;
		try {
			userVOList = userDAO.findAllWithTeacherProfile();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding all the users with teacher profile. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return userVOList;
	}
	
	/**
	 * Finds all the profiles with the selected, given the user.
	 * 
	 * @return a list of profiles.
	 * @throws BusinessException
	 */
	public List<ProfileVO> findAllWithSelected (UserVO userVO) throws BusinessException {
		List<ProfileVO> profileVOList = null;
		try {
			profileVOList = userProfileDAO.findAllWithSelected (userVO);
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while finding the all profiles with selected field. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return profileVOList;
	}
	
	/**
	 * Performs the user login.
	 * 
	 * @param userVO the user.
	 * @return a the result of the login operation.
	 * @throws BusinessException
	 */
	public boolean doLogin (UserVO userVO) throws BusinessException {
		boolean isLogged = false;
		try {
			isLogged = userDAO.doLogin (userVO);			
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while performing the login. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return isLogged;
	}
	
	/**
	 * Gets the row count of users.
	 * 
	 * @return the row count.
	 * @throws BusinessException
	 */
	public int rowCount () throws BusinessException {
		int count = 0;
		try {
			count = userDAO.rowCount();
		} catch (DataAccessException e) {
			String errorMessage = "A business exception error occurred while getting the row count. " + e.getMessage();
			logger.error (errorMessage);
			throw new BusinessException (errorMessage, e.getCause());
		}
		return count;
	}
	
}
