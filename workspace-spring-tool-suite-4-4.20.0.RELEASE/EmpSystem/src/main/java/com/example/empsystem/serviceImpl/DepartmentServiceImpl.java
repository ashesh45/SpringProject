package com.example.empsystem.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.empsystem.dto.DepartmentDto;
import com.example.empsystem.model.Department;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DepartmentDto createDepartment(DepartmentDto dto) {
        Department entity = modelMapper.map(dto, Department.class);
        Department saved = departmentRepo.save(entity);
        return modelMapper.map(saved, DepartmentDto.class);
    }

    @Override
    public DepartmentDto getDepartmentById(int id) {
        Department entity = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        return modelMapper.map(entity, DepartmentDto.class);
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepo.findAll().stream()
                .map(entity -> modelMapper.map(entity, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDto updateDepartment(int id, DepartmentDto dto) {
        Department existing = departmentRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
        existing.setDeptName(dto.getDeptName());
        existing.setHod(dto.getHod());
        existing.setPhone(dto.getPhone());
        Department saved = departmentRepo.save(existing);
        return modelMapper.map(saved, DepartmentDto.class);
    }

    @Override
    public void deleteDepartment(int id) {
        departmentRepo.deleteById(id);
    }
}
