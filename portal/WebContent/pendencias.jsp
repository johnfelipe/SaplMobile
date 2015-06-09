<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.PesquisaDeDocumentos"%>
<%@page import="java.util.List"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

					<%
					PesquisaDeDocumentos pesquisa = (PesquisaDeDocumentos)request.getSession().getAttribute("pesquisa");
					%>	
					
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Câmara Municipal de Jataí - Portal da Transparência Pública - <%=pesquisa.getTitulo() %></title>

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


							
					
					<div class="iface_titulo">
					<span><%=pesquisa.getTitulo() %></span>		
					<% if (pesquisa.getTotalPaginas() > 0) { %> 
								
					<span class="linkPages">Página <%=pesquisa.getPaginaDisplay() %> de <%=pesquisa.getTotalPaginas() %></span>
					
					<div style="
						position: absolute;
						text-align: right;
						right: 4px;
						top: 0px;
						
					">
					<%
					if ((pesquisa.getPagina()/10)*10-1 >= 0) {
						%>
						<a href="<%=Urls.urlPortalSislegisOldBase%>pendencias?sv=<%=pesquisa.getTd().getId()%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=(pesquisa.getPagina()/10)*10%>&q=<%=pesquisa.getQtdDocumentos()%>"><img src="<%=Urls.urlPortalSislegisBase%>img/backpage.png" border=0></a>
						<%
					}
					if (pesquisa.getPagina() > 0) {
					%>
						<a href="<%=Urls.urlPortalSislegisOldBase%>pendencias?sv=<%=pesquisa.getTd().getId()%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=pesquisa.getPaginaDisplay()-1%>&q=<%=pesquisa.getQtdDocumentos()%>"><img src="<%=Urls.urlPortalSislegisBase%>img/back.png" border=0></a>
					<%
					}
					for (int i = (pesquisa.getPagina()/10)*10; i < pesquisa.getTotalPaginas() && (i < (pesquisa.getPagina()/10)*10+10); i++) {					
					
					String link = i==pesquisa.getPagina()?"":("href=\""+Urls.urlPortalSislegisOldBase+"pendencias?sv="+pesquisa.getTd().getId()+"&tl="+(pesquisa.getTl()!=null?pesquisa.getTl().getId():"")+"&p="+(i+1)+"&q="+pesquisa.getQtdDocumentos()+"\"");
					%>
					<a class="<%=i==pesquisa.getPagina()?"linkPagesCorrente":"linkPages"%>"<%=link%>><%=i+1%></a><%					
					}				
					
					if (pesquisa.getPagina()+1 < pesquisa.getTotalPaginas()) {%> 
					
						<a href="<%=Urls.urlPortalSislegisOldBase%>pendencias?sv=<%=pesquisa.getTd().getId()%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=pesquisa.getPaginaDisplay()+1%>&q=<%=pesquisa.getQtdDocumentos()%>"><img src="<%=Urls.urlPortalSislegisBase%>img/next.png" border=0></a>
					
					<%					
					}					
					if ((pesquisa.getPagina()/10)*10+10+1 <= pesquisa.getTotalPaginas()) {%> 
					
						<a href="<%=Urls.urlPortalSislegisOldBase%>pendencias?sv=<%=pesquisa.getTd().getId()%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=(pesquisa.getPagina()/10)*10+10+1%>&q=<%=pesquisa.getQtdDocumentos()%>"><img src="<%=Urls.urlPortalSislegisBase%>img/nextpage.png" border=0></a>
					
					<%					
					}%>
					</div>
					</div>	
					<%
							Documento lei = null;
							Iterator<Documento> itDocumentos = pesquisa.getLista().iterator();
							
							while (itDocumentos.hasNext()) {
								
								lei = itDocumentos.next();
								%>
								
								<div class="linkItens">
								
										<%	if (lei.getPossuiarqdigital()) { %>
										
										<a href="downloadFile.pdf?sv=2&id=<%=lei.getId() %>" target="_blank">
										<img src="<%=Urls.urlPortalSislegisBase%>img/pdf_16.gif" alt="Download do arquivo digital" title="Download do arquivo digital" border=0></a>
										
										<%} %>
										
									<a class="link" href="seeker?iddoc=<%=lei.getId()%>" onmouseover="overInItemDoc('id_<%=lei.getId()%>')" onmouseout="overOutItemDoc('id_<%=lei.getId()%>')"><%=lei.getEpigrafe() %></a><span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span>
									
									<div class="coment" id="id_<%=lei.getId() %>"><%=lei.getEmenta() %></div>
								
								</div>
								<%		
							}
					}
					else {
						out.println("</div><br><br>Sua pesquisa não foi encontrada em nossa base de dados...");
					}
							%>						

			
			
			</div>
		
		</div>

</div>

	<jsp:include page="admin/rodape.jsp"></jsp:include>




</body>
</html> 
