
package br.com.nw51.console.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import br.com.nw51.common.vo.UserVO;
import br.com.nw51.console.controller.LoginController;
import br.com.nw51.console.services.BusinessException;
import br.com.nw51.console.services.UserService;
import br.com.nw51.console.util.Constants;
import br.com.nw51.console.util.SecurityUtils;

/**
 * Authorization Security Filter.
 * 
 * @author Josivan Silva
 *
 */
public class AuthorizationFilter implements Filter {
	
	static Logger logger = Logger.getLogger (AuthorizationFilter.class.getName());
	private List<String> pageAndRoleList = new ArrayList<String>();
	private UserService userService = new UserService ();
	
	@Override
	public void init (FilterConfig filterConfig) throws ServletException {
		this.initPageAndRoleList ();
	}
	
	@Override
	public void doFilter (ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;				
		HttpSession session = request.getSession (false);
		String uri = request.getRequestURI();
		if (this.isValidPage (session, request, uri)) {
			logger.debug("Checking access.");
			if (this.hasAccess (req)) {
				chain.doFilter (req, res);
			} else {
				logger.debug("Has no access.");
				UserVO loggedUser = getLoggedUser (request);
				if (loggedUser != null) {					
					if (loggedUser.getUnauthorizedAccessAttempts() < 2) {
						this.updateUnauthorizedAttempt (loggedUser);
					} else if (loggedUser.getUnauthorizedAccessAttempts() == 2) {
						this.updateUnauthorizedAttempt (loggedUser);
						loggedUser.setState (Constants.USER_STATE_BLOCKED);
						this.updateStateToBlocked (loggedUser);
					}					
				}						
				SecurityUtils.logout (req);
				response.sendRedirect (Constants.URL_LOGIN_PAGE);
			}
		} else {
			chain.doFilter (req, res);
		}
	}	
	
	@Override
	public void destroy() {
				
	}
	
	/**
	 * Checks the page that the logged user has access.
	 * 
	 * @param req the request.
	 * @return the success operation.
	 */
	private boolean hasAccess (ServletRequest req) {
		logger.debug ("Starting hasAccess.");
		boolean hasAccess = false;
		HttpServletRequest request = (HttpServletRequest) req;
		String uri = request.getRequestURI();
		outer:
		for (String item : this.pageAndRoleList) {
			
			String[] pageAndRoleArr = item.split ("@");
			String pages = pageAndRoleArr[0];
			String role = pageAndRoleArr[1];
			String[] pageArr = pages.split ("\\|");
			
			for (String page : pageArr) {				
				if (uri.endsWith (page)) {
					logger.debug ("page: " + page);
					logger.debug ("uri: " + uri);
					logger.debug ("role: " + role);
					
					UserVO loggedUser = getLoggedUser (request);					
					logger.debug ("loggedUser.getEmail(): " + loggedUser.getEmail());
					
					if (SecurityUtils.isUserInRole (role, loggedUser)) {
						hasAccess = true;
						logger.debug ("hasAccess: " + hasAccess);
						break outer;
					} else {
						logger.warn ("The user [" + SecurityUtils.getLoggedUser (request).getEmail() + "] has no access to the page [" + uri + "]");
					}
				}
			}
		}
		logger.debug ("Finishing hasAccess.");
		return hasAccess;
	}
		
	/**
	 * Initializes the page and role list.
	 */
	private void initPageAndRoleList () {
		pageAndRoleList.add ("roles.page|updateRole.page@papel_papel_ler");
		pageAndRoleList.add ("profiles.page|updateProfile.page@papel_perfil_ler");
		pageAndRoleList.add ("users.page|updateUser.page@papel_usuario_ler");
		pageAndRoleList.add ("teachers.page|updateTeacher.page@papel_professor_ler");
		pageAndRoleList.add ("courses.page|updateCourse.page|updateClass.page|updateSlide.page|updateExercise.page|updateQuestion.page@papel_curso_ler");
		pageAndRoleList.add ("careers.page|updateCareer.page@papel_profissao_ler");
		pageAndRoleList.add ("students.page|updateStudent.page@papel_aluno_ler");
		pageAndRoleList.add ("certificates.page|updateCertificate.page@papel_certificado_ler");
		pageAndRoleList.add ("payments.page|updatePayment.page@papel_pagamento_ler");
		pageAndRoleList.add ("logs.page|updateLog.page@papel_log_ler");
	}
	
	/**
	 * Checks if the given page should be checked its access.
	 * 
	 * @param session the http session.
	 * @param request the request.
	 * @param uri the uri.
	 * @return the result operation.
	 */
	private boolean isValidPage (HttpSession session, HttpServletRequest request, String uri) {
		if (session != null
				&& this.getLoggedUser (request) != null
				&& uri.indexOf (Constants.URL_LOGIN_PAGE) == -1
				&& uri.indexOf ("/resources/") == -1
	    		&& uri.indexOf ("/javax.faces.resource/") == -1
	    		&& uri.indexOf ("RES_NOT_FOUND") == -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets logged user from session.
	 * 
	 * @param request the request.
	 * @return the logged user.
	 */
	private UserVO getLoggedUser (HttpServletRequest request) {
		HttpSession session = request.getSession (false);
		if (session.getAttribute("loginController") != null) {
			LoginController loginController = (LoginController) session.getAttribute("loginController");
			if (loginController.getLoggedUser() != null) {
				return loginController.getLoggedUser();
			}
		}
		return null;
	}
	
	/**
	 * Updates the number of unauthorized attempt.
	 * 
	 * @param userVO the user.
	 */
	private void updateUnauthorizedAttempt (UserVO userVO) {
		logger.debug ("Starting updateUnauthorizedAttempt.");
		try {
			int unauthorizedAccessAttempts = userVO.getUnauthorizedAccessAttempts() == 0 ? 1 : userVO.getUnauthorizedAccessAttempts() + 1;
			userVO.setUnauthorizedAccessAttempts (unauthorizedAccessAttempts);
			logger.debug ("unauthorizedAccessAttempts: " + userVO.getUnauthorizedAccessAttempts());
			userService.updateUnauthorizedAccessAttempts (userVO);
		} catch (BusinessException e) {
			String error = "An error occurred while updating the unauthorized attempt. " + e.getMessage();
			logger.error (error);
		}
		logger.debug ("Finishing updateUnauthorizedAttempt.");
	}
	
	/**
	 * Updates the state of the user to state blocked.
	 * 
	 * @param userVO the user.
	 */
	private void updateStateToBlocked (UserVO userVO) {
		logger.debug ("Starting updateStateToBlocked.");
		try {
			userVO.setState (Constants.USER_STATE_BLOCKED);
			userService.updateState (userVO);
			logger.warn ("User account blocked due unauthorized access attempts exceeded.");
		} catch (BusinessException e) {
			String error = "An error occurred while updating the state to blocked. " + e.getMessage();
			logger.error (error);
		}
		logger.debug ("Finishing updateStateToBlocked.");
	}

}
