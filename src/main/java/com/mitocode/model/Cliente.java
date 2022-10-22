package com.mitocode.model;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Document(collection= "clientes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Cliente {
	
	@Id
	private String id;

	@Size(min = 3, message = "nombre minimo 3")
	@Field(name = "nombres")
	private String nombres;

	@Size(min = 3, message = "nombre minimo 3")
	@Field(name = "apellidos")
	private String apellidos;

	@Field(name = "fechaNacimiento")
	private LocalDate fechaNacimiento;

	@Field(name = "urlFoto")
	private String urlFoto;
}