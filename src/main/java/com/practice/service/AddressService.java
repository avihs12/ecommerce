package com.practice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.practice.Entity.Address;
import com.practice.Exceptions.ResourceNotFoundException;
import com.practice.repo.AddressRepo;

@Service
public class AddressService {

	@Autowired
	private AddressRepo addRepo;
	
	public Address createAddress(Address address) {
		return addRepo.save(address);
	}
	
	public List<Address> getAllAddressData(){
		return addRepo.findAll();
	}
	
	public Address getAddressById(Integer id) {
		return addRepo.findById(id).orElseThrow(()-> new 
				ResourceNotFoundException ("The address you're looking is not found in system"));
	}
	
	public Address updateAddressById(Integer id, Address updateAddress) {
		return addRepo.findById(id).map(existAdd -> {
			existAdd.setCity(updateAddress.getCity());
			existAdd.setState(updateAddress.getState());
			existAdd.setPincode(updateAddress.getPincode());
			return addRepo.save(existAdd);
			}).orElseThrow(()-> new  ResourceNotFoundException("the user is not found in systm to update the address"));
	}
	
	public void  deleteAddressById(Integer id) {
		 addRepo.deleteById(id);
	}
	
}
