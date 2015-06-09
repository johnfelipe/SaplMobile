package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the autor_conteudo database table.
 * 
 */
@Entity
@Table(name="autor_conteudo")
public class AutorConteudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="AUTOR_CONTEUDO_ID_GENERATOR", sequenceName="AUTOR_CONTEUDO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="AUTOR_CONTEUDO_ID_GENERATOR")
	private Long id;

	private Boolean colaborador;

	//bi-directional many-to-one association to Autor
    @ManyToOne
	@JoinColumn(name="id_autor")
	private Autor autor;

	//bi-directional many-to-one association to Conteudo
    @ManyToOne
	@JoinColumn(name="id_conteudo")
	private Conteudo conteudo;

    public AutorConteudo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getColaborador() {
		return this.colaborador;
	}

	public void setColaborador(Boolean colaborador) {
		this.colaborador = colaborador;
	}

	public Autor getAutor() {
		return this.autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	public Conteudo getConteudo() {
		return this.conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}
	
}