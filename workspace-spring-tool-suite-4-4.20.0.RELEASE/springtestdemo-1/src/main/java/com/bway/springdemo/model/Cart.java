package com.bway.springdemo.model;


import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class Cart {
	
	
	   @Id
	    private Long id;

	    private Long userId;

	    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
	    private Date date;


	    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	    private List<CartProduct> products;

}
