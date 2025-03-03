package com.banco.financeiro.service;

import com.banco.financeiro.dto.*;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.List;

public interface UsuarioService {

    void createUser(UsuarioPDTO usuarioPDTO);

    SaldoContaDTO findSaldo(Authentication authentication);

    void deposito(Authentication authentication, BigDecimal valor);

    void transferencia(Authentication authentication, @Valid TransferenciaDTO transferenciaDTO);

    List<ExtratoDTO> extrato(Authentication authentication);

    void atualizarSenha(Authentication authentication, AtualizarSenhaDTO atualizarSenhaDTO);
}
