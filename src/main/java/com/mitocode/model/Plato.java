package com.mitocode.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Document(collection= "platos")
@JsonInclude(JsonInclude.Include.NON_NULL)//Se indica que no se agregen los campos nulos en el Json
public class Plato {
	
	@Id
	private String id;
	
	@NotEmpty
	@Size(min = 3, message = "nombre minimo 3")
	@Field(name="nombre")
	private String nombre;
	
	@Field(name="precio")
	private Double precio;
	
	@NotNull
	@Field(name="estado")
	private boolean estado;

	public Plato(String id, @NotEmpty @Size(min = 3, message = "nombre minimo 3") String nombre, Double precio,	@NotNull boolean estado) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.estado = estado;
	}
}