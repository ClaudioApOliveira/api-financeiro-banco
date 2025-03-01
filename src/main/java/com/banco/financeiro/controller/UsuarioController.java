package com.banco.financeiro.controller;

import com.banco.financeiro.dto.ExtratoDTO;
import com.banco.financeiro.dto.SaldoContaDTO;
import com.banco.financeiro.dto.TransferenciaDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.banco.financeiro.dto.UsuarioPDTO;
import com.banco.financeiro.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UsuarioPDTO usuarioPDTO) {
        this.usuarioService.createUser(usuarioPDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/saldo")
    public ResponseEntity<SaldoContaDTO> findSaldo(Authentication authentication) {
        return ResponseEntity.ok(this.usuarioService.findSaldo(authentication));
    }

    @PostMapping("/deposito")
    public ResponseEntity<Void> deposito(Authentication authentication, @RequestBody @NotNull BigDecimal valor) {
        this.usuarioService.deposito(authentication, valor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/transferencia")
    public ResponseEntity<Void> transferencia(Authentication authentication, @RequestBody @Valid TransferenciaDTO transferenciaDTO) {
        this.usuarioService.transferencia(authentication, transferenciaDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/extrato")
    public ResponseEntity<List<ExtratoDTO>> extrato(Authentication authentication) {
        return ResponseEntity.ok(this.usuarioService.extrato(authentication));
    }

}
