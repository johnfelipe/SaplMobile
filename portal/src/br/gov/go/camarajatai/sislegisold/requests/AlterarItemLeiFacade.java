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
 * Servlet implementation class AlterarItemLeiFacade
 */
// @WebServlet("/sislegisold/alterarItemLei")
public class AlterarItemLeiFacade extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlterarItemLeiFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id_dono = 0;
        int nivel = 0;
        int id = 0;
        String texto = "";

        try {

            try {
                id_dono = Integer.parseInt(request.getParameter("id_dono"));
            } catch (NumberFormatException e) {
                id_dono = 0;
            }

            try {
                nivel = Integer.parseInt(request.getParameter("nivel"));
            } catch (NumberFormatException e) {
                nivel = 0;
            }

            id = Integer.parseInt(request.getParameter("id"));
            texto = request.getParameter("texto");

            Itemlei itemAlterado = new Itemlei();
            itemAlterado.setId(id);
            Seeker.getInstance().itemleiDao.selectItem(itemAlterado);

            if (id_dono != 0) {
                itemAlterado.setAlterado(true);
                Seeker.getInstance().itemleiDao.update(itemAlterado);

                itemAlterado.setTexto(texto);
                itemAlterado.setId_dono(id_dono);

                Itemlei itemDono = new Itemlei();
                itemDono.setId(id_dono);
                Seeker.getInstance().itemleiDao.selectItem(itemDono);

                Documento docAlterador = new Documento(itemDono.getId_lei());
                docAlterador = Seeker.getInstance().leiDao.selectOne(docAlterador);

                itemAlterado.setLink_alterador("Alterado pelo " + itemDono.getNomeclaturaCompletaParaPesquisa().trim() + ", " + docAlterador.getEpigrafe()
                        + ".");
                itemAlterado.setId_alterador(itemDono.getId_lei());
                itemAlterado.setAlterado(false);
                itemAlterado.setData_inclusao(itemDono.getData_inclusao());
                Seeker.getInstance().itemleiDao.insert(itemAlterado);

                int[] values = { itemAlterado.getId_alterador(), itemAlterado.getId_lei() };
                ExportToSaplFacade.exportDoc(values, "T");

            } else {
                itemAlterado.setTexto(texto);
                itemAlterado.setId_dono(id_dono);

                itemAlterado.setNivel(nivel);

                Seeker.getInstance().itemleiDao.update(itemAlterado);

                int[] values = { itemAlterado.getId_alterador(), itemAlterado.getId_lei() };
                ExportToSaplFacade.exportDoc(values, "T");

            }

            response.sendRedirect(Urls.urlPortalSislegisOldBase + "seeker?iddoc=" + itemAlterado.getId_lei() + "#" + itemAlterado.getId());

        } catch (Exception e) {
            response.getOutputStream().println("Erro");
        }

    }
}
