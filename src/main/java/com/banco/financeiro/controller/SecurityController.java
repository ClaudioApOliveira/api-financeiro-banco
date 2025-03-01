package com.banco.financeiro.controller;

import com.banco.financeiro.dto.LoginDTO;
import com.banco.financeiro.dto.token.TokenFRDTO;
import com.banco.financeiro.service.SecurityService;
import com.banco.financeiro.utils.Response;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService securityService;

    @PostMapping("/login")
    public ResponseEntity<Response<TokenFRDTO>> login(@Valid @RequestBody LoginDTO loginDTO) {
        log.info("Realizando login do usuário: {}", loginDTO.email());
        Response<TokenFRDTO> response = new Response<>();
        TokenFRDTO tokenFRDTO = this.securityService.login(loginDTO);
        response.setData(tokenFRDTO);
        log.info("Login realizado com sucesso");
        return ResponseEntity.ok(response);
    }

}
