package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Analyzer;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

/**
 * Servlet implementation class EvaluationTextFacade
 */
//@WebServlet("/sislegisold/evaluationText")
public class EvaluationTextFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EvaluationTextFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//
		ServletOutputStream out = response.getOutputStream();
		
		
		response.getOutputStream().print(request.getRemoteAddr()+"<br>");
		
		
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String value = headers.nextElement();
		       response.getOutputStream().println(value+" - <b>"+request.getHeader(value)+"</b><br>");
		}
		
		
				
		String texto = request.getParameter("texto");
		String sv = request.getParameter("sv");
		
		Analyzer analyzer = (Analyzer) Suport.getObjectSession("analyzer", "br.gov.go.camarajatai.sislegisold.business.Analyzer", request);
		
		analyzer.prepare(texto);
		analyzer.processarTudo(out);
		
		
		
		
		
		/*ArrayList<String> listaDeItens = analyzer.getListaDeItens();
		Iterator<String> it = listaDeItens.iterator();
		while (it.hasNext()) {
			texto = it.next();
			if (texto.length() == 0)
				continue;
			out.println();
		}*/
	}
}
