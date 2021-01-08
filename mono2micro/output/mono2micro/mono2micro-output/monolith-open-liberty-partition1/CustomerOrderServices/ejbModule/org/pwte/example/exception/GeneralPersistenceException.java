/**
 * 
 */
package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

/**
 *  nrn
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
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/GeneralPersistenceException.java:GeneralPersistenceException:GeneralPersistenceException [overloaded_#001]");
    }

	/**
	 *  message
	 */
	public GeneralPersistenceException(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/GeneralPersistenceException.java:GeneralPersistenceException:GeneralPersistenceException [overloaded_#002]");
    }

	/**
	 *  cause
	 */
	public GeneralPersistenceException(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/GeneralPersistenceException.java:GeneralPersistenceException:GeneralPersistenceException [overloaded_#003]");
    }

	/**
	 *  message
	 *  cause
	 */
	public GeneralPersistenceException(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/GeneralPersistenceException.java:GeneralPersistenceException:GeneralPersistenceException [overloaded_#004]");
    }

}

