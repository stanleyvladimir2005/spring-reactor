package com.mitocode.controller;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;
import static reactor.function.TupleUtils.function;
import java.net.URI;
import java.util.ArrayList;
import javax.validation.Valid;

import com.mitocode.model.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Links;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.mitocode.dto.RestResponse;
import com.mitocode.service.IDishService;
import com.mitocode.util.PageSupport;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/dishes")
public class DishController {
	
	@Autowired
	private IDishService service;
		
	@GetMapping
	public Mono<ResponseEntity<Flux<Dish>>> findAll() {
		Flux<Dish> fxDishes = service.findAll();
		return Mono.just(ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(fxDishes)
				);
	}
	
	//Forma especial para mostrar la lista en forma content y error
	@GetMapping("/RR")
	public Mono<ResponseEntity<RestResponse>> findAllRR() {
		Flux<Dish> fxDishes = service.findAll();
		return fxDishes
			.collectList()
			.map(list -> {
				RestResponse rr = new RestResponse();
				rr.setContent(list);
				rr.setErrors(new ArrayList<>());	
				return rr;
			})
			.map(rr -> ResponseEntity
					.ok()
					.contentType(MediaType.APPLICATION_JSON)
					.body(rr));			
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Dish>> findById(@PathVariable("id") String id){
		return service.findById(id) //Mono<Dish>
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)
						) //Mono<ResponseEntity<Dish>>
				.defaultIfEmpty(ResponseEntity.notFound().build());				
	}
	
	@PostMapping
	public Mono<ResponseEntity<Dish>> save(@Valid @RequestBody Dish p, final ServerHttpRequest req){
		return service.save(p)
				.map(pl -> ResponseEntity.created(URI.create(req.getURI().toString().concat("/").concat(pl.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(pl)
				);
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Dish>> update(@Valid @RequestBody Dish p, @PathVariable("id") String id){
		Mono<Dish> monoDish = Mono.just(p);
		Mono<Dish> monoBD = service.findById(id);
		return monoBD
				.zipWith(monoDish, (bd, pl) -> {
					bd.setId(id);
					bd.setDishName(pl.getDishName());
					bd.setPrice(pl.getPrice());
					bd.setStatus(pl.isStatus());
					return bd;
				})
				.flatMap(service::update) //bd -> service.update(bd)
				.map(pl -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(pl))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable("id") String id){
		return service.findById(id)
				.flatMap(p -> service.delete(p.getId()) // Mono<Void>  return service.delete(p.getId())
						.then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT))))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	
	@GetMapping("/hateoas/{id}")
	public Mono<EntityModel<Dish>> findByIdHateoas(@PathVariable("id") String id){
		//localhost:8080/platos/60779cc08e37a27164468033	
		Mono<Link> link1 =linkTo(methodOn(DishController.class).findById(id)).withSelfRel().toMono();
		Mono<Link> link2 =linkTo(methodOn(DishController.class).findById(id)).withSelfRel().toMono();
		return link1.zipWith(link2)
				.map(function((left, right) -> Links.of(left, right)))				
				.zipWith(service.findById(id), (lk, p) -> EntityModel.of(p, lk));
	}
	
	@GetMapping("/pageable")
	public Mono<ResponseEntity<PageSupport<Dish>>> listPagebale(@RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "size", defaultValue = "10") int size	){
		Pageable pageRequest = PageRequest.of(page, size);
		return service.listPage(pageRequest)
				.map(p -> ResponseEntity.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(p)	
						)
				.defaultIfEmpty(ResponseEntity.noContent().build());
	}
}