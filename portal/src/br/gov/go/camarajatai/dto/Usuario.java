package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="USUARIO_ID_GENERATOR", sequenceName="USUARIO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USUARIO_ID_GENERATOR")
	private Long id;

	private String email;

	private String loginuser;

	private String nome;

	private String senha;

	//bi-directional many-to-one association to Conteudo
	@OneToMany(mappedBy="usuario")
	private Set<Conteudo> conteudos;

	//bi-directional many-to-one association to Logger
	@OneToMany(mappedBy="usuario")
	private Set<Logger> loggers;

	//bi-directional many-to-one association to Permissao
	@OneToMany(mappedBy="usuario1")
	private Set<Permissao> permissaos1;

	//bi-directional many-to-one association to Permissao
	@OneToMany(mappedBy="usuario2")
	private Set<Permissao> permissaos2;

	//bi-directional many-to-one association to Permissao
	@OneToMany(mappedBy="usuario3")
	private Set<Permissao> permissaos3;

	//bi-directional many-to-one association to Conteudo
    @ManyToOne
	@JoinColumn(name="id_conteudo")
	private Conteudo conteudo;

    public Usuario() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginuser() {
		return this.loginuser;
	}

	public void setLoginuser(String loginuser) {
		this.loginuser = loginuser;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Conteudo> getConteudos() {
		return this.conteudos;
	}

	public void setConteudos(Set<Conteudo> conteudos) {
		this.conteudos = conteudos;
	}
	
	public Set<Logger> getLoggers() {
		return this.loggers;
	}

	public void setLoggers(Set<Logger> loggers) {
		this.loggers = loggers;
	}
	
	public Set<Permissao> getPermissaos1() {
		return this.permissaos1;
	}

	public void setPermissaos1(Set<Permissao> permissaos1) {
		this.permissaos1 = permissaos1;
	}
	
	public Set<Permissao> getPermissaos2() {
		return this.permissaos2;
	}

	public void setPermissaos2(Set<Permissao> permissaos2) {
		this.permissaos2 = permissaos2;
	}
	
	public Set<Permissao> getPermissaos3() {
		return this.permissaos3;
	}

	public void setPermissaos3(Set<Permissao> permissaos3) {
		this.permissaos3 = permissaos3;
	}
	
	public Conteudo getConteudo() {
		return this.conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}
	
}