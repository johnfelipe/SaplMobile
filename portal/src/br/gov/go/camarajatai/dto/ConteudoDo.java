package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the conteudo_do database table.
 * 
 */
@Entity
@Table(name="conteudo_do")
public class ConteudoDo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CONTEUDO_DO_ID_GENERATOR", sequenceName="CONTEUDO_DO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CONTEUDO_DO_ID_GENERATOR")
	private Long id;

	//bi-directional many-to-one association to Conteudo
    @ManyToOne
	@JoinColumn(name="id_conteudo")
	private Conteudo conteudo;

	//bi-directional many-to-one association to DiarioOficial
    @ManyToOne
	@JoinColumn(name="id_diario_oficial")
	private DiarioOficial diarioOficial;

    public ConteudoDo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Conteudo getConteudo() {
		return this.conteudo;
	}

	public void setConteudo(Conteudo conteudo) {
		this.conteudo = conteudo;
	}
	
	public DiarioOficial getDiarioOficial() {
		return this.diarioOficial;
	}

	public void setDiarioOficial(DiarioOficial diarioOficial) {
		this.diarioOficial = diarioOficial;
	}
	
}