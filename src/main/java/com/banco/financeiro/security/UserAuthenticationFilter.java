package com.banco.financeiro.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.banco.financeiro.exception.BusinessException;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.repository.AutenticacaoRepository;
import com.banco.financeiro.utils.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenService jwtTokenService;
	private final AutenticacaoRepository autenticacaoRepository;
	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (checkIfEndpointIsNotPublic(request)) {
			String token = request.getHeader(this.jwtUtil.getHeader());

			if (Objects.nonNull(token)) {
				if (!token.startsWith(this.jwtUtil.getType())) {
					log.error("TokenInvalidTypeException - Token: {}", token);
					throw new BusinessException("Token invalido!");
				}

				token = token.substring(7);

				String subject = jwtTokenService.getSubjectFromToken(token);
				Autenticacao autenticacao = this.autenticacaoRepository.findByEmail(subject)
						.orElseThrow(() -> new BusinessException("Usuário não encontrado!"));
				UserDetailsImpl userDetails = new UserDetailsImpl(autenticacao);
				org.springframework.security.core.Authentication authentication = new UsernamePasswordAuthenticationToken(
						userDetails.getUsername(), null, userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				throw new BusinessException("O token está ausente.");
			}
		}
		filterChain.doFilter(request, response);
	}

	// Verifica se o endpoint requer autenticação antes de processar a requisição
	private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return !Arrays.asList(SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_NOT_REQUIRED).contains(requestURI);
	}
}
