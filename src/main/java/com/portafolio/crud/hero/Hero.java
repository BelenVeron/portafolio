package com.portafolio.crud.hero;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.portafolio.crud.cloudinary.Image;
import com.portafolio.security.entity.User;

@Entity
public class Hero {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
	@OneToOne()
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

	public Hero() {
	}

	public Hero(Image image, User user) {
		super();
		this.image = image;
		this.user = user;
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

	public Long getUser(User user) {
		return user.getId();
	}

	public void setUser(User user) {
		this.user = user;
	}

}
