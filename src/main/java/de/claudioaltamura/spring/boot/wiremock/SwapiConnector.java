package de.claudioaltamura.spring.boot.wiremock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

@Component
public class SwapiConnector {

    private final RestClient restClient;

    @Autowired
    public SwapiConnector(RestClient restClient) {
        this.restClient = restClient;
    }

    public JsonNode getPeople(int id) {
        return restClient.get()
                .uri("/api/people/{id}/?format=json", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(JsonNode.class);
    }

}
