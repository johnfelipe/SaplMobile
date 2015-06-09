package br.gov.go.camarajatai.sislegisold.suport;

//package html;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Matheus Silva Santos
 */
public class ManipulaHTML {

	/* Lista de tags válidas no HTML, em todas suas especificações.
	 * Mais confiável do que tentar descobrir quais são as tags válidas pelo
	 * código-fonte da página.
	 */
	private static List<String> tags_validas = Arrays.asList("a", "abbr", "acronym", "address", "applet",
			"area", "article", "aside", "audio", "b", "base", "basefont", "bdi", "bdo", "big", "blockquote",
			"body", "br", "canvas", "caption", "center", "cite", "code", "col", "colgroup", "command",
			"datalist", "dd", "del", "details", "dfn", "dialog", "dir", "div", "dl", "dt", "em", "embed",
			"fielset", "figcaption", "figure", "font", "footer", "form", "frame", "frameset", "h1", "h2",
			"h3", "h4", "h5", "h6", "head", "header", "hr", "html", "i", "iframe", "img", "input", "ins",
			"kbd", "keygen", "label", "legend", "li", "link", "map", "mark", "menu", "meta", "meter", "nav",
			"noframes", "noscript", "object", "ol", "optgroup", "option", "output", "p", "param", "pre",
			"progress", "q", "rp", "rt", "ruby", "s", "samp", "script", "section", "select", "small", "source",
			"span", "strike", "strong", "style", "sub", "summary", "sup", "table", "tbody", "td", "textarea",
			"tfoot", "th", "thead", "time", "title", "tr", "track", "tt", "u", "ul", "var", "video", "wbr");

	/* Lista de atributos válidos no HTML, semelhante ao caso da lista de tags. */
	private static List<String> atributos_validos = Arrays.asList("width", "abbr", "accept-charset", "accept", "accesskey",
			"action", "align", "alink", "alt", "async", "autocomplete", "autofocus", "autoplay", "archive", "axis",
			"background", "bgcolor", "bordercolor", "border", "buffered", "cellpadding", "cellspacing", "char", "charoff", "challenge",
			"charset", "checked", "cite", "class", "classid", "clear", "code", "codebase", "codetype", "color",
			"cols", "colspan", "compact", "content", "contenteditable", "contextmenu", "coords", "data", "datetime",
			"declare", "defer", "dir", "disabled", "enctype", "face", "for", "frame", "frameborder", "headers",
			"height", "href", "hreflang", "hspace", "http-equiv", "id", "ismap", "label", "lang", "language", "link",
			"longdesc", "marginheight", "marginwidth", "maxlength", "media", "method", "multiple", "name", "nohref",
			"noresize", "noshade", "nowrap", "object", "onblur", "onchange", "onclick", "ondblclick", "onfocus",
			"onkeydown", "onkeypress", "onkeyup", "onload", "onmousedown", "onmousemove", "onmouseout", "onmouseover",
			"onmouseup", "onreset", "onselect", "onsubmit", "onunload", "placeholder", "profile", "prompt", "readonly",
			"rel", "rev", "rows", "rowspan", "rules", "scheme", "scope", "scrolling", "selected", "shape", "size",
			"span", "src", "standby", "start", "style", "summary", "tabindex", "target", "text", "title", "type",
			"usemap", "valign", "value", "valuetype", "version", "vlink", "vspace");


	private static List<String> tags_table = Arrays.asList("table", "td", "tr", "br");


	/**
	 *
	 * @param html Código HTML a ser processado
	 * @param tags_desejadas Lista com as tags que não devem ser exluídas
	 * @param atributos_desejados Lista com os atributos que não devem ser excluídos
	 * @return Código HTML processado
	 */


	public static String limparTable(String html) {
		
		if (html.indexOf("table") == -1 && html.indexOf("TABLE") == -1)
			return html;
			
		List<String> tags_table = Arrays.asList("table", "td", "tr", "br", "ol", "li", "ul", "p");

		List<String> attr_table = Arrays.asList("border", "start", "colspan", "rowspan", "style");

		return eliminaTagsEAtributosInuteis(html, tags_table, attr_table);
	}

	public static String eliminaTagsEAtributosInuteis(String html, List<String> tags_desejadas, List<String> atributos_desejados) {
		// TODO Arranjar um nome melhor para este método
		String regex;

		for(String tag : tags_validas) {                
			if(!tags_desejadas.contains(tag.toLowerCase())) {
				regex = "<\\s*/?"+tag+"\\b/?.*?>";
				html = html.replaceAll(regex, "");
				regex = "<\\s*/?"+tag.toUpperCase()+"\\b/?.*?>";
				html = html.replaceAll(regex, "");
			}
		}
		
		for(String atributo : atributos_validos) {
			if(!atributos_desejados.contains(atributo.toLowerCase())) {
				regex = "(<\\s*/?\\.+)?(\\s"+atributo+"\\b\\s*=?\\s*[\"|']?#?\\w+[\"|']?%?)(\\s?.*/?\\s?>)";
				html = html.replaceAll(regex, "$1$3");
				regex = "(<\\s*/?\\.+)?(\\s"+atributo.toUpperCase()+"\\b\\s*=?\\s*[\"|']?#?\\w+[\"|']?%?)(\\s?.*/?\\s?>)";
				html = html.replaceAll(regex, "$1$3");
			//	html = html.replaceAll("%", "");
			}            
		}		

		return html;
	}

}
