package com.example.empsystem.service;

import java.util.List;

import com.example.empsystem.dto.LeaveRequestDto;

public interface LeaveService {

    LeaveRequestDto applyLeave(Long empId, LeaveRequestDto dto);

    List<LeaveRequestDto> getLeavesByEmployee(Long empId);

    List<LeaveRequestDto> getAllLeaves();

    LeaveRequestDto approveLeave(Long id);

    LeaveRequestDto rejectLeave(Long id);
}
