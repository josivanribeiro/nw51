package br.com.nw51.student.util;

/**
 * Class containing useful constants.
 * 
 * @author Josivan Silva
 *
 */
public class Constants {

	public static final String USER_DIR = "user.dir";
	public static final String DEV_CONFIG_PATH = "dev.config.path";
	/**
	 * Constant that defines the key used by the logged student.
	 */
	public static final String LOGGED_STUDENT = "loggedStudent";
	/**
	 * Constant that defines the state of not logged student.
	 */
	public static final int STUDENT_STATE_NOT_LOGGED = 0;
	/**
	 * Constant that defines the state of logged student.
	 */
	public static final int STUDENT_STATE_LOGGED = 1;
	/**
	 * Constant that defines the state of blocked student.
	 */
	public static final int STUDENT_STATE_BLOCKED = 2;
	
	/**
	 * Page constants.
	 */
	public static final String URL_LOGIN_PAGE = "login.page";
	
	public static final String URI_WELCOME_PAGE = "/student/";
	
	public static final String DASHBOARD_PAGE = "dashboard.page";
	
	public static final String PROFILE_PAGE = "profile.page";
	
	/**
	 * Log service constants.
	 */
	public static final int LOG_STATUS_SUCCESS = 1;
	public static final int LOG_STATUS_ERROR = 0;
	
}
