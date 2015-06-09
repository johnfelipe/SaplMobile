package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the servico database table.
 * 
 */
@Entity
public class Servico implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SERVICO_ID_GENERATOR", sequenceName="SERVICO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SERVICO_ID_GENERATOR")
	private Long id;

	private Boolean autenticavel;

	private String descricao;

	private Boolean status;

	private Boolean visivel;

	//bi-directional many-to-one association to Logger
	@OneToMany(mappedBy="servico")
	private Set<Logger> loggers;

	//bi-directional many-to-one association to Permissao
	@OneToMany(mappedBy="servico")
	private Set<Permissao> permissaos;

	//bi-directional many-to-one association to GrupoServico
    @ManyToOne
	@JoinColumn(name="id_grupo_servico")
	private GrupoServico grupoServico;

    public Servico() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAutenticavel() {
		return this.autenticavel;
	}

	public void setAutenticavel(Boolean autenticavel) {
		this.autenticavel = autenticavel;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getVisivel() {
		return this.visivel;
	}

	public void setVisivel(Boolean visivel) {
		this.visivel = visivel;
	}

	public Set<Logger> getLoggers() {
		return this.loggers;
	}

	public void setLoggers(Set<Logger> loggers) {
		this.loggers = loggers;
	}
	
	public Set<Permissao> getPermissaos() {
		return this.permissaos;
	}

	public void setPermissaos(Set<Permissao> permissaos) {
		this.permissaos = permissaos;
	}
	
	public GrupoServico getGrupoServico() {
		return this.grupoServico;
	}

	public void setGrupoServico(GrupoServico grupoServico) {
		this.grupoServico = grupoServico;
	}
	
}