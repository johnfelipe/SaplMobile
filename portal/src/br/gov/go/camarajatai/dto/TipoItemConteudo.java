package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipo_item_conteudo database table.
 * 
 */
@Entity
@Table(name="tipo_item_conteudo")
public class TipoItemConteudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TIPO_ITEM_CONTEUDO_ID_GENERATOR", sequenceName="TIPO_ITEM_CONTEUDO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TIPO_ITEM_CONTEUDO_ID_GENERATOR")
	private Long id;

	private String descricao;

	private Boolean midia;

	private Boolean obrigatorio;

	private String sigla;

	//bi-directional many-to-one association to ItemConteudo
	@OneToMany(mappedBy="tipoItemConteudo")
	private Set<ItemConteudo> itemConteudos;

	//bi-directional many-to-one association to ItemLegislativo
	@OneToMany(mappedBy="tipoItemConteudo")
	private Set<ItemLegislativo> itemLegislativos;

	//bi-directional many-to-one association to GrupoConteudo
    @ManyToOne
	@JoinColumn(name="id_grupo_conteudo")
	private GrupoConteudo grupoConteudo;

    public TipoItemConteudo() {
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

	public Boolean getMidia() {
		return this.midia;
	}

	public void setMidia(Boolean midia) {
		this.midia = midia;
	}

	public Boolean getObrigatorio() {
		return this.obrigatorio;
	}

	public void setObrigatorio(Boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
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
	
	public GrupoConteudo getGrupoConteudo() {
		return this.grupoConteudo;
	}

	public void setGrupoConteudo(GrupoConteudo grupoConteudo) {
		this.grupoConteudo = grupoConteudo;
	}
	
}