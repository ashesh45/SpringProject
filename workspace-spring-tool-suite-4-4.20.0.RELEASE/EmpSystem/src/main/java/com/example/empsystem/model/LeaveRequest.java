package com.example.empsystem.model;

import java.time.LocalDate;

import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.enumm.LeaveType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emp_leaves")
public class LeaveRequest {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

 	    @ManyToOne
	    @JoinColumn(name = "employee_id", nullable = false)
	    private Employee employee;

	    private LocalDate startDate;

	    private LocalDate endDate;

	    @Enumerated(EnumType.STRING)
	    private LeaveType leaveType;

	    @Enumerated(EnumType.STRING)
	    private LeaveStatus status;

	    @Column(length = 500)
	    private String reason;

	    private LocalDate appliedDate;


}