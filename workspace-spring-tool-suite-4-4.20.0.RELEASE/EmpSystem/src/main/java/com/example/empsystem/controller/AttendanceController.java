package com.example.empsystem.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.empsystem.model.Attendance;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.AttendanceService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

	@Autowired
	private AttendanceService attendanceService;

	@Autowired
	private EmployeeRepository empRepo;

	private Employee getCurrentEmployee(Principal principal) {
		return empRepo.findByUsername(principal.getName());
	}

	@GetMapping("/today")
	public ResponseEntity<?> getMyAttendance(Principal principal) {
		Employee emp = getCurrentEmployee(principal);
		if (emp == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Employee not found");
		}

		Attendance todayAttendance = attendanceService.getTodayAttendance(emp.getId());
		List<Attendance> history = attendanceService.getAttendanceByEmployee(emp.getId());

		boolean canCheckIn = todayAttendance == null;
		boolean canCheckOut = todayAttendance != null && todayAttendance.getCheckOutTime() == null;

		Map<String, Object> response = new HashMap<>();
		response.put("today", todayAttendance);
		response.put("history", history);
		response.put("canCheckIn", canCheckIn);
		response.put("canCheckOut", canCheckOut);

		return ResponseEntity.ok(response);
	}

	@PostMapping("/checkin")
	public ResponseEntity<String> checkIn(Principal principal) {
		Employee emp = getCurrentEmployee(principal);
		if (emp == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Employee not found");
		}
		String msg = attendanceService.checkIn(emp.getId());
		return ResponseEntity.ok(msg);
	}

	@PostMapping("/checkout")
	public ResponseEntity<String> checkOut(Principal principal) {
		Employee emp = getCurrentEmployee(principal);
		if (emp == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Employee not found");
		}
		String msg = attendanceService.checkOut(emp.getId());
		return ResponseEntity.ok(msg);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Attendance>> getAllAttendance() {
		return ResponseEntity.ok(attendanceService.getAllAttendanceSorted());
	}

	@GetMapping("/export/excel")
	public void exportExcel(HttpServletResponse response) throws IOException {
		List<Attendance> records = attendanceService.getAllAttendanceSorted();

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Attendance");

		Row header = sheet.createRow(0);
		String[] cols = {"Employee ID", "First Name", "Last Name", "Date", "Check In", "Check Out", "Status"};
		for (int i = 0; i < cols.length; i++) {
			header.createCell(i).setCellValue(cols[i]);
		}

		int rowIdx = 1;
		for (Attendance a : records) {
			Row row = sheet.createRow(rowIdx++);
			row.createCell(0).setCellValue(a.getEmployee().getId());
			row.createCell(1).setCellValue(a.getEmployee().getFname());
			row.createCell(2).setCellValue(a.getEmployee().getLname());
			row.createCell(3).setCellValue(a.getDate() != null ? a.getDate().toString() : "");
			row.createCell(4).setCellValue(a.getCheckInTime() != null ? a.getCheckInTime().toString() : "");
			row.createCell(5).setCellValue(a.getCheckOutTime() != null ? a.getCheckOutTime().toString() : "");
			row.createCell(6).setCellValue(a.getStatus() != null ? a.getStatus().name() : "");
		}

		for (int i = 0; i < cols.length; i++) {
			sheet.autoSizeColumn(i);
		}

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-Disposition", "attachment; filename=attendance.xlsx");
		workbook.write(response.getOutputStream());
		workbook.close();
	}
}
