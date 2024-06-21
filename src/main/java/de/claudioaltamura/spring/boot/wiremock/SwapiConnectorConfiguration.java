package de.claudioaltamura.spring.boot.wiremock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class SwapiConnectorConfiguration {

    @Value("${swapiConnector.baseURL}")
    private String baseURL;

    @Autowired
    public SwapiConnectorConfiguration() {}

    public SwapiConnectorConfiguration(String baseURL) {
        this.baseURL = baseURL;
    }

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl(baseURL)
                .build();
    }

}
