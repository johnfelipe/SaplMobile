package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the assunto database table.
 * 
 */
@Entity
public class Assunto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ASSUNTO_ID_GENERATOR", sequenceName="ASSUNTO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ASSUNTO_ID_GENERATOR")
	private Long id;

	private String descricao;
	
	//bi-directional many-to-one association to Conteudo
	@OneToMany(mappedBy="assunto")
	private Set<Conteudo> conteudos;

    public Assunto() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Set<Conteudo> getConteudos() {
		return this.conteudos;
	}

	public void setConteudos(Set<Conteudo> conteudos) {
		this.conteudos = conteudos;
	}
	
}