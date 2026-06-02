package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.model.LeaveRequest;

public interface LeaveService {
	

	LeaveRequest applyLeave(Long empId, LeaveRequest request);

    List<LeaveRequest> getAllLeaves();

    LeaveRequest getLeaveById(Long id);

    void deleteLeave(Long id);

    LeaveRequest updateLeaveStatus(Long id, LeaveStatus status);

    List<LeaveRequest> getPendingLeaves();
    
    LeaveRequest approveLeave(Long id);

    LeaveRequest rejectLeave(Long id);

	List<LeaveRequest> findByEmployee(Long id);



}
