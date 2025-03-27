package com.practice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.Entity.User;

public interface UserRepo  extends JpaRepository<User,Integer>{

}
