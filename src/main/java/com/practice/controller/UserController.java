package com.practice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice.Dto.Dto;
import com.practice.Entity.Address;
import com.practice.Entity.Product;
import com.practice.Entity.User;
import com.practice.repo.UserRepo;
import com.practice.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

@Autowired
private UserRepo userRepository;

@Autowired
private UserService uService;

@GetMapping("/test")
public String testNullPointer() {
    String str = null;
    return  str.toString(); 
}

@GetMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public String adminAccess() {
    return "Admin Access Granted!";
}


@PostMapping
public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
	        return ResponseEntity.ok(uService.createUser(user));
}

@GetMapping("/dto/{id}")
public ResponseEntity<Dto> getUserByID (@PathVariable Integer id) {
    return ResponseEntity.ok(uService.getUserIn(id)); 
}


@GetMapping("/allusers")
public ResponseEntity<List<Dto>> getAllUsers() {
    return ResponseEntity.ok(uService.getUsers()); 
}

@GetMapping("/userinfo")
public ResponseEntity<List<User>> getUserInfo(User user){
	return ResponseEntity.ok(uService.getusersInfo());
}

@GetMapping("/{id}")
public ResponseEntity<User>getUser(@PathVariable Integer id) {
    System.out.println("/////////////////////////////gettinhg from method");
	        return  ResponseEntity.ok(uService.getUserById(id));
}

@PutMapping("/{id}")
public ResponseEntity<User> updateUserInfo(@PathVariable Integer id, @RequestBody User user){
		return ResponseEntity.ok(uService.updateUserInfo(id, user));
}	

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteUserById(@PathVariable Integer id) {
    System.out.println("///////////////////////////deleted from method");
    uService.deleteUserById(id);
}



//@GetMapping("/{id}/product")
//public List<Product> UserProduct(@PathVariable Integer id) {
//	User user = userRepository.findById(id).orElse(null);
//	if(user == null) {
//		return  null;
//	}
//	return user.getProduct();
//}


//@GetMapping("/{id}")
//public Map<String, Object> getUserInfo(@PathVariable Integer id) {
//	User user = userRepository.findById(id).orElse(null);
//	if(user == null) {
//		return null;
//	}
//	Map <String,Object> saveResponse = new HashMap<>();
//	saveResponse.put("id",user.getId());
//	saveResponse.put("name", user.getName());
//	saveResponse.put("password", user.getPassword());
//	return saveResponse;
//}

}
