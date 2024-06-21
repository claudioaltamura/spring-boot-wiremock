package de.claudioaltamura.spring.boot.wiremock;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationController {

    private final SwapiConnector swapiConnector;

    @Autowired
    public ApplicationController(SwapiConnector swapiConnector) {
        this.swapiConnector = swapiConnector;
    }

    @RequestMapping(value = "/people/{id}", produces = { "application/json" })
    public ResponseEntity<JsonNode> person(@PathVariable int id) {
        return ResponseEntity.ok(swapiConnector.getPeople(id));
    }

}
