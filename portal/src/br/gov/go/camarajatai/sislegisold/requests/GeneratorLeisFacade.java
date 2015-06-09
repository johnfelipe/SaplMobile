package br.gov.go.camarajatai.sislegisold.requests;

import java.awt.event.ItemEvent;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class insertLeisFacade
 */
//@WebServlet("/sislegisold/generatorLeis")
public class GeneratorLeisFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeneratorLeisFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Documento lei= (Documento)request.getSession().getAttribute("lei");
		
		int direc= Integer.parseInt(request.getParameter("direcao"));
		
		CollectionItensDeLei itensLei= lei.getItemleis();
		
		switch (direc) {
		case 0:{
	
			//itensLei.firstItem();
			//Itemlei item;
			//while(itensLei.hasNext()) {
				
			//	item= itensLei.next();
				
			//	Seeker.getInstance().itemleiDao.insert(item);
				
			//}
			
			RequestDispatcher rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase+"lancamentos");
			rd.include(request, response);
			return;
		
		}

		case 1:{
			
			Itemlei item = null;
			
			if (itensLei.size() > 0) {				
				item = itensLei.removeLast();
				Seeker.getInstance().itemleiDao.delete(item);
			}

			if (item != null) {				
				
				int nivel= item.getNivel();
				
				if (nivel == 800) {				
					
					int artigo= Integer.parseInt(request.getSession().getAttribute("artigo").toString()) - 1;
					request.getSession().setAttribute("artigo", artigo);
					
				}
				
			}			
		}
		break;
		case 3:{
			
			int nivel= Integer.parseInt(request.getParameter("nivel"));
			request.getSession().setAttribute("nivel", nivel);
			int artigo = 0;
			
			
			Itemlei ultimoItem= itensLei.size()==0?null:itensLei.getLast();
			Itemlei item;
			if (ultimoItem==null){
				
				item = new Itemlei();
				item.setNumero(lei.getNumero());
				item.setId_lei(lei.getId());
				item.setId_alterador(lei.getId());
				item.setData_alteracao(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				item.setData_inclusao(lei.getData_lei());
				
				}else{
					
				item= Itemlei.clone(ultimoItem);
			}
			
			item.setNivel(nivel);
			item.setTexto("");
			if (nivel == 800) {
				item.organize();
				artigo= Integer.parseInt(request.getSession().getAttribute("artigo").toString()) + 1;
				request.getSession().setAttribute("artigo", artigo);
				item.setArtigo(artigo);
			}
			else {
				item.increment();
			}
			
			Seeker.getInstance().itemleiDao.insert(item);
			itensLei.add(item);
			
		}
		break;


		default:
			break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase+"gerarEstrutura.jsp");
		rd.include(request, response);
		

	}	


	protected void serviceOld(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Documento lei= (Documento)request.getSession().getAttribute("lei");
		
		int direc= Integer.parseInt(request.getParameter("direcao"));
		
		CollectionItensDeLei itensLei= lei.getItemleis();
		
		switch (direc) {
		case 0:{
	
			itensLei.firstItem();
			Itemlei item;
			while(itensLei.hasNext()){
				item= itensLei.next();
				Seeker.getInstance().itemleiDao.insert(item);		
			}
			RequestDispatcher rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase+"lancamentos");
			rd.include(request, response);
			return;
		
		}

		case 1:{
			Itemlei item = null;
			if (itensLei.size()>0)
				item = itensLei.removeLast();

			if (item != null) {				
				int nivel= item.getNivel();
				
				if (nivel == 800) {				
					int artigo= Integer.parseInt(request.getSession().getAttribute("artigo").toString()) - 1;
					request.getSession().setAttribute("artigo", artigo);
				}
				
			}			
		}
		break;
		case 3:{
			
			int nivel= Integer.parseInt(request.getParameter("nivel"));
			request.getSession().setAttribute("nivel", nivel);
			int artigo = 0;
			
			
			Itemlei ultimoItem= itensLei.size()==0?null:itensLei.getLast();
			Itemlei item;
			if (ultimoItem==null){
				item= new Itemlei();
				item.setNumero(lei.getNumero());
				item.setId_lei(lei.getId());
				item.setId_alterador(lei.getId());
				item.setData_alteracao(new Timestamp(new GregorianCalendar().getTimeInMillis()));
				item.setData_inclusao(lei.getData_lei());
				}else{
				item= Itemlei.clone(ultimoItem);
			}
			
			item.setNivel(nivel);
			item.setTexto("");
			if (nivel == 800) {
				item.organize();
				artigo= Integer.parseInt(request.getSession().getAttribute("artigo").toString()) + 1;
				request.getSession().setAttribute("artigo", artigo);
				item.setArtigo(artigo);
			}
			else 			
				item.increment();
			
			itensLei.add(item);
		}
		break;


		default:
			break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase+"gerarEstrutura.jsp");
		rd.include(request, response);
		

	}	


}
