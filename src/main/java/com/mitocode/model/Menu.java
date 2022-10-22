package com.mitocode.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "menus")
public class Menu {

	@Id
	private String id;

	@Field(name = "icono")
	private String icono;

	@Field(name = "nombre")
	private String nombre;

	@Field(name = "url")
	private String url;

	@Field(name = "roles")
	private List<String> roles;
}