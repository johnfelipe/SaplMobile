package br.gov.go.camarajatai.sislegisold.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipolei database table.
 * 
 */
public class Tipolei extends Dto {
	
	private String descr;
	
	private Tipodoc idTipoDoc;
	
	private int ordem;

	private Set<Documento> leis;
	
	private String localidade;
	private String autoridade;
	private String tipo;
	
	private String linkAlternativo;
	
	private boolean classif_assuntos;
	
	public boolean tmp_oficial = true;
	public int tmp_sugestoes = 0;

    public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public String getAutoridade() {
		return autoridade;
	}

	public void setAutoridade(String autoridade) {
		this.autoridade = autoridade;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Tipolei() {
    }

    public Tipolei(int id) {
    	this.id = id;
    }


	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Set<Documento> getLeis() {
		return this.leis;
	}

	public void setLeis(Set<Documento> leis) {
		this.leis = leis;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setIdTipoDoc(Tipodoc idTipoDoc) {
		this.idTipoDoc = idTipoDoc;
	}

	public Tipodoc getIdTipoDoc() {
		return idTipoDoc;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public int getOrdem() {
		return ordem;
	}
	
	public void setLinkAlternativo(String linkAlternativo) {
		this.linkAlternativo = linkAlternativo;
	}

	public String getLinkAlternativo() {
		return linkAlternativo;
	}

	public int compareTo(Dto o) {
		
		if (this.id == o.id)
			return 0;
		
		if (this.ordem > ((Tipolei)o).getOrdem())
			return 1;
		else 
			return -1;   	
	}

	public boolean getClassif_assuntos() {
		return classif_assuntos;
	}

	public void setClassif_assuntos(boolean classif_assuntos) {
		this.classif_assuntos = classif_assuntos;
	}

	
}