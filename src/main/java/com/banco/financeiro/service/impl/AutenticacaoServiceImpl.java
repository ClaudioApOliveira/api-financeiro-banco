package com.banco.financeiro.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banco.financeiro.constant.AuthenticationRole;
import com.banco.financeiro.dto.UsuarioPDTO;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.repository.AutenticacaoRepository;
import com.banco.financeiro.service.AutenticacaoService;
import com.banco.financeiro.utils.Encryptor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacaoServiceImpl implements AutenticacaoService {

	private final AutenticacaoRepository autenticacaoRepository;

	@Override
	public Autenticacao include(UsuarioPDTO usuarioPDTO, List<AuthenticationRole> roles) {
		Autenticacao autenticacao = Autenticacao.builder().email(usuarioPDTO.email())
				.password(Encryptor.encode(usuarioPDTO.password())).roles(roles).isLocked(false).build();
		return this.autenticacaoRepository.save(autenticacao);
	}

}
