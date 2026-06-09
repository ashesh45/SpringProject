package com.bway.springdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.springdemo.model.User;

public interface UserRepository extends JpaRepository<User, Integer  > {
	
	User findByUsernameAndPassword(String un, String psw);

	Optional<User> findByUsername(String username);

}
