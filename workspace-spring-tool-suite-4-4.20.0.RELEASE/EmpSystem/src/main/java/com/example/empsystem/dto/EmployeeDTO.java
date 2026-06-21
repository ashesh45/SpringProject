package com.example.empsystem.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {

    private Long id;
    private String fname;
    private String lname;
    private String username;
    private String email;
    private String phone;
    private String gender;
    private LocalDate dob;
    private String company;
    private String post;
    private int salary;
    private LocalDate joinDate;
    private String photo;
 

    // Address fields (flattened)
    private String addressName;
    private String addressState;
    private String addressZipcode;

    private List<DepartmentDto> departments;

  //  private List<Integer> departmentIds;

    private String role;
}
