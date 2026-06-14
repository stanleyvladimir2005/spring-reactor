package com.mitocode.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@AllArgsConstructor
@Document(collection= "dishes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Dish {
	
	@Id
	private String id;
	
	@NotEmpty
	@Size(min = 3)
	@Field(name="dishName")
	private String dishName;
	
	@Field(name="price")
	private Double price;
	
	@NotNull
	@Field(name="status")
	private boolean status;
}