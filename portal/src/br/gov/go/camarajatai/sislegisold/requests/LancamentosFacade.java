package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class LancamentosFacade
 */
//@WebServlet("/sislegisold/lancamentos")
public class LancamentosFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LancamentosFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Suport.validIntranet(request, response);
		Documento lei= (Documento)request.getSession().getAttribute("lei");
		int iddoc = 0;
		RequestDispatcher rd;
		if (lei == null) {
			try {
				iddoc = Integer.parseInt(request.getParameter("iddoc"));
			}
			catch (Exception e) {
				rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase+"lancamento.jsp");
				rd.include(request, response);		
				return;
			}
			lei = new Documento();
			lei.setId(iddoc);
			lei = Seeker.getInstance().leiDao.selectOne(lei);
			request.getSession().setAttribute("lei", lei);
		}
		
		String texto = request.getParameter("texto");
		String id = request.getParameter("id");
		Itemlei itemlei;
		
		
		if (texto != null) {
			itemlei = new Itemlei();
			itemlei.setId(Integer.valueOf(id));
			Seeker.getInstance().itemleiDao.selectItem(itemlei);
			itemlei.setTexto(texto);			
			Seeker.getInstance().itemleiDao.update(itemlei);
			lei.setOnLine(false);
			
		}
			
		
		itemlei = Seeker.getInstance().itemleiDao.selectItemForLanc(lei);
		
		if (itemlei != null) {
			
			request.getSession().setAttribute("item", itemlei);
			
		}
		else  {
			request.getSession().removeAttribute("item");
		}
		

		rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase+"lancamento.jsp");
		rd.include(request, response);
		
	}
}
