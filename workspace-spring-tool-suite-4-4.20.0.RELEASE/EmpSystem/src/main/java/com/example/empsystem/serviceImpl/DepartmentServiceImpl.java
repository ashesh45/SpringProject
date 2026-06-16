package com.example.empsystem.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.empsystem.dto.DepartmentDto;
import com.example.empsystem.dto.mapper.DepartmentMapper;
import com.example.empsystem.model.Department;
import com.example.empsystem.repository.DepartmentRepository;
import com.example.empsystem.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepo;

	@Override
	public DepartmentDto createDepartment(DepartmentDto dto) {
		Department department = DepartmentMapper.toEntity(dto);
		Department saved = departmentRepo.save(department);
		return DepartmentMapper.toDto(saved);
	}

	@Override
	public DepartmentDto getDepartmentById(int id) {
		Department department = departmentRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Department not found with id: " + id));
		return DepartmentMapper.toDto(department);
	}

	@Override
	public List<DepartmentDto> getAllDepartments() {
		return departmentRepo.findAll().stream()
				.map(DepartmentMapper::toDto)
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
		return DepartmentMapper.toDto(saved);
	}

	@Override
	public void deleteDepartment(int id) {
		departmentRepo.deleteById(id);
	}

}
