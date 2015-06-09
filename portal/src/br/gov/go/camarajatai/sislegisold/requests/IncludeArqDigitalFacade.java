package br.gov.go.camarajatai.sislegisold.requests;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import br.gov.go.camarajatai.sislegisold.business.Digitalization;
import br.gov.go.camarajatai.sislegisold.business.Digitalization.Page;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

/**
 * Servlet implementation class IncludeArqDigitalFacade
 */
// @WebServlet("/sislegisold/includeArqDigital")
public class IncludeArqDigitalFacade extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */

    public IncludeArqDigitalFacade() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletOutputStream out = response.getOutputStream();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        Object ob = Suport.getObjectSession("digitalization", "br.gov.go.camarajatai.sislegisold.business.Digitalization", request);
        Digitalization digit = (Digitalization) ob;

        List listFile = null;

        out.print(isMultipart);

        if (isMultipart) {
            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());

            // Parse the request
            try {
                listFile = servletFileUpload.parseRequest(request);
            } catch (FileUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            FileItem fItem = null;
            java.util.Iterator<FileItem> it = listFile.iterator();
            byte[] b = null;

            while (it.hasNext()) {
                fItem = it.next();
                if (!fItem.isFormField()) {

                    b = fItem.get();

                    if (b.length == 0)
                        continue;

                    Page page = digit.getNewPage();
                    page.nameFile = Digitalization.normatizeWords(fItem.getName());
                    page.bytesFile = b;
                    page.contentType = fItem.getContentType();

                    digit.add(page);
                }
            }

            // digit.generate(request.getSession().getId());
            response.sendRedirect(request.getHeader("referer"));
        }

        else {

            int sv = Integer.parseInt(request.getParameter("sv"));

            switch (sv) {

            case 1: {

                ob = Suport.getObjectSession("lei", "br.gov.go.camarajatai.sislegisold.dto.Documento", request);
                Documento lei = (Documento) ob;

                if (digit.size() == 1 && !digit.get(0).contentType.contains("jpeg")) {

                    lei.setArqdigital(digit.get(0).bytesFile);

                    lei.setContent_type(digit.get(0).contentType);
                    lei.setName_file(digit.get(0).nameFile);
                    lei.setSize_bytes_files(digit.get(0).bytesFile.length);

                } else {
                    digit.generate(request.getSession().getId());
                    lei.setArqdigital(digit.getArrayFile());
                }

                if (lei.getArqdigital() != null) {

                    lei.setPossuiarqdigital(true);

                    Seeker.getInstance().leiDao.update(lei);
                    Seeker.getInstance().leiDao.upDateArqDigital(lei);

                    ExportToSaplFacade.exportDoc(lei.getId(), "C");

                }

                request.getSession().invalidate();

            }
                break;

            case 2: {
                int pospagremove = Integer.parseInt(request.getParameter("pospag"));
                digit.get(pospagremove).bytesFile = null;

            }
                break;

            }
        }
    }

}
