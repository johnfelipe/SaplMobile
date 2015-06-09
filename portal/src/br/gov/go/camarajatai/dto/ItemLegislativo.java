package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the item_legislativo database table.
 * 
 */
@Entity
@Table(name="item_legislativo")
public class ItemLegislativo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ITEM_LEGISLATIVO_ID_GENERATOR", sequenceName="ITEM_LEGISLATIVO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ITEM_LEGISLATIVO_ID_GENERATOR")
	private Long id;

	private Boolean alterado;

	private Boolean incluido;

	@Column(name="link_alterador")
	private String linkAlterador;

	private String nome;

	private Boolean revogado;

	private String texto;

	//bi-directional many-to-one association to Conteudo
    @ManyToOne
	@JoinColumn(name="id_cont_alterador")
	private Conteudo conteudo1;

	//bi-directional many-to-one association to Conteudo
    @ManyToOne
	@JoinColumn(name="id_conteudo_dono")
	private Conteudo conteudo2;

	//bi-directional many-to-one association to ItemConteudo
    @ManyToOne
	@JoinColumn(name="id_item_alterador")
	private ItemConteudo itemConteudo1;

	//bi-directional many-to-one association to ItemConteudo
    @ManyToOne
	@JoinColumn(name="id_item_dono")
	private ItemConteudo itemConteudo2;

	//bi-directional many-to-one association to Midia
    @ManyToOne
	@JoinColumn(name="id_midia")
	private Midia midia;

	//bi-directional many-to-one association to TipoItemConteudo
    @ManyToOne
	@JoinColumn(name="id_tipo_item_conteudo")
	private TipoItemConteudo tipoItemConteudo;

    public ItemLegislativo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAlterado() {
		return this.alterado;
	}

	public void setAlterado(Boolean alterado) {
		this.alterado = alterado;
	}

	public Boolean getIncluido() {
		return this.incluido;
	}

	public void setIncluido(Boolean incluido) {
		this.incluido = incluido;
	}

	public String getLinkAlterador() {
		return this.linkAlterador;
	}

	public void setLinkAlterador(String linkAlterador) {
		this.linkAlterador = linkAlterador;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Boolean getRevogado() {
		return this.revogado;
	}

	public void setRevogado(Boolean revogado) {
		this.revogado = revogado;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Conteudo getConteudo1() {
		return this.conteudo1;
	}

	public void setConteudo1(Conteudo conteudo1) {
		this.conteudo1 = conteudo1;
	}
	
	public Conteudo getConteudo2() {
		return this.conteudo2;
	}

	public void setConteudo2(Conteudo conteudo2) {
		this.conteudo2 = conteudo2;
	}
	
	public ItemConteudo getItemConteudo1() {
		return this.itemConteudo1;
	}

	public void setItemConteudo1(ItemConteudo itemConteudo1) {
		this.itemConteudo1 = itemConteudo1;
	}
	
	public ItemConteudo getItemConteudo2() {
		return this.itemConteudo2;
	}

	public void setItemConteudo2(ItemConteudo itemConteudo2) {
		this.itemConteudo2 = itemConteudo2;
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
	
}