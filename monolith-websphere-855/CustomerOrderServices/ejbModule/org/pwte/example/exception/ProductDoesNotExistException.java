package org.pwte.example.exception;

public class ProductDoesNotExistException extends Exception {

	private static final long serialVersionUID = 2187990386396121880L;

	public ProductDoesNotExistException() {

	}

	public ProductDoesNotExistException(String message) {
		super(message);
	}

	public ProductDoesNotExistException(Throwable cause) {
		super(cause);
	}

	public ProductDoesNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

}
