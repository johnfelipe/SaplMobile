package br.gov.go.camarajatai.sislegisold.lexml;

import java.io.StringWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

public class RegistroItemController {

    public static void exportarDocumento(Documento docSislegis) {

        RegistroItem registroItem = RegistroItemController.createRegistroItemForHTML(docSislegis);
        RegistroItemController.updateRegistro(registroItem);

    }

    public static void updateRegistro(RegistroItem registroItem) {

        if (registroItem == null)
            return;

        LEXMLConnection con = LEXMLConnection.getInstance();

        PreparedStatement pStm;
        try {

            pStm = con.newPreparedStatement("delete from registro_item where id_registro_item = ?");

            pStm.setString(1, registroItem.getIdRegistroItem());
            pStm.execute();

            pStm = con.newPreparedStatement("INSERT INTO registro_item " + "(id_registro_item, tx_metadado_xml, cd_status, cd_validacao, ts_registro_gmt) "
                    + "VALUES (?, ?, ?, ?, ?)");

            pStm.setString(1, registroItem.getIdRegistroItem());
            pStm.setString(2, registroItem.getTxMatadadoXml());
            pStm.setString(3, registroItem.getCdStatus());
            pStm.setString(4, registroItem.getCdValidacao());
            pStm.setTimestamp(5, registroItem.getTsRegistroGmt());

            pStm.execute();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

    public static RegistroItem createRegistroItemForHTML(Documento docSislegis) {

        Seeker.getInstance().tlDao.refreshTipos(docSislegis);

        if (docSislegis.getId_tipolei().size() == 0)
            return null;

        if (docSislegis.getId_tipolei().get(0).getLocalidade() == null || docSislegis.getId_tipolei().get(0).getLocalidade().length() == 0)
            return null;

        RegistroItem registroItem = null;

        try {

            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element root;
            Element child;
            Text text;

            root = createRootLexML(doc);

            // cria o elemento Item, adiciona atributos, texto interno e
            // adiciona ao XML
            createItem(root, "text/html", "http://sislegis.camarajatai.go.gov.br/portal/seeker?iddoc=" + docSislegis.getId());

            createDocIndividual(root, docSislegis, "@multivigente;texto.atualizado~texto;pt-br");

            if (!docSislegis.getPossuiarqdigital()) {
                createNode(root, "Epigrafe", docSislegis.getEpigrafe());
                createNode(root, "Ementa", docSislegis.getEmenta());
            }

            registroItem = new RegistroItem();
            registroItem.setIdRegistroItem("oai:sislegis.camarajatai.go.gov.br:sislegis/"
                    + Suport.complete(String.valueOf(docSislegis.getId()), "0", 6, Suport.LEFT) + ";html");
            registroItem.setCdStatus("N");
            registroItem.setCdValidacao("I");
            registroItem.setTsRegistroGmt(new Timestamp(new GregorianCalendar().getTimeInMillis()));
            registroItem.setTxMatadadoXml(parseStringXML(doc));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return registroItem;

    }

    public static RegistroItem createRegistroItemForPDF(Documento docSislegis) {

        Seeker.getInstance().tlDao.refreshTipos(docSislegis);

        if (docSislegis.getId_tipolei().size() == 0)
            return null;

        if (!docSislegis.getPossuiarqdigital())
            return null;

        if (docSislegis.getId_tipolei().get(0).getLocalidade() == null || docSislegis.getId_tipolei().get(0).getLocalidade().length() == 0)
            return null;

        LEXMLConnection con = LEXMLConnection.getInstance();

        RegistroItem registroItem = null;

        try {

            DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            Element root;
            Element child;
            Text text;

            root = createRootLexML(doc);

            // cria o elemento Item, adiciona atributos, texto interno e
            // adiciona ao XML
            createItem(root, "application/pdf", "http://sislegis.camarajatai.go.gov.br/portal/downloadFile.pdf?sv=2id=" + docSislegis.getId());

            createDocIndividual(root, docSislegis, "~texto;pt-br");

            createNode(root, "Epigrafe", docSislegis.getEpigrafe());
            createNode(root, "Ementa", docSislegis.getEmenta());

            registroItem = new RegistroItem();
            registroItem.setIdRegistroItem("oai:sislegis.camarajatai.go.gov.br:sislegis/"
                    + Suport.complete(String.valueOf(docSislegis.getId()), "0", 6, Suport.LEFT) + ";pdf");
            registroItem.setCdStatus("N");
            registroItem.setCdValidacao("I");
            registroItem.setTsRegistroGmt(new Timestamp(new GregorianCalendar().getTimeInMillis()));
            registroItem.setTxMatadadoXml(parseStringXML(doc));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return registroItem;

    }

    private static String parseStringXML(Document doc) throws TransformerFactoryConfigurationError, TransformerConfigurationException, TransformerException {
        // set up a transformer
        TransformerFactory transfac = TransformerFactory.newInstance();
        Transformer trans = transfac.newTransformer();
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
        trans.setOutputProperty(OutputKeys.INDENT, "yes");

        // create string from xml tree
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        DOMSource source = new DOMSource(doc);
        trans.transform(source, result);
        String xmlString = sw.toString();

        return xmlString;
    }

    private static void createNode(Element root, String nameNode, String textNode) {
        Element child;
        Text text;

        child = root.getOwnerDocument().createElement(nameNode);

        root.appendChild(child);
        text = root.getOwnerDocument().createTextNode(textNode);

        child.appendChild(text);
    }

    private static void createDocIndividual(Element root, Documento docSislegis, String sufixoURN) {

        Element child;
        Text text;
        child = root.getOwnerDocument().createElement("DocumentoIndividual");
        root.appendChild(child);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // adiciona a URN do documento
        text = root.getOwnerDocument().createTextNode(
                "urn:lex:" + docSislegis.getId_tipolei().get(0).getLocalidade() + ":" + docSislegis.getId_tipolei().get(0).getAutoridade() + ":"
                        + docSislegis.getId_tipolei().get(0).getTipo() + ":" + sdf.format(docSislegis.getData_lei()) + ";" + docSislegis.getNumero()
                        + sufixoURN);
        child.appendChild(text);
    }

    private static void createItem(Element root, String formato, String linkItem) {

        Element child;
        child = root.getOwnerDocument().createElement("Item");
        child.setAttribute("formato", formato);
        child.setAttribute("idPublicador", "16");
        child.setAttribute("tipo", "conteudo");
        root.appendChild(child);

        // adiciona texto ao elemento Item que é seu link
        Text text = root.getOwnerDocument().createTextNode(linkItem);
        child.appendChild(text);

    }

    private static Element createRootLexML(Document doc) {

        Element root;

        // create the root element and add it to the document
        root = doc.createElement("LexML");
        root.setAttribute("xmlns", "http://www.lexml.gov.br/oai_lexml");
        root.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        root.setAttribute("xsi:schemaLocation", "http://www.lexml.gov.br/oai_lexml http://projeto.lexml.gov.br/esquemas/oai_lexml.xsd");
        doc.appendChild(root);

        return root;

    }

}
