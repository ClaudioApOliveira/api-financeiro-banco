package com.banco.financeiro.dto.token;

import java.util.List;

import com.banco.financeiro.constant.AuthenticationRole;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TokenFRDTO(@NotBlank(message = "O campo 'Token' é obrigatório") String token,
		@NotNull(message = "O campo 'Cargos' é obrigatório") @NotEmpty(message = "O usuário deve possuir um ou mais cargos") List<AuthenticationRole> roles) {

}
