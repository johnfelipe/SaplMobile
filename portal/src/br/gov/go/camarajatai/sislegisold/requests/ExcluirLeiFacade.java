package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class RevogarLeiFacade
 */
public class ExcluirLeiFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExcluirLeiFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		int id = Integer.parseInt(request.getParameter("id"));	

		if (!Suport.isIntranetRoot(request)) {
			response.sendRedirect(Urls.urlPortalSislegisOldBase+"seeker?iddoc="+id);
			return;			
		}
		

		Documento doc = new Documento(id);
		doc = Seeker.getInstance().leiDao.selectOne(doc);

		Seeker.getInstance().leiDao.delete(doc);

		response.sendRedirect(Urls.urlPortalSislegisOldBase+"seeker");				
		

		
	}

}
