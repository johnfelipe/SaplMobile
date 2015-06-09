package br.gov.go.camarajatai.sislegisold.suport;

import java.util.GregorianCalendar;

import javax.servlet.jsp.JspWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class XMLStream {

	public Document doc;
	
	public Node registro = null;

	public XMLStream() {
		
		
		newDoc();
	}
	public void newDoc() {
		registro = null;
		doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(false);

		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.newDocument();
		} catch (ParserConfigurationException e) {

		}		
		
	}
	public void printXml(JspWriter out) {
		try {
			// Prepare the DOM document for writing
			Source source = new DOMSource(doc);

			// Prepare the output stream
			Result result = new StreamResult(out);
			Transformer xformer = TransformerFactory.newInstance().newTransformer();
			xformer.transform(source, result);
		
			// Prepare the output stream
			//result = new StreamResult(System.out);
			//xformer = TransformerFactory.newInstance().newTransformer();
			//xformer.transform(source, result);
		
		} catch (TransformerConfigurationException e) {
		} catch (TransformerException e) {
		}
	}	
	
	
	public void response(String tag,String texto) {
		Node nd = doc.getFirstChild();
		if (nd == null) {
			Element el = doc.createElement("response");
			doc.appendChild(el);
			Element elChild = doc.createElement(tag);
			elChild.setTextContent(texto);			
			el.appendChild(elChild);		
		}			
		else {			
			Element elChild = doc.createElement(tag);
			elChild.setTextContent(texto);			
			nd.appendChild(elChild);			
		}
	}
	
		
	public void addRegister() {
		Node nd = doc.getFirstChild();
		if (nd == null) {
			nd = doc.createElement("response");
			doc.appendChild(nd);
		}			
		registro = doc.createElement("registro");
		nd.appendChild(registro);		
	}
	
	public void addField(String tag, String value) {		
		Element el = doc.createElement(tag);
		el.setTextContent(value);		
		registro.appendChild(el);
	}
	
	public void addField(String tag, int value) {		
		Element el = doc.createElement(tag);
		el.setTextContent(String.valueOf(value));		
		registro.appendChild(el);
	}	
	public void addField(String tag, float value) {		
		Element el = doc.createElement(tag);
		el.setTextContent(String.valueOf(value));		
		registro.appendChild(el);
	}	
	public void addField(String tag, long value) {		
		Element el = doc.createElement(tag);
		el.setTextContent(String.valueOf(value));		
		registro.appendChild(el);
	}	
	public void addField(String tag, boolean value) {		
		Element el = doc.createElement(tag);
		el.setTextContent(String.valueOf(value));		
		registro.appendChild(el);
	}	
	public void addField(String tag, GregorianCalendar value) {		
		
		
		Element el = doc.createElement(tag);		
		elementWithDate(el, value);		
		registro.appendChild(el);
	}
	
	private void elementWithDate(Element el, GregorianCalendar g) {
		
		Element y = doc.createElement("y");
		Element m = doc.createElement("m");
		Element d = doc.createElement("d");
		Element h = doc.createElement("h");
		Element mn = doc.createElement("mn");
		Element s = doc.createElement("s");
		
		y.setTextContent(String.valueOf(g.get(GregorianCalendar.YEAR)));
		m.setTextContent(String.valueOf(g.get(GregorianCalendar.MONTH)));
		d.setTextContent(String.valueOf(g.get(GregorianCalendar.DAY_OF_MONTH)));
		h.setTextContent(String.valueOf(g.get(GregorianCalendar.HOUR_OF_DAY)));
		mn.setTextContent(String.valueOf(g.get(GregorianCalendar.MINUTE)));
		s.setTextContent(String.valueOf(g.get(GregorianCalendar.SECOND)));
		
		el.appendChild(y);
		el.appendChild(m);
		el.appendChild(d);
		el.appendChild(h);
		el.appendChild(mn);
		el.appendChild(s);
	}
}
