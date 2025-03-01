package com.banco.financeiro.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioPDTO(@NotBlank(message = "O campo 'Nome' não pode ser vazio ou nulo.") String nome,
		@NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate dataNascimento,
		@NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,
		@NotBlank(message = "Senha é obrigatório") String password) {

}
