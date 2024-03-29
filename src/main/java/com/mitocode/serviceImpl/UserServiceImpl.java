package com.mitocode.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mitocode.model.User;
import com.mitocode.repo.IGenericRepo;
import com.mitocode.repo.IRoleRepo;
import com.mitocode.repo.IUserRepo;
import com.mitocode.service.IUserService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl extends CRUDImpl<User, String> implements IUserService {

	@Autowired
	private IUserRepo repo;
	
	@Autowired
	private IRoleRepo rolRepo;
	
	@Override
	protected IGenericRepo<User, String> getRepo() {
		return repo; 
	}
	
	@Override
	public Mono<com.mitocode.security.User> searchByUser(String user) {
		Mono<User> monoUser = repo.findOneByUser(user);
		List<String> roles = new ArrayList<>();
		return monoUser.flatMap(u -> Flux.fromIterable(u.getRoles())
				.flatMap(rol -> rolRepo.findById(rol.getId())
						.map(r -> {
							roles.add(r.getRolName());
							return r;
						})).collectList().flatMap(list -> {
					u.setRoles(list);
					return Mono.just(u);
				}))
		.flatMap(u -> Mono.just(new com.mitocode.security.User(u.getUser(), u.getPassword(), u.getStatus(), roles)));
	}
}