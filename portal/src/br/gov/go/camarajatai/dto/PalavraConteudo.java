package br.gov.go.camarajatai.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the palavra_conteudo database table.
 * 
 */
@Entity
@Table(name="palavra_conteudo")
public class PalavraConteudo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PALAVRA_CONTEUDO_ID_GENERATOR", sequenceName="PALAVRA_CONTEUDO_ID_SEQ", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PALAVRA_CONTEUDO_ID_GENERATOR")
	private Long id;

	//bi-directional many-to-one association to Dicionario
    @ManyToOne
	@JoinColumn(name="id_dicionario")
	private Dicionario dicionario;

	//bi-directional many-to-one association to ItemConteudo
    @ManyToOne
	@JoinColumn(name="id_item_conteudo")
	private ItemConteudo itemConteudo;

    public PalavraConteudo() {
    }

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Dicionario getDicionario() {
		return this.dicionario;
	}

	public void setDicionario(Dicionario dicionario) {
		this.dicionario = dicionario;
	}
	
	public ItemConteudo getItemConteudo() {
		return this.itemConteudo;
	}

	public void setItemConteudo(ItemConteudo itemConteudo) {
		this.itemConteudo = itemConteudo;
	}
	
}