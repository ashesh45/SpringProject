package com.example.empsystem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.empsystem.dto.AttendanceDto;
import com.example.empsystem.dto.EmployeeDTO;
import com.example.empsystem.dto.LeaveRequestDto;
import com.example.empsystem.dto.PayrollDto;
import com.example.empsystem.enumm.AttendanceStatus;
import com.example.empsystem.enumm.LeaveStatus;
import com.example.empsystem.enumm.LeaveType;
import com.example.empsystem.model.Attendance;
import com.example.empsystem.model.Department;
import com.example.empsystem.model.Employee;
import com.example.empsystem.model.LeaveRequest;
import com.example.empsystem.model.Payroll;

@SpringBootApplication
public class EmpSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpSystemApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setSkipNullEnabled(true);

		mapper.createTypeMap(Employee.class, EmployeeDTO.class)
				.addMappings(m -> {
					m.map(src -> src.getAddress() != null ? src.getAddress().getName() : null,
							EmployeeDTO::setAddressName);
					m.map(src -> src.getAddress() != null ? src.getAddress().getState() : null,
							EmployeeDTO::setAddressState);
					m.map(src -> src.getAddress() != null ? src.getAddress().getZipcode() : null,
							EmployeeDTO::setAddressZipcode);
					m.using(ctx -> {
						@SuppressWarnings("unchecked")
						List<Department> depts = (List<Department>) ctx.getSource();
						if (depts == null) return new ArrayList<>();
						return depts.stream().map(Department::getId).collect(Collectors.toList());
					}).map(Employee::getDepartments, EmployeeDTO::setDepartmentIds);
				});

		mapper.createTypeMap(Attendance.class, AttendanceDto.class)
				.addMappings(m -> {
					m.map(src -> src.getEmployee() != null ? src.getEmployee().getId() : null,
							AttendanceDto::setEmployeeId);
					m.map(src -> src.getEmployee() != null
									? src.getEmployee().getFname() + " " + src.getEmployee().getLname()
									: null,
							AttendanceDto::setEmployeeName);
					m.using(ctx -> {
						AttendanceStatus status = (AttendanceStatus) ctx.getSource();
						return status != null ? status.name() : null;
					}).map(Attendance::getStatus, AttendanceDto::setStatus);
				});

		mapper.createTypeMap(LeaveRequest.class, LeaveRequestDto.class)
				.addMappings(m -> {
					m.map(src -> src.getEmployee() != null ? src.getEmployee().getId() : null,
							LeaveRequestDto::setEmployeeId);
					m.map(src -> src.getEmployee() != null
									? src.getEmployee().getFname() + " " + src.getEmployee().getLname()
									: null,
							LeaveRequestDto::setEmployeeName);
					m.using(ctx -> {
						LeaveType type = (LeaveType) ctx.getSource();
						return type != null ? type.name() : null;
					}).map(LeaveRequest::getLeaveType, LeaveRequestDto::setLeaveType);
					m.using(ctx -> {
						LeaveStatus status = (LeaveStatus) ctx.getSource();
						return status != null ? status.name() : null;
					}).map(LeaveRequest::getStatus, LeaveRequestDto::setStatus);
				});

		mapper.createTypeMap(Payroll.class, PayrollDto.class)
				.addMappings(m -> {
					m.map(src -> src.getEmployee() != null ? src.getEmployee().getId() : null,
							PayrollDto::setEmployeeId);
					m.map(src -> src.getEmployee() != null
									? src.getEmployee().getFname() + " " + src.getEmployee().getLname()
									: null,
							PayrollDto::setEmployeeName);
				});

		return mapper;
	}
}
