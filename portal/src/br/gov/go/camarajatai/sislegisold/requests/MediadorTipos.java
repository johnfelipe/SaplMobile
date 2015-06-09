package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Tipodoc;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.suport.XMLStream;

/**
 * Servlet implementation class MediadorTipos
 */
public class MediadorTipos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MediadorTipos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
		ServletOutputStream out = response.getOutputStream();
		response.setHeader("Content-Type", "text/html; charset=ISO-8859-1");
		int tipo = Integer.parseInt(request.getParameter("tipo"));
		
		switch (tipo) {
		case 1: { // tipo chaves				
			Iterator<Tipodoc> itTd = Seeker.getInstance().tdDao.selectTips().iterator();
			Tipodoc td;
			String result = "";
			result = "[";
			while (itTd.hasNext()) {
				
				td = itTd.next();
				result += "{"+"\"id\":\""+td.getId()+"\""+",\"nome\":\""+td.getDescr()+"\""+"}";
				
				if (itTd.hasNext())
					result += ", ";
				
			}
			result += "]";
			//result += " ";
			out.print(result);
		}
		break;
		case 2: { // tipo chaves				
			Tipodoc td = new Tipodoc(Integer.parseInt(request.getParameter("selectTipo")));
			Iterator<Tipolei> itTL = Seeker.getInstance().tlDao.selectAllWith(td).iterator();
			Tipolei tl;
			String result = "";
			result = "[";
			while (itTL.hasNext()) {
				
				tl = itTL.next();
				result += "{"+"\"id\":\""+tl.getId()+"\""+",\"nome\":\""+tl.getDescr()+"\""+"}";
				
				if (itTL.hasNext())
					result += ", ";
				
			}
			result += "]";
			//result += " ";
			out.print(result);
		}
		break;
		}
	}

}
