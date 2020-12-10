/**
 * 
 */
package org.pwte.example.exception;

/**
 * @author nrn
 *
 */
public class CustomerDoesNotExistException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8840757878297468020L;

	/**
	 * 
	 */
	public CustomerDoesNotExistException() {

	}

	/**
	 * @param message
	 */
	public CustomerDoesNotExistException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public CustomerDoesNotExistException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public CustomerDoesNotExistException(String message, Throwable cause) {
		super(message, cause);

	}

}
