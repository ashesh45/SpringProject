package com.bway.springdemo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.springdemo.model.Employee;
import com.bway.springdemo.model.Product;

public interface ProductRepository extends JpaRepository <Product, Integer> {


}
