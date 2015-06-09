package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.DocumentException;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.pdf.CertidaoDePublicacao;

/**
 * Servlet implementation class CertidaoFacade
 */
public class CertidaoFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CertidaoFacade() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("ContentType,", "application/pdf");
		response.setHeader("Content-Disposition", "inline; filename=ficha.pdf");



		int id = Integer.parseInt(request.getParameter("iddoc"));

		int numPages = Integer.parseInt(request.getParameter("p"));
		
		int numVias = 0;
		
		try {
			numVias = Integer.parseInt(request.getParameter("v"));
		} catch (Exception e) {
			numVias = 1;
		}

		Documento doc = new Documento();
		doc.setId(id);

		doc = Seeker.getInstance().leiDao.selectOne(doc);

			try {
				new CertidaoDePublicacao().run(doc, response.getOutputStream(), numPages, numVias, request);
				Seeker.getInstance().leiDao.update(doc);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}

}
