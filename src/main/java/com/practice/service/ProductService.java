package com.practice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.Entity.Product;
import com.practice.Exceptions.ResourceNotFoundException;
import com.practice.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired 
	public ProductRepo prepo;
	
	public List<Product> getAllProducts(){
		return prepo.findAll();
	}
	
	public Product getProductById(Integer id) {
		   return prepo.findById(id).orElseThrow(()-> new ResourceNotFoundException
				   ("Item that your looking is not found"));
	}
	
	public Product updateProduct(Integer id, Product product) {
		return prepo.findById(id).map(existProduct ->{
			existProduct.setName(product.getName());
			existProduct.setPrice(product.getPrice());
			existProduct.setQuantity(product.getQuantity());
			return prepo.save(existProduct);
		}).orElseThrow(()-> new ResourceNotFoundException("The item youre trying to update is not found in system"));
	}
	
	
	public void deleteProductById(Integer id) {
		prepo.deleteById(id);
	}
	
	


}
