package com.mitocode.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.Data;

@Data
public class ErrorResponse {

	Map<String, Object> error = new HashMap<>();	
	
	public ErrorResponse() {
	}
	
	public ErrorResponse(Map<String, Object> error) {		
		this.error = error;
	}
}