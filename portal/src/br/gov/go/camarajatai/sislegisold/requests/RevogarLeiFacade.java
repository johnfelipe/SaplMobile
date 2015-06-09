package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class RevogarLeiFacade
 */
public class RevogarLeiFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RevogarLeiFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		

		int id_revogador = Integer.parseInt(request.getParameter("id_revogador"));
		int id = Integer.parseInt(request.getParameter("id"));	
		

		Documento docRevogador = new Documento(id_revogador);
		docRevogador = Seeker.getInstance().leiDao.selectOne(docRevogador);

		Documento doc= new Documento(id);
		doc = Seeker.getInstance().leiDao.selectOne(doc);
		doc.setId_revogador(docRevogador);
		doc.setLink_revogador(doc.getEpigrafe()+" revogada pela "+docRevogador.getEpigrafe());
		Seeker.getInstance().leiDao.update(doc);
		
		response.sendRedirect(Urls.urlPortalSislegisOldBase+"seeker?iddoc="+id);			
		

		
	}

}
