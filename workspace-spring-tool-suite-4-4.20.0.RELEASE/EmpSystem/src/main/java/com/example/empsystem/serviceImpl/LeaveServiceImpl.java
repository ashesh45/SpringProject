package com.example.empsystem.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.empsystem.dto.LeaveRequestDto;
import com.example.empsystem.dto.mapper.LeaveRequestMapper;
import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.model.Employee;
import com.example.empsystem.model.LeaveRequest;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.repository.LeaveRequestRepository;
import com.example.empsystem.service.LeaveService;

@Service
@Transactional
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRequestRepository leaveRepo;

    @Autowired
    private EmployeeRepository empRepo;

    @Override
    public LeaveRequestDto applyLeave(Long empId, LeaveRequestDto dto) {
        Employee emp = empRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveRequest entity = LeaveRequestMapper.toEntity(empId, dto);
        entity.setEmployee(emp);
        entity = leaveRepo.save(entity);
        return LeaveRequestMapper.toDto(entity);
    }

    @Override
    public List<LeaveRequestDto> getLeavesByEmployee(Long empId) {
        return leaveRepo.findByEmployeeId(empId).stream()
                .map(LeaveRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDto> getAllLeaves() {
        return leaveRepo.findAll().stream()
                .map(LeaveRequestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeaveRequestDto approveLeave(Long id) {
        LeaveRequest entity = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
        entity.setStatus(LeaveStatus.APPROVED);
        entity = leaveRepo.save(entity);
        return LeaveRequestMapper.toDto(entity);
    }

    @Override
    public LeaveRequestDto rejectLeave(Long id) {
        LeaveRequest entity = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
        entity.setStatus(LeaveStatus.REJECTED);
        entity = leaveRepo.save(entity);
        return LeaveRequestMapper.toDto(entity);
    }
}
