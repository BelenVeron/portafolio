package com.portafolio.crud.workExperience;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portafolio.crud.cloudinary.Image;
import com.portafolio.security.entity.User;

@Entity
public class WorkExperience {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @NotNull
    private String degree;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate start;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate end;
    private boolean inProgress;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image logo;
    
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    
    
	public WorkExperience() {
	}

	public WorkExperience(@NotNull String degree, LocalDate start, LocalDate end, boolean inProgress, Image logo,
			String description, User user) {
		super();
		this.degree = degree;
		this.start = start;
		this.end = end;
		this.inProgress = inProgress;
		this.logo = logo;
		this.description = description;
		this.user = user;
	}



	public void setUser(User user) {
		this.user = user;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public Image getLogo() {
		return logo;
	}


	public void setLogo(Image image) {
		this.logo = image;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}
    

}
