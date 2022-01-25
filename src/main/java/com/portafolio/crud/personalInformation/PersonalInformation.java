package com.portafolio.crud.personalInformation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class PersonalInformation {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	@NotNull
    private String name;
    private String picture;
    private String degree;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String summary;
    
    @Column(name="user_id")
    private int userId;


	public PersonalInformation() {
	}

	public PersonalInformation(@NotNull String name, String picture, String degree, String summary, int userId) {
		this.name = name;
		this.picture = picture;
		this.degree = degree;
		this.summary = summary;
		this.userId = userId;
	}
	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}
