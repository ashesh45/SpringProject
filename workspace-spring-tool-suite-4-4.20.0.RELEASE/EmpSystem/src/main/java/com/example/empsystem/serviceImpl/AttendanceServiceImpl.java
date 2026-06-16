package com.example.empsystem.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.empsystem.dto.AttendanceDto;
import com.example.empsystem.dto.mapper.AttendanceMapper;
import com.example.empsystem.enumm.AttendanceStatus;
import com.example.empsystem.model.Attendance;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.AttendanceRepository;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.AttendanceService;

@Service
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private static final LocalTime OFFICE_START = LocalTime.of(9, 0);

    @Autowired
    private AttendanceRepository attendanceRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    @Override
    public String checkIn(Long empId) {
        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        if (attendanceRepo.findByEmployeeAndDate(employee, today).isPresent()) {
            return "Already checked in today";
        }

        Attendance attendance = new Attendance();
        attendance.setEmployee(employee);
        attendance.setDate(today);
        attendance.setCheckInTime(LocalTime.now());

        if (LocalTime.now().isAfter(OFFICE_START)) {
            attendance.setStatus(AttendanceStatus.LATE);
        } else {
            attendance.setStatus(AttendanceStatus.PRESENT);
        }

        attendanceRepo.save(attendance);
        return "Check-in successful";
    }

    @Override
    public String checkOut(Long empId) {
        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        LocalDate today = LocalDate.now();

        Attendance attendance = attendanceRepo.findByEmployeeAndDate(employee, today)
                .orElseThrow(() -> new RuntimeException("No check-in record found for today"));

        if (attendance.getCheckOutTime() != null) {
            return "Already checked out today";
        }

        attendance.setCheckOutTime(LocalTime.now());

        long hours = ChronoUnit.HOURS.between(attendance.getCheckInTime(), attendance.getCheckOutTime());
        if (hours < 4) {
            attendance.setStatus(AttendanceStatus.ABSENT);
        }

        attendanceRepo.save(attendance);
        return "Check-out successful";
    }

    @Override
    public List<AttendanceDto> getMyAttendance(Long empId) {
        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return attendanceRepo.findByEmployee(employee).stream()
                .map(AttendanceMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceDto getTodayAttendance(Long empId) {
        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Attendance attendance = attendanceRepo.findByEmployeeAndDate(employee, LocalDate.now())
                .orElse(null);
        return AttendanceMapper.toDto(attendance);
    }

    @Override
    public List<AttendanceDto> getAllAttendanceSorted() {
        return attendanceRepo.findAllByOrderByDateDesc().stream()
                .map(AttendanceMapper::toDto)
                .collect(Collectors.toList());
    }
}
