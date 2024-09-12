package com.purna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaRepositories(basePackages = "com.purna.repositories")
public class RepositoryMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RepositoryMicroservicesApplication.class, args);
	}

}
