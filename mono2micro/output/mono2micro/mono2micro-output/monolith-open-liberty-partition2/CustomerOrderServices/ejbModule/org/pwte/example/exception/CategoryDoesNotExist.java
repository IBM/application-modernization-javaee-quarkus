/**
 * 
 */
package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

/**
 *  nrn
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
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/CategoryDoesNotExist.java:CategoryDoesNotExist:CategoryDoesNotExist [overloaded_#001]");
    }

	/**
	 *  message
	 */
	public CategoryDoesNotExist(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/CategoryDoesNotExist.java:CategoryDoesNotExist:CategoryDoesNotExist [overloaded_#002]");
    }

	/**
	 *  cause
	 */
	public CategoryDoesNotExist(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/CategoryDoesNotExist.java:CategoryDoesNotExist:CategoryDoesNotExist [overloaded_#003]");
    }

	/**
	 *  message
	 *  cause
	 */
	public CategoryDoesNotExist(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/CategoryDoesNotExist.java:CategoryDoesNotExist:CategoryDoesNotExist [overloaded_#004]");
    }

}

