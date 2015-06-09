<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page
	import="br.gov.go.camarajatai.sislegisold.dao.PostgreConnection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.TreeSet"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipolei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Itemlei"%>
<%@page
	import="br.gov.go.camarajatai.sislegisold.business.IteratorLeis"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>
<%@page
	import="br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei"%>
<%@page import="java.util.Map.Entry"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>CÃmara Municipal de Jataí - Portal da Transparência Pública</title>

	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/jquery.js"></script>
		
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>	
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>	


	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/styleGeral.css">
	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/menu.css">



<script type="text/javascript">
function habilita(objeto)
{
 document.getElementById(objeto).style.visibility=(document.getElementById(objeto).style.visibility=='hidden'?'visible':'hidden');
 document.getElementById(objeto).style.height=document.getElementById(objeto).style.visibility=='hidden'?"0px":"25px";
}

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

			

<div class="iface_titulo"><span>Gerar Estrutura</span></div>
<div>

		<%

		Documento lei = null;	
		
		Object ob = request.getSession().getAttribute("lei");
		
		lei = null;
		if (ob != null) {
			
			lei = (Documento)ob;			
		
		%>
		<div class="titulo"><span><%=lei.getEpigrafe() %></span></div>
		
		<jsp:include page="escreverLei.jsp"/>
		
		<div align="center">
		
		
		
		<a href="<%=Urls.urlPortalSislegisOldBase%>generatorLeis?direcao=1"> <img alt="" src="<%=Urls.urlPortalSislegisBase%>img/setas_duplas_Cima.jpg" border="0"> </a>
		<%
		Entry<Integer,String> entry = null;
		entry  = CollectionItensDeLei.niveis.firstEntry();
		
		if (entry != null)
			do {
				%>
				
				<a href="<%=Urls.urlPortalSislegisOldBase%>generatorLeis?direcao=3&nivel=<%=entry.getKey() %>"><%=entry.getValue()%></a>&nbsp;&nbsp;&nbsp;
						
				<%
				
				entry = CollectionItensDeLei.niveis.higherEntry(entry.getKey());
			} while (entry != null);
		
		%>
		
		
		<br><br><br><br>
		</div>
		
		<%
		} 
		%>		
			
			
			</div>
		
		</div>

</div>
</div>







<script type="text/javascript">

	window.scrollTo(0, 10000000);
	
</script>
</body>
</html> 
