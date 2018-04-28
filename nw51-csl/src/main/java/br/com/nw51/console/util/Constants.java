package br.com.nw51.console.util;

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
	 * Constant that defines the key used by the logged user.
	 */
	public static final String LOGGED_USER = "loggedUser";
	/**
	 * User constant that defines the state of not logged user.
	 */
	public static final int USER_STATE_NOT_LOGGED = 0;
	/**
	 * User constant that defines the state of logged user.
	 */
	public static final int USER_STATE_LOGGED = 1;
	/**
	 * User constant that defines the state of blocked user.
	 */
	public static final int USER_STATE_BLOCKED = 2;
	
	/**
	 * Page names constants.
	 */
	public static final String USERS_PAGE   = "users.page";
	public static final String COURSES_PAGE = "courses.page";
	/**
	 * Authentication filter constants.
	 */
	public static final String URL_LOGIN_PAGE = "/csl/login.page";
	/**
	 * Role read name constants.
	 */
	public static final String ROLE_USER_READ   = "papel_usuario_ler";
	public static final String ROLE_COURSE_READ = "papel_curso_ler";
	/**
	 * Log service constants.
	 */
	public static final int LOG_STATUS_SUCCESS = 1;
	public static final int LOG_STATUS_ERROR = 0;
	
}
