package com.burakbayramin.mini_banking_app;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "MiniBankingApp",
				description = "MiniBankingApp is a simple banking application that allows users to create accounts, deposit and withdraw money, and transfer money between accounts.",
				version = "v1",
				contact = @Contact(
						name = "Burak Bayramin",
						email = "burakbyrmn@gmail.com",
						url = "https://burakbyrmn.vercel.app"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/burakbayramin/mini-banking-app"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "REST API Documentation",
				url = "https://localhost:8080/swagger-ui.html"
		)
)
public class MiniBankingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiniBankingAppApplication.class, args);
	}

}
