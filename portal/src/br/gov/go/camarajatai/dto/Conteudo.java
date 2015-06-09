package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the conteudo database table.
 * 
 */
@Entity
public class Conteudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTEUDO_ID_GENERATOR", sequenceName="CONTEUDO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTEUDO_ID_GENERATOR")
	private Long id;

	@Column(name="data_cadastro")
	private Timestamp dataCadastro;

	@Column(name="data_expiracao")
	private Timestamp dataExpiracao;

	@Column(name="data_publicacao")
	private Timestamp dataPublicacao;

	private String descricao;

	@Column(name="id_conteudo_pai")
	private Long idConteudoPai;

	private String numero;

	private Integer status;

	private String titulo;

	//bi-directional many-to-one association to AutorConteudo
	@OneToMany(mappedBy="conteudo")
	private Set<AutorConteudo> autorConteudos;

	//bi-directional many-to-one association to Assunto
    @ManyToOne
	@JoinColumn(name="id_assunto")
	private Assunto assunto;

	//bi-directional many-to-one association to GrupoConteudo
    @ManyToOne
	@JoinColumn(name="id_grupo_conteudo")
	private GrupoConteudo grupoConteudo;

	//bi-directional many-to-one association to Midia
    @ManyToOne
	@JoinColumn(name="id_midia_destaque")
	private Midia midia;

	//bi-directional many-to-one association to Usuario
    @ManyToOne
	@JoinColumn(name="id_user_dono")
	private Usuario usuario;

	//bi-directional many-to-one association to ConteudoDo
	@OneToMany(mappedBy="conteudo")
	private Set<ConteudoDo> conteudoDos;

	//bi-directional many-to-one association to ItemConteudo
	@OneToMany(mappedBy="conteudo")
	private Set<ItemConteudo> itemConteudos;

	//bi-directional many-to-one association to ItemLegislativo
	@OneToMany(mappedBy="conteudo1")
	private Set<ItemLegislativo> itemLegislativos1;

	//bi-directional many-to-one association to ItemLegislativo
	@OneToMany(mappedBy="conteudo2")
	private Set<ItemLegislativo> itemLegislativos2;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="conteudo")
	private Set<Usuario> usuarios;

    public Conteudo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Timestamp dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Timestamp getDataExpiracao() {
		return this.dataExpiracao;
	}

	public void setDataExpiracao(Timestamp dataExpiracao) {
		this.dataExpiracao = dataExpiracao;
	}

	public Timestamp getDataPublicacao() {
		return this.dataPublicacao;
	}

	public void setDataPublicacao(Timestamp dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdConteudoPai() {
		return this.idConteudoPai;
	}

	public void setIdConteudoPai(Long idConteudoPai) {
		this.idConteudoPai = idConteudoPai;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Set<AutorConteudo> getAutorConteudos() {
		return this.autorConteudos;
	}

	public void setAutorConteudos(Set<AutorConteudo> autorConteudos) {
		this.autorConteudos = autorConteudos;
	}
	
	public Assunto getAssunto() {
		return this.assunto;
	}

	public void setAssunto(Assunto assunto) {
		this.assunto = assunto;
	}
	
	public GrupoConteudo getGrupoConteudo() {
		return this.grupoConteudo;
	}

	public void setGrupoConteudo(GrupoConteudo grupoConteudo) {
		this.grupoConteudo = grupoConteudo;
	}
	
	public Midia getMidia() {
		return this.midia;
	}

	public void setMidia(Midia midia) {
		this.midia = midia;
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Set<ConteudoDo> getConteudoDos() {
		return this.conteudoDos;
	}

	public void setConteudoDos(Set<ConteudoDo> conteudoDos) {
		this.conteudoDos = conteudoDos;
	}
	
	public Set<ItemConteudo> getItemConteudos() {
		return this.itemConteudos;
	}

	public void setItemConteudos(Set<ItemConteudo> itemConteudos) {
		this.itemConteudos = itemConteudos;
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
	
	public Set<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
}