package com.mitocode.repo;

import org.springframework.data.mongodb.repository.Query;
import com.mitocode.model.Menu;
import reactor.core.publisher.Flux;

public interface IMenuRepo extends IGenericRepo <Menu, String> {

	//Hacemos una query para buscar el rol
	@Query("{'roles' : { $in: ?0 }}")
	Flux<Menu> obtenerMenus(String[] roles);
}