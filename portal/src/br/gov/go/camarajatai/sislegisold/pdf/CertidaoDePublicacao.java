package br.gov.go.camarajatai.sislegisold.pdf;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class CertidaoDePublicacao extends Relatorio {

    public void run(Documento doc, OutputStream out, int numPages, int numVias, HttpServletRequest request) throws DocumentException, MalformedURLException,
            IOException {

        Document document = new Document(PageSize.A4, 35, 15, 35, 15);
        document.setPageCount(1);

        PdfWriter pd = PdfWriter.getInstance(document, out);
        pd.setFullCompression();
        document.open();

        PdfContentByte cb = pd.getDirectContent();

        LineSeparator line;
        Paragraph p;
        PdfPTable tabExterna = null;
        PdfPCell caExterna;

        PdfPTable tab = null;
        PdfPCell ca;

        int numCols = 40;
        int widthCols = 8;

        tab = new PdfPTable(numCols);
        tab.setExtendLastRow(false);
        tab.setTotalWidth(numCols * widthCols);
        tab.setLockedWidth(true);

        int[] cols = new int[numCols];
        for (int i = 0; i < numCols; i++)
            cols[i] = widthCols;
        tab.setWidths(cols);

        int jj = 0;
        int numLinhas = 43;
        float bottomLine = 0f;

        Paragraph par = new Paragraph();
        par.add(new Chunk("C", new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
        par.add(new Chunk("ERTIDÃO DE ", new Font(FontFamily.HELVETICA, 9, Font.BOLD)));
        par.add(new Chunk("P", new Font(FontFamily.HELVETICA, 12, Font.BOLD)));
        par.add(new Chunk("UBLICAÇÃO", new Font(FontFamily.HELVETICA, 9, Font.BOLD)));
        par.add(new Chunk(" - Nº ", new Font(FontFamily.HELVETICA, 9, Font.BOLD)));

        if (doc.getCod_certidao() == 0)
            doc.setCod_certidao(Seeker.getInstance().leiDao.nextCodCertidao());

        String numero = String.valueOf(doc.getCod_certidao());

        par.add(new Chunk(Suport.complete(numero, "0", 6, Suport.LEFT) + "-" + Urls.siglaApp + "  " + Suport.DateTimeToStr(doc.getData_inclusao()), new Font(
                FontFamily.HELVETICA, 10, Font.BOLD)));

        // par.add(new Chunk("   -   99/99/9999 - 99:99", new
        // Font(FontFamily.HELVETICA, 9, Font.BOLD)));

        ca = new PdfPCell(par);
        ca.setColspan(40);
        ca.setVerticalAlignment(PdfPCell.ALIGN_TOP);
        ca.setHorizontalAlignment(PdfPCell.ALIGN_JUSTIFIED_ALL);
        ca.setPadding(0);
        setBordersCell(ca, 0f, 0f, 0f, 0f);
        tab.addCell(ca);

        ca = newCell(9f, false, "Certifico para os devidos fins, " + "nos termos do §1º art. 81 da Lei Orgânica do Município de Jataí, "
                                + "que o presente documento foi publicado eletronicamente no "
                                + "Portal Transparência Pública do Legislativo Municipal de Jataí.", 40, 1, -1f, PdfPCell.ALIGN_JUSTIFIED_ALL);
        setBordersCell(ca, 0f, 0f, 0f, 0f);
        tab.addCell(ca);

        String codigo = Suport.complete(numero, "0", 6, Suport.LEFT) + "-" + Urls.siglaApp + "  " + Suport.dateTimeYYYYMMDDHHMMSS(doc.getData_inclusao());

        Barcode39 codeEAN = new Barcode39();
        codeEAN.setCode(codigo);
        codeEAN.setAltText("");
        // codeEAN.setGuardBars(false);
        // codeEAN.setCodeType(Barcode39.CODE128);
        codeEAN.setSize(5);
        codeEAN.setBaseline(5);
        codeEAN.setBarHeight(30);

        ca = new PdfPCell(codeEAN.createImageWithBarcode(cb, null, null));
        ca.setColspan(40);
        // ca.setRowspan(2);
        ca.setVerticalAlignment(PdfPCell.ALIGN_TOP);
        ca.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        setBordersCell(ca, 0f, 0f, 0f, 0f);
        ca.setPaddingLeft(0);
        ca.setPaddingRight(0);
        tab.addCell(ca);

        Image im = Image.getInstance(request.getRealPath("") + "/img/brazao.png");

        im.scaleAbsolute(75, 53);
        ca = new PdfPCell(im);
        ca.setColspan(9);
        ca.setRowspan(4);
        ca.setVerticalAlignment(PdfPCell.ALIGN_TOP);
        ca.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        setBordersCell(ca, 0f, 0f, 0f, 0f);
        tab.addCell(ca);

        ca = newCell(9, false, " ", 31, 1, 0f, PdfPCell.ALIGN_RIGHT);
        setBordersCell(ca, 0f, 0f, 0f, 0f);
        tab.addCell(ca);
        ca = newCell(9, false, " ", 31, 1, 0f, PdfPCell.ALIGN_RIGHT);
        setBordersCell(ca, 0f, 0f, 0f, 0f);
        tab.addCell(ca);

        ca = newCell(9, false, " ", 31, 1, 0f, PdfPCell.ALIGN_RIGHT);
        setBordersCell(ca, 0f, 0f, 0.3f, 0f);
        tab.addCell(ca);

        ca = newCell(8, true, "Departamento de Documentação Eletrônica", 31, 1, 13f, PdfPCell.ALIGN_RIGHT);
        setBordersCell(ca, 0f, 0f, 0f, 0f);
        ca.setPadding(0);
        tab.addCell(ca);

        if (Urls.appBase.equals("portal")) {
            ca = newCell(5, false, "Documento público de " + (numPages == 1 ? "1 página" : numPages + " páginas")
                                   + ", "
                                   + "impresso em "
                                   + numVias
                                   + (numVias == 1 ? " via" : " vias")
                                   + ", "
                                   + "acessível em "
                                   + Urls.urlSaplNormas
                                   + doc.getId()
                                   + "\nCódigo Hash512:", 40, 1, -1f, PdfPCell.ALIGN_LEFT);
        } else {

            ca = newCell(5, false, "Documento público de " + (numPages == 1 ? "1 página" : numPages + " páginas")
                                   + ", "
                                   + "impresso em "
                                   + numVias
                                   + (numVias == 1 ? " via" : " vias")
                                   + ", "
                                   + "acessível em "
                                   + Urls.urlPortalDomain
                                   + "seeker?iddoc="
                                   + doc.getId()
                                   + "\nCódigo Hash512:", 40, 1, -1f, PdfPCell.ALIGN_LEFT);
        }

        setBordersCell(ca, 0.1f, 0f, 0f, 0f);
        ca.setPadding(0);
        tab.addCell(ca);

        Seeker.getInstance().leiDao.selectHashArqDigital(doc, "sha512");
        codigo = doc.getHash_arqdigital().toUpperCase();
        ca = newCell(6f, false, codigo.substring(0, codigo.length() / 2) + "\n" + codigo.substring(codigo.length() / 2), 40, 1, -1f, PdfPCell.ALIGN_LEFT);
        setBordersCell(ca, 0f, 0f, 0f, 0f);
        ca.setPadding(0);
        tab.addCell(ca);
        /*
         * BarcodeQRCode qrcode = new BarcodeQRCode(
         * "teste de grafia de texto em qr code.teste de grafia de texto em qr code.teste de grafia de texto em qr code.teste de grafia de texto em qr code.teste de grafia de texto em qr code"
         * , 10, 10, null); im = qrcode.getImage(); im.scalePercent(200); ca =
         * new PdfPCell(im); ca.setColspan(40); // ca.setRowspan(4);
         * ca.setVerticalAlignment(PdfPCell.ALIGN_TOP);
         * ca.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); setBordersCell(ca,
         * 0f, 0f, 0f, 0f); tab.addCell(ca);
         */

        /*
         * Barcode128 codeEAN = new Barcode128(); codeEAN.setCode(
         * "52689b25437ee95b2a1a9ce76bfbb805fd1df5541374503d3b21e7ca13a7cd9a96e2f89cd865cc6f9dc4ce64775303dd09a7aec969d04c5fad98de717d139238"
         * ); codeEAN.setAltText(""); //codeEAN.setGuardBars(false);
         * codeEAN.setCodeType(Barcode128.CODE128); codeEAN.setSize(5);
         * codeEAN.setBaseline(5); codeEAN.setBarHeight(20); ca = new
         * PdfPCell(codeEAN.createImageWithBarcode(cb, null, null));
         * ca.setColspan(40); //ca.setRowspan(4);
         * ca.setVerticalAlignment(PdfPCell.ALIGN_TOP);
         * ca.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); setBordersCell(ca,
         * 0f, 0f, 0f,0f); tab.addCell(ca);
         */

        tabExterna = new PdfPTable(1);
        tabExterna.setWidthPercentage(60);

        caExterna = new PdfPCell(tab);
        caExterna.setHorizontalAlignment(PdfPCell.ALIGN_LEFT);
        caExterna.setPadding(10);
        caExterna.setPaddingBottom(3);
        caExterna.setRotation(180);
        setBordersCell(caExterna, 1f, 1f, 1f, 1f);

        tabExterna.addCell(caExterna);
        tabExterna.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);

        document.add(tabExterna);

        document.close();

    }

}
