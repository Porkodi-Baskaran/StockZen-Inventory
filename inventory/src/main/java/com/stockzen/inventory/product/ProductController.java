package com.stockzen.inventory.product;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	ProductService productService;
	
	@PostMapping("/add")
	public ResponseEntity<Product> addProduct(@RequestBody Product product)
	{
		Product savedProduct = productService.addproduct(product);
		return new ResponseEntity<>(savedProduct,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id,@RequestBody Product updatedProduct)
	{
		Product product = productService.updateProduct(id, updatedProduct);
		if(product !=null)
		{
			return new ResponseEntity<>(product,HttpStatus.OK);
		}
		
		else
		{
			return new ResponseEntity<>("Product not found with Id: "+id,HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable Integer id)
	{
		try {
			String result=productService.deleteProduct(id);
			return new ResponseEntity<>(result,HttpStatus.OK);
		}
		catch(Exception e)
		{
			return new ResponseEntity<>("Product not found with Id: "+id,HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> viewProductById(@PathVariable Integer id)
	{
		Optional<Product> product= productService.viewProductsbyid(id);
		if(product.isPresent())
		{
			return new ResponseEntity<>(product.get(),HttpStatus.OK);
		}
		else {
            return new ResponseEntity<>("Product not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<Product>> viewAllProduct()
	{
		List<Product> products = productService.viewAllProducts();
		if(products.isEmpty())
		{
			 return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
		}
		return new ResponseEntity<>(products, HttpStatus.OK);
	}
	
}
