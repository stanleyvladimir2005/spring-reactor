package com.mitocode.dto;

import java.util.Map;
import lombok.Data;

@Data
public class ErrorResponse {

	Map<String, Object> error;

	public ErrorResponse(Map<String, Object> error) {
		this.error = error;
	}
}