package com.portafolio.crud.personalInformation;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.portafolio.security.entity.User;

public class PersonalInformationDto {

	private int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotBlank
    private String name;
    private String picture;
    private String degree;
    private String summary;
    @NotBlank
    private int userId;
    private String username;

	public PersonalInformationDto() {
	}

	public PersonalInformationDto(@NotNull String name, String picture, String degree, String summary, User user) {
		this.name = name;
		this.picture = picture;
		this.degree = degree;
		this.summary = summary;
	}
	
	
	
	public PersonalInformationDto(@NotBlank String name, String picture, String degree, String summary,
			String username) {
		super();
		this.name = name;
		this.picture = picture;
		this.degree = degree;
		this.summary = summary;
		this.username = username;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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
