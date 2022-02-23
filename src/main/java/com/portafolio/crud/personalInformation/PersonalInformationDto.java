package com.portafolio.crud.personalInformation;

import javax.validation.constraints.NotBlank;

import com.portafolio.crud.cloudinary.Image;

public class PersonalInformationDto {

	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotBlank
    private String name;
    private String degree;
    private String summary;
    private String username;
    private Image image;

	public PersonalInformationDto() {
	}

		
	public PersonalInformationDto(@NotBlank String name, String degree, String summary,
			String username, Image image) {
		super();
		this.name = name;
		this.degree = degree;
		this.summary = summary;
		this.username = username;
		this.image = image;
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
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
