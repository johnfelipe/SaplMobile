package br.gov.go.camarajatai.sislegisold.business;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.TreeMap;

import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.dto.Documento;

public class CollectionItensDeLei extends LinkedList<Itemlei> {	

	//public static String romanos[] = {"I","II","III","IV","V","VI","VII","VIII","IX","X","XI","XII","XIII","XIV","XV","XVI","XVII","XVIII","XIX","XX","XXI","XXII","XXIII","XXIV","XXV","XXVI","XXVII","XXVIII","XXIX","XXX","XXXI","XXXII","XXXIII","XXXIV","XXXV","XXXVI","XXXVII","XXXVIII"};
	public static String alinea[] = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
	
	public static TreeMap<Integer, String> niveis = new TreeMap<Integer, String>();
	
	private ListIterator<Itemlei> itCollection = null;
	
	private Itemlei atual = null;

	public CollectionItensDeLei() {
		generateNiveis();
	}

	public static void generateNiveis() {
		
		niveis.clear();
		
		niveis.put(100, "numero");
		niveis.put(150, "anexo");
		niveis.put(200, "livro");
		niveis.put(300, "parte");
		niveis.put(400, "titulo");
		niveis.put(500, "capitulo");
		niveis.put(550, "capitulo .var");
		niveis.put(600, "secao");
		niveis.put(650, "secao .var");
		niveis.put(700, "subsecao");
		niveis.put(750, "secao .item");
		niveis.put(800, "artigo");
		niveis.put(900, "artigovar");
		niveis.put(1000, "paragrafo");
		niveis.put(1001, "par_unico");
		niveis.put(1100, "inciso");
		niveis.put(1150, "inciso .var");
		niveis.put(1175, "inciso .var .var");
		niveis.put(1200, "alinea");
		niveis.put(1300, "item");	
		niveis.put(1400, "item .sub");	
		niveis.put(1500, "item .sub .sub");	

	}
	public static Integer keyForValue(String value) {
		
		generateNiveis();
		
		Integer result = 0;
		
		java.util.Map.Entry<Integer, String> entry;
		entry = niveis.firstEntry();
		do {
			result = entry.getKey();
			entry = niveis.higherEntry(result);
		}
		while (!entry.getValue().equals(value));
		
		return result;
	}
	
	public void firstItem() {
		itCollection = listIterator();
	}
	public int nextIndex() {
		return itCollection.nextIndex();
	}
	public boolean hasNext() {
		if (itCollection == null)
			firstItem();
		return itCollection.hasNext();
		
	}
	
	public Itemlei next() {
		
		Itemlei itemlei = itCollection.next();		
		return itemlei;
	}
	
	public Itemlei atual() {
		return atual;
	}
	
	
/*	public void organize() {
		ListIterator<Itemlei> itGeral = listIterator();
		ListIterator<Itemlei> itInsert = null;
		Itemlei itemInsert;
		Itemlei itemPai = null;
		while (itGeral.hasNext() && (itemInsert = itGeral.next()).getId_dono() != 0 ) {
			
			itGeral.remove();
			
			itInsert = listIterator();
			
			while (itInsert.hasNext() && (itemPai = itInsert.next()).getId() != itemInsert.getId_dono() )
				 {	}
			
			
			while (itInsert.hasNext() && (itemPai = itInsert.next()).getId_dono() == itemInsert.getId_dono() )
			 {	}
			
			itInsert.previous();
			System.out.println(itemPai.getId()+".");
			itInsert.add(itemInsert);
			
			itGeral = listIterator();
			
		}		
		
		itCollection = listIterator();
	}*/

	public void newOrganize(Documento lei) {
		
		LinkedList<Itemlei> listaItensOutraLei = new LinkedList<Itemlei>();
		LinkedList<Itemlei> listaItensAlteradosPorOutraLei = new LinkedList<Itemlei>();
		
		ListIterator<Itemlei> it;
		
		itCollection = listIterator();
		
		Itemlei il = null;
		while (itCollection.hasNext()) {
			
			 il = itCollection.next();
			 	
			 if (il.getId_alterador() != lei.getId()) {
				 itCollection.remove();
				 listaItensAlteradosPorOutraLei.add(0,il);
			 }
			 
			 else if (il.getId_lei() != lei.getId()) {
				 itCollection.remove();				 
				 listaItensOutraLei.add(il);				 
			 }
		}
		
		/*
		 * listaItensOutraLei são itens de leis que a lei da pesquisa atual está alterando
		 * 			esses itens devem ser inseridos na collection atraves do seu id_dono logo apos seu dono, 
		 * 				antes o primeiro item sem dono.
		 * 
		 */
		
		it = listaItensOutraLei.listIterator();
		
	//	while (it.hasNext()) {
	//		System.out.println(it.next().getNomeclaturaCompletaParaPesquisa());
	//	}
		

		while (!listaItensOutraLei.isEmpty()) {
			
			
			il = listaItensOutraLei.removeLast();
			//System.out.println(il.getNomeclaturaCompletaParaPesquisa());
				
			
			itCollection = listIterator();
			while (itCollection.hasNext()) {
				
				atual = itCollection.next();
				//System.out.println(atual.getNomeclaturaCompletaParaPesquisa());
				if (atual.getId_dono() == 0 && il.getId_dono() == atual.getId())
					break;
				
				if (atual.getId_dono() == il.getId_dono())
					break;				
				
			}
				
			itCollection.add(il);		
		}
		

		/*
		 * listaItensAlteradosPorOutraLei são itens  da lei pesquisada mas alterados por outra
		 * 
		 * 			esses itens devem ser inseridos na ordem numérica logo apos o seu semelhante, 
		 * 				seja por alteração, revogação ou inclusão
		 */
		
		
		
		boolean inserido = false;
		while (!listaItensAlteradosPorOutraLei.isEmpty()) {
			
			
			il = listaItensAlteradosPorOutraLei.removeFirst();
			
			inserido = false;
			itCollection = listIterator();			
			while (itCollection.hasNext())	 {
				
				atual = itCollection.next();
				
				if (atual.getId_lei() != il.getId_lei())
					continue;				
				
				if (atual.compare(il) > 0) {
					
					itCollection.previous();
					itCollection.add(il);
					inserido = true;
					break;
					
				}				
			}	
			
			if (!inserido) 
				itCollection.add(il);
			
			
		}
		
		
		
		
		
		
		/*
		
		ListIterator<Itemlei> itInsert = null;
		Itemlei itemInsert;
		Itemlei itemPai = null;
		while (itGeral.hasNext() && (itemInsert = itGeral.next()).getId_dono() != 0 ) {
			
			itGeral.remove();
			
			itInsert = listIterator();
			
			while (itInsert.hasNext() && (itemPai = itInsert.next()).getId() != itemInsert.getId_dono() )
				 {	}
			
			
			while (itInsert.hasNext() && (itemPai = itInsert.next()).getId_dono() == itemInsert.getId_dono() )
			 {	}
			
			itInsert.previous();
			System.out.println(itemPai.getId()+".");
			itInsert.add(itemInsert);
			
			itGeral = listIterator();
			
		}	*/	
		
		itCollection = listIterator();
		
	}
}
