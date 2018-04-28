package br.com.nw51.console.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.nw51.common.util.Utils;
import br.com.nw51.common.vo.ProfileRoleVO;
import br.com.nw51.common.vo.UserProfileVO;
import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.controller.LoginController;

/**
 * Security Utils helper class.
 * 
 * @author Josivan Silva
 *
 */
public class SecurityUtils {
	
	static Logger logger = Logger.getLogger (SecurityUtils.class.getName());
	
	public static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{10,20})";
	
	/**
	 * Validates the password.
	 * 
	 * @param pwd the password.
	 * @return
	 */
	public static boolean isValidPwd (String pwd) {
		return pwd.matches (PASSWORD_PATTERN);
	}
	
	/**
	 * Performs the user logout.
	 */
	public static void logout (ServletRequest request) {
		HttpSession session = ((HttpServletRequest) request).getSession (false);
		if (session != null) {
		    session.invalidate();
		}
	}
	
	/**
	 * Gets the logged user from the session.
	 * 
	 * @param request the request containing the session.
	 * @return
	 */
	public static UserVO getLoggedUser (ServletRequest request) {
		LoginController loginController = getLoginController (request);
		return loginController.getLoggedUser();
	}
	
	/**
	 * Gets the LoginController instance from session.
	 * 
	 * @param request the request containing the session.
	 * @return a loginController instance.
	 */
	public static LoginController getLoginController (ServletRequest request) {
		LoginController loginController = (LoginController)((HttpServletRequest)request).getSession().getAttribute("loginController");
		return loginController;
	}
	
	/**
	 * Gets a SHA-512 hash password.
	 * 
	 * @param passwordToHash the password to hash.
	 * @param salt the salt.
	 * @return the hash password.
	 */
	public static String getSHA512Password (String passwordToHash, String salt) {
	    String generatedPassword = null;
        MessageDigest md = null;
        byte[] bytes = null;
        if (!Utils.isNonEmpty(passwordToHash) && !Utils.isNonEmpty(salt)) {
        	String error = "The password and salt are empty.";
        	logger.error (error);
        	throw new IllegalArgumentException (error);
        }
        StringBuilder sb = new StringBuilder();
		try {
			md = MessageDigest.getInstance ("SHA-512");
		} catch (NoSuchAlgorithmException e1) {
			logger.error ("An error occurred while get an instance of SHA-512. " + e1.getMessage());
		}        
        try {
			md.update (salt.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e2) {
			logger.error ("An error occurred while encoding salt to UTF-8. " + e2.getMessage());
		}        
		try {
			bytes = md.digest (passwordToHash.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e3) {
			logger.error ("An error occurred while encoding password to UTF-8. " + e3.getMessage());
		}
        for (int i=0; i< bytes.length ;i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        generatedPassword = sb.toString();
        return generatedPassword;
	}
	
	/**
	 * Checks if the logged user has the given role.
	 * 
	 * @param name the role name.
	 * @param name the userVO.
	 * @return the operation result.
	 */
	public static boolean isUserInRole (String name, UserVO userVO) {
		boolean isInRole = false;
		if (userVO != null 
				&& userVO.getUserProfileVOList() != null 
				&& userVO.getUserProfileVOList().size() > 0) {
			List<UserProfileVO> userProfileVOList = userVO.getUserProfileVOList();
			if (userProfileVOList != null && userProfileVOList.size() > 0) {
				for (UserProfileVO userProfileVO : userProfileVOList) {				
					if (userProfileVO.getProfileVO() != null
							&& userProfileVO.getProfileVO().getProfileRoleVOList() != null
							&& userProfileVO.getProfileVO().getProfileRoleVOList().size() > 0) {					
						for (ProfileRoleVO profileRoleVO : userProfileVO.getProfileVO().getProfileRoleVOList()) {						
							if (profileRoleVO.getRoleVO().getName().equalsIgnoreCase (name)) {
								isInRole = true;
								break;
							}						
						}	
					}				
				}
			}
		}
		return isInRole;
	}
	
}
