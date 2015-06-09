<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipolei"%> 
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
int flagFirstTL = 0;

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
					
					<div class="iface_titulo">
					<span><%=pesquisa.getTitulo() + (pesquisa.getTl() != null && pesquisa.getTl().getOrdem() > 999 ? " - Classificação Oficial":"")%></span>		
					<% if (pesquisa.getTotalPaginas() > 0) { %> 
					
					<span id="numbPages">
					<%
					if ((pesquisa.getPagina()/10)*10-1 >= 0) {
						%>
						<a href="<%=Urls.urlPortalSislegisOldBase%>seeker?sv=<%=(pesquisa.getTd() != null?pesquisa.getTd().getId():0) %>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=(pesquisa.getPagina()/10)*10%>&q=<%=pesquisa.getQtdDocumentos()%>&busca=<%=pesquisa.getTextoSearch() %>&tp=<%=pesquisa.getTp() %>"><img src="<%=Urls.urlPortalSislegisBase%>img/backpage.png" border=0></a>
						<%
					}
					if (pesquisa.getPagina() > 0) {
					%>
						<a href="<%=Urls.urlPortalSislegisOldBase%>seeker?sv=<%=(pesquisa.getTd() != null?pesquisa.getTd().getId():0)%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=pesquisa.getPaginaDisplay()-1%>&q=<%=pesquisa.getQtdDocumentos()%>&busca=<%=pesquisa.getTextoSearch() %>&tp=<%=pesquisa.getTp() %>"><img src="<%=Urls.urlPortalSislegisBase%>img/back.png" border=0></a>
					<%
					}
					for (int i = (pesquisa.getPagina()/10)*10; i < pesquisa.getTotalPaginas() && (i < (pesquisa.getPagina()/10)*10+10); i++) {					
					
					String link = i==pesquisa.getPagina()?"":("href=\""+Urls.urlPortalSislegisOldBase+"seeker?sv="+(pesquisa.getTd() != null?pesquisa.getTd().getId():0)+"&tl="+(pesquisa.getTl()!=null?pesquisa.getTl().getId():"")+"&p="+(i+1)+"&q="+pesquisa.getQtdDocumentos()+"&busca="+pesquisa.getTextoSearch()+"&tp="+pesquisa.getTp()+"\"");
					%>
					<a class="<%=i==pesquisa.getPagina()?"linkPagesCorrente":"linkPages"%>"<%=link%>><%=i+1%></a><%					
					}				
					
					if (pesquisa.getPagina()+1 < pesquisa.getTotalPaginas()) {%> 
					
						<a href="<%=Urls.urlPortalSislegisOldBase%>seeker?sv=<%=(pesquisa.getTd() != null?pesquisa.getTd().getId():0)%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=pesquisa.getPaginaDisplay()+1%>&q=<%=pesquisa.getQtdDocumentos()%>&busca=<%=pesquisa.getTextoSearch() %>&tp=<%=pesquisa.getTp() %>"><img src="<%=Urls.urlPortalSislegisBase%>img/next.png" border=0></a>
					
					<%					
					}					
					if ((pesquisa.getPagina()/10)*10+10+1 <= pesquisa.getTotalPaginas()) {%> 
					
						<a href="<%=Urls.urlPortalSislegisOldBase%>seeker?sv=<%=(pesquisa.getTd() != null?pesquisa.getTd().getId():0)%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=(pesquisa.getPagina()/10)*10+10+1%>&q=<%=pesquisa.getQtdDocumentos()%>&busca=<%=pesquisa.getTextoSearch() %>&tp=<%=pesquisa.getTp() %>"><img src="<%=Urls.urlPortalSislegisBase%>img/nextpage.png" border=0></a>
					
					<%					
					}%>
					</span>
					
					</div>	
					<!-- div class="filtroBusca">
					   <div>área</div>
					   <span>Filtros</span>
					</div-->
					<%
							
						for (int iAss = 0; iAss < 2; iAss++) {

							Documento lei = null;
							Iterator<Documento> itDocumentos = pesquisa.getLista().iterator();
							
							if ( iAss == 1 && pesquisa.getTl() != null && pesquisa.getTl().getOrdem() > 999 && itDocumentos.hasNext()) {
								%><br><br><br>
								<div class="iface_titulo">
					               <span><%=pesquisa.getTitulo() + (pesquisa.getTl() != null && pesquisa.getTl().getOrdem() > 999 ? " - Sugestões de Usuário":"")%></span></div>		
								<%
							}

							while (itDocumentos.hasNext()) {
								
								lei = itDocumentos.next();	 

								if (lei.getId_doc_principal() != null)
									continue;
								
								if (!lei.getPublicado() && !Suport.isIntranet(request))
									continue;
								
								
							 	Seeker.getInstance().tlDao.refreshTipos(lei);
							 	

								if (iAss == 0 && pesquisa.getTl() != null) {
									
									flagFirstTL = 0;
									for (Tipolei tll: lei.getId_tipolei()) {
										if (tll.getId() == pesquisa.getTl().getId() && !tll.tmp_oficial) {
											pesquisa.setTl(tll);
											flagFirstTL++;
											break;
										}
									}
									if (flagFirstTL != 0)
										continue;  
								} 
								else {
									
								//	flagFirstTL = 0;
								//	for (Tipolei tll: lei.getId_tipolei()) {
								//		if (!tll.tmp_oficial) {
								//			pesquisa.setTl(tll);
								//			flagFirstTL++;
								//			break;
								//		}
								//	}
								//	if (flagFirstTL != 0)
								//		continue;  
								}
								itDocumentos.remove();
							 	 													
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
									    <a class="link" href="seeker?iddoc=<%=lei.getId()%>">
									    <%=lei.getEpigrafe() %></a>
									    <% if (pesquisa.getTl() != null && pesquisa.getTl().tmp_oficial) {%>
									    	<span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span>
									    <% } else if (pesquisa.getTl() != null && !pesquisa.getTl().tmp_oficial && pesquisa.getTl().tmp_sugestoes > 0) {
									    	%>
									    	<span class="contadores" title="Quantidade de Sugestões de usuários."> (<%=pesquisa.getTl().tmp_sugestoes == 1 ? "Um usuário sugeriu essa classificação...": pesquisa.getTl().tmp_sugestoes+ " usuários sugeriram essa classificação..."  %>)</span>
									    <% } else {
									    	%>
									     <% } %>
									    											
									    <% if (Suport.isIntranet(request)) { %>
									    <span class="<%= lei.getPublicado() ? "docPub":"docPriv"%>"> - <%= lei.getPublicado()?"[Documento Público]":"[Documento Privado]"%></span>
									
										<% } %>
										<div>
											<div class="docAssuntos"> 
										<%  
										flagFirstTL = 0;
										for (Tipolei tll: lei.getId_tipolei()) {
											
											if (!tll.tmp_oficial)
												continue;
											
											if (flagFirstTL > 1)
												out.print(", ");
											
											out.print(tll.getDescr());
											
											if (flagFirstTL++ == 0) {
												out.print(":");
											}
											
											
										}%>		 
										</div>
										
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
							            
							        <a class="link" href="seeker?iddoc=<%=lei.getId()%>" onmouseover="overInItemDoc('id_<%=lei.getId()%>')" onmouseout="overOutItemDoc('id_<%=lei.getId()%>')"><%=lei.getEpigrafe() %></a>
							        
							         <% if (pesquisa.getTl() != null && pesquisa.getTl().tmp_oficial) {%>
									    	<span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span>
									   <% } else if (pesquisa.getTl() != null && !pesquisa.getTl().tmp_oficial && pesquisa.getTl().tmp_sugestoes > 0) {
									    	%>
									    	<span class="contadores" title="Quantidade de Sugestões de usuários."> (<%=pesquisa.getTl().tmp_sugestoes == 1 ? "Um usuário sugeriu essa classificação...": pesquisa.getTl().tmp_sugestoes+ " usuários sugeriram essa classificação..."  %>)</span>
									    <% } else {
									    	%>
									     <% } %>
									    
                                    <% if (Suport.isIntranet(request)) { %>
									    <span class="<%= lei.getPublicado() ? "docPub":"docPriv"%>"> - <%= lei.getPublicado()?"[Documento Público]":"[Documento Privado]"%></span>
									
										<% } %>
											<div class="docAssuntos"> 
										<%  
										flagFirstTL = 0;
										for (Tipolei tll: lei.getId_tipolei()) {

											if (!tll.tmp_oficial)
												continue;
											
											if (flagFirstTL > 1)
												out.print(" -  ");
											
											out.print(tll.getDescr());
											
											if (flagFirstTL++ == 0) {
												out.print(": ");
											}
											
											
										}%>		 
										</div>
										
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
					}
					}
					else {
						out.println("</div><br><br>Nenhum documento nesta categoria...");
					}
							%>						
			
			
			</div>
		
		</div>

</div>




</body>
</html> 
