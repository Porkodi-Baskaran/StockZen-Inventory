package com.stockzen.inventory.purchase.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stockzen.inventory.product.*;
import com.stockzen.inventory.purchase.dto.PurchaseItemDTO;
import com.stockzen.inventory.purchase.dto.PurchaseRequestDTO;
import com.stockzen.inventory.purchase.entity.Purchase;
import com.stockzen.inventory.purchase.entity.PurchaseMaster;
import com.stockzen.inventory.purchase.entity.Supplier;
import com.stockzen.inventory.purchase.repository.PurchaseMasterRepo;
import com.stockzen.inventory.purchase.repository.PurchaseRepo;
import com.stockzen.inventory.purchase.repository.SupplierRepo;

import jakarta.transaction.Transactional;

@Service
public class PurchaseService {
	@Autowired
	private PurchaseMasterRepo purchaseMasterRepo;
	
    @Autowired
    private PurchaseRepo purchaseRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private TransactionRepo transactionRepo;
    
    @Autowired
    private SupplierRepo supplierRepo;
    
    
    Supplier supplier;

    @Transactional
    public String addPurchase(PurchaseRequestDTO dto) {

    	// 1. If supplierId is sent — use it
    	if (dto.getSupplierId() != null) {
    	    supplier = supplierRepo.findById(dto.getSupplierId())
    	            .orElse(null);
    	} else {
    	    supplier = null;
    	}

    	// 2. If supplier not found by ID, search by phone number
    	if (supplier == null && dto.getSupplierPhone() != null) {
    	    supplier = supplierRepo.findByPhoneNumber(dto.getSupplierPhone()).orElse(null);
    	}

    	// 3. If still not found → create new supplier
    	if (supplier == null) {
    	    supplier = new Supplier();
    	    supplier.setName(dto.getSupplierName());
    	    supplier.setAddress(dto.getSupplierAddress());
    	    supplier.setPhoneNumber(dto.getSupplierPhone());
    	    supplier = supplierRepo.save(supplier);
    	}

        PurchaseMaster master = new PurchaseMaster();
        master.setSupplier(supplier);
        master.setInvoiceNumber(dto.getInvoiceNumber());
        master.setPurchaseDate(LocalDate.now());
        master.setPaymentType(dto.getPaymentType());

        int grandTotal = 0;

        master = purchaseMasterRepo.save(master);

        for (PurchaseItemDTO itemDto : dto.getItems()) {

            Product product = productRepo.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            int base = itemDto.getQuantity() * itemDto.getPricePerUnit();
            int gst = (base * itemDto.getGstPercentage()) / 100;
            int total = base + gst;

            // Update stock
            product.setOpeningquantity(product.getOpeningquantity() + itemDto.getQuantity());
            productRepo.save(product);

            // Save item
            Purchase item = new Purchase();
            item.setPurchaseMaster(master);
            item.setProduct(product);
            item.setQuantity(itemDto.getQuantity());
            item.setPricePerUnit(itemDto.getPricePerUnit());
            item.setGstPercentage(itemDto.getGstPercentage());
            item.setGstAmount(gst);
            item.setTotalAmount(total);
            purchaseRepo.save(item);

            grandTotal += total;
        }

        master.setGrandTotal(grandTotal);
        master.setAmountPaid(dto.getAmountPaid());
        master.setBalanceAmount(grandTotal - dto.getAmountPaid());
        purchaseMasterRepo.save(master);
        
        //Transaction
        Transaction txn = new Transaction();
        txn.setType(TransactionType.PURCHASE);
        txn.setDate(master.getPurchaseDate());
        txn.setName(master.getSupplier().getName()); // Supplier name
        txn.setInvoiceNumber(master.getInvoiceNumber());
        txn.setAmountPaid(master.getAmountPaid());
        txn.setBalanceAmount(master.getBalanceAmount());
        txn.setPaymentType(master.getPaymentType());
        txn.setStatus("COMPLETED");

        transactionRepo.save(txn);


        return "Purchase saved successfully";
    }
    
    public PurchaseMaster getPurchaseById(Integer id) {
        return purchaseMasterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));
    }
    
    @Transactional
    public String deletePurchase(Integer id) {

        PurchaseMaster master = purchaseMasterRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        List<Purchase> items = purchaseRepo.findByPurchaseMasterId(id);

        // Rollback stock
        for (Purchase item : items) {
            Product product = item.getProduct();
            product.setOpeningquantity(
                    product.getOpeningquantity() - item.getQuantity()
            );
            productRepo.save(product);
        }

        // Delete child items
        purchaseRepo.deleteAll(items);

        //Delete related transactions
        transactionRepo.deleteByInvoiceNumber(master.getInvoiceNumber());

        //Delete master
        purchaseMasterRepo.delete(master);

        return "Purchase deleted successfully";
    }

   }
