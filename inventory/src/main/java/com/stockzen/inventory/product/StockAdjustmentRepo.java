package com.stockzen.inventory.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockAdjustmentRepo extends JpaRepository<StockAdjustment, Integer>{

}
