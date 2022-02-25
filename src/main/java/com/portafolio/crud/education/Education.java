package com.portafolio.crud.education;

import java.time.LocalDate;

import javax.persistence.CascadeType;
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
public class Education {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@NotNull
	private String institution;
    private String degree;
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate date;
    private String Period;
    
    @OneToOne()
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

	public Education() {
	}

	public Education(@NotNull String institution, String degree, LocalDate date, String period, Image image,
			User user) {
		super();
		this.institution = institution;
		this.degree = degree;
		this.date = date;
		Period = period;
		this.image = image;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public Long getUserId(User user) {
		return user.getId();
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
    
    
}
