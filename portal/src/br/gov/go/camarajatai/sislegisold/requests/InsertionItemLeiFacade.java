package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class InsertionItemLei
 */
// @WebServlet("/sislegisold/insertionItemLei")
public class InsertionItemLeiFacade extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertionItemLeiFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Documento doc = new Documento();
        doc.setId(Integer.parseInt(request.getParameter("id_lei")));
        doc = Seeker.getInstance().selectOneDoc(doc);

        Documento docAlterador = new Documento();
        docAlterador.setId(Integer.parseInt(request.getParameter("id_alterador")));
        docAlterador = Seeker.getInstance().selectOneDoc(docAlterador);

        Itemlei item = new Itemlei();
        Itemlei itemAlterador = new Itemlei();

        itemAlterador.setId(Integer.parseInt(request.getParameter("id_dono")));
        Seeker.getInstance().itemleiDao.selectItem(itemAlterador);

        item.setId_alterador(docAlterador.getId());
        item.setId_lei(doc.getId());
        item.setId_dono(itemAlterador.getId());

        item.setRevogado(false);
        item.setAlterado(false);
        item.setIncluido(false);
        item.setLink_alterador(request.getParameter("link_alterador"));

        String inclui, altera, revoga, unico;
        inclui = request.getParameter("inclui");
        altera = request.getParameter("altera");
        revoga = request.getParameter("revoga");
        unico = request.getParameter("unico");

        if (inclui != null) {
            item.setIncluido(true);
        }

        Object ob = request.getSession().getAttribute("artigo");
        int artigo = 0;
        if (ob != null)
            artigo = (Integer) ob;

        if (Integer.parseInt(request.getParameter("artigo")) != 0)
            artigo = Integer.parseInt(request.getParameter("artigo"));

        item.setId_lei(doc.getId());
        item.setNumero(doc.getNumero());
        item.setParte(Integer.parseInt(request.getParameter("parte")));
        item.setAnexo(Integer.parseInt(request.getParameter("anexo")));
        item.setLivro(Integer.parseInt(request.getParameter("livro")));
        item.setTitulo(Integer.parseInt(request.getParameter("titulo")));
        item.setCapitulo(Integer.parseInt(request.getParameter("capitulo")));
        item.setCapitulovar(Integer.parseInt(request.getParameter("capitulovar")));
        item.setSecao(Integer.parseInt(request.getParameter("secao")));
        item.setSecaovar(Integer.parseInt(request.getParameter("secaovar")));
        item.setSubsecao(Integer.parseInt(request.getParameter("subsecao")));
        item.setItemsecao(Integer.parseInt(request.getParameter("itemsecao")));
        item.setArtigo(Integer.parseInt(request.getParameter("artigo")));
        item.setArtigovar(Integer.parseInt(request.getParameter("artigovar")));
        item.setParagrafo(Integer.parseInt(request.getParameter("paragrafo")));
        item.setInciso(Integer.parseInt(request.getParameter("inciso")));
        item.setIncisovar(Integer.parseInt(request.getParameter("incisovar")));
        item.setIncisovarvar(Integer.parseInt(request.getParameter("incisovarvar")));
        item.setAlinea(Integer.parseInt(request.getParameter("alinea")));
        item.setItem(Integer.parseInt(request.getParameter("item")));
        item.setSubitem(Integer.parseInt(request.getParameter("subitem")));
        item.setSubsubitem(Integer.parseInt(request.getParameter("subsubitem")));
        item.setData_inclusao(docAlterador.getData_lei());
        item.setData_alteracao(new Timestamp(new GregorianCalendar().getTimeInMillis()));

        if (request.getParameter("nivel") != null)
            item.setNivel(Integer.parseInt(request.getParameter("nivel")));

        if (request.getParameter("unico") != null && item.getNivel() == 1000)
            item.setNivel(1001);

        // item.setData_inclusao(new Timestamp(doc.getData_lei().getTime()));
        // item.setData_alteracao(new Timestamp(new
        // GregorianCalendar().getTimeInMillis()));

        item.setTexto(request.getParameter("texto"));
        // item.setTexto(DBConnection.TransformCodding(request.getParameter("texto")));

        if (altera != null || revoga != null) {

            // item.setLink_alterador("alterado pelo "+itemAlterador.getNomeclaturaCompletaParaPesquisa()+", Lei nº "+itemAlterador.getNumero());
            Seeker.getInstance().itemleiDao.insert(item);

            Seeker.getInstance().itemleiDao.selectItemByData(item);
            item.setAlterado(altera != null);
            item.setRevogado(revoga != null);

            Seeker.getInstance().itemleiDao.update(item);
        } else {
            Seeker.getInstance().itemleiDao.insert(item);
        }

        int[] values = { item.getId_alterador(), item.getId_lei() };
        ExportToSaplFacade.exportDoc(values, "T");

        if (artigo != 0) {
            request.getSession().setAttribute("artigo", new Integer(artigo));
        }

        if (item.getNivel() == 1001) {
            item.setParagrafo(0);
        }
        item.setArtigo(artigo);
        request.setAttribute("item", item);

        RequestDispatcher rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "cadastro.jsp");
        rd.include(request, response);

    }

}
