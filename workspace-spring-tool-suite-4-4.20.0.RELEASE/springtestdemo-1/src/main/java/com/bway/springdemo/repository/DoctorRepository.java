package com.bway.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.springdemo.model.Doctor;




public interface DoctorRepository extends JpaRepository <Doctor, Integer>  {
	
	Doctor findByUsernameAndPassword(String un, String psw);

}
