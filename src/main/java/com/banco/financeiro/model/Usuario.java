package com.banco.financeiro.model;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sfb_usuario")
public class Usuario implements Serializable{

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
	
}
