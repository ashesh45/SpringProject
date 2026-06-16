package com.example.empsystem.dto.mapper;

import com.example.empsystem.dto.AttendanceDto;
import com.example.empsystem.model.Attendance;

public class AttendanceMapper {

    private AttendanceMapper() {}

    public static AttendanceDto toDto(Attendance entity) {
        if (entity == null) return null;

        return AttendanceDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .checkInTime(entity.getCheckInTime())
                .checkOutTime(entity.getCheckOutTime())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .employeeId(entity.getEmployee() != null ? entity.getEmployee().getId() : null)
                .employeeName(entity.getEmployee() != null
                        ? entity.getEmployee().getFname() + " " + entity.getEmployee().getLname()
                        : null)
                .build();
    }
}
