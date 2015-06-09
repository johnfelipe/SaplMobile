package br.gov.go.camarajatai.sislegisold.suport;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Urls {

    public static final String appBase = "portal";

    // nao trocar portal aqui...
    public static final String siglaApp = appBase.equals("portal") ? "NJ" : "DA";

    public static final String urlPortalSislegisBase = "/" + appBase + "/";
    public static final String urlPortalSislegisOldBase = "/" + appBase + "/";

    public static final String urlProjectSislegisBase = "/";
    public static final String urlProjectSislegisOldBase = "/";

    public static final String urlPortalDomain = "http://sislegis.camarajatai.go.gov.br/" + appBase + "/";

    public static final String urlSaplNormas = "http://sapl.camarajatai.go.gov.br/sapl/consultas/norma_juridica/norma_juridica_mostrar_proc?cod_norma=";

    // public static final String urlPortalDomain =
    // "http://localhost:8080/publicacoes/";

    public static void forward(HttpServletRequest request, HttpServletResponse response, String msg, String view) throws ServletException, IOException {
        RequestDispatcher rd;
        rd = request.getRequestDispatcher(view);

        if (msg != null)
            request.setAttribute("msg", msg);

        rd.forward(request, response);
    }

}
