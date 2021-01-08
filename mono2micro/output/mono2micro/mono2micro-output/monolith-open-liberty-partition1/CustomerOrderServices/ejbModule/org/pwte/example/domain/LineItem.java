package org.pwte.example.domain;

import com.ibm.cardinal.util.*;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;




public class LineItem implements Serializable {
    private static final long serialVersionUID = -447939565773238603L;


	

	

	
	
	
	
	

	public BigDecimal getPriceCurrent() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:getPriceCurrent");
    }

	public void setPriceCurrent(BigDecimal priceCurrent) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:setPriceCurrent");
    }
	
	

	
	

	

	
	public int getOrderId() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:getOrderId");
    }

	public void setOrderId(int orderId) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:setOrderId");
    }

	public int getProductId() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:getProductId");
    }

	public void setProductId(int productId) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:setProductId");
    }



	public long getQuantity() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:getQuantity");
    }

	public void setQuantity(long quantity) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:setQuantity");
    }

	public BigDecimal getAmount() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:getAmount");
    }

	public void setAmount(BigDecimal amount) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:setAmount");
    }

	public Product getProduct() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:getProduct");
    }

	public void setProduct(Product product) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:setProduct");
    }

	
	public Order getOrder() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:getOrder");
    }

	
	public void setOrder(Order order) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:setOrder");
    }

	
	void calculateTotalAdd() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:calculateTotalAdd");
    }

	
	void recalculateTotal() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:recalculateTotal");
    }

	
	void calculateTotalRemove() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:calculateTotalRemove");
    }

	
	public void setVersion(long version) {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:setVersion");
    }

	
	public long getVersion() {
		throw new CardinalException("ERROR: dummy function called at CustomerOrderServices/ejbModule/org/pwte/example/domain/LineItem.java:LineItem:getVersion");
    }

}

