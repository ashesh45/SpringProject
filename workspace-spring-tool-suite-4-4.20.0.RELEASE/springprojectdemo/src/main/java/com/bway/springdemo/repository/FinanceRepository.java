package com.bway.springdemo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.springdemo.model.FinanceResponse;


public interface FinanceRepository extends JpaRepository <FinanceResponse,Long> {

	Optional<FinanceResponse> findTopByOrderByIdDesc();

}
