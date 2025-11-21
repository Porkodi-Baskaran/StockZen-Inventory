package com.stockzen.inventory.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer>
{
	void deleteByInvoiceNumber(String invoiceNumber);
}
