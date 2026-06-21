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
public class ProfileDTO {

	 private Long id;
	    private String fname;
	    private String lname;
	    private String username;
	    private String email;
	    private String phone;
	    private String post;
}
