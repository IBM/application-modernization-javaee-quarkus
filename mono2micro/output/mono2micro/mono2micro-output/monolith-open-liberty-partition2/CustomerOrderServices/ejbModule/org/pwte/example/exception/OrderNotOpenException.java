/**
 * 
 */
package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

/**
 *  nrn
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
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderNotOpenException.java:OrderNotOpenException:OrderNotOpenException [overloaded_#001]");
    }

	/**
	 *  message
	 */
	public OrderNotOpenException(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderNotOpenException.java:OrderNotOpenException:OrderNotOpenException [overloaded_#002]");
    }

	/**
	 *  cause
	 */
	public OrderNotOpenException(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderNotOpenException.java:OrderNotOpenException:OrderNotOpenException [overloaded_#003]");
    }

	/**
	 *  message
	 *  cause
	 */
	public OrderNotOpenException(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderNotOpenException.java:OrderNotOpenException:OrderNotOpenException [overloaded_#004]");
    }

}

