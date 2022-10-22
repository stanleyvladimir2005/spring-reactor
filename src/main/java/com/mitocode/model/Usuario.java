package com.mitocode.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "usuarios")
public class Usuario {

	@Id
	private String id;

	@Field(name = "usuario")
	private String usuario;

	@Field(name = "clave")
	private String clave;

	@Field(name = "estado")
	private Boolean estado;

	private List<Rol> roles;
}