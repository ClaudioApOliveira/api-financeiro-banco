package com.banco.financeiro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginDTO(
		@NotNull(message = "O campo 'Email' é obrigatório") @Size(min = 6, max = 80, message = "O campo 'Email' deve conter entre 6 e 80 caracteres") @Email(message = "O campo 'Email' é inválido") String email,
		@Size(min = 8, max = 40, message = "O campo 'Senha' deve conter entre 8 a 40 caracteres") String password) {

}
