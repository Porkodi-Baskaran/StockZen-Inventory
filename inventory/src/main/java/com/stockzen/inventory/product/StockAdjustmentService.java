package com.stockzen.inventory.product;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class StockAdjustmentService {
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	StockAdjustmentRepo stockAdjustmentRepo;
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Transactional
	public void adjustStock(Integer productId,StockAdjustment adjustment)
	{
		Product product = productRepo.findById(productId).orElseThrow(()->new RuntimeException("Product not found"));
	
		if(adjustment.getAdjustmentType() == AdjustmentType.ADD)
		{
			product.setOpeningquantity(product.getOpeningquantity()+adjustment.getQty());
			
			Integer updateStockValue=adjustment.getQty()* adjustment.getPerPiecePrice();
			product.setStockValue(product.getStockValue()+updateStockValue);
		}
		else if(adjustment.getAdjustmentType() == AdjustmentType.REDUCE)
		{
			if(product.getOpeningquantity()< adjustment.getQty())
				throw new RuntimeException("Insufficient stock to reduce");
			product.setOpeningquantity(product.getOpeningquantity()-adjustment.getQty());
			
			Integer updateStockValue=adjustment.getQty()* adjustment.getPerPiecePrice();
			product.setStockValue(product.getStockValue()-updateStockValue);
		}
		
		productRepo.save(product);
		adjustment.setProduct(product);
		stockAdjustmentRepo.save(adjustment);
		
		
		 Transaction trans = new Transaction();
		    trans.setProduct(product);
		    trans.setDate(LocalDate.now());
		    trans.setName(adjustment.getDetails()); 
		    trans.setQuantity(adjustment.getQty());
		    trans.setPriceperunit(adjustment.getPerPiecePrice());
		    trans.setStatus("Stock Adjustment done");
		    
		    if (adjustment.getAdjustmentType() == AdjustmentType.ADD) {
		        trans.setType(TransactionType.ADD_ADJUSTMENT);
		    } else {
		        trans.setType(TransactionType.REDUCE_ADJUSTMENT);
		    }

		    
		    transactionRepo.save(trans);
	}
}
