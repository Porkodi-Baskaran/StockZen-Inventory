package com.stockzen.inventory.purchase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.stockzen.inventory.purchase.entity.Supplier;
import com.stockzen.inventory.purchase.repository.SupplierRepo;

import java.util.List;

@RestController
@RequestMapping("/supplier")
@CrossOrigin("*")
public class SupplierController {

    @Autowired
    private SupplierRepo supplierRepo;

    @PostMapping("/add")
    public Supplier add(@RequestBody Supplier supplier) {
        return supplierRepo.save(supplier);
    }

    @GetMapping("/all")
    public List<Supplier> list() {
        return supplierRepo.findAll();
    }
}
