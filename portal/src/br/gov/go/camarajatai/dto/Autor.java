package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the autor database table.
 * 
 */
@Entity
public class Autor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUTOR_ID_GENERATOR", sequenceName="AUTOR_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTOR_ID_GENERATOR")
	private Long id;

	private String nome;

	//bi-directional many-to-one association to AutorConteudo
	@OneToMany(mappedBy="autor")
	private Set<AutorConteudo> autorConteudos;

    public Autor() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<AutorConteudo> getAutorConteudos() {
		return this.autorConteudos;
	}

	public void setAutorConteudos(Set<AutorConteudo> autorConteudos) {
		this.autorConteudos = autorConteudos;
	}
	
}