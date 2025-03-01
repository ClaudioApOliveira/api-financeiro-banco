package com.banco.financeiro.dto.token;

import com.banco.financeiro.dto.UsuarioRPDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TokenFRDTO(@NotBlank(message = "O campo 'Token' é obrigatório") String token,
                         @NotNull
                         UsuarioRPDTO user) {

}
