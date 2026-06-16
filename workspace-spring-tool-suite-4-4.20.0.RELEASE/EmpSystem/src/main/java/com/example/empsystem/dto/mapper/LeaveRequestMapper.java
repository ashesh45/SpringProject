package com.example.empsystem.dto.mapper;

import com.example.empsystem.dto.LeaveRequestDto;
import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.model.LeaveRequest;

public class LeaveRequestMapper {

    private LeaveRequestMapper() {}

    public static LeaveRequestDto toDto(LeaveRequest entity) {
        if (entity == null) return null;

        return LeaveRequestDto.builder()
                .id(entity.getId())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .leaveType(entity.getLeaveType() != null ? entity.getLeaveType().name() : null)
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .reason(entity.getReason())
                .appliedDate(entity.getAppliedDate())
                .employeeId(entity.getEmployee() != null ? entity.getEmployee().getId() : null)
                .employeeName(entity.getEmployee() != null
                        ? entity.getEmployee().getFname() + " " + entity.getEmployee().getLname()
                        : null)
                .build();
    }

    public static LeaveRequest toEntity(Long employeeId, LeaveRequestDto dto) {
        if (dto == null) return null;

        LeaveRequest entity = new LeaveRequest();
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setReason(dto.getReason());
        entity.setAppliedDate(java.time.LocalDate.now());
        entity.setStatus(LeaveStatus.PENDING);

        if (dto.getLeaveType() != null) {
            entity.setLeaveType(com.example.empsystem.enumm.LeaveType.valueOf(dto.getLeaveType()));
        }

        return entity;
    }
}
