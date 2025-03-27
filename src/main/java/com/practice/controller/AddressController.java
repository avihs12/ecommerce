package com.practice.controller;

import java.util.List;

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

import com.practice.Entity.Address;
import com.practice.Entity.Product;
import com.practice.Entity.User;
import com.practice.repo.AddressRepo;
import com.practice.service.AddressService;

@RequestMapping("/address")
@RestController
public class AddressController {

@Autowired
private AddressRepo addRepo;

@Autowired
private AddressService addService;

	@PostMapping
	public ResponseEntity<Address> createAddress(@RequestBody Address address  ){
		return ResponseEntity.status(HttpStatus.CREATED).body(addService.createAddress(address));
	}
	
	@GetMapping("/{id}")
	public  ResponseEntity<Address >UserAddress(@PathVariable Integer id) {
		return ResponseEntity.ok(addService.getAddressById(id));
    }
	
	@GetMapping
	public ResponseEntity<List<Address> > getAllAddress(){
		return ResponseEntity.ok(addService.getAllAddressData());
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Address> updateAddress(@PathVariable Integer id,@RequestBody Address address){
			return ResponseEntity.ok(addService.updateAddressById(id, address));
	}	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	  public void deleteAddressById(@PathVariable Integer id) {
		addService.deleteAddressById(id);
	}

	
}
