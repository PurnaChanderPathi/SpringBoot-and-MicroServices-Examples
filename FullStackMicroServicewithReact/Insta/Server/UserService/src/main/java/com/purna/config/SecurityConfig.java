package com.purna.config;

import com.purna.service.UserDetailsService;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(requests->requests.requestMatchers("api/v1/users/**").permitAll()
				.requestMatchers("api/v1/auth/**").permitAll()
				.anyRequest().authenticated())
		.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	FilterRegistrationBean<CorsFilter> coresFilter() {

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.addAllowedOriginPattern("*");
		corsConfiguration.addAllowedHeader("Authorization");
		corsConfiguration.addAllowedHeader("Content-Type");
		corsConfiguration.addAllowedHeader("Accept");
		corsConfiguration.addAllowedMethod("GET");
		corsConfiguration.addAllowedMethod("POST");
		corsConfiguration.addAllowedMethod("DELETE");
		corsConfiguration.addAllowedMethod("PUT");
		corsConfiguration.addAllowedMethod("OPTIONS");
		corsConfiguration.setMaxAge(3600L);
		source.registerCorsConfiguration("/**", corsConfiguration);

		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter());
		bean.setOrder(-110);
		return bean;

	}

	@Bean
	public WebClient.Builder webClientBuilder(){
		return WebClient.builder();
	}

}
