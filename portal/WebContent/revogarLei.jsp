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

<%
Suport.validIntranet(request, response);
%>


<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%><html>

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
			
							
				<div class="iface_titulo"><span>Revogação de Lei</span></div>
				<div>
				
				<%
				
					int iddoc = Integer.parseInt(request.getParameter("iddoc"));
									
					Documento _lei = new Documento();
					_lei.setId(iddoc);
					_lei = Seeker.getInstance().leiDao.selectOne(_lei);
					
				%>
				
				<div class="linkItens">
				<span class="link"><%=_lei.getEpigrafe() %></span><br>
				</div>
				
				
				<div class="linkItens">
				
						<form action="<%=Urls.urlPortalSislegisOldBase%>revogarLei" method="post">
						Informe o identificador da Lei revogadora:<br>
						<input type="text" name="id_revogador" size="10"/><br><br>
						<input type="hidden" name="id" value = "<%=iddoc%>"/>
						<input type="submit" value ="Enviar"/>
						</form>
				</div>
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
