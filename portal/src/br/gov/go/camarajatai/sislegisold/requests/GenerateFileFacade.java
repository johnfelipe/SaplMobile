package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Digitalization;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class GenerateImageFacade
 */
//@WebServlet("/sislegisold/downloadFile.pdf")
public class GenerateFileFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenerateFileFacade() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try{
			int sv = Integer.parseInt(request.getParameter("sv"));

			switch (sv) {
			case 1: { 
				int id = Integer.parseInt(request.getParameter("id"));
				Object ob = Suport.getObjectSession("digitalization", "br.gov.go.camarajatai.sislegisold.business.Digitalization", request);
				Digitalization digit = (Digitalization) ob;

				Digitalization.Page page = digit.get(id); 

				response.setHeader("contentType", page.contentType);
				ServletOutputStream out = response.getOutputStream();

				out.write(page.bytesFile);
			} break;
			case 2: {

				int id = Integer.parseInt(request.getParameter("id"));

				Documento lei = new Documento();
				lei.setId(id);

				lei = Seeker.getInstance().leiDao.selectOne(lei);

				if (lei.getPublicado()) {

					Seeker.getInstance().leiDao.selectArqDigital(lei);

					if (lei.getContent_type() == null || lei.getContent_type().length() == 0)
						response.setHeader("contentType", "application/pdf");
					else
						response.setHeader("contentType", lei.getContent_type());

					if (lei.getName_file() != null && lei.getName_file().length() > 0)
						response.setHeader("Content-Disposition", "inline; filename="+lei.getName_file());
					else 
						response.setHeader("Content-Disposition", "inline; filename=Doc_"+Digitalization.normatizeWords(lei.getEpigrafe())+".pdf");				

					ServletOutputStream out = response.getOutputStream();
					out.write(lei.getArqdigital());
					out.flush();
				//	out.close();
				}
				

			}break;
			case 3: {				
				// captura todos documentos do ID do request e 

				int id = Integer.parseInt(request.getParameter("id"));

				Documento lei = new Documento();
				lei.setId(id);

				lei = Seeker.getInstance().leiDao.selectOne(lei);
				Seeker.getInstance().leiDao.selectArqDigital(lei);

				List<Documento> lDocs = Seeker.getInstance().leiDao.getDocsBase(lei);
				Iterator<Documento> itDocsDeps = lDocs.iterator();
				Documento docDeps = null;

				while (itDocsDeps.hasNext()) {
					docDeps = itDocsDeps.next();	

					if (!docDeps.getPublicado() && !Suport.isIntranet(request))
						itDocsDeps.remove();
				}
				itDocsDeps = lDocs.iterator();


				response.setHeader("contentType", "application/zip");
				response.setHeader("Content-Disposition", "attachment; filename=Arquivos-Processo-"+lei.getNumero()+"-"+(1900+lei.getData_lei().getYear())+".zip");

				ServletOutputStream out = response.getOutputStream();
				ZipOutputStream zOut;

				zOut = new ZipOutputStream(out);
				zOut.setLevel(9);

				zOut.setMethod(ZipOutputStream.DEFLATED);

				ZipEntry ze;


				while (itDocsDeps.hasNext()) {

					docDeps = itDocsDeps.next();
					docDeps = Seeker.getInstance().leiDao.selectOne(docDeps);

					if (!docDeps.getPublicado())
						continue;					


					Seeker.getInstance().leiDao.selectArqDigital(docDeps);

					//System.out.println(docDeps.getEpigrafe()+" ==== "+docDeps.getName_file());

					if (docDeps.getName_file() == null || docDeps.getName_file().length() == 0)
						docDeps.setName_file(Digitalization.normatizeWords(docDeps.getEpigrafe())+".pdf");

					ze = new ZipEntry(docDeps.getName_file());
					ze.setMethod(ZipEntry.DEFLATED);

					try {
						zOut.putNextEntry(ze);
					} catch (IOException e) {
						return;
					}				
					zOut.write(docDeps.getArqdigital());


					System.out.println(Digitalization.getMD5(docDeps.getArqdigital()));

				}
				zOut.flush();
				zOut.close();
				return;
			}

			}

		}catch(Exception e) {
			e.printStackTrace();

		//	response.sendRedirect(Urls.urlPortalSislegisOldBase+"/admin/error.jsp");
		}
	}

}
