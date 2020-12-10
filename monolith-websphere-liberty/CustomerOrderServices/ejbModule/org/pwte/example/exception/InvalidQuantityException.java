/**
 * 
 */
package org.pwte.example.exception;

/**
 * @author nrn
 *
 */
public class InvalidQuantityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -747266767912834979L;

	/**
	 * 
	 */
	public InvalidQuantityException() {

	}

	/**
	 * @param message
	 */
	public InvalidQuantityException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public InvalidQuantityException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidQuantityException(String message, Throwable cause) {
		super(message, cause);

	}

}
