package com.practice.Entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Product {
	
	@Id  
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	@NotBlank(message = "product name must be not blank")
	@Size(min=3, max=50, message ="product name must be between 3 to 50")
	String name;
	@NotNull(message = "product price must be not blank")
	@Min(value = 1, message = "Pincode must be at least 6 digits")
	@Max(value = 100000, message = "Pincode must be at most 6 digits") 
	Integer price;
	@NotNull(message = "product item size must be not blank")
	@Min(value = 1, message = "Pincode must be at least 6 digits")
	@Max(value = 50, message = "Pincode must be at most 6 digits") 
	Integer quantity;
	LocalDate date = LocalDate.now();
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore //recusion will rejected by this , due to many to one one to many in both entities it will come circularly
	private User user;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}

	
}
