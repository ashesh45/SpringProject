package com.bway.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.springdemo.model.FinanceResponse;
import com.bway.springdemo.model.Story;


public interface FinanceRepository extends JpaRepository <FinanceResponse,Long>  {




}
