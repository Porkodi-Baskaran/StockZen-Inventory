package com.stockzen.inventory.product;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TransactionService
{
	@Autowired
	TransactionRepo transrepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	
	public void recordtransaction(TransactionsDTO dto)
	{
		Transaction transaction=new Transaction();
		
		Product product=productRepo.findById(dto.getProductId()).orElseThrow(()->new RuntimeException("product not found"));
		
		
		transaction.setProduct(product);
        transaction.setType(TransactionType.valueOf(dto.getType()));
        transaction.setName(dto.getName());
        transaction.setDate(LocalDate.now());
        transaction.setQuantity(dto.getQuantity());
        transaction.setPriceperunit(dto.getPricePerUnit());
        if (dto.getType().equals("PURCHASE") || dto.getType().equals("SALE")) {
            transaction.setStatus(dto.getStatus());
        }
        else
        {
        	transaction.setStatus("");
        }

		transrepo.save(transaction);
	}
	
	public List<Transaction> getAllTransaction()
	{
		return transrepo.findAll();
	}
	
	public Transaction getTransactionById(Integer id)
	{
		Transaction transaction = transrepo.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
		return transaction;
	}
}
