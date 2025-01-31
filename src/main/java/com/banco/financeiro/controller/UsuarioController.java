package com.banco.financeiro.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.financeiro.dto.UsuarioPDTO;
import com.banco.financeiro.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

}
