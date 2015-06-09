package br.gov.go.camarajatai.sislegisold.business;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletOutputStream;

import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.suport.RomanNumber;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

public class Analyzer {

	private String originalText = "";
	
	private ArrayList<String[]> listaDeItens = new ArrayList<String[]>();	
	
	private Itemlei[] listaDeItensDeLei; 

	public Analyzer() {}
	
	public void prepare(String text) {
		
		originalText = text;
		listaDeItens = new ArrayList<String[]>();
		
		String lista[] = text.trim().split("\n");
		
		for (int i = 0; i < lista.length; i++) {
			if (lista[i].length() == 0)
				continue;
			listaDeItens.add(lista[i].trim().split(" "));
		}		
	}
	

	public void processarTudo(ServletOutputStream out) throws IOException {
		
		if (listaDeItens.size() == 0)
			return;
		
		listaDeItensDeLei = new Itemlei[listaDeItens.size()];
		Itemlei item;
		
		Iterator<String[]> it = listaDeItens.iterator();
		String texto[];
		String itemTexto;
		char carac;
		
		for (int i = 0; i < listaDeItensDeLei.length; i++) {
			
			listaDeItensDeLei[i] = null;			
			texto = it.next();
			
			for (int j = 0; j < texto.length; j++) {
				System.out.print(texto[j]+" ");
			}
			System.out.println();
			if (texto[0].toLowerCase().equals("art.")) {
				createForThree(texto, i, "artigo");
				carac = texto[1].charAt(texto[1].length()-1);
				if (carac < 48 || carac > 57)
					texto[1] = texto[1].substring(0, texto[1].length()-1);
				
				listaDeItensDeLei[i].setArtigo(Integer.parseInt(texto[1]));
				
				
				System.out.println(listaDeItensDeLei[i].getNomeclatura()+listaDeItensDeLei[i].getTexto()+"\n\n");
				
				
			}			
			
			else if (texto[0].toLowerCase().equals("§")) {
				createForThree(texto, i, "paragrafo");
				carac = texto[1].charAt(texto[1].length()-1);
				if (carac < 48 || carac > 57)
					texto[1] = texto[1].substring(0, texto[1].length()-1);
				
				listaDeItensDeLei[i].setParagrafo(Integer.parseInt(texto[1]));
				
				
				System.out.println(listaDeItensDeLei[i].getNomeclatura()+listaDeItensDeLei[i].getTexto()+"\n\n");
				
				
			}			
			else if (texto[0].toLowerCase().equals("parágrafo")) {
				createForThree(texto, i, "par_unico");				
				listaDeItensDeLei[i].setParagrafo(Integer.parseInt(texto[1]));
				
				
				System.out.println(listaDeItensDeLei[i].getNomeclatura()+listaDeItensDeLei[i].getTexto()+"\n\n");
				
				
			}			
			else if (RomanNumber.isRoman(texto[0])) {
				createForThree(texto, i, "inciso");
				listaDeItensDeLei[i].setInciso(RomanNumber.getNumber(texto[0]));
				
				
				System.out.println(listaDeItensDeLei[i].getNomeclatura()+listaDeItensDeLei[i].getTexto()+"\n\n");
				
				
			}
			
			
		
		}
		
		for (int i = 0; i < listaDeItensDeLei.length; i++) {
			
			if(listaDeItensDeLei[i] == null)
				continue;
			
			out.print(Suport.utfToIso(listaDeItensDeLei[i].getTexto())+"<br>");	
		}
		

		
	}

	private void createForThree(String[] texto, int i, String nivel) {
		String itemTexto;
		listaDeItensDeLei[i] = new Itemlei();
		itemTexto = "";
		for (int j = 3; j < texto.length; j++) {
			itemTexto += texto[j]+" ";
		}
		listaDeItensDeLei[i].setTexto(itemTexto);
		listaDeItensDeLei[i].setNivel(CollectionItensDeLei.keyForValue(nivel));
		listaDeItens.set(i, null);
		
		
		
	}	
}
