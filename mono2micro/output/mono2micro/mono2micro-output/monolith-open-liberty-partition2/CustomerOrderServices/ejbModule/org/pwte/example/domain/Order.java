package org.pwte.example.domain;

import com.ibm.cardinal.util.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;



public class Order implements Serializable {
    private static final long serialVersionUID = -7488064826451093257L;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	  
	
	  
	
	public long getVersion() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:getVersion");
    }
	
	
	public void setVersion(long version) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:setVersion");
    }
	public Set<LineItem> getLineitems() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:getLineitems");
    }
	public void setLineitems(Set<LineItem> lineitmes) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:setLineitems");
    }
	
	
	public AbstractCustomer getCustomer() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:getCustomer");
    }
	
	
	public void setCustomer(AbstractCustomer customer) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:setCustomer");
    }
	public int getOrderId() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:getOrderId");
    }
	public void setOrderId(int orderId) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:setOrderId");
    }
	public BigDecimal getTotal() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:getTotal");
    }
	public void setTotal(BigDecimal total) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:setTotal");
    }
	public String getStatus() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:getStatus");
    }
	public void setStatus(String status) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:setStatus");
    }

	public Date getSubmittedTime() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:getSubmittedTime");
    }

	public void setSubmittedTime(Date submittedTime) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/Order.java:Order:setSubmittedTime");
    }

}

