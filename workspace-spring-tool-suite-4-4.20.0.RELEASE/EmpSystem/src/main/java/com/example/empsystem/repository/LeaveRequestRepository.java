package com.example.empsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.model.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>  {
	

	List<LeaveRequest> findByEmployeeId(Long employeeId);
    List<LeaveRequest> findByStatus(LeaveStatus status);

}
