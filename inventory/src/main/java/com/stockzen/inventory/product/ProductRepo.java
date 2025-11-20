package com.stockzen.inventory.product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface ProductRepo extends JpaRepository<Product, Integer>
{	
}
