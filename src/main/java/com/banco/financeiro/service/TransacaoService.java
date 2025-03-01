package com.banco.financeiro.service;

import com.banco.financeiro.constant.TipoTransacao;
import com.banco.financeiro.dto.ExtratoDTO;
import com.banco.financeiro.model.Transacao;
import com.banco.financeiro.model.Usuario;

import java.math.BigDecimal;
import java.util.List;

public interface TransacaoService {
    void createTransacao(BigDecimal valor, TipoTransacao tipoTransacao, Usuario usuario);

    List<Transacao> findByUsuario(Usuario usuario);
}
