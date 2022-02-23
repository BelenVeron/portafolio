package com.portafolio.security.dto;


public class JwtDto {
    private String token;

    public JwtDto(String token) {
        this.token = token;
    } 

    public JwtDto() {

	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
