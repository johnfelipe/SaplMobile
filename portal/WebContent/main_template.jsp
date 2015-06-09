<%@page import="br.gov.go.camarajatai.sislegisold.dto.DocDestaque"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipodoc"%>
<%@page import="java.util.List"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Câmara Municipal de Jataí­ - Portal da Transparência
Pública</title>

	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/jquery.js"></script>
		

<script type="text/javascript"
	src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>
<script type="text/javascript"
	src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>


<link rel="stylesheet"
	href="<%=Urls.urlPortalSislegisBase%>lib/styleGeral.css">
<link rel="stylesheet"
	href="<%=Urls.urlPortalSislegisBase%>lib/menu.css">

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
						
			<div class="sislegis_leftCol">
			
							
				<% if (comMenu) {
					
					
					%> 
					<jsp:include page="admin/menu.jsp"></jsp:include>
				<%	
				}	
				%>
			
			</div>
		
			<div class="sislegis_mainCol">
			
			
			
			</div>
		
		</div>

</div>

	<jsp:include page="admin/rodape.jsp"></jsp:include>

</body>
</html>
