package com.banco.financeiro.model;

import com.banco.financeiro.constant.TipoTransacao;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sfb_transacao")
public class Transacao {

    @Id
    @Column(name = "cd_transacao")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vl_transacao", nullable = false)
    private BigDecimal valor;

    @ToString.Exclude
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_transacao", nullable = false)
    private TipoTransacao tipoTransacao;

    @Builder.Default
    @Column(name = "dt_transacao", nullable = false)
    private LocalDateTime dataTransacao = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "cd_usuario")
    private Usuario usuario;
}
