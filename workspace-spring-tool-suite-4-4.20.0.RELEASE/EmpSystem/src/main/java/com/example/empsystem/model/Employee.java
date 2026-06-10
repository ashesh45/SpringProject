package com.example.empsystem.model;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString(exclude = "departments")
@Table(name = "employee_tbl")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fname;
    private String lname;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String gender;

    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dob;

    private String company;
    private String post;
    private int salary;

    private LocalDate joinDate;

    private String photo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToMany
    private List<Department> departments;
    private String role;


}