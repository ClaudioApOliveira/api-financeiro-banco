package com.banco.financeiro.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@PropertySource("classpath:security.properties")
public class JwtUtil {

	@Getter
	@Value("${token.type}")
	private String type;

	@Getter
	@Value("${token.header}")
	private String header;

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;

}
