package com.example.empsystem.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.empsystem.dto.DepartmentDto;
import com.example.empsystem.dto.EmployeeDTO;
import com.example.empsystem.dto.request.CreateEmployeeRequest;
import com.example.empsystem.model.Department;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.EmployeeService;
import com.example.empsystem.service.FileUploadService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private DepartmentRepository deptRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public EmployeeDTO createEmployee(CreateEmployeeRequest request, MultipartFile file, String path) {
        if (file != null && !file.isEmpty()) {
            try {
                String photo = fileUploadService.uploadImage(path, file);
                request.setPhoto(photo);
            } catch (IOException e) {
                throw new RuntimeException("File upload failed: " + e.getMessage());
            }
        }

        Employee entity = modelMapper.map(request, Employee.class);
        entity.setPassword(request.getPassword());

        if (request.getDepartmentIds() != null && !request.getDepartmentIds().isEmpty()) {
            List<Department> departments = request.getDepartmentIds().stream()
                    .map(deptRepo::findById)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());
            entity.setDepartments(departments);
        }

        entity = empRepo.save(entity);
        return modelMapper.map(entity, EmployeeDTO.class);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee entity = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return modelMapper.map(entity, EmployeeDTO.class);
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {

        Page<Employee> employees = empRepo.findAll(pageable);

        return employees.map(employee -> {

            EmployeeDTO dto = new EmployeeDTO();

            dto.setId(employee.getId());
            dto.setFname(employee.getFname());
            dto.setLname(employee.getLname());
            dto.setUsername(employee.getUsername());
            dto.setEmail(employee.getEmail());

            if(employee.getDepartments()!=null){
                dto.setDepartments(
                    employee.getDepartments()
                    .stream()
                    .map(dep -> modelMapper.map(dep, DepartmentDto.class))
                    .toList()
                );
            }

            return dto;
        });
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto, MultipartFile file, String path) {
        if (file != null && !file.isEmpty()) {
            try {
                String photo = fileUploadService.uploadImage(path, file);
                dto.setPhoto(photo);
            } catch (IOException e) {
                throw new RuntimeException("File upload failed: " + e.getMessage());
            }
        }

        Employee entity = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        modelMapper.map(dto, entity);

        if (dto.getDepartments() != null) {

            List<Department> departments = dto.getDepartments()
                    .stream()
                    .map(deptDto -> deptRepo.findById(deptDto.getId()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            entity.setDepartments(departments);
        }

        entity = empRepo.save(entity);
        return modelMapper.map(entity, EmployeeDTO.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        empRepo.deleteById(id);
    }
}
