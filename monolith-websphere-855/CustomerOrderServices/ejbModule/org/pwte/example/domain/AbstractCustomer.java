package org.pwte.example.domain;

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

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "CUSTOMER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractCustomer implements Serializable {

	private static final long serialVersionUID = -4988539679853665972L;

	public AbstractCustomer() {

	}

	@Id
	@Column(name = "CUSTOMER_ID")

	protected int customerId;
	protected String name;
	protected String type;
	@Column(name = "USERNAME", table = "CUSTOMER")
	protected String user;

	@Embedded
	protected Address address;

	@OneToOne(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	@JoinColumn(name = "OPEN_ORDER", referencedColumnName = "ORDER_ID")
	protected Order openOrder;

	@OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
	protected Set<Order> orders;

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	public Order getOpenOrder() {
		return openOrder;
	}

	public void setOpenOrder(Order openOrder) {
		this.openOrder = openOrder;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@JsonIgnore
	public int getCustomerId() {
		return customerId;
	}

	@JsonIgnore
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@JsonIgnore
	public String getUser() {
		return user;
	}

	@JsonIgnore
	public void setUser(String user) {
		this.user = user;
	}

}
