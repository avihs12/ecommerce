package com.practice.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.practice.Entity.Product;
import com.practice.repo.ProductRepo;
import com.practice.service.ProductService;

@RequestMapping("/products")
@RestController
public class ProductController {

    @Autowired
    private ProductService pService;

    @Autowired 
    private ProductRepo pRepo;


 
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)  
    public Product addProduct(@RequestBody Product items) {
            System.out.println("Endpoint /products POST hit");
            return pRepo.save(items);
    }
    
    @GetMapping
    public ResponseEntity<List<Product> > getProducts() {
            System.out.println("Endpoint /products hit");
            return  ResponseEntity.ok(pService.getAllProducts());
    }
    
   @GetMapping("/{id}")
   public ResponseEntity<Product>  getItemFromList(@PathVariable Integer id) {
	   System.out.println(pRepo.findById(id));
	   Product product = pService.getProductById(id);
	   return ResponseEntity.ok(product);
   }
   
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Product>modifyProduct(@PathVariable  Integer id, @RequestBody Product items) {
    		return ResponseEntity.ok(pService.updateProduct(id, items));
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
     public void   deleteProduct(@PathVariable Integer id) {
    	pService.deleteProductById(id);
    }
   
}
