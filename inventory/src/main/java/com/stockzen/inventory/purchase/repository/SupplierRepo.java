package com.stockzen.inventory.purchase.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stockzen.inventory.purchase.entity.Supplier;

public interface SupplierRepo extends JpaRepository<Supplier, Integer> {
	Optional<Supplier> findByName(String name);
	
	Optional<Supplier> findByPhoneNumber(String phoneNumber);
}
