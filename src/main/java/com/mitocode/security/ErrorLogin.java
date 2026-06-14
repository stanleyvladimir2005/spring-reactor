package com.mitocode.security;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorLogin {

	private String mensaje;
	private Date timestamp;
}