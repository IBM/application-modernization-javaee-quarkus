package org.pwte.example.domain;

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

@Entity
@Table(name="ORDERS")
public class Order implements Serializable {
	
	private static final long serialVersionUID = -7488064826451093257L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ORDER_ID")
	protected int orderId;
	protected BigDecimal total;
	//public static enum Status { OPEN, SUBMITTED, SHIPPED, CLOSED }
	public static String STATUS_OPEN = "OPEN";
	public static String STATUS_SUBMITTED = "SUBMITTED";
	protected String status;
	
	@Column(name="SUBMIT_TIME")
	protected Date submittedTime;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CUSTOMER_ID",referencedColumnName="CUSTOMER_ID")
	protected AbstractCustomer customer;
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy="order")
	protected Set<LineItem> lineitems;
	  
	@Version
	protected long version;
	  
	@JsonbTransient
	public long getVersion() {
		return version;
	}
	
	@JsonbTransient
	public void setVersion(long version) {
		this.version = version;
	}
	public Set<LineItem> getLineitems() {
		return lineitems;
	}
	public void setLineitems(Set<LineItem> lineitmes) {
		this.lineitems = lineitmes;
	}
	
	@JsonbTransient
	public AbstractCustomer getCustomer() {
		return customer;
	}
	
	@JsonbTransient
	public void setCustomer(AbstractCustomer customer) {
		this.customer = customer;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Date getSubmittedTime() {
		return submittedTime;
	}

	public void setSubmittedTime(Date submittedTime) {
		this.submittedTime = submittedTime;
	}

}
