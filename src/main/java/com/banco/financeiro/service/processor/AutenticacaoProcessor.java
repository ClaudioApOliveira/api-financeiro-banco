package com.banco.financeiro.service.processor;

import org.springframework.stereotype.Component;

import com.banco.financeiro.exception.BusinessException;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.repository.AutenticacaoRepository;
import com.banco.financeiro.utils.MessageUtils;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AutenticacaoProcessor {

	private final AutenticacaoRepository autenticacaoRepository;

	public void alreadyExists(String email) {
		this.autenticacaoRepository.findByEmail(email).ifPresent(auth -> {
			throw new BusinessException(MessageUtils.getMensagemValidacao("autenticacao.already.exists"));
		});

	}

	public Autenticacao exists(String email) {
		return this.autenticacaoRepository.findByEmail(email)
				.orElseThrow(() -> new BusinessException(MessageUtils.getMensagemValidacao("autenticacao.not.found")));
	}

}
