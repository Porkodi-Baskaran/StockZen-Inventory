package com.stockzen.inventory.product;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockAdjustment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	@NotNull(message = "Adjustment type is required")
	@Enumerated(EnumType.STRING)
	private AdjustmentType adjustmentType;
	
	@NotNull(message="Quantity is required")
	@Min(value=1,message="Quantity must be atleast 1")
	private Integer qty;
	
	@NotNull(message="Per piece price is required")
	@Min(value=0,message="Price can't be negative")
	private Integer perPiecePrice;
	
	@Size(max = 200, message = "Details must be less than 200 characters")
	private String details;
	
	private LocalDate date=LocalDate.now();
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public AdjustmentType getAdjustmentType() {
		return adjustmentType;
	}
	public void setAdjustmentType(AdjustmentType adjustmentType) {
		this.adjustmentType = adjustmentType;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Integer getPerPiecePrice() {
		return perPiecePrice;
	}
	public void setPerPiecePrice(Integer perPiecePrice) {
		this.perPiecePrice = perPiecePrice;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
}
