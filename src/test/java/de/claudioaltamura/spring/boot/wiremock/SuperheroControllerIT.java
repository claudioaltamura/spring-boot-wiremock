package de.claudioaltamura.spring.boot.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {WireMockInitializer.class})
class SuperheroControllerIT {

	@Autowired
	private WireMockServer wireMockServer;

	@Autowired
	private WebTestClient webTestClient;

	@LocalServerPort
	private Integer port;

	@AfterEach
	public void afterEach() {
		this.wireMockServer.resetAll();
	}

	@Test
	void testGetAllShouldReturnDataFromClient() {
		this.wireMockServer.stubFor(
				WireMock.get("/superheroes")
						.willReturn(aResponse()
								.withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
								.withBody("[{\"id\": 1, \"name\":\"Batman\",\"power\":90.0,\"realName\":\"Bruce Wayne\",\"city\":\"Gotham City\"}]"))
		);

		this.webTestClient
				.get()
				.uri("http://localhost:" + port + "/superheroes")
				.exchange()
				.expectStatus()
				.is2xxSuccessful()
				.expectBody()
				.jsonPath("$[0].name")
				.isEqualTo("Batman")
				.jsonPath("$.length()")
				.isEqualTo(1);
	}
}
