/**
 * 
 */
package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

/**
 *  nrn
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
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/InvalidQuantityException.java:InvalidQuantityException:InvalidQuantityException [overloaded_#001]");
    }

	/**
	 *  message
	 */
	public InvalidQuantityException(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/InvalidQuantityException.java:InvalidQuantityException:InvalidQuantityException [overloaded_#002]");
    }

	/**
	 *  cause
	 */
	public InvalidQuantityException(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/InvalidQuantityException.java:InvalidQuantityException:InvalidQuantityException [overloaded_#003]");
    }

	/**
	 *  message
	 *  cause
	 */
	public InvalidQuantityException(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/InvalidQuantityException.java:InvalidQuantityException:InvalidQuantityException [overloaded_#004]");
    }

}

