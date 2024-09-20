package com.purna.config;

import javax.net.ssl.SSLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebConfig {

	 @Bean
	    public WebClient webClient() throws SSLException {
	        HttpClient httpClient = HttpClient.create()
	                .secure(spec -> {
						try {
							spec.sslContext(SslContextBuilder.forClient()
							        .trustManager(InsecureTrustManagerFactory.INSTANCE).build());
						} catch (SSLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});

	        ExchangeStrategies strategies = ExchangeStrategies.builder()
	                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)) // 16MB buffer size
	                .build();

	        return WebClient.builder()
	                .clientConnector(new ReactorClientHttpConnector(httpClient))
	                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
	                .exchangeStrategies(strategies)
	                .build();
	    }

	@Bean
	public WebClient.Builder webClientBuilder(){
		return WebClient.builder();
	}

}
