package com.banco.financeiro.dto;

import com.banco.financeiro.constant.AuthenticationRole;

import java.util.List;

public record UsuarioRPDTO(Long id, String email, List<AuthenticationRole> roles) {
}
