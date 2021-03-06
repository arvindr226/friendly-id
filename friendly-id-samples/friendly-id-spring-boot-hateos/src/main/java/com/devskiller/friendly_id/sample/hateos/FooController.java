package com.devskiller.friendly_id.sample.hateos;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.EmbeddedWrapper;
import org.springframework.hateoas.core.EmbeddedWrappers;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devskiller.friendly_id.Url62;
import com.devskiller.friendly_id.sample.hateos.domain.Bar;
import com.devskiller.friendly_id.sample.hateos.domain.Foo;

import static com.devskiller.friendly_id.Url62.encode;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@ExposesResourceFor(FooResource.class)
@RequestMapping("/foos")
public class FooController {

	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final EntityLinks entityLinks;
	private final FooResourceAssembler assembler;

	public FooController(EntityLinks entityLinks, FooResourceAssembler assembler) {
		this.entityLinks = entityLinks;
		this.assembler = assembler;
	}

	@GetMapping("/{id}")
	public HttpEntity<FooResource> get(@PathVariable UUID id) {
		log.info("Get {}", id);
		Foo foo = new Foo(id, "Foo");

		FooResource fooResource = assembler.toResource(foo);
		return ResponseEntity.ok(fooResource);

	}

	@PutMapping("/{id}")
	public HttpEntity<FooResource> update(@PathVariable UUID id, @RequestBody FooResource fooResource) {
		log.info("Update {} : {}", id, fooResource);
		Foo entity = new Foo(fooResource.getUuid(), fooResource.getName());
		return ResponseEntity.ok(assembler.toResource(entity));
	}

	@PostMapping
	public HttpEntity<FooResource> create(@RequestBody FooResource fooResource) {
		HttpHeaders headers = new HttpHeaders();
		Foo entity = new Foo(fooResource.getUuid(), "Foo");

		// ...

		headers.setLocation(entityLinks.linkForSingleResource(FooResource.class, encode(entity.getId())).toUri());
		return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}

}
