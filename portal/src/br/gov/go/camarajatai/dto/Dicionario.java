package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the dicionario database table.
 * 
 */
@Entity
public class Dicionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DICIONARIO_ID_GENERATOR", sequenceName="DICIONARIO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DICIONARIO_ID_GENERATOR")
	private Long id;

	private String palavra;

	//bi-directional many-to-one association to PalavraConteudo
	@OneToMany(mappedBy="dicionario")
	private Set<PalavraConteudo> palavraConteudos;

    public Dicionario() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPalavra() {
		return this.palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public Set<PalavraConteudo> getPalavraConteudos() {
		return this.palavraConteudos;
	}

	public void setPalavraConteudos(Set<PalavraConteudo> palavraConteudos) {
		this.palavraConteudos = palavraConteudos;
	}
	
}