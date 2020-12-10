package org.pwte.example.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class LineItemId implements Serializable {

	private static final long serialVersionUID = -2981956753791571664L;

	protected int orderId;

	protected int productId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + orderId;
		result = PRIME * result + productId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final LineItemId other = (LineItemId) obj;
		if (orderId != other.orderId)
			return false;
		if (productId != other.productId)
			return false;
		return true;
	}

}
