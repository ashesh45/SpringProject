package com.example.empsystem.dto.mapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.empsystem.dto.EmployeeDTO;
import com.example.empsystem.dto.request.CreateEmployeeRequest;
import com.example.empsystem.model.Address;
import com.example.empsystem.model.Department;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.DepartmentRepository;

public class EmployeeMapper {

    private EmployeeMapper() {}

    public static EmployeeDTO toDto(Employee entity) {
        if (entity == null) return null;

        List<Integer> deptIds = entity.getDepartments() != null
                ? entity.getDepartments().stream()
                        .map(Department::getId)
                        .collect(Collectors.toList())
                : Collections.emptyList();

        EmployeeDTO.EmployeeDTOBuilder builder = EmployeeDTO.builder()
                .id(entity.getId())
                .fname(entity.getFname())
                .lname(entity.getLname())
                .username(entity.getUsername())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .gender(entity.getGender())
                .dob(entity.getDob())
                .company(entity.getCompany())
                .post(entity.getPost())
                .salary(entity.getSalary())
                .joinDate(entity.getJoinDate())
                .photo(entity.getPhoto())
                .role(entity.getRole())
                .departmentIds(deptIds);

        if (entity.getAddress() != null) {
            builder.addressName(entity.getAddress().getName())
                   .addressState(entity.getAddress().getState())
                   .addressZipcode(entity.getAddress().getZipcode());
        }

        return builder.build();
    }

    public static Employee toEntity(CreateEmployeeRequest request,
                                    DepartmentRepository deptRepo) {
        if (request == null) return null;

        Employee entity = new Employee();
        entity.setFname(request.getFname());
        entity.setLname(request.getLname());
        entity.setUsername(request.getUsername());
        entity.setPassword(request.getPassword());
        entity.setEmail(request.getEmail());
        entity.setPhone(request.getPhone());
        entity.setGender(request.getGender());
        entity.setDob(request.getDob());
        entity.setCompany(request.getCompany());
        entity.setPost(request.getPost());
        entity.setSalary(request.getSalary());
        entity.setJoinDate(request.getJoinDate());
        entity.setRole(request.getRole());
        entity.setPhoto(request.getPhoto());

        if (request.getAddressName() != null || request.getAddressState() != null || request.getAddressZipcode() != null) {
            Address address = new Address();
            address.setName(request.getAddressName());
            address.setState(request.getAddressState());
            address.setZipcode(request.getAddressZipcode());
            entity.setAddress(address);
        }

        if (request.getDepartmentIds() != null && !request.getDepartmentIds().isEmpty()) {
            List<Department> departments = request.getDepartmentIds().stream()
                    .map(deptRepo::findById)
                    .filter(Optional::isPresent)
                    .map(java.util.Optional::get)
                    .collect(Collectors.toList());
            entity.setDepartments(departments);
        }

        return entity;
    }

    public static void updateEntity(Employee entity, EmployeeDTO dto,
                                    DepartmentRepository deptRepo) {
        if (dto == null || entity == null) return;

        entity.setFname(dto.getFname());
        entity.setLname(dto.getLname());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setGender(dto.getGender());
        entity.setDob(dto.getDob());
        entity.setCompany(dto.getCompany());
        entity.setPost(dto.getPost());
        entity.setSalary(dto.getSalary());
        entity.setJoinDate(dto.getJoinDate());
        entity.setRole(dto.getRole());
        entity.setPhoto(dto.getPhoto());

        if (dto.getAddressName() != null || dto.getAddressState() != null || dto.getAddressZipcode() != null) {
            Address address = entity.getAddress();
            if (address == null) {
                address = new Address();
                entity.setAddress(address);
            }
            address.setName(dto.getAddressName());
            address.setState(dto.getAddressState());
            address.setZipcode(dto.getAddressZipcode());
        }

        if (dto.getDepartmentIds() != null) {
            List<Department> departments = dto.getDepartmentIds().stream()
                    .map(deptRepo::findById)
                    .filter(Optional::isPresent)
                    .map(java.util.Optional::get)
                    .collect(Collectors.toList());
            entity.setDepartments(departments);
        }
    }
}
