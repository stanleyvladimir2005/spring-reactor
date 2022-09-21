package com.mitocode.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FiltroDTO {
	
	private String idCliente;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;

}
