/**
 * 
 */
package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

/**
 *  nrn
 *
 */
public class OrderAlreadyOpenException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8610794306148655383L;


	

	/**
	 * 
	 */
	public OrderAlreadyOpenException() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderAlreadyOpenException.java:OrderAlreadyOpenException:OrderAlreadyOpenException [overloaded_#001]");
    }

	/**
	 *  message
	 */
	public OrderAlreadyOpenException(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderAlreadyOpenException.java:OrderAlreadyOpenException:OrderAlreadyOpenException [overloaded_#002]");
    }

	/**
	 *  cause
	 */
	public OrderAlreadyOpenException(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderAlreadyOpenException.java:OrderAlreadyOpenException:OrderAlreadyOpenException [overloaded_#003]");
    }

	/**
	 *  message
	 *  cause
	 */
	public OrderAlreadyOpenException(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderAlreadyOpenException.java:OrderAlreadyOpenException:OrderAlreadyOpenException [overloaded_#004]");
    }

}

