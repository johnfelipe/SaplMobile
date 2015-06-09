package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.PesquisaDeDocumentos;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.dto.Tipodoc;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class PendenciasFacade
 */
public class PendenciasFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PendenciasFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher rd;
		
		Seeker seeker = Seeker.getInstance();

		Tipodoc td = new Tipodoc(2);
		td = seeker.tdDao.selectOne(td);
		
		PesquisaDeDocumentos p = seeker.prepararPesquisa(request,td, 0, 0);	
		
		
		Iterator<Documento> it = p.getLista().iterator(); 
		Documento doc = null;
		Itemlei il = null;
		
		while (it.hasNext()) {
			
			doc = it.next();
		
			if (doc.getEmenta().length() == 0)
				continue;
			
			if (!doc.getPossuiarqdigital())
				continue;
			
			if (Seeker.getInstance().itemleiDao.selectItemForLanc(doc) != null)
				continue;
				
			Seeker.getInstance().itemleiDao.selectLei(doc);
			if (doc.getItemleis().size() == 0)
				continue;
			
			it.remove();	
			p.setTotalDocumentos(p.getTotalDocumentos()-1);
		}
		

		rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase+"pendencias.jsp");
		rd.include(request, response);
		
	}

}
