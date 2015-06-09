<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="br.gov.go.camarajatai.sislegisold.dto.Itemlei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%><html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Câmara Municipal de Jataí - Portal da Transparência Pública</title>

	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/jquery.js"></script>
		
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>	
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>	


	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/styleGeral.css">
	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/menu.css">
   
</head> 
<body> 
<% 
boolean comMenu = true; 
%>
 

<div class="GlobalStyle">
	
		<jsp:include page="admin/topo.jsp"></jsp:include>
	
	
		<div class="Corpo">
								
				<% if (comMenu) {%>
				
						
			<div class="sislegis_leftCol">
			
				 
					<jsp:include page="admin/menu.jsp"></jsp:include>
				
			</div>
				<%	
				}	
				%>
			
		
			<div class="sislegis_mainCol">
			
			
			<%


boolean isIntranet = Suport.isIntranet(request);

%>


<table  align="center">
	<tr>
		<td>
		<%
			Suport.validIntranet(request, response);
		%> <%
 	Object ob = request.getAttribute("item");

 	Itemlei item = null;
 	if (ob == null) {

 		item = new Itemlei();
 	} else {
 		item = (Itemlei) ob;
 		request.removeAttribute("lei");
 	}
 	Itemlei it = Itemlei.clone(item);
 	if (item.getSubsubitem() != 0)
 		item.setSubsubitem(item.getSubsubitem() + 1);
 	else if (item.getSubitem() != 0)
 		item.setSubitem(item.getSubitem() + 1);
 	else if (item.getItem() != 0)
 		item.setItem(item.getItem() + 1);
 	else if (item.getAlinea() != 0)
 		item.setAlinea(item.getAlinea() + 1);
 	else if (item.getIncisovarvar() != 0)
 		item.setIncisovarvar(item.getIncisovarvar() + 1);
 	else if (item.getIncisovar() != 0)
 		item.setIncisovar(item.getIncisovar() + 1);
 	else if (item.getInciso() != 0)
 		item.setInciso(item.getInciso() + 1);
 	else if (item.getParagrafo() != 0)
 		item.setParagrafo(item.getParagrafo() + 1);
 	else if (item.getArtigovar() != 0)
 		item.setArtigovar(item.getArtigovar() + 1);
 	else if (item.getArtigo() != 0)
 		item.setArtigo(item.getArtigo() + 1);
 	else if (item.getItemsecao() != 0)
 		item.setItemsecao(item.getItemsecao() + 1);
 	else if (item.getSubsecao() != 0)
 		item.setSubsecao(item.getSubsecao() + 1);
 	else if (item.getSecaovar() != 0)
 		item.setSecaovar(item.getSecaovar() + 1);
 	else if (item.getSecao() != 0)
 		item.setSecao(item.getSecao() + 1);
 	else if (item.getCapitulo() != 0)
 		item.setCapitulo(item.getCapitulo() + 1);
 	else if (item.getTitulo() != 0)
 		item.setTitulo(item.getTitulo() + 1);
 	else if (item.getParte() != 0)
 		item.setParte(item.getParte() + 1);
 	else if (item.getLivro() != 0)
 		item.setLivro(item.getLivro() + 1);
 	else if (item.getAnexo() != 0)
 		item.setAnexo(item.getAnexo() + 1);
 %>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()+1%>&livro=0&parte=0&titulo=0&capitulo=0&capitulovar=0&secao=0&secaovar=0&subsecao=0&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Anexo <%=it.getAnexo()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()+1%>&parte=0&titulo=0&capitulo=0&capitulovar=0&secao=0&secaovar=0&subsecao=0&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Livro <%=it.getLivro()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()+1%>&titulo=0&capitulo=0&capitulovar=0&secao=0&secaovar=0&subsecao=0&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Parte <%=it.getParte()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()+1%>&capitulo=0&capitulovar=0&secao=0&secaovar=0&subsecao=0&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Titulo <%=it.getTitulo()+1%></a><br>
			
		<!-- Capitulos -->
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()+1%>&secao=0&secaovar=0&subsecao=0&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Capitulo <%=it.getCapitulo()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()+1%>&secao=0secaovar=0&&subsecao=0&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Capitulo .var<%=it.getCapitulovar()+1%></a><br>

		<!-- Seções -->
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()+1%>&secaovar=0&subsecao=0&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Seção <%=it.getSecao()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()+1%>&subsecao=0&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Seção <%=it.getSecaovar()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()+1%>&artigo=0&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Subseção <%=it.getSubsecao()+1%></a><br>

		<!-- Artigo -->
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()+1%>&artigovar=0&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Artigo <%=it.getArtigo()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()%>&artigovar=<%=it.getArtigovar()+1%>&paragrafo=0&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Artigo <%=it.getArtigo()+1%></a><br>

			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()%>&artigovar=<%=it.getArtigovar()%>&paragrafo=<%=it.getParagrafo()+1%>&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0">Parágrafo <%=it.getParagrafo()+1%></a>&nbsp;&nbsp;&nbsp;
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()%>&artigovar=<%=it.getArtigovar()%>&paragrafo=1&inciso=0&alinea=0&item=0&subitem=0&subsubitem=0&nivel=1001">Parágrafo Único</a>
			<br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()%>&artigovar=<%=it.getArtigovar()%>&paragrafo=<%=it.getParagrafo()%>&inciso=<%=it.getInciso()+1%>&alinea=0&item=0&subitem=0&subsubitem=0">Inciso <%=it.getInciso()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()%>&artigovar=<%=it.getArtigovar()%>&paragrafo=<%=it.getParagrafo()%>&inciso=<%=it.getInciso()%>&alinea=<%=it.getAlinea()+1%>&item=0&subitem=0&subsubitem=0">Alinea <%=it.getAlinea()+1%></a><br>

			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()%>&artigovar=<%=it.getArtigovar()%>&paragrafo=<%=it.getParagrafo()%>&inciso=<%=it.getInciso()%>&alinea=<%=it.getAlinea()%>&item=<%=it.getItem()+1%>&subitem=0&subsubitem=0">Item <%=it.getItem()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()%>&artigovar=<%=it.getArtigovar()%>&paragrafo=<%=it.getParagrafo()%>&inciso=<%=it.getInciso()%>&alinea=<%=it.getAlinea()%>&item=<%=it.getItem()%>&subitem=<%=it.getSubitem()+1%>&subsubitem=0">Sub-Item <%=it.getSubitem()+1%></a><br>
			<a href= "<%=Urls.urlPortalSislegisOldBase%>insertionItemLei?iddoc=<%=it.getId_lei()%>&anexo=<%=it.getAnexo()%>&livro=<%=it.getLivro()%>&parte=<%=it.getParte()%>&titulo=<%=it.getTitulo()%>&capitulo=<%=it.getCapitulo()%>&capitulovar=<%=it.getCapitulovar()%>&secao=<%=it.getSecao()%>&secaovar=<%=it.getSecaovar()%>&subsecao=<%=it.getSubsecao()%>&artigo=<%=it.getArtigo()%>&artigovar=<%=it.getArtigovar()%>&paragrafo=<%=it.getParagrafo()%>&inciso=<%=it.getInciso()%>&alinea=<%=it.getAlinea()%>&item=<%=it.getItem()%>&subitem=<%=it.getSubitem()%>&subsubitem=<%=it.getSubsubitem()+1%>">SubSub-Item <%=it.getSubsubitem()+1%></a><br>
		</td>
	</tr>
	<tr>
		<td>

		<form method="post" action="<%=Urls.urlPortalSislegisOldBase%>insertionItemLei">

		<hr size="1">
		idLei:<input type="text" name="id_lei" size="6" value="<%=item.getId_lei() %>">&nbsp;&nbsp;&nbsp;
		idAlterador:<input type="text" name="id_alterador" size="6" value="<%=item.getId_alterador() %>">&nbsp;&nbsp;&nbsp;
		idDono:<input type="text" name="id_dono" size="6" value="<%=item.getId_dono() %>">
		<br>
		link alterador:<input type="text" name="link_alterador" value="<%=item.getLink_alterador() %>" size="30"><br>
		<input type="checkbox" name="revoga" <%=item.getRevogado()?"checked=\"checked\"":"" %>>Revogado</input>
		<input type="checkbox" name="altera" <%=item.getAlterado()?"checked=\"checked\"":"" %>>Alterado</input>
		<input type="checkbox" name="inclui" <%=item.getIncluido()?"checked=\"checked\"":"" %>>Incluido</input>
		<hr size="1">
		
		
		Anexo:<input type="text" name="anexo" size="6" value="<%=item.getAnexo()%>"><br>
		Livro:<input type="text" name="livro" size="6" value="<%=item.getLivro()%>">&nbsp;&nbsp;&nbsp;
			Parte:<input type="text" name="parte" size="6" value="<%=item.getParte()%>">&nbsp;&nbsp;&nbsp;
		Título:<input type="text" name="titulo" size="6" value="<%=item.getTitulo()%>"><br><br>
			Capítulo:<input	type="text" name="capitulo" size="6" value="<%=item.getCapitulo()%>">&nbsp;&nbsp;&nbsp;
			Cqpítulo.var:<input	type="text" name="capitulovar" size="6" value="<%=item.getCapitulovar()%>"><br><br>
		Seção:<input type="text" name="secao" size="6" value="<%=item.getSecao()%>">&nbsp;&nbsp;&nbsp;
		Seção.var:<input	type="text" name="secaovar" size="6" value="<%=item.getSecaovar()%>"><br><br>
			Subseção:<input type="text" name="subsecao" size="6" value="<%=item.getSubsecao()%>">&nbsp;&nbsp;&nbsp;
			Itemseção:<input type="text" name="itemsecao" size="6" value="<%=item.getItemsecao()%>"><br><br>
		Artigo:<input type="text" name="artigo" size="6" value="<%=item.getArtigo()%>">&nbsp;&nbsp;&nbsp;
		ArtigoVar:<input type="text" name="artigovar" size="6" value="<%=item.getArtigovar()%>"><br><br>
			Paragrafo:<input type="text" name="paragrafo" value="<%=item.getParagrafo()%>" size="6">&nbsp;&nbsp;
				<input type="checkbox" name="unico">Único</input><br><br>
			Inciso:<input type="text" name="inciso"	value="<%=item.getInciso()%>" size="6">&nbsp;
			<input type="text" name="incisovar"	value="<%=item.getIncisovar()%>" size="6">&nbsp;
			<input type="text" name="incisovarvar"	value="<%=item.getIncisovarvar()%>" size="6"><br><br>
			Alinea:<input type="text" name="alinea"	value="<%=item.getAlinea()%>" size="6"><br><br>
			Item:<input type="text" name="item"	value="<%=item.getItem()%>" size="6">&nbsp;&nbsp;&nbsp;
			SubItem:<input type="text" name="subitem"	value="<%=item.getSubitem()%>" size="6">&nbsp;&nbsp;&nbsp;
			SubSubItem:<input type="text" name="subsubitem"	value="<%=item.getSubsubitem()%>" size="6">&nbsp;&nbsp;&nbsp;
		<br>
		Texto:<br>
		<textarea name="texto" cols="50" rows="8"></textarea>
		<input type="submit" value="Enviar">
		
		</form>

		</td>
	</tr>
</table>
				
			</div>
		
		</div>

</div>




<script type="text/javascript">

	window.scrollTo(0, 10000000);
	
</script>

</body>
</html> 
