package com.bway.springdemo.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Product {
	
	@Id
	private int id;
	private String title;
	private double price;
	
	@Column(columnDefinition = "longtext")
	private String description;
	private String category;
	private String image;
	
	
	@OneToOne(cascade = CascadeType.ALL )
	@JoinColumn(name = "rating_id")
	private Rating rating;

}
