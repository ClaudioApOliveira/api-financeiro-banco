package com.banco.financeiro.service.impl;

import org.springframework.stereotype.Service;

import com.banco.financeiro.dto.LoginDTO;
import com.banco.financeiro.dto.token.TokenFRDTO;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.service.SecurityService;
import com.banco.financeiro.service.processor.SecurityProcessor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

	private final SecurityProcessor securityProcessor;

	@Override
	public TokenFRDTO login(LoginDTO loginDTO) {
		Autenticacao autenticacao = this.securityProcessor.check(loginDTO.email());
		this.securityProcessor.matchPassword(loginDTO.password(), autenticacao.getPassword());
		String token = this.securityProcessor.authenticate(loginDTO.email(), loginDTO.password());
		return new TokenFRDTO(token, autenticacao.getRoles());
	}

}
