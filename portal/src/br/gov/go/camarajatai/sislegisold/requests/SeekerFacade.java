package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.PesquisaDeDocumentos;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dao.PostgreConnection;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Tipodoc;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class Seeker
 */
// @WebServlet("/sislegisold/seeker")
public class SeekerFacade extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeekerFacade() {
        super();
        // TODO Auto-generated constructor stub
        Seeker.getInstance();

    }

    public void destroy() {
        PostgreConnection.getInstance().close();
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        RequestDispatcher rd;
        int sv = 0;

        if (Suport.redirectSapl(request, response))
            return;

        if (request.getParameter("sv") != null)
            sv = Integer.parseInt(request.getParameter("sv"));

        if (sv == 0) {

            request.removeAttribute("lei");

            Documento doc;

            Seeker seeker = Seeker.getInstance();

            String iddoc = request.getParameter("iddoc");
            String busca = request.getParameter("busca");
            String export = request.getParameter("export");
            String sapl = request.getParameter("sapl");

            doc = new Documento();
            try {
                doc.setId(Integer.parseInt(iddoc));
            } catch (Exception e) {
            }

            if (busca == null)
                busca = "";

            if (doc.getId() != 0) {
                doc = Seeker.getInstance().selectOneDoc(doc);

                if (doc != null) {

                    if (busca.length() == 0)
                        seeker.itemleiDao.selectLei(doc);
                    else
                        doc = seeker.selectSearch(doc, busca);

                    if (!Suport.isIntranet(request))
                        seeker.clickLei(doc, request, response);
                }
                request.setAttribute("lei", doc);

                if (doc == null || doc.completo)

                    if (export != null && export.equals("true"))
                        Urls.forward(request, response, null, Urls.urlProjectSislegisOldBase + sapl + "BuscaleiExport.jsp");
                    else
                        Urls.forward(request, response, null, Urls.urlProjectSislegisOldBase + "buscalei.jsp");

                else {
                    rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "pesquisa.jsp");
                    rd.include(request, response);
                }

                return;
            }

            String numDoc = request.getParameter("numDoc");
            String id_tipolei = request.getParameter("tl");
            String tp = request.getParameter("tp");

            // doc.setEmenta(busca);

            if (tp != null) {

                PesquisaDeDocumentos p = seeker.prepararPesquisa(request, null, 0, 0, (Suport.isIntranet(request) ? null : true));

                if (p.getLista().size() == 1)
                    response.sendRedirect(Urls.urlPortalSislegisBase + "seeker?iddoc=" + p.getLista().get(0).getId());
                else {
                    rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "listagem.jsp");
                    rd.include(request, response);
                }
            } else {

                try {
                    doc.setNumero(Integer.parseInt(numDoc));
                } catch (Exception e) {
                }
                try {
                    Tipolei tl = new Tipolei(Integer.parseInt(id_tipolei));
                    doc.getId_tipolei().add(tl);
                } catch (Exception e) {
                    doc.getId_tipolei().clear();
                    ;
                }

                if (busca.length() == 0) {

                    PesquisaDeDocumentos p = seeker.prepararPesquisa(request, null, 0, 0, (Suport.isIntranet(request) ? null : true));

                    if (p.getLista().size() == 1)
                        response.sendRedirect(Urls.urlPortalSislegisBase + "seeker?iddoc=" + p.getLista().get(0).getId());
                    else {

                        String frame = request.getParameter("frame");

                        if (frame != null && frame.equals("1"))
                            rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "listagem_frame.jsp");
                        else
                            rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "listagem.jsp");
                        rd.include(request, response);
                    }
                    return;

                } else
                    doc = seeker.selectSearch(doc, busca);

                if (!Suport.isIntranet(request))
                    seeker.clickLei(doc, request, response);

                request.setAttribute("lei", doc);

                if (doc.completo)
                    Urls.forward(request, response, null, Urls.urlProjectSislegisOldBase + "buscalei.jsp");
                else {
                    rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "pesquisa.jsp");
                    rd.include(request, response);
                }

                return;

            }

            /*
             * Documento lei = null; Tipolei tl = null;
             * 
             * lei = new Documento();
             * 
             * lei.setId_tipolei(null); if (Integer.parseInt(id_tipolei) != 0) {
             * tl = new Tipolei(Integer.parseInt(id_tipolei));
             * lei.setId_tipolei(tl); }
             * 
             * 
             * 
             * 
             * 
             * lei = Seeker.getInstance().selectSearch(lei,
             * (busca==null?"":busca));
             */

            /*
             * if (Integer.parseInt(strLei) != 0) {
             * lei.setId(Integer.parseInt(strLei)); lei =
             * Seeker.getInstance().selectOneLei(lei);
             * 
             * if (lei == null) { lei = new Documento(); lei.setItemleis(new
             * CollectionItensDeLei()); request.setAttribute("lei", lei); rd =
             * request
             * .getRequestDispatcher(Urls.urlProjectSislegisOldBase+"pesquisa.jsp"
             * ); rd.include(request, response); return; } if
             * (!Suport.isIntranet(request)) Seeker.getInstance().clickLei(lei);
             * } else if (Integer.parseInt(numLei) != 0) {
             * lei.setNumero(Integer.parseInt(numLei)); lei =
             * Seeker.getInstance().selectOneLeiForNum(lei);
             * 
             * if (lei == null) { lei = new Documento(); lei.setItemleis(new
             * CollectionItensDeLei()); request.setAttribute("lei", lei); rd =
             * request
             * .getRequestDispatcher(Urls.urlProjectSislegisOldBase+"pesquisa.jsp"
             * ); rd.include(request, response); return; } if
             * (!Suport.isIntranet(request)) Seeker.getInstance().clickLei(lei);
             * }
             * 
             * if (Integer.parseInt(id_tipolei) != 0) { tl = new
             * Tipolei(Integer.parseInt(id_tipolei)); lei.setId_tipolei(tl); }
             */
        } else if (sv > 0) {

            Seeker seeker = Seeker.getInstance();

            Tipodoc td = new Tipodoc(sv);
            td = seeker.tdDao.selectOne(td);

            seeker.prepararPesquisa(request, td, 0, 0, (Suport.isIntranet(request) ? null : true));

            rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "listagem.jsp");
            rd.include(request, response);
        } else if (sv == -1) {

            Seeker seeker = Seeker.getInstance();

            String iddoc = request.getParameter("iddoc");
            String busca = request.getParameter("busca");

            Documento doc = new Documento();
            try {
                doc.setId(Integer.parseInt(iddoc));
            } catch (Exception e) {
            }

            if (busca == null)
                busca = "";

            if (doc.getId() != 0) {
                doc = Seeker.getInstance().selectOneDoc(doc);

                seeker.itemleiDao.selectLei(doc);

                request.setAttribute("lei", doc);

                if (doc == null || doc.completo) {
                    rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "classificar.jsp");
                    rd.include(request, response);
                }
                return;
            }
        } else {
            rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "error.jsp");
            rd.include(request, response);
            return;
        }
    }
}
