package br.gov.go.camarajatai.sislegisold.dto;

public class DocDestaque extends Dto{

	private Documento id_doc = null;
	private String titulo = "";
	private int ordem = 0;
		
	public Documento getId_doc() {
		return id_doc;
	}

	public void setId_doc(Documento id_doc) {
		this.id_doc = id_doc;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}



	
	
	
	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
