package com.mitocode.security;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

//Clase S3
@Data
@AllArgsConstructor
public class AuthResponse {

	private String token;
	private Date expiration;
}