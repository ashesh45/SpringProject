package com.bway.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.springdemo.model.Cart;



public interface CartRepository extends JpaRepository <Cart,Long>  {

}
