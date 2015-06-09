package br.gov.go.camarajatai.sislegisold.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipolei database table.
 * 
 */
public class Arquivo extends Dto {
	
	private String nome;
	private String descr;
	
	private String codArquivo;
		
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public Arquivo() {
    }

    public Arquivo(int id) {
    	this.id = id;
    }

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return nome;
	}

	public void setCodArquivo(String codArquivo) {
		this.codArquivo = codArquivo;
	}

	public String getCodArquivo() {
		return codArquivo;
	}
	
}