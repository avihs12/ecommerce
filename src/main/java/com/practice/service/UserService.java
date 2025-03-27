package com.practice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.practice.Dto.Dto;
import com.practice.Entity.User;
import com.practice.Exceptions.ResourceNotFoundException;
import com.practice.repo.UserRepo;

import jakarta.validation.Valid;

@Service
public class UserService {
	
	@Autowired
	private UserRepo uRepo;
	
	private Dto dto;
	
	@Autowired
	private ModelMapper modelmapper;
	
	public User createUser(@Valid User user) {
		if(user.getProduct()!=null){
			user.getProduct().forEach(product -> {
				product.setUser(user);
				System.out.println((user.getName()+"   "+product.getName()));
			});
		}
		return uRepo.save(user);
	}
	
	  public Dto getUserDto() {
	        return new Dto(); 
	    }

	public Dto getUserIn(Integer id) {
		Logger logger = LoggerFactory.getLogger(UserService.class);
	    
	    User user = uRepo.findById(id)
	                     .orElseThrow(() -> {
	                         logger.error("User not found with ID: " + id);
	                         return new ResourceNotFoundException("User not found with ID: " + id);
	                     });
	    return modelmapper.map(user, Dto.class);
	}

	public List<Dto> getUsers() {
		List<User> users = this.uRepo.findAll();
		
		return users.stream()
                .map(user -> modelmapper.map(user, Dto.class))
                .collect(Collectors.toList());
		}
	
	public List<User> getusersInfo(){
		List<User> user = uRepo.findAll();
		return user.stream()
				.map(userr -> new User( userr.getId(), userr.getName(),userr.getPassword()))
				.collect(Collectors.toList());
	}

	@Cacheable(value = "user",key = "#id")
	public User getUserById(Integer id) {
		System.out.println("getting data from method....//////////////////////////////////////////////////////");
		return uRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("The user id  "+id+"  is not found in the system"));
	}

	@CachePut(value = "user",key = "#id")
	public User updateUserInfo(Integer id,User user) {
		return uRepo.findById(id).map(existUser->
		{  existUser.setName(user.getName());
			existUser.setPassword(user.getPassword());
			System.out.println("getting data from method....  put(updates)");
			return uRepo.save(existUser);

		}).orElseThrow(()-> new ResourceNotFoundException("The item  "+id+"  you're looking for is not found in system to update"));
	}
	@CacheEvict(value = "user",key = "#id")
	public void deleteUserById(Integer id) {
		if(!uRepo.existsById(id)) {
			throw new ResourceNotFoundException("The item  "+id+"  you're looking  is not found in system to delete");
		}
		System.out.println("getting data from method.... evict _-_ deleting");
		uRepo.deleteById(id);

	}
}
