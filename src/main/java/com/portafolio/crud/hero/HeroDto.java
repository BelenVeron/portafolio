package com.portafolio.crud.hero;

import com.portafolio.crud.cloudinary.Image;

public class HeroDto {
	
	private Long id;
	private Image image;
	 
	public HeroDto() {
	}

	public HeroDto(Long id, Image image) {
		super();
		this.id = id;
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
	  

}
