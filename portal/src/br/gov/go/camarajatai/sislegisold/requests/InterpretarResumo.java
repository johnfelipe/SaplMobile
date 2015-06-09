package br.gov.go.camarajatai.sislegisold.requests;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Arquivo;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

/**
 * Servlet implementation class InterpretarResumo
 */
public class InterpretarResumo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InterpretarResumo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//service1(response);
		service2(response);
	}
	
	private void service2(HttpServletResponse response) throws IOException {
		FileInputStream in;
		File inFile;
		ArrayList<byte[]> file;
		for (int i = 1111; i < 1400; i++) {
			
			file = new ArrayList<byte[]>();
			
			byte buffer[];
			int byteRead = 0;
			try {			
				in = new FileInputStream(inFile = new File("/home/leandro/TEMP/"+i+".pdf"));
			}
			catch(FileNotFoundException e) {
				continue;
			}
			byte[] bytesAux = new byte[1024];
			while ((byteRead = in.read(buffer = new byte[1024])) > 0 ) {
								
				if (byteRead < 1024) {
					
					bytesAux = new byte[byteRead];					
					
					for (int j= 0; j < byteRead; j++) 
						bytesAux[j] = buffer[j];
					
					file.add(bytesAux);
					
				}
				else {
					file.add(buffer);
				}
			}
			
			buffer = new byte[(file.size()-1)*1024+bytesAux.length];
			
			Iterator<byte[]> it = file.iterator();
			int pos = 0;
			while (it.hasNext()) {
				bytesAux = it.next();
				
				for (int j = 0; j < bytesAux.length; j++) {
					buffer[pos++] = bytesAux[j];
				}
			}
			//int j = 0;
			//j++;			
			//response.getOutputStream().write(buffer);
			
			
			Documento doc = new Documento();
			doc.setNumero(i);
			doc = Seeker.getInstance().leiDao.selectOneForNum(doc);
			
			if (doc.getPossuiarqdigital())
				continue;
			
			doc.setArqdigital(buffer);
			Seeker.getInstance().leiDao.upDateArqDigital(doc);
			System.out.println(i);
		}
		
		
	}
	
	

	private void service1(HttpServletResponse response) throws IOException,
	FileNotFoundException {
		DataInputStream in = null;
		ServletOutputStream out = response.getOutputStream();

		in = new DataInputStream(new FileInputStream("/home/leandro/dde/resumo.csv"));

		String strLista[] = null;
		String str = null;

		ArrayList<String[]> lista = new ArrayList<String[]>();

		while ((str = in.readLine()) != null) {
			strLista = str.split(";");

			lista.add(0, strLista);
		}

		Iterator<String[]> itLista = lista.iterator();


		Documento doc, docAux;
		while (itLista.hasNext()) {
			strLista = itLista.next();

			doc = new Documento();
			try {
				doc.setNumero(Integer.parseInt(strLista[3]));
			}
			catch (NumberFormatException e) {
				continue;
			}

			docAux = Seeker.getInstance().leiDao.selectOneForNum(doc);

			if (docAux != null) {
				doc = docAux;				
			}
			else {
				doc.setData_lei(new Timestamp(Suport.strBRToDate(strLista[0]+"/"+strLista[1]+"/"+strLista[2]).getTimeInMillis()));				
			}


			if (strLista[4].length() > 0 ) {
				doc.setId_arquivo(new Arquivo(Integer.parseInt(strLista[4].substring(1, 4))));
				doc.setId_arquivo(Seeker.getInstance().arquivoDao.selectOne(doc.getId_arquivo()));
			}

			if (strLista.length > 5)
				if (strLista[5].length() > 0 && doc.getId() == 0 ) {
					doc.setIndicacao("Documento físico ainda não localizado.");				
				}

			if (doc.getId() == 0) {
				doc.setEpigrafe("Lei nº "+doc.getNumero()+" de "+strLista[0]+" de "+Suport.monthNames[Integer.parseInt(strLista[1])-1].toLowerCase()+" de "+strLista[2]);
				doc.setId_tipolei(new Tipolei(1));
				Seeker.getInstance().leiDao.insert(doc);
			}
			else {
				Seeker.getInstance().leiDao.update(doc);
			}
			out.println(doc.getId()+" - "+doc.getNumero()+" - "+doc.getEpigrafe()+" - "+doc.getId_arquivo().getCodArquivo());






		}
	}

}
