package br.com.nw51.console.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.nw51.console.controller.LoginController;
import br.com.nw51.console.util.Constants;

/**
 * Authentication Security Filter.
 * 
 * @author Josivan Silva
 *
 */
public class AuthenticationFilter implements Filter {
	
	static Logger logger = Logger.getLogger (AuthenticationFilter.class.getName());
		
	@Override
	public void init (FilterConfig filterConfig) throws ServletException {
		
	}

	@Override
	public void doFilter (ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
	    HttpServletResponse response = (HttpServletResponse) res;
	    String uri = request.getRequestURI();
	    HttpSession session = request.getSession (false);
	    if (this.isValidURI (uri)) {
	    	logger.debug ("uri: " + uri);
	    	if (!this.isLoggedUser (request, session)) {
				logger.warn ("User is not authenticated.");
				if (session != null) {
					session.invalidate();
				}
				response.sendRedirect (Constants.URL_LOGIN_PAGE);
			}
	    }
	    chain.doFilter (request, response);
	}	

	@Override
	public void destroy() {
		
	}
	
	/**
	 * Checks if the URI is valid or not.
	 * 
	 * @param uri the URI.
	 * @return
	 */
	private boolean isValidURI (String uri) {
		if (!uri.equals ("/csl/")
				&& uri.indexOf (Constants.URL_LOGIN_PAGE) == -1
	    		&& uri.indexOf ("/resources/") == -1
	    		&& uri.indexOf ("/javax.faces.resource/") == -1
	    		&& uri.indexOf ("RES_NOT_FOUND") == -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if the user is logged or not.
	 * 
	 * @param request the request.
	 * @return the operation result.
	 */
	private boolean isLoggedUser (HttpServletRequest request, HttpSession session) {
		boolean isLogged = false;
		if (session != null				
				&& session.getAttribute("loginController") != null) {
			LoginController loginController = (LoginController) session.getAttribute("loginController");
			if (loginController.isLogged()
					&& loginController.getLoggedUser() != null) {
				isLogged = true;
			}
		}
		return isLogged;
	}

}
