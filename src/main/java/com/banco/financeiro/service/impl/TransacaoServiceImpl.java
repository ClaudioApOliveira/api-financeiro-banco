package com.banco.financeiro.service.impl;

import com.banco.financeiro.constant.TipoTransacao;
import com.banco.financeiro.model.Transacao;
import com.banco.financeiro.model.Usuario;
import com.banco.financeiro.repository.TransacaoRepository;
import com.banco.financeiro.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;

    @Override
    public void createTransacao(BigDecimal valor, TipoTransacao tipoTransacao, Usuario usuario) {
        log.info("Criando transação para o usuário: {}", usuario.getNome());;
        Transacao transacao = Transacao.builder().valor(valor)
                .tipoTransacao(tipoTransacao).usuario(usuario)
                .build();
        this.transacaoRepository.save(transacao);
        log.info("Transação criada com sucesso!");
    }

    @Override
    public List<Transacao> findByUsuario(Usuario usuario) {
        log.info("Buscando transações do usuário: {}", usuario.getNome());
        return this.transacaoRepository.findByUsuarioId(usuario.getId());
    }
}
