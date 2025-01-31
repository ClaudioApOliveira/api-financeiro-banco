package com.banco.financeiro.service;

import java.util.List;

import com.banco.financeiro.constant.AuthenticationRole;
import com.banco.financeiro.dto.UsuarioPDTO;
import com.banco.financeiro.model.Autenticacao;

public interface AutenticacaoService {

	Autenticacao include(UsuarioPDTO usuarioPDTO, List<AuthenticationRole> roles);

}
