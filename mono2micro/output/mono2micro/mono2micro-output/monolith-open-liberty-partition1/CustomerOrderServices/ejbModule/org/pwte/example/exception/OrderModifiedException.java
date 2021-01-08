package org.pwte.example.exception;

import com.ibm.cardinal.util.*;

public class OrderModifiedException extends Exception {
    private static final long serialVersionUID = -7003204590830712051L;


	

	public OrderModifiedException() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderModifiedException.java:OrderModifiedException:OrderModifiedException [overloaded_#001]");
    }

	public OrderModifiedException(String message) {
		
		super(message);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderModifiedException.java:OrderModifiedException:OrderModifiedException [overloaded_#002]");
    }

	public OrderModifiedException(Throwable cause) {
		
		super(cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderModifiedException.java:OrderModifiedException:OrderModifiedException [overloaded_#003]");
    }

	public OrderModifiedException(String message, Throwable cause) {
		
		super(message, cause);
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/exception/OrderModifiedException.java:OrderModifiedException:OrderModifiedException [overloaded_#004]");
    }

}

