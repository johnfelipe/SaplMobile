package br.gov.go.camarajatai.sislegisold.requests;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.util.password.StrongPasswordEncryptor;

import br.gov.go.camarajatai.olerite.OleriteController;
import br.gov.go.camarajatai.olerite.dto.Descontos;
import br.gov.go.camarajatai.olerite.dto.Funcionario;
import br.gov.go.camarajatai.olerite.dto.Liquidacao;
import br.gov.go.camarajatai.olerite.dto.Proventos;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

/**
 * Servlet implementation class OleriteFacade
 */
public class OleriteFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OleriteFacade() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		boolean acessoLiberado = true;
		boolean permitirExpandirProventos = true;

		if (!acessoLiberado)
			return;

		//ServletOutputStream out = response.getOutputStream();

		String anoinformado = request.getParameter("ano");
		String mesinformado = request.getParameter("mes");
		String matricula = request.getParameter("matricula");

		boolean prov_expandir = request.getParameter("p")!=null;
		prov_expandir = prov_expandir && permitirExpandirProventos;

		String tipo = request.getParameter("tipo");
		String action = request.getParameter("action");

		boolean valueNulls = false;

		if (anoinformado == null) {

			GregorianCalendar data = new GregorianCalendar();

			anoinformado = String.valueOf(data.get(GregorianCalendar.YEAR));
			mesinformado = Suport.complete(String.valueOf(data.get(GregorianCalendar.MONTH)+1), "0", 2, Suport.LEFT);

			if (Integer.parseInt(anoinformado+mesinformado) < 201308) {
				anoinformado = "2013";
				mesinformado = "08";
			}
		}		
		else {
			valueNulls = true;

		}


		String caminhoreal= "/home/pletus/PLETUS/FOLPAG/Exports/OLERITES/PRF0003/"+anoinformado+"/"+mesinformado;
		//String caminhoreal= request.getRealPath(anoinformado+"/"+mesinformado);

		ArrayList<Funcionario> lFunc = null;

		try {
			lFunc = OleriteController.getFolha(anoinformado, mesinformado, caminhoreal, prov_expandir, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		if (lFunc == null) {

			if (valueNulls)
				request.setAttribute("msg", "Mês Solicitado não encontrado. Exibindo mês corrente!");


			GregorianCalendar data = new GregorianCalendar();
			data.add(GregorianCalendar.MONTH, -1);

			anoinformado = String.valueOf(data.get(GregorianCalendar.YEAR));
			mesinformado = Suport.complete(String.valueOf(data.get(GregorianCalendar.MONTH)+1), "0", 2, Suport.LEFT);

			if (Integer.parseInt(anoinformado+mesinformado) < 201308) {
				anoinformado = "2013";
				mesinformado = "08";
			}



			caminhoreal= "/home/pletus/PLETUS/FOLPAG/Exports/OLERITES/PRF0003/"+anoinformado+"/"+mesinformado;
			//caminhoreal= request.getRealPath(anoinformado+"/"+mesinformado);

			lFunc = null;

			try {
				lFunc = OleriteController.getFolha(anoinformado, mesinformado, caminhoreal, prov_expandir, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//ServletOutputStream out = response.getOutputStream();

		if (action != null && action.equals("olerite_func")) {

			ArrayList<Funcionario> lFuncAux = lFunc;
			lFunc = new ArrayList<Funcionario>();

			Iterator<Funcionario> itFunc = lFuncAux.iterator();

			Funcionario f;
			while (itFunc.hasNext()) {

				f = itFunc.next();

				if (f.getMatricula() == Integer.parseInt(matricula)) {
					lFunc.add(f);
				}		

				/*	StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
				String encryptedPassword = passwordEncryptor.encryptPassword("123456");
				 */

				/*	out.println("do $$\n" +
						"declare return_id integer;\n" +
						"begin\n" +
						"   insert into usuario (matricula, nome, login, ativo, senha) values ('"+f.getMatricula()+"', '"+f.getNome().trim()+"', '"+f.getNome().trim().split(" ")[0].toLowerCase()+"', true"+", '"+encryptedPassword+"')  RETURNING id into return_id;\n" +
								"   insert into permissoes (servico, usuario) values (4, return_id);\n" +
								"   insert into permissoes (servico, usuario) values (5, return_id);\n" +
								"end $$;");*/
			}
		}

		//Filtro de Cargos
		/*String cargosPermitidos[] = new String[1];
		cargosPermitidos[0] = "PRESIDENTE";
		cargosPermitidos[1] = "VEREADOR";

		Iterator<Funcionario> itFunc = lFunc.iterator();
		Funcionario f;
		while (itFunc.hasNext()) {

			f = itFunc.next();

			boolean isPerm = false;

			for (int i = 0; i < cargosPermitidos.length; i++) {

				if (f.getCargo().indexOf(cargosPermitidos[i]) != -1) {
					isPerm = true;
					break;
				}				
			}			

			if (!isPerm) {
				itFunc.remove();				
			}

		}*/

		request.setAttribute("listaFuncionarios", lFunc);
		request.setAttribute("anoinformado", anoinformado);
		request.setAttribute("mesinformado", mesinformado);
		request.setAttribute("matricula", matricula);

		RequestDispatcher rd = null;
		if (tipo == null || tipo.equals("2"))
			rd = request.getRequestDispatcher("listaFuncionarios.jsp");
		else 
			rd = request.getRequestDispatcher("listaSalarios.jsp");

		rd.forward(request, response);

		return;

		/*
		if (action == null) {

			action = "olerite_lista";			

			RequestDispatcher rd = request.getRequestDispatcher("listaFuncionarios.jsp");
			rd.forward(request, response);
			return;
		}
		 */





	}
}
