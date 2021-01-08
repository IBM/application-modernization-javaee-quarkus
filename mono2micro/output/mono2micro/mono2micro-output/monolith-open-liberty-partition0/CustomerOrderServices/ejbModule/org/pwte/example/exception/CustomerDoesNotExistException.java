/**
 * 
 */
package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

/**
 *  nrn
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
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/CustomerDoesNotExistException.java:CustomerDoesNotExistException:CustomerDoesNotExistException [overloaded_#001]");
    }

	/**
	 *  message
	 */
	public CustomerDoesNotExistException(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/CustomerDoesNotExistException.java:CustomerDoesNotExistException:CustomerDoesNotExistException [overloaded_#002]");
    }

	/**
	 *  cause
	 */
	public CustomerDoesNotExistException(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/CustomerDoesNotExistException.java:CustomerDoesNotExistException:CustomerDoesNotExistException [overloaded_#003]");
    }

	/**
	 *  message
	 *  cause
	 */
	public CustomerDoesNotExistException(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/CustomerDoesNotExistException.java:CustomerDoesNotExistException:CustomerDoesNotExistException [overloaded_#004]");
    }

}

