package com.mitocode.security;

import java.util.Date;
import lombok.Data;

//Clase S3
@Data
public class AuthResponse {

	private String token;
	private Date expiracion;
	
	public AuthResponse(String token, Date expiracion) {
		super();
		this.token = token;
		this.expiracion = expiracion;
	}	
}