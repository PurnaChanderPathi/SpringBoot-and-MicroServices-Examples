package com.purna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayMicroservicesApplication.class, args);
	}

}
