package com.portafolio.crud.personalInformation;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.portafolio.crud.cloudinary.Image;
import com.portafolio.security.entity.User;

@Entity
public class PersonalInformation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@NotNull
    private String name;
    private String degree;
    @Lob
    private String summary;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    
    // user information
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


	public PersonalInformation() {
	}

	

	public PersonalInformation(@NotNull String name, String degree, String summary, User user) {
		this.name = name;
		this.degree = degree;
		this.summary = summary;
		this.user = user;
	}
	
	/*
	 * Only return the id of the user to security
	 * */
	public Long getUserId(User user) {
		return user.getId();
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
