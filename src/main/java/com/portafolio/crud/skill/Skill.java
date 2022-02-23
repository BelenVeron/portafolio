package com.portafolio.crud.skill;

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
public class Skill {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@NotNull
	private String name;
    private int percent; 
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

	public Skill() {
	}

	public Skill(@NotNull String name, int percent, User user) {
		super();
		this.name = name;
		this.percent = percent;
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

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public Long getUserId(User user) {
		return user.getId();
	}

	public void setUser(User user) {
		this.user = user;
	}
    

}
