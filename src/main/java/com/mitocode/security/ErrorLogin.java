package com.mitocode.security;

import java.util.Date;

import lombok.Data;

@Data
public class ErrorLogin {

	private String mensaje;
	private Date timestamp;
	
	public ErrorLogin(String mensaje, Date timestamp) {		
		this.mensaje = mensaje;
		this.timestamp = timestamp;
	}
}