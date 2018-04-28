package br.com.nw51.student.log4j;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Log4j initialization class.
 * 
 * @author Josivan Silva
 *
 */
public class Log4jInit extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String LOG4J_FILE = "student_log4j.properties";
	static Logger logger = Logger.getLogger (Log4jInit.class.getName());
	
	public void init() {
		String prefix =  getServletContext().getRealPath("/");
		prefix = prefix.replace("student/", "config/");
	    PropertyConfigurator.configure (prefix + LOG4J_FILE);
	    logger.info ("Log4j successfully initialized.");
	}

	public void doGet (HttpServletRequest req, HttpServletResponse res) {	
	}
	
}
