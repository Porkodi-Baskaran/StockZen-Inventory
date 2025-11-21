package com.stockzen.inventory.purchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stockzen.inventory.purchase.dto.PurchaseRequestDTO;
import com.stockzen.inventory.purchase.entity.PurchaseMaster;
import com.stockzen.inventory.purchase.service.PurchaseService;

@RestController
@RequestMapping("/purchase")
@CrossOrigin("*")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @PostMapping("/add")
    public String addPurchase(@RequestBody PurchaseRequestDTO dto) {
        return purchaseService.addPurchase(dto);
    }
    
    @GetMapping("/{id}")
    public PurchaseMaster getById(@PathVariable Integer id) {
        return purchaseService.getPurchaseById(id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        return purchaseService.deletePurchase(id);
    }

}
