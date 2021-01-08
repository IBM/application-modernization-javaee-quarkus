/**
 * 
 */
package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

/**
 *  nrn
 *
 */
public class NoLineItemsException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = -3162059688727892010L;


	

	/**
	 * 
	 */
	public NoLineItemsException() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/NoLineItemsException.java:NoLineItemsException:NoLineItemsException [overloaded_#001]");
    }

	/**
	 *  message
	 */
	public NoLineItemsException(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/NoLineItemsException.java:NoLineItemsException:NoLineItemsException [overloaded_#002]");
    }

	/**
	 *  cause
	 */
	public NoLineItemsException(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/NoLineItemsException.java:NoLineItemsException:NoLineItemsException [overloaded_#003]");
    }

	/**
	 *  message
	 *  cause
	 */
	public NoLineItemsException(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/NoLineItemsException.java:NoLineItemsException:NoLineItemsException [overloaded_#004]");
    }

}

