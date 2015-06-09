package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


/**
 * The persistent class for the midia database table.
 * 
 */
@Entity
public class Midia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="MIDIA_ID_GENERATOR", sequenceName="MIDIA_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="MIDIA_ID_GENERATOR")
	private Long id;

	private byte[] arquivo;

	@Column(name="id_serial")
	private Long idSerial;

	@Column(name="num_local")
	private BigDecimal numLocal;

	private String origem;

	private Boolean publico;

	//bi-directional many-to-one association to Conteudo
	@OneToMany(mappedBy="midia")
	private Set<Conteudo> conteudos;

	//bi-directional many-to-one association to ItemConteudo
	@OneToMany(mappedBy="midia")
	private Set<ItemConteudo> itemConteudos;

	//bi-directional many-to-one association to ItemLegislativo
	@OneToMany(mappedBy="midia")
	private Set<ItemLegislativo> itemLegislativos;

    public Midia() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getArquivo() {
		return this.arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public Long getIdSerial() {
		return this.idSerial;
	}

	public void setIdSerial(Long idSerial) {
		this.idSerial = idSerial;
	}

	public BigDecimal getNumLocal() {
		return this.numLocal;
	}

	public void setNumLocal(BigDecimal numLocal) {
		this.numLocal = numLocal;
	}

	public String getOrigem() {
		return this.origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Boolean getPublico() {
		return this.publico;
	}

	public void setPublico(Boolean publico) {
		this.publico = publico;
	}

	public Set<Conteudo> getConteudos() {
		return this.conteudos;
	}

	public void setConteudos(Set<Conteudo> conteudos) {
		this.conteudos = conteudos;
	}
	
	public Set<ItemConteudo> getItemConteudos() {
		return this.itemConteudos;
	}

	public void setItemConteudos(Set<ItemConteudo> itemConteudos) {
		this.itemConteudos = itemConteudos;
	}
	
	public Set<ItemLegislativo> getItemLegislativos() {
		return this.itemLegislativos;
	}

	public void setItemLegislativos(Set<ItemLegislativo> itemLegislativos) {
		this.itemLegislativos = itemLegislativos;
	}
	
}