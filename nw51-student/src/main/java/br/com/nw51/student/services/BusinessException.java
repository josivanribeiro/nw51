package br.com.nw51.student.services;

/**
 * Root of the hierarchy of business exceptions.
 * 
 * @author Josivan Silva
 *
 */
public class BusinessException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor for BusinessException.
	 * 
	 * @param message the detail message.
	 */
	public BusinessException (String message) {
		super (message);
	}

	/**
	 * Constructor for BusinessException.
	 * 
	 * @param message the detail message.
	 * @param cause the root cause.
	 */
	public BusinessException (String message, Throwable cause) {
		super (message, cause);
	}
}

