/**
 * 
 */
package org.pwte.example.exception;

/**
 * @author nrn
 *
 */
public class GeneralPersistenceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2989571709692535331L;

	/**
	 * 
	 */
	public GeneralPersistenceException() {

	}

	/**
	 * @param message
	 */
	public GeneralPersistenceException(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public GeneralPersistenceException(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public GeneralPersistenceException(String message, Throwable cause) {
		super(message, cause);

	}

}
