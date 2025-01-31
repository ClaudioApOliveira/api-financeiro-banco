package com.banco.financeiro.service.processor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.banco.financeiro.exception.BusinessException;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.security.JwtTokenService;
import com.banco.financeiro.security.UserDetailsServiceImpl;
import com.banco.financeiro.utils.Encryptor;
import com.banco.financeiro.utils.MessageUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityProcessor {

	private final AutenticacaoProcessor autenticacaoProcessor;
	private final JwtTokenService tokenService;
	private final UserDetailsServiceImpl userDetailsService;
	private final AuthenticationManager authenticationManager;

	public Autenticacao check(String email) {

		Autenticacao authentication = this.autenticacaoProcessor.exists(email);
		this.wasLocked(authentication);

		return authentication;
	}

	public void wasLocked(Autenticacao Autenticacao) {
		if (Autenticacao.getIsLocked()) {
			throw new BusinessException(MessageUtils.getMensagemValidacao("locked.account"));
		}

	}

	public void matchPassword(String password, String encodedPassword) {
		Boolean isSamePassword = Encryptor.match(password, encodedPassword);
		if (!isSamePassword) {
			throw new BusinessException(MessageUtils.getMensagemValidacao("wrong.password"));
		}

	}

	public String authenticate(String email, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
				password);

		org.springframework.security.core.Authentication authenticate = this.authenticationManager
				.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
		return this.tokenService.generateToken(userDetails);
	}
}
