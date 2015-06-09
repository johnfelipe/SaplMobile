package br.gov.go.camarajatai.sislegisold.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Set;


/**
 * The persistent class for the tipolei database table.
 * 
 */
public class Tipodoc extends Dto {

	private String descr;	
	private int ordem;


	public Tipodoc() {
	}

	public Tipodoc(int id) {
		this.id = id;
	}


	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public int getOrdem() {
		return ordem;
	}
	
	
	public int compareTo(Dto o) {
		
		if (this.id == o.id)
			return 0;
		
		if (this.ordem > ((Tipodoc)o).getOrdem())
			return 1;
		else 
			return -1;   	
	}


}