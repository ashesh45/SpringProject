package com.example.empsystem.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.empsystem.enumm.AttendanceStatus;
import com.example.empsystem.model.Attendance;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.AttendanceRepository;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService {
   
	 private static final LocalTime OFFICE_START = LocalTime.of(9, 0);
	
	@Autowired
	private AttendanceRepository attendanceRepo;
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	
	@Override
	public String checkIn(Long id) {
		// TODO Auto-generated method stub
		 Employee employee = employeeRepo.findById(id)
	                .orElseThrow(() -> new RuntimeException("Employee not found"));

	        LocalDate today = LocalDate.now();

	        if (attendanceRepo.findByEmployeeAndDate(employee, today).isPresent()) {
	            return "Already checked in today";
	        }

	        Attendance attendance = new Attendance();
	        attendance.setEmployee(employee);
	        attendance.setDate(today);
	        attendance.setCheckInTime(LocalTime.now());

	        LocalTime now = LocalTime.now();

	        if (now.isAfter(OFFICE_START)) {
	            attendance.setStatus(AttendanceStatus.LATE);
	        } else {
	            attendance.setStatus(AttendanceStatus.PRESENT);
	        }

	        attendanceRepo.save(attendance);

	        return "Check-in successful";
	}

	@Override
	public String checkOut(Long empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attendance> getAllAttendance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attendance> getAttendanceByEmployee(Long empId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attendance getTodayAttendance(Long empId) {
		// TODO Auto-generated method stub
		return null;
	}

}
