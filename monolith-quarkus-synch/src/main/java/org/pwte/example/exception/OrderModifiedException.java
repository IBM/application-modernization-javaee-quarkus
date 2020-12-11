package org.pwte.example.exception;

public class OrderModifiedException extends Exception {

	private static final long serialVersionUID = -7003204590830712051L;

	public OrderModifiedException() {
		
	}

	public OrderModifiedException(String message) {
		super(message);
	}

	public OrderModifiedException(Throwable cause) {
		super(cause);
	}

	public OrderModifiedException(String message, Throwable cause) {
		super(message, cause);
	}

}
