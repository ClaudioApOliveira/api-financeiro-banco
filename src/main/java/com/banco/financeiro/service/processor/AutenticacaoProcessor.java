package com.banco.financeiro.service.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.banco.financeiro.exception.BusinessException;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.repository.AutenticacaoRepository;
import com.banco.financeiro.utils.MessageUtils;

import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AutenticacaoProcessor {

	private final AutenticacaoRepository autenticacaoRepository;

	public void alreadyExists(String email) {
		log.info("Verificando se já existe autenticação para o email: {}", email);
		this.autenticacaoRepository.findByEmail(email).ifPresent(auth -> {
			throw new BusinessException(MessageUtils.getMensagemValidacao("autenticacao.already.exists"));
		});

	}

	public Autenticacao exists(String email) {
		log.info("Verificando se existe autenticação para o email: {}", email);
		return this.autenticacaoRepository.findByEmail(email)
				.orElseThrow(() -> new BusinessException(MessageUtils.getMensagemValidacao("autenticacao.not.found")));
	}

}
