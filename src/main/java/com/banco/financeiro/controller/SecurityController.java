package com.banco.financeiro.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banco.financeiro.dto.LoginDTO;
import com.banco.financeiro.dto.token.TokenFRDTO;
import com.banco.financeiro.service.SecurityService;
import com.banco.financeiro.utils.Response;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

	private final SecurityService securityService;

	@PostMapping("/login")
	public ResponseEntity<Response<TokenFRDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
		Response<TokenFRDTO> response = new Response<>();
		TokenFRDTO tokenFRDTO = this.securityService.login(loginDTO);
		response.setData(tokenFRDTO);
		return ResponseEntity.ok(response);
	}

}
