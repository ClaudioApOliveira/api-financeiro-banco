package com.banco.financeiro.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TransferenciaDTO(
        @NotBlank(message = "Número da conta é obrigatório")
        String numero,
        @NotNull(message = "Valor é obrigatório")
        BigDecimal valor) {
}
