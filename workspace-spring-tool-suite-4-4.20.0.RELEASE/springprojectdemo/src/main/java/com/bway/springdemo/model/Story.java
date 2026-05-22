package com.bway.springdemo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

	@Id
	private String id;

	@Column(length = 500)
	private String slug;

	@Column(length = 1000)
	private String url;

	@Column(length = 1000)
	private String topic_en;

	@Column(length = 1000)
	private String topic_ne;

	@Column(length = 3000)
	private String summary_en;

	@Column(length = 3000)
	private String summary_ne;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "story_id")
	private List<Source> sources;

}
