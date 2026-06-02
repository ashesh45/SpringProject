package com.example.empsystem.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.model.Employee;
import com.example.empsystem.model.LeaveRequest;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.repository.LeaveRequestRepository;
import com.example.empsystem.service.LeaveService;



@Service
public class LeaveServiceImpl implements LeaveService {

	
    @Autowired
    private LeaveRequestRepository leaveRepo;
	
    @Autowired
    private EmployeeRepository empRepo;
    

	@Override
	public List<LeaveRequest> getAllLeaves() {
		// TODO Auto-generated method stub
		 return leaveRepo.findAll();
	}

	@Override
	public LeaveRequest getLeaveById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteLeave(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LeaveRequest updateLeaveStatus(Long id, LeaveStatus status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LeaveRequest> getPendingLeaves() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LeaveRequest applyLeave(Long empId, LeaveRequest request) {
		Employee emp = empRepo.findById(empId)
	                .orElseThrow(() -> new RuntimeException("Employee not found"));

	        request.setEmployee(emp);
	        request.setStatus(LeaveStatus.PENDING);
	        request.setAppliedDate(LocalDate.now());

	        return leaveRepo.save(request);
	    }

	@Override
	public LeaveRequest approveLeave(Long id) {
		// TODO Auto-generated method stub
		  LeaveRequest leave =
	                leaveRepo.findById(id).orElseThrow();

	        leave.setStatus(LeaveStatus.APPROVED);

	        return leaveRepo.save(leave);
	}

	@Override
	public LeaveRequest rejectLeave(Long id) {
		// TODO Auto-generated method stub
		  LeaveRequest leave = leaveRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Leave not found"));

	        leave.setStatus(LeaveStatus.REJECTED);

	        return leaveRepo.save(leave);
	}

	@Override
	public List<LeaveRequest> findByEmployee(Long id) {
		// TODO Auto-generated method stub
		 return leaveRepo.findByEmployeeId(id);
	}

	}


