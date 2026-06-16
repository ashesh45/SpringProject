package com.example.empsystem.dto.mapper;

import com.example.empsystem.dto.DepartmentDto;
import com.example.empsystem.model.Department;

public class DepartmentMapper {

    public static DepartmentDto toDto(Department entity) {
        if (entity == null) return null;

        return DepartmentDto.builder()
                .id(entity.getId())
                .deptName(entity.getDeptName())
                .hod(entity.getHod())
                .phone(entity.getPhone())
                .build();
    }

    public static Department toEntity(DepartmentDto dto) {
        if (dto == null) return null;

        Department entity = new Department();
        entity.setId(dto.getId());
        entity.setDeptName(dto.getDeptName());
        entity.setHod(dto.getHod());
        entity.setPhone(dto.getPhone());
        return entity;
    }
}
