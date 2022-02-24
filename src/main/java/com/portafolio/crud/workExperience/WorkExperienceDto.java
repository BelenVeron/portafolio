package com.portafolio.crud.workExperience;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portafolio.crud.cloudinary.Image;

public class WorkExperienceDto {

	private Long id;
    private String degree;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate start;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate finished;
    private boolean inProgress;
    private String description;
    private Image image;
    
    
	public WorkExperienceDto() {
	}

	public WorkExperienceDto(Long id, String degree, LocalDate start, LocalDate finished, boolean inProgress,
			String description, Image image) {
		super();
		this.id = id;
		this.degree = degree;
		this.start = start;
		this.finished = finished;
		this.inProgress = inProgress;
		this.description = description;
		this.image = image;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	public LocalDate getStart() {
		return start;
	}


	public void setStart(LocalDate start) {
		this.start = start;
	}


	public LocalDate getfinished() {
		return finished;
	}


	public void setfinished(LocalDate finished) {
		this.finished = finished;
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
