package org.pwte.example.domain;

import com.ibm.cardinal.util.*;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Basic;




public class ResidentialCustomer extends AbstractCustomer implements
		Serializable {

	public ResidentialCustomer() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/ResidentialCustomer.java:ResidentialCustomer:ResidentialCustomer");
    }
	
	
	
	
	


	public short getHouseholdSize() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/ResidentialCustomer.java:ResidentialCustomer:getHouseholdSize");
    }


	public void setHouseholdSize(short householdSize) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/ResidentialCustomer.java:ResidentialCustomer:setHouseholdSize");
    }


	public boolean isFrequentCustomer() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/ResidentialCustomer.java:ResidentialCustomer:isFrequentCustomer");
    }


	public void setFrequentCustomer(String frequentCustomer) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/ResidentialCustomer.java:ResidentialCustomer:setFrequentCustomer");
    }


	
	
	
}
