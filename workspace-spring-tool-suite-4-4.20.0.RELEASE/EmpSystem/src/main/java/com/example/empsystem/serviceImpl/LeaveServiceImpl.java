package com.example.empsystem.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.empsystem.dto.LeaveRequestDto;
import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.enumm.LeaveType;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public LeaveRequestDto applyLeave(Long empId, LeaveRequestDto dto) {
        Employee emp = empRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LeaveRequest entity = new LeaveRequest();
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        entity.setAppliedDate(java.time.LocalDate.now());
        entity.setStatus(LeaveStatus.PENDING);

        if (dto.getLeaveType() != null) {
            entity.setLeaveType(LeaveType.valueOf(dto.getLeaveType()));
        }

        entity.setEmployee(emp);
        entity = leaveRepo.save(entity);
        return modelMapper.map(entity, LeaveRequestDto.class);
    }

    @Override
    public List<LeaveRequestDto> getLeavesByEmployee(Long empId) {
        return leaveRepo.findByEmployeeId(empId).stream()
                .map(entity -> modelMapper.map(entity, LeaveRequestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<LeaveRequestDto> getAllLeaves() {
        return leaveRepo.findAll().stream()
                .map(entity -> modelMapper.map(entity, LeaveRequestDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public LeaveRequestDto approveLeave(Long id) {
        LeaveRequest entity = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
        entity.setStatus(LeaveStatus.APPROVED);
        entity = leaveRepo.save(entity);
        return modelMapper.map(entity, LeaveRequestDto.class);
    }

    @Override
    public LeaveRequestDto rejectLeave(Long id) {
        LeaveRequest entity = leaveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + id));
        entity.setStatus(LeaveStatus.REJECTED);
        entity = leaveRepo.save(entity);
        return modelMapper.map(entity, LeaveRequestDto.class);
    }
}
