package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the permissao database table.
 * 
 */
@Entity
public class Permissao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERMISSAO_ID_GENERATOR", sequenceName="PERMISSAO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERMISSAO_ID_GENERATOR")
	private Long id;

	@Column(name="data_bloqueio")
	private Timestamp dataBloqueio;

	@Column(name="data_concessao")
	private Timestamp dataConcessao;

	private Boolean status;

	//bi-directional many-to-one association to Logger
	@OneToMany(mappedBy="permissao")
	private Set<Logger> loggers;

	//bi-directional many-to-one association to Servico
    @ManyToOne
	@JoinColumn(name="id_servico")
	private Servico servico;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="id_usuario_bloqueador")
	private Usuario usuario1;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario2;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="id_usuario_update")
	private Usuario usuario3;

    public Permissao() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDataBloqueio() {
		return this.dataBloqueio;
	}

	public void setDataBloqueio(Timestamp dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	public Timestamp getDataConcessao() {
		return this.dataConcessao;
	}

	public void setDataConcessao(Timestamp dataConcessao) {
		this.dataConcessao = dataConcessao;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Set<Logger> getLoggers() {
		return this.loggers;
	}

	public void setLoggers(Set<Logger> loggers) {
		this.loggers = loggers;
	}
	
	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public Usuario getUsuario1() {
		return this.usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}
	
	public Usuario getUsuario2() {
		return this.usuario2;
	}

	public void setUsuario2(Usuario usuario2) {
		this.usuario2 = usuario2;
	}
	
	public Usuario getUsuario3() {
		return this.usuario3;
	}

	public void setUsuario3(Usuario usuario3) {
		this.usuario3 = usuario3;
	}
	
}