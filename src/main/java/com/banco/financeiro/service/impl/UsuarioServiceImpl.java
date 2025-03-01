package com.banco.financeiro.service.impl;

import com.banco.financeiro.constant.AuthenticationRole;
import com.banco.financeiro.constant.TipoTransacao;
import com.banco.financeiro.dto.ExtratoDTO;
import com.banco.financeiro.dto.SaldoContaDTO;
import com.banco.financeiro.dto.TransferenciaDTO;
import com.banco.financeiro.dto.UsuarioPDTO;
import com.banco.financeiro.exception.BusinessException;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.model.Usuario;
import com.banco.financeiro.repository.UsuarioRepository;
import com.banco.financeiro.service.AutenticacaoService;
import com.banco.financeiro.service.TransacaoService;
import com.banco.financeiro.service.UsuarioService;
import com.banco.financeiro.service.processor.AutenticacaoProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AutenticacaoService autenticacaoService;
    private final AutenticacaoProcessor autenticacaoProcessor;
    private final TransacaoService transacaoService;

    @Override
    public void createUser(UsuarioPDTO usuarioPDTO) {
        this.autenticacaoProcessor.alreadyExists(usuarioPDTO.email());
        List<AuthenticationRole> roles = List.of(AuthenticationRole.ROLE_COMMON);
        Autenticacao autenticacao = this.autenticacaoService.include(usuarioPDTO, roles);
        Usuario usuario = Usuario.builder().nome(usuarioPDTO.nome()).autenticacao(autenticacao)
                .dataNascimento(usuarioPDTO.dataNascimento()).saldo(BigDecimal.ZERO)
                .numeroConta(UUID.randomUUID().toString()).build();
        this.usuarioRepository.save(usuario);
    }

    @Override
    public SaldoContaDTO findSaldo(Authentication authentication) {
        Autenticacao autenticacao = this.autenticacaoService.findByEmail(authentication.getName());
        Usuario usuario = autenticacao.getUsuario();
        return new SaldoContaDTO(usuario.getNumeroConta(), usuario.getSaldo());
    }

    @Override
    public void deposito(Authentication authentication, BigDecimal valor) {
        Autenticacao autenticacao = this.autenticacaoService.findByEmail(authentication.getName());
        Usuario usuario = autenticacao.getUsuario();
        usuario.setSaldo(usuario.getSaldo().add(valor));
        this.usuarioRepository.save(usuario);
        this.transacaoService.createTransacao(valor, TipoTransacao.DEPOSITO, usuario);
    }

    @Override
    public void transferencia(Authentication authentication, TransferenciaDTO transferenciaDTO) {
        Autenticacao autenticacao = this.autenticacaoService.findByEmail(authentication.getName());
        Usuario usuario = autenticacao.getUsuario();
        if (usuario.getSaldo().compareTo(transferenciaDTO.valor()) < 0) {
            throw new BusinessException("Saldo insuficiente");
        }
        if (transferenciaDTO.numero().equals(usuario.getNumeroConta())) {
            throw new BusinessException("Não é possível transferir para a mesma conta");
        }
        Usuario usuarioDestino = this.usuarioRepository.findByNumeroConta(transferenciaDTO.numero())
                .orElseThrow(() -> new BusinessException("Conta destino não encontrada"));
        usuario.setSaldo(usuario.getSaldo().subtract(transferenciaDTO.valor()));
        usuarioDestino.setSaldo(usuarioDestino.getSaldo().add(transferenciaDTO.valor()));
        this.usuarioRepository.save(usuario);
        this.usuarioRepository.save(usuarioDestino);
        this.transacaoService.createTransacao(transferenciaDTO.valor(), TipoTransacao.TRANSFERENCIA, usuario);
        this.transacaoService.createTransacao(transferenciaDTO.valor(), TipoTransacao.DEPOSITO, usuarioDestino);
    }

    @Override
    public List<ExtratoDTO> extrato(Authentication authentication) {
        Usuario usuario = this.autenticacaoService.findByEmail(authentication.getName()).getUsuario();
        return this.transacaoService.findByUsuario(usuario).stream()
                .map(transacao -> new ExtratoDTO(transacao.getUsuario().getId(), transacao.getValor(),
                        transacao.getTipoTransacao(), transacao.getDataTransacao())).toList();
    }

}
