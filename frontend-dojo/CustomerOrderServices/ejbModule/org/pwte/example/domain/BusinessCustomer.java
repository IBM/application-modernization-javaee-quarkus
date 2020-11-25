package org.pwte.example.domain;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Column;

@Entity
@DiscriminatorValue("BUSINESS")
public class BusinessCustomer extends AbstractCustomer implements Serializable {

	public BusinessCustomer() {
		// TODO Auto-generated constructor stub
	}
	
	@Column(name="BUSINESS_VOLUME_DISCOUNT")
	protected String volumeDiscount;
	
	
	@Column(name="BUSINESS_PARTNER")
	protected String businessPartner;
	
	
	@Column(name="BUSINESS_DESCRIPTION")
	protected String description;
	public boolean isVolumeDiscount() {
		return "Y".equals(volumeDiscount);
	}
	public void setVolumeDiscount(String volumeDiscount) {
		this.volumeDiscount = volumeDiscount;
	}
	public boolean isBusinessPartner() {
		return "Y".equals(businessPartner);
	}
	public void setBusinessPartner(String businessPartner) {
		this.businessPartner = businessPartner;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}