package com.banco.financeiro.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.banco.financeiro.constant.AuthenticationRole;
import com.banco.financeiro.dto.UsuarioPDTO;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.model.Usuario;
import com.banco.financeiro.repository.UsuarioRepository;
import com.banco.financeiro.service.AutenticacaoService;
import com.banco.financeiro.service.UsuarioService;
import com.banco.financeiro.service.processor.AutenticacaoProcessor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final AutenticacaoService autenticacaoService;
	private final AutenticacaoProcessor autenticacaoProcessor;

	@Override
	public void createUser(UsuarioPDTO usuarioPDTO) {
		this.autenticacaoProcessor.alreadyExists(usuarioPDTO.email());
		List<AuthenticationRole> roles = List.of(AuthenticationRole.ROLE_COMMON);
		Autenticacao autenticacao = this.autenticacaoService.include(usuarioPDTO, roles);
		Usuario usuario = Usuario.builder().nome(usuarioPDTO.nome()).autenticacao(autenticacao)
				.dataNascimento(usuarioPDTO.dataNascimento()).build();
		this.usuarioRepository.save(usuario);
	}

}
