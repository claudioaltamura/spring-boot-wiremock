package de.claudioaltamura.spring.boot.wiremock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuperheroClientApp
{
	public static void main( String[] args )
	{
		SpringApplication.run(SuperheroClientApp.class, args);
	}
}