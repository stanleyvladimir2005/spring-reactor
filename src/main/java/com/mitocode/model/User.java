package com.mitocode.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = "users")
public class User {

	@Id
	private String id;

	@Field(name = "user")
	private String user;

	@Field(name = "password")
	private String password;

	@Field(name = "status")
	private Boolean status;

	private List<Role> roles;
}