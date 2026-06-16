package com.example.empsystem.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.empsystem.dto.EmployeeDTO;
import com.example.empsystem.dto.mapper.EmployeeMapper;
import com.example.empsystem.dto.request.CreateEmployeeRequest;
import com.example.empsystem.model.Employee;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.repository.EmployeeRepository;
import com.example.empsystem.service.EmployeeService;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository empRepo;

    @Autowired
    private DepartmentRepository deptRepo;

    @Override
    public EmployeeDTO createEmployee(CreateEmployeeRequest request) {
        Employee entity = EmployeeMapper.toEntity(request, deptRepo);
        entity = empRepo.save(entity);
        return EmployeeMapper.toDto(entity);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        Employee entity = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        return EmployeeMapper.toDto(entity);
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        return empRepo.findAll(pageable)
                .map(EmployeeMapper::toDto);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee entity = empRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        EmployeeMapper.updateEntity(entity, dto, deptRepo);
        entity = empRepo.save(entity);
        return EmployeeMapper.toDto(entity);
    }

    @Override
    public void deleteEmployee(int id) {
        empRepo.deleteById((long) id);
    }
}
