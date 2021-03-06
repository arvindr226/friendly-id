package com.devskiller.friendly_id.sample.hateos;

import java.util.UUID;

import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devskiller.friendly_id.sample.hateos.domain.Bar;
import com.devskiller.friendly_id.sample.hateos.domain.Foo;

@RestController
@ExposesResourceFor(BarResource.class)
@RequestMapping("/foos/{fooId}/bars")
public class BarController {

	private final BarResourceAssembler assembler;

	public BarController(BarResourceAssembler assembler) {
		this.assembler = assembler;
	}

	@GetMapping("/{id}")
	public BarResource getBar(@PathVariable UUID fooId, @PathVariable UUID id) {
		return assembler.toResource(new Bar(id, "Bar", new Foo(fooId, "Root Foo")));
	}

}
