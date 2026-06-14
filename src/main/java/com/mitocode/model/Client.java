package com.mitocode.model;

import java.time.LocalDate;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@Document(collection= "clients")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {
	
	@Id
	private String id;

	@Size(min = 3)
	@Field(name = "firstName")
	private String firstName;

	@Size(min = 3)
	@Field(name = "lastName")
	private String lastName;

	@Field(name = "birthday")
	private LocalDate birthday;

	@Field(name = "urlPhoto")
	private String urlPhoto;
}