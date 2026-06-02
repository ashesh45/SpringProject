package com.example.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springsecurity.model.MyUser;

public interface UserRepository extends JpaRepository<MyUser, Integer>{
	
	MyUser findByUsername(String username);
}
