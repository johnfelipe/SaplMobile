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
<div class="Menu">
 
<%
if (Suport.isIntranet(request)) {
%>
	<ul class="onglets-vert InitMenu">	
	    <div id="iface_titulo">Administração</div>
		<li><a href="cad.jsp">Cadastro de Documentos</a></li>		
		<li><a href="listaPendentesClassificacao.jsp">Classificação</a></li>		
		<!-- li><a href="/publicacoes/pendencias?sv=2&tl=&p=1&q=1000">Pendências</a></li>
		<li><a href="tramita.jsp">Processos em Tramite</a></li-->
	</ul>
<%	
}
Tipodoc td = new Tipodoc(3);
Iterator<Tipolei> itTl = Seeker.getInstance().tlDao.selectAllWith(td).iterator();
Tipolei tl;
%>

<span style="padding: 10px 10px 0px 10px; font-size: 8pt; display:block; text-align: left; color:#900;">
Sua experiência de navegação em nosso portal pode ser melhor com você navegando pelo<br><strong>Google Chrome</strong>!</span>


<ul class="onglets-vert InitMenu">

<div>Inicio</div>

	<li><a href="http://www.camarajatai.go.gov.br">Portal C&acirc;mara</a></li>
	<li><a href="http://sapl.camarajatai.go.gov.br/sapl/1?mtnm=nm&autostart=on">Legisla&ccedil;&atilde;o</a></li>
	<li><a href="http://sislegis.camarajatai.go.gov.br/publicacoes">Doc's Administrativos</a></li>
	<li><a href="http://sapl.camarajatai.go.gov.br/sapl/1?mtnm=mt&autostart=on">Atividade Legislativa</a></li>	
	<li><a href="http://www.camarajatai.go.gov.br/portal/transparencia/despesas">Hist&oacute;rico de Despesas</a></li>
	
    <li><a href="http://sislegis.camarajatai.go.gov.br/publicacoes/olerite.do?tipo=1">Remun. dos Servidores</a></li>
	<li><a href="http://sislegis.camarajatai.go.gov.br/publicacoes/olerite.do?tipo=2">Quadro de Pessoal</a></li>
	
	
	<%while (itTl.hasNext()) { tl = itTl.next();%>
	<li><a href="<%=Urls.urlPortalSislegisOldBase%>seeker?sv=<%=td.getId()+"&tl="+tl.getId()%>"><%=tl.getDescr()%></a></li><%} %>
</ul>


<%

Iterator<Tipodoc> itTd = Seeker.getInstance().tdDao.getCollection().iterator();

while (itTd.hasNext()) {
	 
	td = itTd.next();

	if (td.getId() < 4 || (td.getId() == 9 && !Urls.appBase.equals("publicacoes")))
		continue;

	%>
	<ul class="onglets-vert InitMenu">
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
}%>



</div>
