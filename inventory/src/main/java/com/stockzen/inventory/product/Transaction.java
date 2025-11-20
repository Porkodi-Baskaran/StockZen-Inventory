package com.stockzen.inventory.product;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transaction 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
    
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    
    private String name;
    
    private LocalDate date;
    
    private Integer quantity;
    
    private Integer priceperunit;
    
    private String status;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPriceperunit() {
		return priceperunit;
	}

	public void setPriceperunit(Integer priceperunit) {
		this.priceperunit = priceperunit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Transaction [Id=" + Id + ", product=" + product + ", type=" + type + ", name=" + name + ", date=" + date
				+ ", quantity=" + quantity + ", priceperunit=" + priceperunit + ", status=" + status + "]";
	}
    
    
    
    
	
}
