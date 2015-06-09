package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Arquivo;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.lexml.RegistroItemController;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class insertLeisFacade
 */
// @WebServlet("/sislegisold/insertionLeis")
public class InsertLeisFacade extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertLeisFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String arquivo = request.getParameter("id_arquivo");

        String docPrincipal = request.getParameter("id_doc_principal");
        String classificacao = request.getParameter("classificacao");
        Documento doc = new Documento();

        doc.setId(Integer.parseInt(request.getParameter("id")));

        if (doc.getId() != 0) {
            doc = Seeker.getInstance().selectOneDoc(doc);
        }

        if (classificacao != null) {

            if (Suport.isIntranet(request) && classificacao.equals("0")) {
                String tipolei[] = request.getParameterValues("tipolei");
                for (String strTl : tipolei) {
                    Tipolei tl = new Tipolei();
                    tl.setId(Integer.parseInt(strTl));
                    doc.getId_tipolei().add(tl);
                }
                Seeker.getInstance().leiDao.updateListaTipoLei(doc);
                response.sendRedirect(Urls.urlPortalSislegisBase + "listaPendentesClassificacao.jsp");
            } else {
                String remoteAddr = request.getRemoteAddr();
                ReCaptchaImpl reCaptcha = new ReCaptchaImpl();
                reCaptcha.setPrivateKey("6LdP5fsSAAAAALat73E4WJnSrb19PyJgyGk6lncp");

                String challenge = request.getParameter("recaptcha_challenge_field");
                String uresponse = request.getParameter("recaptcha_response_field");
                ReCaptchaResponse reCaptchaResponse = reCaptcha.checkAnswer(remoteAddr, challenge, uresponse);

                request.setAttribute("lei", doc);

                if (reCaptchaResponse.isValid()) {
                    Suport.setCookie(response, "contrib_assuntos_" + doc.getId(), "true", 0,
                            "Controle simples básico para usuários que já contribuiram com a classificação de Normas.");

                    String tipolei[] = request.getParameterValues("tipolei");
                    for (String strTl : tipolei) {
                        Tipolei tl = new Tipolei();
                        tl.setId(Integer.parseInt(strTl));
                        doc.getId_tipolei().add(tl);
                    }
                    Seeker.getInstance().leiDao.updateListaTipoLeiSugestoes(doc);

                    response.sendRedirect(Urls.urlPortalSislegisBase + "seeker?iddoc=" + doc.getId() + "&msg=Obrigado por contribuir!!!");
                } else {
                    Urls.forward(request, response, "Código informado não está correto!", Urls.urlProjectSislegisOldBase + "buscalei.jsp");
                }
            }
            return;
        }

        doc.setId_arquivo(new Arquivo(Integer.parseInt(arquivo)));

        if (doc.getId_arquivo().getId() == 0)
            doc.setId_arquivo(null);

        doc.setNumero(Integer.parseInt(request.getParameter("numero")));
        doc.setEpigrafe(request.getParameter("epigrafe"));
        doc.setEmenta(request.getParameter("ementa"));
        doc.setPreambulo(request.getParameter("preambulo"));
        doc.setEnunciado(request.getParameter("enunciado"));
        doc.setIndicacao(request.getParameter("indicacao"));
        doc.setTexto_final(request.getParameter("texto_final"));
        doc.setAssinatura(request.getParameter("assinatura"));
        doc.setCargo_assinante(request.getParameter("cargo_assinante"));

        doc.setPublicado(Boolean.parseBoolean(request.getParameter("doc_status_pub")));

        doc.setData_lei(new Timestamp(Suport.strBRToDate(request.getParameter("data_lei")).getTimeInMillis()));

        doc.setId_doc_principal(new Documento(Integer.parseInt(docPrincipal)));

        if (doc.getId_doc_principal().getId() == 0)

            doc.setId_doc_principal(null);

        else {

            doc.setId_doc_principal(Seeker.getInstance().selectOneDoc(doc.getId_doc_principal()));

            // ajustar para data e hora do doc principal
            // doc.getId_doc_principal().setData_alteracao(doc.getData_lei());

            doc.getId_doc_principal().setData_alteracao(new Timestamp(new GregorianCalendar().getTimeInMillis()));

            Seeker.getInstance().leiDao.update(doc.getId_doc_principal());

        }

        if (doc.getId() == 0) {

            // ajustar para data e hora do doc principal
            // doc.setData_inclusao(doc.getData_lei());
            // doc.setData_alteracao(doc.getData_lei());

            doc.setData_inclusao(new Timestamp(new GregorianCalendar().getTimeInMillis()));
            doc.setData_alteracao(new Timestamp(new GregorianCalendar().getTimeInMillis()));

            Seeker.getInstance().leiDao.insert(doc);

        } else {

            // doc.setData_inclusao(doc.getData_lei());
            // doc.setData_alteracao(doc.getData_lei());

            doc.setData_alteracao(new Timestamp(new GregorianCalendar().getTimeInMillis()));
            // Descomentar
            Seeker.getInstance().leiDao.update(doc);
        }

        Seeker.getInstance().itemleiDao.selectLei(doc);

        int artigo = 0;

        Itemlei item;
        CollectionItensDeLei itensLei = doc.getItemleis();
        itensLei.firstItem();

        while (itensLei.hasNext()) {

            item = itensLei.next();

            // Capturar o último número de artigo para possiveis inserções
            if (item.getArtigo() != 0)
                artigo = item.getArtigo();

            // if (item.getId_dono() != 0)
            // continue;

            item.setData_inclusao(doc.getData_lei());
            Seeker.getInstance().itemleiDao.update(item);
        }

        HttpSession session = request.getSession();

        session.setAttribute("lei", doc);
        session.setAttribute("nivel", 800);
        session.setAttribute("artigo", artigo);

        // //doc.setId_tipolei(Seeker.getInstance().tlDao.selectOne(doc.getId_tipolei()));

        RegistroItemController.exportarDocumento(doc);

        ExportToSaplFacade.exportDoc(doc.getId(), "T");

        RequestDispatcher rd;
        // if (doc.getId_tipolei().getOrdem() >= 0) {
        rd = request.getRequestDispatcher(Urls.urlProjectSislegisOldBase + "gerarEstrutura.jsp");
        rd.include(request, response);
        // }
        // else {
        // rd =
        // request.getRequestDispatcher(Urls.urlProjectSislegisOldBase+"cad.jsp");
        // rd.include(request, response);
        // }

    }

}
