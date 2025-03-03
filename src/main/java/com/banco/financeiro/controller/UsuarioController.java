package com.banco.financeiro.controller;

import com.banco.financeiro.dto.*;
import com.banco.financeiro.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UsuarioPDTO usuarioPDTO) {
        log.info("Criando usuário: {}", usuarioPDTO);
        this.usuarioService.createUser(usuarioPDTO);
        log.info("Usuário criado com sucesso");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/saldo")
    public ResponseEntity<SaldoContaDTO> findSaldo(Authentication authentication) {
        log.info("Buscando saldo do usuário: {}", authentication.getName());
        return ResponseEntity.ok(this.usuarioService.findSaldo(authentication));
    }

    @PostMapping("/deposito")
    public ResponseEntity<Void> deposito(Authentication authentication, @RequestBody @NotNull BigDecimal valor) {
        log.info("Realizando depósito de {} para o usuário: {}", valor, authentication.getName());
        this.usuarioService.deposito(authentication, valor);
        log.info("Depósito realizado com sucesso");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Void> transferencia(Authentication authentication, @RequestBody @Valid TransferenciaDTO transferenciaDTO) {
        log.info("Realizando transferência de {} para o usuário: {}", transferenciaDTO.valor(), authentication.getName());
        this.usuarioService.transferencia(authentication, transferenciaDTO);
        log.info("Transferência realizada com sucesso");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/extrato")
    public ResponseEntity<List<ExtratoDTO>> extrato(Authentication authentication) {
        log.info("Buscando extrato do usuário: {}", authentication.getName());
        return ResponseEntity.ok(this.usuarioService.extrato(authentication));
    }

    @PostMapping("/senha")
    public ResponseEntity<Void> transferencia(Authentication authentication, @RequestBody @Valid AtualizarSenhaDTO atualizarSenhaDTO) {
        log.info("Atualizando senha do usuário: {}", authentication.getName());
        this.usuarioService.atualizarSenha(authentication, atualizarSenhaDTO);
        log.info("Senha atualizada com sucesso");
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
