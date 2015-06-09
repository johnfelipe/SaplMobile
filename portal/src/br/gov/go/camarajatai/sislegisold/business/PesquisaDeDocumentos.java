package br.gov.go.camarajatai.sislegisold.business;

import java.util.List;


import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Tipodoc;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;

public class PesquisaDeDocumentos {

	public static final int QTD_DOCUMENTOS_POR_PAGINA = 50;
	
	private int pagina = 0;
	private int qtdDocumentos = QTD_DOCUMENTOS_POR_PAGINA;
	private int totalDocumentos = 0;
	private int totalPaginas = 0;
	
	private int numDoc = 0;
	private Tipodoc td = null;
	private Tipolei tl = null;
	private String titulo = "";
	private List<Documento> lista;
	private String textoSearch = "";
	private String tp="on";
	
	private Boolean publico = null;
	
	public int getPagina() {
		return pagina;
	}
	public int getPaginaDisplay() {
		return pagina+1;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina-1;
	}
	public int getQtdDocumentos() {
		return qtdDocumentos;
	}
	public void setQtdDocumentos(int qtdDocumentos) {
		this.qtdDocumentos = qtdDocumentos;
	}
	public int getTotalDocumentos() {
		return totalDocumentos;
	}
	public void setTotalDocumentos(int _totalDocumentos) {
		this.totalDocumentos = _totalDocumentos;
				
		if (pagina*qtdDocumentos > totalDocumentos) {
			pagina = 0;
			qtdDocumentos = 10;
		}
	}
	public int getTotalPaginas() {
		return totalPaginas;
	}
	public void setTotalPaginas(int totalPaginas) {
		this.totalPaginas = totalPaginas;
	}
	
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public List<Documento> getLista() {
		return lista;
	}
	public void setLista(List<Documento> lista) {
		this.lista = lista;
	}
	public void resetValues() {
		pagina = 0;
		qtdDocumentos = QTD_DOCUMENTOS_POR_PAGINA;
		totalDocumentos = 0;
		totalPaginas = 0;		
		titulo = "";
		lista = null;
		textoSearch = "";
		
	}
	public void setTextoSearch(String textoSearch) {
				
		this.textoSearch = (textoSearch == null) ? "" : textoSearch.trim();
	}
	public String getTextoSearch() {
		return textoSearch;
	}
	public void setTd(Tipodoc td) {
		this.td = td;
	}
	public Tipodoc getTd() {
		return td;
	}
	public void setTl(Tipolei tl) {
		this.tl = tl;
	}
	public Tipolei getTl() {
		return tl;
	}
	public void calcularValores() {
		
		//totalDocumentos = lista.size();

		totalPaginas = totalDocumentos / qtdDocumentos;
		totalPaginas += (totalDocumentos % qtdDocumentos != 0?1:0);

		
	}
	public void setTp(String tp) {
		this.tp = tp;
	}
	public String getTp() {
		return tp;
	}
	public void setNumDoc(int numDoc) {
		this.numDoc = numDoc;
	}
	public int getNumDoc() {
		return numDoc;
	}
	public Boolean getPublico() {
		return publico;
	}
	public void setPublico(Boolean publico) {
		this.publico = publico;
	}
	
	
}
