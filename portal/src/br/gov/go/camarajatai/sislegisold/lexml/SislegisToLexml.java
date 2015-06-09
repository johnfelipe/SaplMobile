package br.gov.go.camarajatai.sislegisold.lexml;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;

/**
 * Servlet implementation class SislegisToLexml
 */
public class SislegisToLexml extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SislegisToLexml() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Documento> lista = Seeker.getInstance().leiDao.selectAll();

        RegistroItem registroItem;

        for (Documento docSislegis : lista) {

            /*
             * String quebraEmentaInicio[] =
             * docSislegis.getEmenta().split("\""); String s = ""; for (int i =
             * 0; i < quebraEmentaInicio.length; i++) { s +=
             * quebraEmentaInicio[i]; }
             * 
             * docSislegis.setEmenta(s);
             * 
             * Seeker.getInstance().leiDao.update(docSislegis);
             */

            /*
             * String quebraEmentaInicio[] = docSislegis.getEmenta().split("");
             * String s = ""; for (int i = 0; i < quebraEmentaInicio.length;
             * i++) { s += quebraEmentaInicio[i]; }
             * 
             * String quebraEmentaFim[] = s.split(""); s = ""; for (int i = 0;
             * i < quebraEmentaFim.length; i++) { s += quebraEmentaFim[i]; }
             * docSislegis.setEmenta(s);
             * 
             * Seeker.getInstance().leiDao.update(docSislegis);
             */

            registroItem = RegistroItemController.createRegistroItemForHTML(docSislegis);
            RegistroItemController.updateRegistro(registroItem);

            if (registroItem != null)
                System.out.print(registroItem.getIdRegistroItem());

            registroItem = RegistroItemController.createRegistroItemForPDF(docSislegis);
            RegistroItemController.updateRegistro(registroItem);

            if (registroItem != null)
                System.out.print("-------------" + registroItem.getIdRegistroItem());

            System.out.println();
        }
    }
}
