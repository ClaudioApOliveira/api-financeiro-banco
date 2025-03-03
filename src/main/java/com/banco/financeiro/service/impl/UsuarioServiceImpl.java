package com.banco.financeiro.service.impl;

import com.banco.financeiro.constant.AuthenticationRole;
import com.banco.financeiro.constant.TipoTransacao;
import com.banco.financeiro.dto.*;
import com.banco.financeiro.exception.BusinessException;
import com.banco.financeiro.model.Autenticacao;
import com.banco.financeiro.model.Usuario;
import com.banco.financeiro.repository.UsuarioRepository;
import com.banco.financeiro.service.AutenticacaoService;
import com.banco.financeiro.service.TransacaoService;
import com.banco.financeiro.service.UsuarioService;
import com.banco.financeiro.service.processor.AutenticacaoProcessor;
import com.banco.financeiro.service.processor.SecurityProcessor;
import com.banco.financeiro.utils.Encryptor;
import com.banco.financeiro.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final AutenticacaoService autenticacaoService;
    private final AutenticacaoProcessor autenticacaoProcessor;
    private final TransacaoService transacaoService;
    private final SecurityProcessor securityProcessor;

    @Override
    public void createUser(UsuarioPDTO usuarioPDTO) {
        log.info("Criando usuário: {}", usuarioPDTO);
        this.autenticacaoProcessor.alreadyExists(usuarioPDTO.email());
        List<AuthenticationRole> roles = List.of(AuthenticationRole.ROLE_COMMON);
        Autenticacao autenticacao = this.autenticacaoService.include(usuarioPDTO, roles);
        Usuario usuario = Usuario.builder().nome(usuarioPDTO.nome()).autenticacao(autenticacao)
                .dataNascimento(usuarioPDTO.dataNascimento()).saldo(BigDecimal.ZERO)
                .numeroConta(UUID.randomUUID().toString()).build();
        this.usuarioRepository.save(usuario);
        log.info("Usuário criado com sucesso: {}", usuario);
    }

    @Override
    public SaldoContaDTO findSaldo(Authentication authentication) {
        log.info("Buscando saldo da conta do usuário: {}", authentication.getName());
        Autenticacao autenticacao = this.autenticacaoService.findByEmail(authentication.getName());
        Usuario usuario = autenticacao.getUsuario();
        log.info("Saldo da conta do usuário: {}", usuario.getSaldo());
        return new SaldoContaDTO(usuario.getNumeroConta(), usuario.getSaldo());
    }

    @Override
    public void deposito(Authentication authentication, BigDecimal valor) {
        log.info("Realizando depósito na conta do usuário: {}", authentication.getName());
        Autenticacao autenticacao = this.autenticacaoService.findByEmail(authentication.getName());
        Usuario usuario = autenticacao.getUsuario();
        usuario.setSaldo(usuario.getSaldo().add(valor));
        this.usuarioRepository.save(usuario);
        this.transacaoService.createTransacao(valor, TipoTransacao.DEPOSITO, usuario);
        log.info("Depósito realizado com sucesso na conta do usuário: {}", usuario.getNumeroConta());
    }

    @Override
    public void transferencia(Authentication authentication, TransferenciaDTO transferenciaDTO) {
        log.info("Realizando transferência na conta do usuário: {}", authentication.getName());
        Autenticacao autenticacao = this.autenticacaoService.findByEmail(authentication.getName());
        Usuario usuario = autenticacao.getUsuario();
        if (usuario.getSaldo().compareTo(transferenciaDTO.valor()) < 0) {
            log.error(MessageUtils.getMensagemValidacao("balance.insufficient", usuario.getSaldo()));
            throw new BusinessException(MessageUtils.getMensagemValidacao("balance.insufficient", usuario.getSaldo()));
        }
        if (transferenciaDTO.numero().equals(usuario.getNumeroConta())) {
            log.error(MessageUtils.getMensagemValidacao("user.cannot.transfer.to.the.same.account", usuario.getNumeroConta()));
            throw new BusinessException(MessageUtils.getMensagemValidacao("user.cannot.transfer.to.the.same.account", usuario.getNumeroConta()));
        }
        Usuario usuarioDestino = this.usuarioRepository.findByNumeroConta(transferenciaDTO.numero())
                .orElseThrow(() -> new BusinessException(MessageUtils
                        .getMensagemValidacao("usuario.by.conta.not.found", transferenciaDTO.numero())));
        usuario.setSaldo(usuario.getSaldo().subtract(transferenciaDTO.valor()));
        usuarioDestino.setSaldo(usuarioDestino.getSaldo().add(transferenciaDTO.valor()));
        this.usuarioRepository.save(usuario);
        this.usuarioRepository.save(usuarioDestino);
        this.transacaoService.createTransacao(transferenciaDTO.valor(), TipoTransacao.TRANSFERENCIA, usuario);
        this.transacaoService.createTransacao(transferenciaDTO.valor(), TipoTransacao.DEPOSITO, usuarioDestino);
        log.info("Transferência realizada com sucesso na conta do usuário: {}", usuario.getNumeroConta());
    }

    @Override
    public List<ExtratoDTO> extrato(Authentication authentication) {
        log.info("Buscando extrato da conta do usuário: {}", authentication.getName());
        Usuario usuario = this.autenticacaoService.findByEmail(authentication.getName()).getUsuario();
        return this.transacaoService.findByUsuario(usuario).stream()
                .map(transacao -> new ExtratoDTO(transacao.getUsuario().getId(), transacao.getValor(),
                        transacao.getTipoTransacao(), transacao.getDataTransacao())).toList();
    }

    @Override
    public void atualizarSenha(Authentication authentication, AtualizarSenhaDTO atualizarSenhaDTO) {
        Autenticacao autenticacao = this.autenticacaoService.findByEmail(authentication.getName());
        if (Boolean.FALSE.equals(Encryptor.match(atualizarSenhaDTO.passwordOld(), autenticacao.getPassword()))) {
            throw new BusinessException(MessageUtils.getMensagemValidacao("password.not.compatible"));
        }

        autenticacao.setPassword(Encryptor.encode(atualizarSenhaDTO.password()));
        this.autenticacaoService.saveAutenticacao(autenticacao);
    }

}
