<%@page import="java.util.ListIterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dao.PostgreConnection"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>
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
					
					<div class="iface_titulo">
					<span>Documentos pendentes de Classificação</span>		
					<% 
					
					
					 %> 
					
					 
					
					</div>	
					<!-- div class="filtroBusca">
					   <div>área</div>
					   <span>Filtros</span>
					</div-->
					<%
							Documento lei = null;
					        ArrayList<Documento> listaDocumento = Seeker.getInstance().leiDao.selectAll();
                        
                       
                       
							ListIterator<Documento> itDocumentos = listaDocumento.listIterator(listaDocumento.size());
							
							while (itDocumentos.hasPrevious()) {
								
								lei = itDocumentos.previous();								

								if (lei.getId_doc_principal() != null)
									continue;
								
								if (!lei.getPublicado() && !Suport.isIntranet(request))
									continue;	
								
								
							 	Seeker.getInstance().tlDao.refreshTipos(lei);
							 	
							 	if (lei.getId_tipolei().size() > 1)
							 		continue;
							 	else if (lei.getId_tipolei().size() == 1 && !lei.getId_tipolei().get(0).getClassif_assuntos()) {
							 		continue;
							 	}
							 	
							 	//lei.getId_tipolei().setIdTipoDoc(Seeker.getInstance().tdDao.selectOne(lei.getId_tipolei().getIdTipoDoc()));
																						
							    List<Documento> lDocs = Seeker.getInstance().leiDao.getDocsBase(lei);
								Iterator<Documento> itDocsDeps = lDocs.iterator();
								Documento docDeps = null;

								while (itDocsDeps.hasNext()) {
									docDeps = itDocsDeps.next();	
									
									if (!docDeps.getPublicado() && !Suport.isIntranet(request))
										itDocsDeps.remove();
								}
								itDocsDeps = lDocs.iterator();
									%>								
								<div class="linkItens">								
										<%	if (lei.getPossuiarqdigital()) { %>										
										<a href="<%=lei.getContent_type().indexOf("pdf")!=-1?"downloadFile.pdf":"downloadFile"%>?sv=2&id=<%=lei.getId() %>" target="_blank">
											<img src="<%=Urls.urlPortalSislegisBase%>img/<%=lei.getContent_type().indexOf("pdf")!=-1?"pdf_16.gif":"file.png" %>" alt="Download do arquivo digital - <%=lei.getName_file() %>" title="Download do arquivo digital - <%=lei.getName_file() %>" border=0>
										</a>
										
										<%} 										
										if (itDocsDeps.hasNext()) {											
										%>
										<a class="link" href="javascript:toogleDocs(this,'docs<%=lei.getId()%>')" onmouseover="overInItemDoc('id_<%=lei.getId()%>')" onmouseout="overOutItemDoc('id_<%=lei.getId()%>')">
									    <span id="sdocs<%=lei.getId() %>">+</span></a>
									    <a class="link" href="seeker?sv=-1&iddoc=<%=lei.getId()%>">
									    <%=lei.getEpigrafe() %></a>
									    <span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span>
									    											
									    <% if (Suport.isIntranet(request)) { %>
									    <span class="<%= lei.getPublicado() ? "docPub":"docPriv"%>"> - <%= lei.getPublicado()?"[Documento Público]":"[Documento Privado]"%></span>
									
										<% } %>
										<div>
										<div class="docPub">Status: <%=lei.getId_tipolei().size() == 0 ? "" :lei.getId_tipolei().get(0).getIdTipoDoc().getDescr() %> - <%=lei.getId_tipolei().size() == 0 ? "" :lei.getId_tipolei().get(0).getDescr() %></div>
										
								    	<span class="docPriv" style="float:left; height: 20px; padding-right: 10px; clear:right;">
								    		<a href="downloadFile?sv=3&id=<%=lei.getId() %>">
												<img src="<%=Urls.urlPortalSislegisBase%>img/file.png" alt="Baixar Todos os arquivos deste processo" title="Baixar Todos os arquivos deste processo" border=0>
											</a>
								    	   </span>
								
										<div class="contadores" style="color:red;">Processo contém <%=lDocs.size()==1?"1 Documento":(lDocs.size()+" Documentos")%><br>
										Última alteração: <%=Suport.timeStampToStr(lDocs.get(0).getData_alteracao()) %></div>
                                     </div>
                                     <% } 
										else {
										%>							        
							            
							        <a class="link" href="seeker?sv=-1&iddoc=<%=lei.getId()%>" onmouseover="overInItemDoc('id_<%=lei.getId()%>')" onmouseout="overOutItemDoc('id_<%=lei.getId()%>')"><%=lei.getEpigrafe() %></a><span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span>
                                    <% if (Suport.isIntranet(request)) { %>
									    <span class="<%= lei.getPublicado() ? "docPub":"docPriv"%>"> - <%= lei.getPublicado()?"[Documento Público]":"[Documento Privado]"%></span>
									
										<% } %>
									<% } %>
									
									<div class="coment">Data de Publicação: <%=Suport.timeStampToStr(lei.getData_inclusao()) %></div>
									
									<div class="coment" id="id_<%=lei.getId() %>"><%=lei.getEmenta()%></div>
									
                                    
                                    <% if (Urls.appBase.equals("publicacoes") && false) { %>
                                     <a class="docPub"href="seeker?iddoc=<%=lei.getId()%>"><img align="absmiddle" border="0" width="16px" src="img/acomp_email.png"> Acompanhar Processo</a>
									
									<% } %>
									
									<div id="docs<%=lei.getId()%>" style="display:none"> 
										
									<%	
										while (itDocsDeps.hasNext()) {
											docDeps = itDocsDeps.next();	
											
											if (!docDeps.getPublicado() && !Suport.isIntranet(request))
												continue;
											
									%>
											 
										<div class="linkItens"  style="margin-left: 30px; border-top: 1px solid #c2c2a9;  border-left: 1px solid #c2c2a9; background-color: #f2f2e9;">
										
												<%	if (docDeps.getPossuiarqdigital()) { %>
												
												<a href="<%=docDeps.getContent_type().indexOf("pdf")!=-1?"downloadFile.pdf":"downloadFile"%>?sv=2&id=<%=docDeps.getId() %>" target="_blank">
												<img src="<%=Urls.urlPortalSislegisBase%>img/<%=docDeps.getContent_type().indexOf("pdf")!=-1?"pdf_16.gif":"file.png" %>" alt="Download do arquivo digital - <%=docDeps.getName_file() %>" title="Download do arquivo digital - <%=docDeps.getName_file() %>" border=0></a>
												
												<%} %>
												
											<a class="link" href="seeker?iddoc=<%=docDeps.getId()%>" onmouseover="overInItemDoc('id_<%=docDeps.getId()%>')" onmouseout="overOutItemDoc('id_<%=docDeps.getId()%>')"><%=docDeps.getEpigrafe() %></a>
											<span class="contadores" title="Quantidade de acessos a este documento"> (<%=docDeps.getConsultas() %>)</span>
											
											<% if (Suport.isIntranet(request)) { %>
									            <span class="<%= docDeps.getPublicado() ? "docPub":"docPriv"%>"> - <%= docDeps.getPublicado()?"[Documento Público]":"[Documento Privado]"%></span>
										    <% } %>
    									    <div class="coment">Data de Publicação: <%=Suport.timeStampToStr(docDeps.getData_inclusao()) %></div>
											<div class="coment" id="id_<%=docDeps.getId() %>"><%=docDeps.getEmenta() %></div>
											</div> 
											 
									<% 
									}
							
									%>
								</div>
								</div>											  
									<% 									
							}
									%>
			
			</div>
		
		</div>

</div>




</body>
</html> 
