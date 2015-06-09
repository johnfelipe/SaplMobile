package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the logger database table.
 * 
 */
@Entity
public class Logger implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LOGGER_ID_GENERATOR", sequenceName="LOGGER_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LOGGER_ID_GENERATOR")
	private Long id;

	private Timestamp data;

	private String descricao;

	private String host;

	@Column(name="id_registro")
	private Long idRegistro;

	@Column(name="id_serial")
	private Long idSerial;

	//bi-directional many-to-one association to Permissao
    @ManyToOne
	@JoinColumn(name="id_permissao")
	private Permissao permissao;

	//bi-directional many-to-one association to Servico
    @ManyToOne
	@JoinColumn(name="id_servico")
	private Servico servico;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="id_usuario")
	private Usuario usuario;

    public Logger() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getData() {
		return this.data;
	}

	public void setData(Timestamp data) {
		this.data = data;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getHost() {
		return this.host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Long getIdRegistro() {
		return this.idRegistro;
	}

	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}

	public Long getIdSerial() {
		return this.idSerial;
	}

	public void setIdSerial(Long idSerial) {
		this.idSerial = idSerial;
	}

	public Permissao getPermissao() {
		return this.permissao;
	}

	public void setPermissao(Permissao permissao) {
		this.permissao = permissao;
	}
	
	public Servico getServico() {
		return this.servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}