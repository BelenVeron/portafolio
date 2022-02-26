package com.portafolio.crud.project;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.portafolio.crud.cloudinary.Image;
import com.portafolio.security.entity.User;

@Entity
public class Project {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@NotNull
    private String name;
	@JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate date;
	@Lob
    private String description;
    private String link;
    
    @OneToOne()
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    
    // user information
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

	public Project() {
	}

	public Project(@NotNull String name, LocalDate date, String description, String link, User user) {
		super();
		this.name = name;
		this.date = date;
		this.description = description;
		this.link = link;
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

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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
