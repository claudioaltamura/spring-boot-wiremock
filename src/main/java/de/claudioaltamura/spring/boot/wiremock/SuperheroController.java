package de.claudioaltamura.spring.boot.wiremock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.databind.node.ArrayNode;

@RestController
@RequestMapping("/superheroes")
public class SuperheroController {

	private final WebClient superheroWebClient;

	public SuperheroController(WebClient superheroWebClient) {
		this.superheroWebClient = superheroWebClient;
	}

	@GetMapping
	public ArrayNode getAll() {
		return this.superheroWebClient
				.get()
				.uri("/superheroes")
				.retrieve()
				.bodyToMono(ArrayNode.class)
				.block();
	}
}