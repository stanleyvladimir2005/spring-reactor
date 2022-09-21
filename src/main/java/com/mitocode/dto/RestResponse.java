package com.mitocode.dto;

import java.util.List;

import lombok.Data;

@Data
public class RestResponse {

	private List<?> content;
	private List<ErrorResponse> errors;
}
