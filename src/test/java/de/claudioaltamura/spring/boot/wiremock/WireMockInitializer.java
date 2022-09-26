package de.claudioaltamura.spring.boot.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;

import java.util.Map;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

class WireMockInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	private WireMockServer wireMockServer;

	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
		wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
		wireMockServer.start();

		configurableApplicationContext
				.getBeanFactory()
				.registerSingleton("wireMockServer", wireMockServer);

		configurableApplicationContext.addApplicationListener(applicationEvent -> {
			if (applicationEvent instanceof ContextClosedEvent) {
				wireMockServer.stop();
			}
		});

		TestPropertyValues
				.of(Map.of("superhero_base_url", "http://localhost:" + wireMockServer.port()))
				.applyTo(configurableApplicationContext);

		addErrorStub();
	}

	private void addErrorStub() {
		wireMockServer.stubFor(get(urlPathEqualTo("/webservice"))
				.atPriority(9)
				.withHeader("Accept", matching("text/.*"))
				.willReturn(aResponse()
						.withStatus(503)));

	}
}