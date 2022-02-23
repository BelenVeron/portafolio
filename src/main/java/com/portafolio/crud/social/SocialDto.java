package com.portafolio.crud.social;

import javax.validation.constraints.NotBlank;

public class SocialDto {

	@NotBlank
	private SocialEnum social;
    private String link;
    
	public SocialDto() {
	}

	public SocialDto(@NotBlank SocialEnum social, String link) {
		super();
		this.social = social;
		this.link = link;
	}

	public SocialEnum getSocial() {
		return social;
	}

	public void setSocial(SocialEnum social) {
		this.social = social;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	} 
    
    
}
