package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the grupo_conteudo database table.
 * 
 */
@Entity
@Table(name="grupo_conteudo")
public class GrupoConteudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GRUPO_CONTEUDO_ID_GENERATOR", sequenceName="GRUPO_CONTEUDO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GRUPO_CONTEUDO_ID_GENERATOR")
	private Long id;

	private String coluna;

	private String nome;

	//bi-directional many-to-one association to Conteudo
	@OneToMany(mappedBy="grupoConteudo")
	private Set<Conteudo> conteudos;

	//bi-directional many-to-one association to TipoItemConteudo
	@OneToMany(mappedBy="grupoConteudo")
	private Set<TipoItemConteudo> tipoItemConteudos;

    public GrupoConteudo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getColuna() {
		return this.coluna;
	}

	public void setColuna(String coluna) {
		this.coluna = coluna;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Set<Conteudo> getConteudos() {
		return this.conteudos;
	}

	public void setConteudos(Set<Conteudo> conteudos) {
		this.conteudos = conteudos;
	}
	
	public Set<TipoItemConteudo> getTipoItemConteudos() {
		return this.tipoItemConteudos;
	}

	public void setTipoItemConteudos(Set<TipoItemConteudo> tipoItemConteudos) {
		this.tipoItemConteudos = tipoItemConteudos;
	}
	
}