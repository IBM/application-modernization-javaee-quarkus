package org.pwte.example.domain;

import com.ibm.cardinal.util.*;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;





public abstract class AbstractCustomer implements Serializable {
    private static final long serialVersionUID = -4988539679853665972L;


	

	public AbstractCustomer() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:AbstractCustomer");
    }

	
	
	
	

	

	

	

	public Set<Order> getOrders() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:getOrders");
    }

	public void setOrders(Set<Order> orders) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:setOrders");
    }

	public Order getOpenOrder() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:getOpenOrder");
    }

	public void setOpenOrder(Order openOrder) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:setOpenOrder");
    }

	public Address getAddress() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:getAddress");
    }

	public void setAddress(Address address) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:setAddress");
    }

	
	public int getCustomerId() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:getCustomerId");
    }

	
	public void setCustomerId(int customerId) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:setCustomerId");
    }

	public String getName() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:getName");
    }

	public void setName(String name) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:setName");
    }

	public String getType() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:getType");
    }

	public void setType(String type) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:setType");
    }

	
	public String getUser() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:getUser");
    }

	
	public void setUser(String user) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/AbstractCustomer.java:AbstractCustomer:setUser");
    }

}

