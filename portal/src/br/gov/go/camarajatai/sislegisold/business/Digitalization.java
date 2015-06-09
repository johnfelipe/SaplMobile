package br.gov.go.camarajatai.sislegisold.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class Digitalization {

	private String nameFile;
	
	public class Page {
		public String nameFile = "";
		public byte[] bytesFile = null;
		public String contentType = "";
		
		private Page() {
			
		}
		
	}
	private ArrayList<Page> lPages = null;
	
	private void createLPages() {
		if (lPages == null) {
			lPages = new ArrayList<Page>();
		}	
	}
	
	public Page getNewPage() {
		Page page = new Page();
		return page;
	}	
	
	public void add(Page page) {
		createLPages();
		lPages.add(page);
	}
	
	public void remove(int pos) {
		createLPages();
		if (pos < lPages.size())
			lPages.remove(pos);
	}
	
	public Page get(int pos) {
		createLPages();
		
		if (pos < lPages.size())		
			return lPages.get(pos);
		
		return null;
	}
	
	public int size() {
		createLPages();
		return lPages.size();
	}
	
	public Iterator<Page> iterator() {
		createLPages();
		organizePages();
		return lPages.iterator();
	}

	
	private void organizePages() {
		
		Iterator<Page> it = lPages.iterator();
		Page page;
		while (it.hasNext()) {
			page = it.next();
			if (page.bytesFile == null) {
				it.remove();
			}
		}
		
		
	}
	
	
	public void generate(String nameSession) {

		
		
		try {
			
			Document document = new Document();
			document.setPageCount(1);
			document.setMargins(0, 0, 0, 0);

			nameFile = "/tmp/"+nameSession + ".pdf"; 
			
			PdfWriter pd = PdfWriter.getInstance(document,
					new FileOutputStream(nameFile));
			
			
			pd.setFullCompression();
			document.open();

			Iterator<Page> it = iterator();
			Page page;
			Image im;
			while (it.hasNext()) {
				page = it.next();
				
				if (page.bytesFile == null)
					continue;
				
				im = Image.getInstance(page.bytesFile);
				im.scaleAbsolute(595, 840);
				document.add(im);
				if (it.hasNext())
					document.newPage();
			}
			document.close();
			pd.close();			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public byte[] getArrayFile() {
		
		byte[] file = null;
		
		ArrayList<byte[]> lPartFiles = new ArrayList<byte[]>();
		try {
			FileInputStream in = null;
			in = new FileInputStream(new File(nameFile));
			file = new byte[in.available()];
			
			in.read(file);
			
			
		} catch (Exception e) {
			
		}
		
		return file; 
	}
	
	public static String normatizeWords(String texto) {


		texto = preNormatizeWords(texto).toLowerCase();

		String aut ="abcdefghijklmnopqrstuvwxyz ãâáàä ẽêéèë ĩîíìï õôóòö ũûúùü 0123456789 . ç ";
		String result = "";

		for (int i = 0; i < texto.length(); i++) {

			if (aut.indexOf(texto.substring(i, i+1)) == -1)
				continue;

			result += texto.substring(i, i+1);
		}
		
		return replaceCaracteres(result);
	}

	private static String preNormatizeWords(String texto) {

		//Retirar Tags
		int ini = 0;
		int fim = 0;

		if (texto.indexOf("<") != -1)
			texto = texto.replaceAll("\\<.*?\\>", "");

		final String listaCaracteres[] = {"\t", "\r\n", "(", ")", "-", ";",  ",", "'", "\"",
				"","", "","º","/","\\", ":", "&", "+", };

		for (String carac : listaCaracteres) {
			while ((ini = texto.indexOf(carac)) != -1) {			  
				texto = texto.substring(0, ini) + " " +texto.substring(ini+carac.length());		   		   
			}		
		}

		return texto;
	}
	
	private static String replaceCaracteres(String palavra) {

		String autOld ="ãâáàä ẽêéèë ĩîíìï õôóòö ũûúùü ç";
		String autNew ="aaaaa-eeeee-iiiii-ooooo-uuuuu-c";

		for (int i = 0; i < autOld.length(); ++i ) {
			palavra = palavra.replace(autOld.substring(i, i+1), autNew.substring(i, i+1));
		}	
		return palavra;	
	}
	
	
	public static String getMD5(byte[] sq) {
		
		String result = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
						
			BigInteger hash = new BigInteger(1, md.digest(sq));  
			result = hash.toString(16);              
	        return result; 
			
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	
}
