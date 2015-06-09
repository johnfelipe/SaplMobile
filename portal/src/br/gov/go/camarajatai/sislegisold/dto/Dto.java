package br.gov.go.camarajatai.sislegisold.dto;

import java.io.Serializable;


/**
 *
 * @author Leandro
 */
public abstract class Dto implements Serializable, Comparable<Dto> {

	protected int id = 0;
	
	public String msg = "";
	
	public boolean completo = true;
	
	public Dto(int id) {
		this.id = id;
	}
	
	public Dto() {
		
	}

    public int getId() {
    	return id;
    }
    public void setId(int id) {
    	this.id = id;
    }
    public abstract String toString();
    
    public int compareTo(Dto o) {
    		return this.id == o.id ? 0 : (this.id > o.id ? 1 : -1);   	
    }
    
    public boolean equals(Dto o) {
    	return id == o.id;
    }
}
