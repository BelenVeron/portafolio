package com.portafolio.crud.social;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SocialEnum {
	
	FACEBOOK("facebook"),
	YOUTUBE("youtube"),
	INSTAGRAM("instagram"),
	TWITTER("twitter"),
	LINKEDIN("linkedin");
	
	private String social;
	
	private SocialEnum(String social) {
		this.social=social;
	}
	
	@JsonCreator
	public static SocialEnum desocial(final String social) {
		return java.util.stream.Stream.of(SocialEnum.values()).filter(targetEnum -> targetEnum.social.equals(social)).findFirst().orElse(null);
	}
	
	@JsonValue
	public String getCode() {
		return social;
	}

}
