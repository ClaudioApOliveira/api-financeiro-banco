package com.banco.financeiro.service.impl;

import java.util.List;

import com.banco.financeiro.exception.BusinessException;
import com.banco.financeiro.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.banco.financeiro.constant.AuthenticationRole;
import com.banco.financeiro.dto.UsuarioPDTO;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.repository.AutenticacaoRepository;
import com.banco.financeiro.service.AutenticacaoService;
import com.banco.financeiro.utils.Encryptor;

import lombok.RequiredArgsConstructor;

@Slf4j
@Service
@RequiredArgsConstructor
public class AutenticacaoServiceImpl implements AutenticacaoService {

    private final AutenticacaoRepository autenticacaoRepository;

    @Override
    public Autenticacao include(UsuarioPDTO usuarioPDTO, List<AuthenticationRole> roles) {
        log.info("Incluindo autenticação para o usuário: {}", usuarioPDTO.email());
        Autenticacao autenticacao = Autenticacao.builder().email(usuarioPDTO.email())
                .password(Encryptor.encode(usuarioPDTO.password())).roles(roles).isLocked(false).build();
        log.info("Autenticação incluída com sucesso para o usuário: {}", usuarioPDTO.email());
        return this.autenticacaoRepository.save(autenticacao);
    }

    @Override
    public Autenticacao findByEmail(String email) {
        log.info("Buscando autenticação pelo email: {}", email);
        return this.autenticacaoRepository.findByEmail(email).orElseThrow(() ->
                new BusinessException(MessageUtils.getMensagemValidacao("autenticacao.not.found")));
    }

}
