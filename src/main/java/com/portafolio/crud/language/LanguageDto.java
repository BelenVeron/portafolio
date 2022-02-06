package com.portafolio.crud.language;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class LanguageDto {
	
	@NotBlank
	private String name;
    private String level;
    
	public LanguageDto() {
	}

	public LanguageDto(@NotBlank String name, String level) {
		super();
		this.name = name;
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	} 
    

}
