package com.practice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.practice.Entity.Address;

import java.util.Optional;


@Repository
public interface AddressRepo  extends JpaRepository<Address,Integer>{

    Optional<Address> findByCityAndStateAndPincode(String city, String state, Long pinCode);

}
