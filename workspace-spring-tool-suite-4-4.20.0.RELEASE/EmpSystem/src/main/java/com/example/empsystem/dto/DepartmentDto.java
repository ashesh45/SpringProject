package com.example.empsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {

    private Integer id;
    private String deptName;
    private String hod;
    private String phone;

    // Helper: create a DTO with just an ID reference
    public static DepartmentDto reference(int id) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(id);
        return dto;
    }
}
