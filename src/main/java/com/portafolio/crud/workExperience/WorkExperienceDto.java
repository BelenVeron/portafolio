package com.portafolio.crud.workExperience;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portafolio.security.entity.User;

public class WorkExperienceDto {

    private String degree;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate start;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate end;
    private boolean inProgress;
    private String description;
    
    private User user;
    
    
	public WorkExperienceDto() {
	}

	public WorkExperienceDto(@NotNull String degree, LocalDate start, LocalDate end, boolean inProgress,
			String description, User user) {
		super();
		this.degree = degree;
		this.start = start;
		this.end = end;
		this.inProgress = inProgress;
		this.description = description;
		this.user = user;
	}



	public void setUser(User user) {
		this.user = user;
	}


	public String getDegree() {
		return degree;
	}


	public void setDegree(String degree) {
		this.degree = degree;
	}


	public LocalDate getStart() {
		return start;
	}


	public void setStart(LocalDate start) {
		this.start = start;
	}


	public LocalDate getEnd() {
		return end;
	}


	public void setEnd(LocalDate end) {
		this.end = end;
	}


	public boolean getInProgress() {
		return inProgress;
	}


	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
    
}
