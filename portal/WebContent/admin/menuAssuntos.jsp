<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipodoc"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipolei"%>
<%@page import="java.util.TreeSet"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>

<% 
Tipodoc td;
Iterator<Tipolei> itTl;
Tipolei tl; 

Iterator<Tipodoc> itTd = Seeker.getInstance().tdDao.getCollection().iterator();

boolean flagInicio = true;
while (itTd.hasNext()) {
	 
	td = itTd.next();

	if (td.getId() != 9 && Urls.appBase.equals("portal"))
		continue;
	 
	
	if (flagInicio) {
		flagInicio = false;
		%>
		<div class="Menu"> 
		<%
	}

	%>
	<ul class="onglets-vert InitMenu" style="margin-top:0px;">
	<div id="iface_titulo"><%=td.getDescr() %></div>	
	
	<%
	itTl = Seeker.getInstance().tlDao.selectAllWith(td).iterator();	
	while (itTl.hasNext()) { 
		tl = itTl.next();
		
		String str = "";
		
		//if (tl.getOrdem() % 1000 != 0 && tl.getOrdem() >= 1000 )
		//	str = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
		
		
		
		
		if (tl.getLinkAlternativo() != null) {
		%>
		<li><a href="<%=Urls.urlPortalSislegisOldBase+tl.getLinkAlternativo()%>"><%=str %><%=tl.getDescr()%></a></li>		
	<%
	   }
		else { 
	%>
		<li><a href="<%=Urls.urlPortalSislegisOldBase%>seeker?sv=<%=td.getId()+"&tl="+tl.getId()%>"><%=str %><%=tl.getDescr()%></a></li>
	<%}
	}
	%>
	</ul>	
	<%
	
	if (!itTd.hasNext()) { 
		%>  
	</div>
		<%
	}
}%>
