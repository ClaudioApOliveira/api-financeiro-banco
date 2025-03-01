package com.banco.financeiro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sfb_usuario")
public class Usuario implements Serializable {

    private static final long serialVersionUID = -5957438529861564439L;

    @Id
    @Column(name = "cd_usuario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nm_usuario")
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 1, max = 100, message = "O campo 'Nome' deve conter entre 1 e 100 caracteres")
    private String nome;

    @Column(name = "dt_nascimento")
    @NotNull(message = "O campo ´Data Nascimento' é obrigatório")
    private LocalDate dataNascimento;

    @ToString.Exclude
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Autenticacao autenticacao;

    @Column(name = "nr_conta", unique = true, nullable = false)
    private String numeroConta;

    @Column(name = "vl_saldo", precision = 19, scale = 2)
    private BigDecimal saldo;

}
