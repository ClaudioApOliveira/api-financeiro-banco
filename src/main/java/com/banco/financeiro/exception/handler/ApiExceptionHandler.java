package com.banco.financeiro.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler {

    private final HttpServletRequest request;

    private static final String APPLICATION_NAME = "Api Financeiro Banco";
    private static final String ERROR_BAD_REQUEST = "Requisição Inválida.";
    private static final String ERROR_INTERNAL_SERVER = "Erro ao processar requisição.";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> constraintValidacaoHandler(ConstraintViolationException exception) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.nameUUIDFromBytes(APPLICATION_NAME.getBytes(StandardCharsets.UTF_8))
                        .toString())
                .titulo(ERROR_BAD_REQUEST)
                .codigo(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .status(HttpStatus.BAD_REQUEST.name())
                .detalhe(exception.getMessage())
                .metadado(ApiErrorResponse.Metadado.builder()
                        .nmServico(APPLICATION_NAME)
                        .idTransacao(UUID.nameUUIDFromBytes(APPLICATION_NAME.concat(LocalDateTime.now().toString())
                                .getBytes(StandardCharsets.UTF_8)).toString())
                        .timestamp(LocalDateTime.now().toString())
                        .url(request.getRequestURL().toString())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        // Extrair as mensagens de erro dos campos (se houver)
        StringBuilder detalhesErro = new StringBuilder();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> detalhesErro
                .append(fieldError.getDefaultMessage())
                .append("."));

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.nameUUIDFromBytes(APPLICATION_NAME.getBytes(StandardCharsets.UTF_8))
                        .toString())
                .titulo(ERROR_BAD_REQUEST)
                .codigo(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .status(HttpStatus.BAD_REQUEST.name())
                .detalhe(detalhesErro.toString())
                .metadado(ApiErrorResponse.Metadado.builder()
                        .nmServico(APPLICATION_NAME)
                        .idTransacao(UUID.nameUUIDFromBytes(APPLICATION_NAME.concat(LocalDateTime.now().toString())
                                .getBytes(StandardCharsets.UTF_8)).toString())
                        .timestamp(LocalDateTime.now().toString())
                        .url(request.getRequestURL().toString())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> genericExceptionHandler(Exception exception) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .id(UUID.nameUUIDFromBytes(APPLICATION_NAME.getBytes(StandardCharsets.UTF_8))
                        .toString())
                .titulo(ERROR_INTERNAL_SERVER)
                .codigo(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .detalhe(exception.getMessage())
                .metadado(ApiErrorResponse.Metadado.builder()
                        .nmServico(APPLICATION_NAME)
                        .idTransacao(UUID.nameUUIDFromBytes(APPLICATION_NAME.concat(LocalDateTime.now().toString())
                                .getBytes(StandardCharsets.UTF_8)).toString())
                        .timestamp(LocalDateTime.now().toString())
                        .url(request.getRequestURL().toString())
                        .build())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse);
    }
}
