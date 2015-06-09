<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Itemlei"%><html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Câmara Municipal de Jataí - Portal da Transparência Pública</title>

	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/jquery.js"></script>
		
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>	
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>	


	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/styleGeral.css">
	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/menu.css">

<script type="text/javascript"> 

</script> 

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
		Documento lei = null;	
		Documento _lei = null;	
		
		Object ob = request.getAttribute("lei");
		
		if (ob == null) {
			ob = request.getSession().getAttribute("lei");
		}
		
		
		lei = null;
		
		
		if (ob != null) {
			
			lei = (Documento)ob;	
		
		CollectionItensDeLei collection = lei.getItemleis();
		
		
		int leiAtual = -1;
		Itemlei itemLei;
		
		
		%>
		
			
				<div class="ContainersLeft" style="margin-right: 0px;">
					<div class="iface_titulo">Pesquisa<%=lei.getId()!=0?" restrita a: "+lei.getEpigrafe():" Global" %></div>
		
					<form method="post" action="<%=Urls.urlPortalSislegisOldBase%>seeker">
						<!-- Por nº de Lei:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->Por palavra:<br>
						<input type="hidden" value="<%=lei.getId() %>" name="iddoc" size=6>
						<input type="text" name="busca" size="30" />
						<input type="submit" value="Buscar"></form>
				</div>
						
		<br><br>
		<%		
		
		int qtdItem = collection.size();
		
		if (qtdItem == 0)
			out.println("Sua pesquisa não foi encontrada em nossa base de dados...");
		else if (qtdItem == 1)
			out.println("Foi encontrado um item...");
		else 
			out.println("Foram encontrados "+qtdItem+" itens");
		
		
		if (collection.hasNext()) 
		do {
			
			itemLei = collection.next();
			if (leiAtual == -1 || leiAtual != itemLei.getId_lei()) {
				
				if (leiAtual != -1) 
					out.println("</div><br>");
				
				out.println("<div class=\"linkItens\">");
				
				leiAtual = itemLei.getId_lei();
				
				_lei = new Documento();
				_lei.setId(itemLei.getId_lei());
				_lei = Seeker.getInstance().leiDao.selectOne(_lei);
				
				if (_lei.getPossuiarqdigital()) { %>
					<a href="downloadFile.pdf?sv=2&id=<%=_lei.getId()%>"><img src="<%=Urls.urlPortalSislegisBase%>img/pdf_16.gif" alt="Download do arquivo digital" title="Download do arquivo digital" border=0></a>
				<%} %><span class="contadores" title="Quantidade de acessos a este documento"> (<%=_lei.getConsultas() %>)</span>
			
				<a class="link" href="seeker?iddoc=<%=_lei.getId()%>" onmouseover="javascript:getElementById('id_<%=_lei.getId() %>').style.fontSize = '14pt';" onmouseout="javascript:getElementById('id_<%=_lei.getId() %>').style.fontSize = '11pt';"><%=_lei.getEpigrafe() %></a><br>
				<div class="coment" id="id_<%=_lei.getId() %>"><%=_lei.getEmenta() %></div>
				<%
			}			
			%>
			<%="<a class=\"coment\" href=\"seeker?iddoc="  +  itemLei.getId_lei()  +  "#"  +  itemLei.getId()  +  "\">"  +  itemLei.getNomeclaturaCompletaParaPesquisa() +"</a>&nbsp;&nbsp;"+itemLei.getTexto()%><br><br><br>
			<%		
		} while (collection.hasNext());
		
		if (leiAtual != -1)
			out.println("</div>");
		}
		%><br>
			
			
			</div>
		
		</div>

</div>

	<jsp:include page="admin/rodape.jsp"></jsp:include>

</body>
</html> 
