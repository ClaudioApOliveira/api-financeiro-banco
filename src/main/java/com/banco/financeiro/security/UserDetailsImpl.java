package com.banco.financeiro.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.banco.financeiro.model.Autenticacao;

import lombok.Getter;

@Getter
public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = -7781633277163986169L;

	private transient Autenticacao autenticacao;

	public UserDetailsImpl(Autenticacao autenticacao) {
		this.autenticacao = autenticacao;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return autenticacao.getPassword();
	}

	@Override
	public String getUsername() {
		return autenticacao.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
