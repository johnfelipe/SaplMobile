package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class RevogarItemLeiFacade
 */
//@WebServlet("/sislegisold/revogarItemLei")
public class RevogarItemLeiFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RevogarItemLeiFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		int id_dono = 0;
		int id = 0;
		String texto = "<span style=\"color:#d00;\">Revogado...</span>";
		
		try {
		
		
		id_dono = Integer.parseInt(request.getParameter("id_dono"));
		id = Integer.parseInt(request.getParameter("id"));		
		
		Itemlei itemRevogado = new Itemlei();
		itemRevogado.setId(id);
		Seeker.getInstance().itemleiDao.selectItem(itemRevogado);
		itemRevogado.setRevogado(true);
		Seeker.getInstance().itemleiDao.update(itemRevogado);

		Itemlei itemRevogador = new Itemlei();
		itemRevogador.setId(id_dono);
		Seeker.getInstance().itemleiDao.selectItem(itemRevogador);

		Documento docRevogador = new Documento(itemRevogador.getId_lei());
		docRevogador = Seeker.getInstance().leiDao.selectOne(docRevogador);

		
		itemRevogado.setTexto(texto);
		itemRevogado.setId_dono(id_dono);
		itemRevogado.setLink_alterador("Revogado pelo "+itemRevogador.getNomeclaturaCompletaParaPesquisa().trim()+", "+docRevogador.getEpigrafe()+".");
		itemRevogado.setId_alterador(itemRevogador.getId_lei());
		itemRevogado.setData_inclusao(itemRevogador.getData_inclusao());
		itemRevogado.setRevogado(false);		
		
		Seeker.getInstance().itemleiDao.insert(itemRevogado);
		
		response.sendRedirect(Urls.urlPortalSislegisOldBase+"seeker?iddoc="+itemRevogado.getId_lei()+"#"+itemRevogado.getId());			
		
		
		}
		catch (Exception e) {
			response.getOutputStream().println("Erro");
		}
		
	}

}
