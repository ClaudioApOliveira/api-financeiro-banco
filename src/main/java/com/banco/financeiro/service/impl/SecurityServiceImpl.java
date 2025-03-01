package com.banco.financeiro.service.impl;

import com.banco.financeiro.dto.UsuarioRPDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.banco.financeiro.dto.LoginDTO;
import com.banco.financeiro.dto.token.TokenFRDTO;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.service.SecurityService;
import com.banco.financeiro.service.processor.SecurityProcessor;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final SecurityProcessor securityProcessor;

    @Override
    public TokenFRDTO login(LoginDTO loginDTO) {
        log.info("Realizando login do usuário: {}", loginDTO.email());
        Autenticacao autenticacao = this.securityProcessor.check(loginDTO.email());
        this.securityProcessor.matchPassword(loginDTO.password(), autenticacao.getPassword());
        String token = this.securityProcessor.authenticate(loginDTO.email(), loginDTO.password());
        log.info("Usuário logado com sucesso: {}", loginDTO.email());
        return new TokenFRDTO(token, new UsuarioRPDTO(autenticacao.getUsuario().getId(), autenticacao.getEmail(), autenticacao.getRoles()));
    }

}
