package com.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ActionServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActionServiceApplication.class, args);
	}

}
