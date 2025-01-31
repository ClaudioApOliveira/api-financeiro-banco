package com.banco.financeiro.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

	private final UserAuthenticationFilter userAuthenticationFilter;

	protected static final String[] ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED = {"/user/create", "/security/login"};

	// Endpoints que requerem autenticação para serem acessados
	protected static final String[] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {};

	// Endpoints que só podem ser acessador por usuários com permissão de cliente
	protected static final String[] ENDPOINTS_CUSTOMER = {};

	// Endpoints que só podem ser acessador por usuários com permissão de
	// administrador
	protected static final String[] ENDPOINTS_ADMIN = {};

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()))
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						authorize -> authorize.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).permitAll()
								.requestMatchers(ENDPOINTS_WITH_AUTHENTICATION_REQUIRED).authenticated()
								.requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRADOR")
								.requestMatchers(ENDPOINTS_CUSTOMER).hasRole("COMMON").anyRequest().denyAll())
				.addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.headers(headers -> headers.cacheControl(HeadersConfigurer.CacheControlConfig::disable)).build();
	}

	@Bean
	protected AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PATCH");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("OPTIONS");
		source.registerCorsConfiguration("/**", config);
		return source;
	}
}
