package com.bway.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.springdemo.model.Patient;

public interface PatientRepository extends JpaRepository <Patient, Integer> {

	 
}
