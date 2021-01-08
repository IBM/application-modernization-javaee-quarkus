package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

public class ProductDoesNotExistException extends Exception {
    private static final long serialVersionUID = 2187990386396121880L;


	

	public ProductDoesNotExistException() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/ProductDoesNotExistException.java:ProductDoesNotExistException:ProductDoesNotExistException [overloaded_#001]");
    }

	public ProductDoesNotExistException(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/ProductDoesNotExistException.java:ProductDoesNotExistException:ProductDoesNotExistException [overloaded_#002]");
    }

	public ProductDoesNotExistException(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/ProductDoesNotExistException.java:ProductDoesNotExistException:ProductDoesNotExistException [overloaded_#003]");
    }

	public ProductDoesNotExistException(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/ProductDoesNotExistException.java:ProductDoesNotExistException:ProductDoesNotExistException [overloaded_#004]");
    }

}

