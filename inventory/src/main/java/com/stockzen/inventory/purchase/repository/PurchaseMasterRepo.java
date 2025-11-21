package com.stockzen.inventory.purchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockzen.inventory.purchase.entity.PurchaseMaster;

public interface PurchaseMasterRepo extends JpaRepository<PurchaseMaster, Integer> {
}

