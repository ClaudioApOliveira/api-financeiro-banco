package com.banco.financeiro.service.impl;

import com.banco.financeiro.constant.TipoTransacao;
import com.banco.financeiro.dto.ExtratoDTO;
import com.banco.financeiro.model.Transacao;
import com.banco.financeiro.model.Usuario;
import com.banco.financeiro.repository.TransacaoRepository;
import com.banco.financeiro.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final TransacaoRepository transacaoRepository;

    @Override
    public void createTransacao(BigDecimal valor, TipoTransacao tipoTransacao, Usuario usuario) {
        Transacao transacao = Transacao.builder().valor(valor)
                .tipoTransacao(tipoTransacao).usuario(usuario)
                .build();
        this.transacaoRepository.save(transacao);
    }

    @Override
    public List<Transacao> findByUsuario(Usuario usuario) {
        return this.transacaoRepository.findByUsuarioId(usuario.getId());
    }
}
