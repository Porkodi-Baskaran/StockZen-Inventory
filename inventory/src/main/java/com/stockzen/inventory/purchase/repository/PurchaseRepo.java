package com.stockzen.inventory.purchase.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockzen.inventory.purchase.entity.Purchase;

public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {
	List<Purchase> findByPurchaseMasterId(Integer purchaseMasterId);
}

