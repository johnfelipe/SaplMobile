package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the diario_oficial database table.
 * 
 */
@Entity
@Table(name="diario_oficial")
public class DiarioOficial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIARIO_OFICIAL_ID_GENERATOR", sequenceName="DIARIO_OFICIAL_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIARIO_OFICIAL_ID_GENERATOR")
	private Long id;

	private Integer ano;

	@Column(name="data_geracao")
	private Timestamp dataGeracao;

	@Column(name="data_publicacao")
	private Timestamp dataPublicacao;

	private String numero;

	//bi-directional many-to-one association to ConteudoDo
	@OneToMany(mappedBy="diarioOficial")
	private Set<ConteudoDo> conteudoDos;

    public DiarioOficial() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAno() {
		return this.ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Timestamp getDataGeracao() {
		return this.dataGeracao;
	}

	public void setDataGeracao(Timestamp dataGeracao) {
		this.dataGeracao = dataGeracao;
	}

	public Timestamp getDataPublicacao() {
		return this.dataPublicacao;
	}

	public void setDataPublicacao(Timestamp dataPublicacao) {
		this.dataPublicacao = dataPublicacao;
	}

	public String getNumero() {
		return this.numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Set<ConteudoDo> getConteudoDos() {
		return this.conteudoDos;
	}

	public void setConteudoDos(Set<ConteudoDo> conteudoDos) {
		this.conteudoDos = conteudoDos;
	}
	
}