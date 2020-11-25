/**
 * 
 */
package org.pwte.example.exception;

/**
 * @author nrn
 *
 */
public class OrderNotOpenException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6233880919327209994L;

	/**
	 * 
	 */
	public OrderNotOpenException() {

	}

	/**
	 * @param message
	 */
	public OrderNotOpenException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public OrderNotOpenException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public OrderNotOpenException(String message, Throwable cause) {
		super(message, cause);

	}

}
