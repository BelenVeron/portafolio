package com.portafolio.crud.social;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.portafolio.security.entity.User;

@Entity
public class Social {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@NotNull
	private SocialEnum social;
    private String link; 
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

	public Social() {
	}

	public Social(@NotNull SocialEnum social, String link, User user) {
		super();
		this.social = social;
		this.link = link;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getUserId(User user) {
		return user.getId();
	}

	public void setUser(User user) {
		this.user = user;
	}
    
    
}
