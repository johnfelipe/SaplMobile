package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the grupo_servico database table.
 * 
 */
@Entity
@Table(name="grupo_servico")
public class GrupoServico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="GRUPO_SERVICO_ID_GENERATOR", sequenceName="GRUPO_SERVICO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="GRUPO_SERVICO_ID_GENERATOR")
	private Long id;

	private String descricao;

	//bi-directional many-to-one association to Servico
	@OneToMany(mappedBy="grupoServico")
	private Set<Servico> servicos;

    public GrupoServico() {
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

	public Set<Servico> getServicos() {
		return this.servicos;
	}

	public void setServicos(Set<Servico> servicos) {
		this.servicos = servicos;
	}
	
}