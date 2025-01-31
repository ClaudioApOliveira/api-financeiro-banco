package com.banco.financeiro.model;

import java.io.Serializable;
import java.util.List;

import com.banco.financeiro.constant.AuthenticationRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "sfb_autenticacao")
public class Autenticacao implements Serializable {

	private static final long serialVersionUID = -1951813344163599206L;

	@Id
	@Column(name = "cd_autenticacao")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Email
	@Column(name = "nm_email", nullable = false, unique = true)
	@Size(min = 6, max = 80, message = "O campo 'E-mail' deve conter entre 6 e 80 caracteres")
	private String email;

	@ToString.Exclude
	@Column(name = "nm_password", nullable = false)
	private String password;

	@Column(name = "is_locked", nullable = false)
	private Boolean isLocked;

	@ToString.Exclude
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	@ElementCollection(targetClass = AuthenticationRole.class, fetch = FetchType.EAGER)
	@JoinTable(name = "sfb_role_autenticacao", joinColumns = @JoinColumn(name = "cd_autenticacao", referencedColumnName = "cd_autenticacao"))
	private List<AuthenticationRole> roles;

	@ToString.Exclude
	@OneToOne(cascade = CascadeType.ALL)
	@JoinTable(name = "sfb_usuario_autenticacao", joinColumns = {
			@JoinColumn(name = "cd_usuario", referencedColumnName = "cd_usuario") }, inverseJoinColumns = {
					@JoinColumn(name = "cd_autenticacao", referencedColumnName = "cd_autenticacao") })
	private Usuario usuario;
}
