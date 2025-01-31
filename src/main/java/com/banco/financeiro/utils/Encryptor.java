package com.banco.financeiro.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Encryptor {

	public static String encode(String value) {
		return new BCryptPasswordEncoder().encode(value);
	}

	public static Boolean match(String value, String encoded) {
		return new BCryptPasswordEncoder().matches(value, encoded);
	}

}
