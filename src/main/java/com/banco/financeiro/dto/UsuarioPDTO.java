package com.banco.financeiro.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuarioPDTO(@NotBlank(message = "O campo 'Nome' não pode ser vazio ou nulo.") String nome,
                          @NotNull @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate dataNascimento,
                          @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,
                          @NotBlank(message = "Senha é obrigatório")
                          @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
                          String password) {

}
