package com.example.demo.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderRequest {

	private List<OrderItemRequest> items;

	public List<OrderItemRequest> getItems() {
		return items;
	}

	public void setItems(List<OrderItemRequest> items) {
		this.items = items;
	}

	
	public static class OrderItemRequest {

		private String productCode;
		private Integer quantity;
		
		private BigDecimal unitPrice;

		public String getProductCode() {
			return productCode;
		}

		public void setProductCode(String productCode) {
			this.productCode = productCode;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public BigDecimal getUnitPrice() {
			return unitPrice;
		}

		public void setUnitPrice(BigDecimal unitPrice) {
			this.unitPrice = unitPrice;
		}
	}
}
