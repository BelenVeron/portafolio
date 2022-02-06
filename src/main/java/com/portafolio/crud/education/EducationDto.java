package com.portafolio.crud.education;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portafolio.security.entity.User;

public class EducationDto {
	
    @NotBlank
	private String institution;
    private String degree;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate date;
    private String Period;
    
    private User user;
    
   
	public EducationDto() {
	}

	public EducationDto(@NotBlank String institution, String degree, LocalDate date, String period, User user) {
		super();
		this.institution = institution;
		this.degree = degree;
		this.date = date;
		Period = period;
		this.user = user;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getPeriod() {
		return Period;
	}

	public void setPeriod(String period) {
		Period = period;
	}

	public Long getUserId() {
		return user.getId();
	}

	public void setUser(User user) {
		this.user = user;
	}

    

}
