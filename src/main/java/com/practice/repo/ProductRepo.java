package com.practice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.practice.Entity.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer>{
	
//	@Query("select p from Product p where p.name = :name")
//	List<Product> findByName(String name);
	

	

}
