package br.com.nw51.console.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.controller.LoginController;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.UserService;
import br.com.nw51.console.util.Constants;

/**
 * SessionExpired Listener.
 * 
 * @author Josivan Silva
 *
 */
public class SessionExpiredListener implements HttpSessionListener {

	static Logger logger = Logger.getLogger (SessionExpiredListener.class.getName());
	private UserService userService = new UserService ();
	
	@Override
	public void sessionCreated (HttpSessionEvent se) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void sessionDestroyed (HttpSessionEvent se) {
		logger.debug ("Starting sessionDestroyed.");
		UserVO userVO = null;
		userVO = this.getLoggedUser (se.getSession());
		logger.debug ("userVO.getState(): " + userVO.getState());		
		if (userVO != null && userVO.getState() == Constants.USER_STATE_LOGGED) {
			try {
				userVO.setState (Constants.USER_STATE_NOT_LOGGED);
				userService.updateState (userVO);
			} catch (BusinessException e) {
				String error = "An error occurred while updating the state of users. " + e.getMessage();
				logger.error (error);
			}
		}		
		logger.debug ("Finishing sessionDestroyed.");
	}
	
	/**
	 * Gets logged user from session.
	 * 
	 * @param session the session.
	 * @return the logged user.
	 */
	private UserVO getLoggedUser (HttpSession session) {
		LoginController loginController = (LoginController) session.getAttribute("loginController");
		return loginController.getLoggedUser();
	}

}
