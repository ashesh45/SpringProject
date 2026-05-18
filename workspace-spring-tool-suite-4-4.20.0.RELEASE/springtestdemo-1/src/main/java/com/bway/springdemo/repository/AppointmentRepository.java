package com.bway.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bway.springdemo.model.Appointment;



public interface AppointmentRepository extends JpaRepository <Appointment, Integer> {

}
