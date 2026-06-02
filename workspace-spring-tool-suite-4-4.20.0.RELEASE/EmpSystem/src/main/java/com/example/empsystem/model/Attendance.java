package com.example.empsystem.model;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.empsystem.enumm.AttendanceStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name ="attendance_tbl")
public class Attendance {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private LocalDate date;

	    private LocalTime checkInTime;

	    private LocalTime checkOutTime;

	    @Enumerated(EnumType.STRING)
	    private AttendanceStatus status;// Present, Absent, Late

	    @ManyToOne
	    @JoinColumn(name = "employee_id")
	    private Employee employee;

	
}
