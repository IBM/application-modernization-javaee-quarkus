package org.pwte.example.domain;

import com.ibm.cardinal.util.*;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Column;



public class BusinessCustomer extends AbstractCustomer implements Serializable {

	public BusinessCustomer() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/BusinessCustomer.java:BusinessCustomer:BusinessCustomer");
    }
	
	
	
	
	
	
	
	
	public boolean isVolumeDiscount() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/BusinessCustomer.java:BusinessCustomer:isVolumeDiscount");
    }
	public void setVolumeDiscount(String volumeDiscount) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/BusinessCustomer.java:BusinessCustomer:setVolumeDiscount");
    }
	public boolean isBusinessPartner() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/BusinessCustomer.java:BusinessCustomer:isBusinessPartner");
    }
	public void setBusinessPartner(String businessPartner) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/BusinessCustomer.java:BusinessCustomer:setBusinessPartner");
    }
	public String getDescription() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/BusinessCustomer.java:BusinessCustomer:getDescription");
    }
	public void setDescription(String description) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/BusinessCustomer.java:BusinessCustomer:setDescription");
    }
	

}
