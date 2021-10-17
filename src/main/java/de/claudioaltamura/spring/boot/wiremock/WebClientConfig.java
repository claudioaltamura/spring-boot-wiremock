package de.claudioaltamura.spring.boot.wiremock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient todoWebClient(
			@Value("${superhero_base_url}") String superheroBaseUrl,
			WebClient.Builder webClientBuilder) {
		return webClientBuilder
				.baseUrl(superheroBaseUrl)
				.defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
				.build();
	}
}