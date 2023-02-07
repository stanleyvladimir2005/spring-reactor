package com.mitocode.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class FilterDTO {
	private String idClient;
	private LocalDate starDate;
	private LocalDate endDate;
}