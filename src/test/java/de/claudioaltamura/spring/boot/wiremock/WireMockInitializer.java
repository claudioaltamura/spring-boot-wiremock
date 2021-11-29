package de.claudioaltamura.spring.boot.wiremock;

import java.util.Map;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

class WireMockInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
		WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().dynamicPort());
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
	}
}