<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%><%
Suport.validIntranet(request, response);
%>
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

<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%><html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Câmara Municipal de Jataí - Portal da Transparência Pública</title>

	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/jquery.js"></script>
		
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>	
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>	
    <script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/ckeditor/ckeditor.js"></script>

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
			
			
		<div class="iface_titulo"><span>Lançamentos</span></div>
		
		
		<div>

		<%

		Documento lei = null;	
		Itemlei item = null;
		
		Object ob = request.getSession().getAttribute("lei");
		
		
		lei = null;
		if (ob != null) {
			
			lei = (Documento)ob;
			
			ob = request.getSession().getAttribute("item");
			
			if (ob != null) {
				item = (Itemlei) ob;
		
		%>
		<div class="titulo"><span><%=lei.getEpigrafe() %></span></div>
		
		<br><br>
		<br>
		

		<form action="<%=Urls.urlPortalSislegisOldBase%>lancamentos" method="post">
		Informe:<br>
		<%=item.getNomeclaturaCompleta()%><br>
		<input type="hidden" name="id" value = "<%=item.getId() %>"/>
		
		
		<!--  textarea rows="20" cols="80" name="texto" onkeyup="Javacript:document.getElementById('preview').innerHTML = this.value"></textarea-->
		
		
		
		<textarea id="texto" name="texto" tabindex="10"  style="width:100%;" rows="6"></textarea>
		
		
		
		
		<input type="submit" value ="Enviar"/>
		</form>
		<div id="preview"></div>
		
		
		<jsp:include page="escreverLei.jsp"></jsp:include>
		
		<%
			}
			else {
				%>
			
				<div class="titulo"><span><%=lei.getEpigrafe() %></span></div>	
				
				<br><br><br><br><br><br><br><br>		
				<div class="titulo"><span>Lançamento para este documento está completo.</span></div>
				
				<%
				request.getSession().invalidate();
				
			}
		} 
		else {
		%>		
			<form action="<%=Urls.urlPortalSislegisOldBase%>lancamentos" method="post">
			Informe o número da norma para seus trabalhos: 			
			<input type="text" size="10" name="iddoc">
			<input type="submit" value ="Iniciar"/>
			</form>
			
			
			
		<%
		} 
		%>		
			
			
			</div>
		
		</div>

</div>
</div>

	<jsp:include page="admin/rodape.jsp"></jsp:include>

<script type="text/javascript">
	window.scrollTo(0, 1);
	
</script>
</body>
</html> 
