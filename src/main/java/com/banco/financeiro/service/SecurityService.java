package com.banco.financeiro.service;

import com.banco.financeiro.dto.LoginDTO;
import com.banco.financeiro.dto.token.TokenFRDTO;

public interface SecurityService {

	TokenFRDTO login(LoginDTO loginDTO);

}
