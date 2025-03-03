package com.banco.financeiro.dto;

import jakarta.validation.constraints.Size;

public record AtualizarSenhaDTO(
        @Size(min = 6, max = 20, message = "A nova senha deve ter entre 6 e 20 caracteres")
        String password,
        @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
        String passwordOld) {
}
