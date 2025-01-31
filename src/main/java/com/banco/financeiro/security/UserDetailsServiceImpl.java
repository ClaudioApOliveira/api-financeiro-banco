package com.banco.financeiro.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.banco.financeiro.exception.BusinessException;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.repository.AutenticacaoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final AutenticacaoRepository autenticacaoRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Autenticacao user = this.autenticacaoRepository.findByEmail(username)
				.orElseThrow(() -> new BusinessException("Usuário não encontrado."));
		return new UserDetailsImpl(user);
	}
}
