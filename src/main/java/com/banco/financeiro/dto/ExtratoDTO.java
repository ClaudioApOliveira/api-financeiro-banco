package com.banco.financeiro.dto;

import com.banco.financeiro.constant.TipoTransacao;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExtratoDTO(Long id, BigDecimal valor, TipoTransacao tipoTransacao, LocalDateTime data) {
}
