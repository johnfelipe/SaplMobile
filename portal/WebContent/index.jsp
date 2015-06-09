<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipolei"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<script type="text/javascript" src="/portal/lib/jquery.js"></script>

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

if (!Urls.appBase.equals("portal")) {
	
	response.sendRedirect(Urls.urlPortalSislegisBase+"seeker");
	return;
}
else {
   if (Suport.redirectSapl(request, response))
       return;
}


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
				 if (comMenu && Urls.appBase.equals("portal")) {%>
			  		<div class="sislegis_rightCol">
							<jsp:include page="admin/menuAssuntos.jsp"></jsp:include>
					</div>
				<%	
				}	
				%>
			
		
			<div class="sislegis_mainCol">
			
				<!--  div class="materia">
					<p class="paragraf" ><span class="boldcolor">
					Saudações!!! Obrigado por visitar nosso projeto. Hoje temos xxx documentos do Município de Jataí já cadastrados em nossa base de dados. Estamos trabalhando diariamente para melhorarmos cada vez mais esse serviço que acreditamos ser de grande relevância para nossa sociedade.
					</span>
					</p>
				</div-->


				<jsp:include page="formPesquisa.jsp"></jsp:include>


						
				<table  border="0" width=100% cellspacing=0 cellpadding=0>
				<tr valign="top">
				<td width="50%" >
						<br>
				<div class="ContainersLeft">
					<div class="iface_titulo">Destaques
					</div>
					<%
						Iterator<DocDestaque> itDocDest = Seeker.getInstance().docDestDao.getCollection().iterator();
						DocDestaque docDest;
						
						while (itDocDest.hasNext()) {
							docDest = itDocDest.next();
							%>
							<a href="seeker?iddoc=<%=docDest.getId_doc().getId()%>"><%=docDest.getTitulo() %></a>
							<%		
						}
						%>
				</div>
				</td>
				<td><br>
				<div class="ContainersRight">
					<div class="iface_titulo"><a href="seeker?sv=2" title="Clique aqui e confira todos os documentos cadastrados por ordem de data">Documentos Recentes</a>
					</div>
					<%
						Documento lei = null;		
						Tipodoc td = Seeker.getInstance().tdDao.selectOne(new Tipodoc(2));
						List<Documento> listDocumentos = Seeker.getInstance().prepararPesquisa(null,td,0,5, true).getLista();
						Iterator<Documento> itDocumentos = listDocumentos.iterator();
						
						int i = 0; 
						while (itDocumentos.hasNext() && i < 5) {
							lei = itDocumentos.next();
							
							if (!lei.getPublicado())
								continue;
							
							i++;
							%>
							<a href="seeker?iddoc=<%=lei.getId()%>" onmouseover="javascript:getElementById('id_<%=lei.getId() %>').style.fontSize = '11pt';" onmouseout="javascript:getElementById('id_<%=lei.getId() %>').style.fontSize = '0pt';"><%=lei.getEpigrafe()%><span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span></a>
							<div class="coment" id="id_<%=lei.getId() %>"><%=lei.getEmenta() %></div>
							<%		
						}
						%>
				</div>
				
				<br>
				<div class="ContainersRight">
					<div class="iface_titulo"><a href="seeker?sv=1" title="Clique aqui e confira todos os documentos em ordem pelos mais acessados">Documentos mais consultados</a>
					</div>
					<%
						 lei = null;		
						td = Seeker.getInstance().tdDao.selectOne(new Tipodoc(1));
						 listDocumentos = Seeker.getInstance().prepararPesquisa(null,td,0,5,true).getLista();
						 itDocumentos = listDocumentos.iterator();
					 
						while (itDocumentos.hasNext()) {
							lei = itDocumentos.next();
							%>
							<a href="seeker?iddoc=<%=lei.getId()%>" onmouseover="javascript:getElementById('id_<%=lei.getId() %>').style.fontSize = '11pt';" onmouseout="javascript:getElementById('id_<%=lei.getId() %>').style.fontSize = '0pt';"><%=lei.getEpigrafe()%><span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span></a>
							<div class="coment" id="id_<%=lei.getId() %>"><%=lei.getEmenta() %></div>
							<%		
						}
						%>
				</div>
				</td></tr>
				</table>
			
			</div>
		
		</div>



		


</div>

	<jsp:include page="admin/rodape.jsp"></jsp:include>

</body>
</html>