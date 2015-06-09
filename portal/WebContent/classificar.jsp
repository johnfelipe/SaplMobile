<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipodoc"%>
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

			

<div class="iface_titulo"><span>Classificação</span></div>
<div>

		<%

		Documento lei = null;	
		
		Object ob = request.getAttribute("lei");
		
		lei = null;
		if (ob != null) {
			
			lei = (Documento)ob;	
			
			Seeker.getInstance().tlDao.refreshTipos(lei);
		
		%>
		 
	
<form action="<%=Urls.urlPortalSislegisOldBase%>insertionLeis"  style="float: left; width: 250px;">
	<input type="hidden" name="id"  value="<%=lei.getId() %>">	 
	<input type="hidden" name="classificacao" value="0">	 
		 
		<div align="center"> <input type="submit" value="Gravar Alterações"> </div>
	
		
<%
Tipolei tl;
Tipodoc td = null;

Iterator<Tipodoc> itTd = Seeker.getInstance().tdDao.getCollection().iterator();
Iterator<Tipolei> itTl = null;

while (itTd.hasNext()) {
	 
	td = itTd.next();
	
	if (td.getId() < 4)
		continue;

	%>
	<ul class="onglets-vert InitMenu">
	<div id="iface_titulo"><%=td.getDescr() %></div>		
	<%
	itTl = Seeker.getInstance().tlDao.selectAllWith(td).iterator();	
	while (itTl.hasNext()) { 
		tl = itTl.next();		 
		
		
		boolean flagCheckbox = false;
		
		for (Tipolei tlDoc : lei.getId_tipolei()) {
			
			if (tlDoc.getId() == tl.getId()) {
				flagCheckbox = true;
				break;
			}
				
		}		
		
		if (tl.getLinkAlternativo() != null) {
		%>
		<li><label><input type="checkbox" name="tipolei" value="<%=tl.getId()%>" <%=flagCheckbox?"checked":"" %>  /> <a href=""><%=tl.getDescr()%></a></label></li>		
	<%
	   }
		else { 
	%>
		<li><label><input style="float:left;" type="checkbox" name="tipolei" <%=flagCheckbox?"checked":"" %>  value="<%=tl.getId()%>"/><a><%=tl.getDescr()%></a></label></li>
	<%}
	}
	%>
	</ul>	
<%
}%> 
		<div align="center"> <input type="submit" value="Gravar Alterações"> </div>
	 
		 </form>
		  
		<hr>
		<div class="titulo"><span><%=lei.getEpigrafe() %></span></div>
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		
		<jsp:include page="escreverLeiAnon.jsp"/>
		
		<div align="center">
		
		
		
		
		
		<br><br><br><br>
		</div>
		
		<%
		} 
		%>		
			
			
			</div>
		
		</div>

</div>
</div>



 
</body>
</html> 
