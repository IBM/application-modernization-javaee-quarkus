/**
 * 
 */
package org.pwte.example.exception;

/**
 * @author nrn
 *
 */
public class CategoryDoesNotExist extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2384396576925376484L;

	/**
	 * 
	 */
	public CategoryDoesNotExist() {

	}

	/**
	 * @param message
	 */
	public CategoryDoesNotExist(String message) {
		super(message);

	}

	/**
	 * @param cause
	 */
	public CategoryDoesNotExist(Throwable cause) {
		super(cause);

	}

	/**
	 * @param message
	 * @param cause
	 */
	public CategoryDoesNotExist(String message, Throwable cause) {
		super(message, cause);

	}

}
