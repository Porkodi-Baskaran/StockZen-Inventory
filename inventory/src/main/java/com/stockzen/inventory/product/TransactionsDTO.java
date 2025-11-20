package com.stockzen.inventory.product;

public class TransactionsDTO 
{
		private Integer productId;
	    private String type;       
	    private String name;
	    private Integer quantity;
	    private Integer pricePerUnit;
	    private String status;
	    
	    
	    
		public Integer getProductId() {
			return productId;
		}
		public void setProductId(Integer productId) {
			this.productId = productId;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		public Integer getPricePerUnit() {
			return pricePerUnit;
		}
		public void setPricePerUnit(Integer pricePerUnit) {
			this.pricePerUnit = pricePerUnit;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
	    
	    
}
