package com.practice.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
   @NotBlank(message = "The city must not be empty")
   @Size(min = 3, max = 20, message = "The city name should be between 3 to 20 characters")
	private String city;
   @NotBlank(message = "The state must not be empty")
   @Size(min = 3, max = 20, message = "The state name should be between 3 to 20 characters")
	private String state;
   @NotNull(message = "The pincode must not be empty")
   @Min(value = 100000, message = "Pincode must be at least 6 digits")
   @Max(value = 999999, message = "Pincode must be at most 6 digits") 
	private Long pincode;
	
	@OneToOne(mappedBy = "address",cascade = CascadeType.ALL, orphanRemoval = true)
	private User user;
	
	public Address() {
		
	}
	public Address(Integer id, String city, String state, Long pincode) {
		super();
		this.id = id;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getPincode() {
		return pincode;
	}
	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", city=" + city + ", state=" + state + ", pincode=" + pincode + "]";
	}


}
