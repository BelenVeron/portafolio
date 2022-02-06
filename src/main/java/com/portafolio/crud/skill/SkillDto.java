package com.portafolio.crud.skill;

import javax.validation.constraints.NotBlank;

public class SkillDto {
	
	@NotBlank
	private String name;
    private int percent;
    
	public SkillDto() {
	}

	public SkillDto(@NotBlank String name, int percent) {
		super();
		this.name = name;
		this.percent = percent;
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
    
	
    
}
