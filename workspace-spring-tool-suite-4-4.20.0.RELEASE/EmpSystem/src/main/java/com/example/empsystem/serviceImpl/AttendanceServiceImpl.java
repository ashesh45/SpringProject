package com.example.empsystem.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.empsystem.dto.AttendanceDto;
import com.example.empsystem.enumm.AttendanceStatus;

import jakarta.servlet.http.HttpServletResponse;
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

    @Autowired
    private ModelMapper modelMapper;

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
                .map(entity -> modelMapper.map(entity, AttendanceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public AttendanceDto getTodayAttendance(Long empId) {
        Employee employee = employeeRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        Attendance attendance = attendanceRepo.findByEmployeeAndDate(employee, LocalDate.now())
                .orElse(null);
        return modelMapper.map(attendance, AttendanceDto.class);
    }

    @Override
    public List<AttendanceDto> getAllAttendanceSorted() {
        return attendanceRepo.findAllByOrderByDateDesc().stream()
                .map(entity -> modelMapper.map(entity, AttendanceDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public void exportExcel(HttpServletResponse response) {
        List<AttendanceDto> records = getAllAttendanceSorted();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Attendance");

            Row header = sheet.createRow(0);
            String[] cols = {"Employee ID", "First Name", "Last Name", "Date", "Check In", "Check Out", "Status"};
            for (int i = 0; i < cols.length; i++) {
                header.createCell(i).setCellValue(cols[i]);
            }

            int rowIdx = 1;
            for (AttendanceDto a : records) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(a.getEmployeeId() != null ? a.getEmployeeId() : 0);
                row.createCell(1).setCellValue(a.getEmployeeName() != null ? a.getEmployeeName().split(" ")[0] : "");
                row.createCell(2).setCellValue(a.getEmployeeName() != null && a.getEmployeeName().contains(" ")
                        ? a.getEmployeeName().substring(a.getEmployeeName().indexOf(' ') + 1) : "");
                row.createCell(3).setCellValue(a.getDate() != null ? a.getDate().toString() : "");
                row.createCell(4).setCellValue(a.getCheckInTime() != null ? a.getCheckInTime().toString() : "");
                row.createCell(5).setCellValue(a.getCheckOutTime() != null ? a.getCheckOutTime().toString() : "");
                row.createCell(6).setCellValue(a.getStatus() != null ? a.getStatus() : "");
            }

            for (int i = 0; i < cols.length; i++) {
                sheet.autoSizeColumn(i);
            }

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=attendance.xlsx");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException("Excel export failed: " + e.getMessage());
        }
    }
}
