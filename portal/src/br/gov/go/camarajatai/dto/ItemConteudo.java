package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the item_conteudo database table.
 * 
 */
@Entity
@Table(name="item_conteudo")
public class ItemConteudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ITEM_CONTEUDO_ID_GENERATOR", sequenceName="ITEM_CONTEUDO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_CONTEUDO_ID_GENERATOR")
	private Long id;

	private String texto;

	//bi-directional many-to-one association to Conteudo
    @ManyToOne
	@JoinColumn(name="id_conteudo_dono")
	private Conteudo conteudo;

	//bi-directional many-to-one association to ItemConteudo
    @ManyToOne
	@JoinColumn(name="id_item_dono")
	private ItemConteudo itemConteudo;

	//bi-directional many-to-one association to ItemConteudo
	@OneToMany(mappedBy="itemConteudo")
	private Set<ItemConteudo> itemConteudos;

	//bi-directional many-to-one association to Midia
    @ManyToOne
	@JoinColumn(name="id_midia")
	private Midia midia;

	//bi-directional many-to-one association to TipoItemConteudo
    @ManyToOne
	@JoinColumn(name="id_tipo_item_conteudo")
	private TipoItemConteudo tipoItemConteudo;

	//bi-directional many-to-one association to ItemLegislativo
	@OneToMany(mappedBy="itemConteudo1")
	private Set<ItemLegislativo> itemLegislativos1;

	//bi-directional many-to-one association to ItemLegislativo
	@OneToMany(mappedBy="itemConteudo2")
	private Set<ItemLegislativo> itemLegislativos2;

	//bi-directional many-to-one association to PalavraConteudo
	@OneToMany(mappedBy="itemConteudo")
	private Set<PalavraConteudo> palavraConteudos;

    public ItemConteudo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Conteudo getConteudo() {
		return this.conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}
	
	public ItemConteudo getItemConteudo() {
		return this.itemConteudo;
	}

	public void setItemConteudo(ItemConteudo itemConteudo) {
		this.itemConteudo = itemConteudo;
	}
	
	public Set<ItemConteudo> getItemConteudos() {
		return this.itemConteudos;
	}

	public void setItemConteudos(Set<ItemConteudo> itemConteudos) {
		this.itemConteudos = itemConteudos;
	}
	
	public Midia getMidia() {
		return this.midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}
	
	public TipoItemConteudo getTipoItemConteudo() {
		return this.tipoItemConteudo;
	}

	public void setTipoItemConteudo(TipoItemConteudo tipoItemConteudo) {
		this.tipoItemConteudo = tipoItemConteudo;
	}
	
	public Set<ItemLegislativo> getItemLegislativos1() {
		return this.itemLegislativos1;
	}

	public void setItemLegislativos1(Set<ItemLegislativo> itemLegislativos1) {
		this.itemLegislativos1 = itemLegislativos1;
	}
	
	public Set<ItemLegislativo> getItemLegislativos2() {
		return this.itemLegislativos2;
	}

	public void setItemLegislativos2(Set<ItemLegislativo> itemLegislativos2) {
		this.itemLegislativos2 = itemLegislativos2;
	}
	
	public Set<PalavraConteudo> getPalavraConteudos() {
		return this.palavraConteudos;
	}

	public void setPalavraConteudos(Set<PalavraConteudo> palavraConteudos) {
		this.palavraConteudos = palavraConteudos;
	}
	
}