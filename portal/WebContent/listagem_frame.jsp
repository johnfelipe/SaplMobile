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

<%
PesquisaDeDocumentos pesquisa = (PesquisaDeDocumentos)request.getSession().getAttribute("pesquisa");

if (Urls.appBase.equals("portal"))
   pesquisa.setTitulo("Normas Jurídicas.");
else 
   pesquisa.setTitulo("Documentos Administrativos.");


%>	
<script type="text/javascript" src="<%=Urls.urlPortalDomain%>lib/libSislegis.js"></script>	

	<script type="text/javascript" src="<%=Urls.urlPortalDomain%>lib/menuBox.js"></script>
	<link rel="stylesheet" href="<%=Urls.urlPortalDomain%>lib/menu.css">
<link rel="stylesheet" href="<%=Urls.urlPortalDomain%>lib/styleGeral_frame.css">
			
			<div class="sislegis_mainCol">
					<div class="iface_titulo">
					<span title="Últimos documentos publicados no Portal da Transparência Pública do Poder Legislativo de Jataí."><%=pesquisa.getTitulo() %></span>		
					<% if (pesquisa.getTotalPaginas() > 0) { %> 
					
					<span id="numbPages">
					<%
					if ((pesquisa.getPagina()/10)*10-1 >= 0) {
						%>
						<a target="_blank" href="<%=Urls.urlPortalDomain%>seeker?sv=<%=(pesquisa.getTd() != null?pesquisa.getTd().getId():0) %>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=(pesquisa.getPagina()/10)*10%>&q=<%=pesquisa.getQtdDocumentos()%>&busca=<%=pesquisa.getTextoSearch() %>&tp=<%=pesquisa.getTp() %>"><img src="<%=Urls.urlPortalDomain%>img/backpage.png" border=0></a>
						<%
					}
					if (pesquisa.getPagina() > 0) {
					%>
						<a target="_blank"  href="<%=Urls.urlPortalDomain%>seeker?sv=<%=(pesquisa.getTd() != null?pesquisa.getTd().getId():0)%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=pesquisa.getPaginaDisplay()-1%>&q=<%=pesquisa.getQtdDocumentos()%>&busca=<%=pesquisa.getTextoSearch() %>&tp=<%=pesquisa.getTp() %>"><img src="<%=Urls.urlPortalDomain%>img/back.png" border=0></a>
					<%
					}
					for (int i = (pesquisa.getPagina()/10)*10; i < pesquisa.getTotalPaginas() && (i < (pesquisa.getPagina()/10)*10+10); i++) {					
					
					String link = i==pesquisa.getPagina()?"":("href=\""+Urls.urlPortalDomain+"seeker?sv="+(pesquisa.getTd() != null?pesquisa.getTd().getId():0)+"&tl="+(pesquisa.getTl()!=null?pesquisa.getTl().getId():"")+"&p="+(i+1)+"&q="+pesquisa.getQtdDocumentos()+"&busca="+pesquisa.getTextoSearch()+"&tp="+pesquisa.getTp()+"\"");
					%>
					<a target="_blank"  class="<%=i==pesquisa.getPagina()?"linkPagesCorrente":"linkPages"%>"<%=link%>><%=i+1%></a><%					
					}				
					
					if (pesquisa.getPagina()+1 < pesquisa.getTotalPaginas()) {%> 
					
						<a target="_blank"  href="<%=Urls.urlPortalDomain%>seeker?sv=<%=(pesquisa.getTd() != null?pesquisa.getTd().getId():0)%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=pesquisa.getPaginaDisplay()+1%>&q=<%=pesquisa.getQtdDocumentos()%>&busca=<%=pesquisa.getTextoSearch() %>&tp=<%=pesquisa.getTp() %>"><img src="<%=Urls.urlPortalDomain%>img/next.png" border=0></a>
					
					<%					
					}					
					if ((pesquisa.getPagina()/10)*10+10+1 <= pesquisa.getTotalPaginas()) {%> 
					
						<a target="_blank"  href="<%=Urls.urlPortalDomain%>seeker?sv=<%=(pesquisa.getTd() != null?pesquisa.getTd().getId():0)%>&tl=<%=pesquisa.getTl()!=null?pesquisa.getTl().getId():""%>&p=<%=(pesquisa.getPagina()/10)*10+10+1%>&q=<%=pesquisa.getQtdDocumentos()%>&busca=<%=pesquisa.getTextoSearch() %>&tp=<%=pesquisa.getTp() %>"><img src="<%=Urls.urlPortalDomain%>img/nextpage.png" border=0></a>
					
					<%					
					}%>
					</span>
					</div>	
					<%
							Documento lei = null;
							Iterator<Documento> itDocumentos = pesquisa.getLista().iterator();
							
							while (itDocumentos.hasNext()) {
								
								lei = itDocumentos.next();								

								if (lei.getId_doc_principal() != null)
									continue;
								
								if (!lei.getPublicado() && !Suport.isIntranet(request))
									continue;	
								
								
								Seeker.getInstance().tlDao.refreshTipos(lei);
							 	
								//lei.setId_tipolei(Seeker.getInstance().tlDao.selectOne(lei.getId_tipolei()));
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
										
										<a target="_blank" href="<%=Urls.urlPortalDomain%><%=lei.getContent_type().indexOf("pdf")!=-1?"downloadFile.pdf":"downloadFile"%>?sv=2&id=<%=lei.getId() %>" target="_blank">
											<img src="<%=Urls.urlPortalDomain%>img/<%=lei.getContent_type().indexOf("pdf")!=-1?"pdf_16.gif":"file.png" %>" alt="Download do arquivo digital - <%=lei.getName_file() %>" title="Download do arquivo digital - <%=lei.getName_file() %>" border=0>
										</a>
										
										<%} 										
										if (itDocsDeps.hasNext()) {											
										%>
									<a target="_self"  class="link" href="javascript:toogleDocs(this,'docs<%=lei.getId()%>')" onmouseover="overInItemDoc('id_<%=lei.getId()%>')" onmouseout="overOutItemDoc('id_<%=lei.getId()%>')">
									     <span id="sdocs<%=lei.getId() %>">+</span> <%=lei.getEpigrafe() %></a>
									    <span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span>
									    											
									    <% if (Suport.isIntranet(request)) { %>
									    <span class="<%= lei.getPublicado() ? "docPub":"docPriv"%>"> - <%= lei.getPublicado()?"[Documento Público]":"[Documento Privado]"%></span>
									
										<% } %>
										<div>
										<div class="docPub">Status: <%=lei.getId_tipolei().size() == 0 ? "" :lei.getId_tipolei().get(0).getIdTipoDoc().getDescr() %> - <%=lei.getId_tipolei().size() == 0 ? "" :lei.getId_tipolei().get(0).getDescr() %></div>
										
								    	<span class="docPriv" style="float:left; height: 20px; padding-right: 10px; clear:right;">
								    		<a target="_blank"  href="<%=Urls.urlPortalDomain%>downloadFile?sv=3&id=<%=lei.getId() %>">
												<img src="<%=Urls.urlPortalDomain%>img/file.png" alt="Baixar Todos os arquivos deste processo" title="Baixar Todos os arquivos deste processo" border=0>
										    </a>
								    	   </span>
								
										<div class="contadores" style="color:red;">Processo contém <%=lDocs.size()==1?"1 Documento":(lDocs.size()+" Documentos")%><br>
										Última alteração: <%=Suport.timeStampToStr(lDocs.get(0).getData_alteracao()) %></div>
                                     </div>
                                     <% } 
										else {
										%>							        
							            
							        <a target="_blank"  class="link" href="<%=Urls.urlPortalDomain%>seeker?iddoc=<%=lei.getId()%>" onmouseover="overInItemDoc('id_<%=lei.getId()%>')" onmouseout="overOutItemDoc('id_<%=lei.getId()%>')"><%=lei.getEpigrafe() %></a><span class="contadores" title="Quantidade de acessos a este documento"> (<%=lei.getConsultas() %>)</span>
                                    
									<% } %>
									
									<div class="coment">Data de Publicação: <%=Suport.timeStampToStr(lei.getData_inclusao()) %></div>
									
									<div class="coment" id="id_<%=lei.getId() %>"><%=lei.getEmenta()%></div>

									
									<div id="docs<%=lei.getId()%>" style="display:none"> 
										
									<%	
										while (itDocsDeps.hasNext()) {
											docDeps = itDocsDeps.next();	
											
											if (!docDeps.getPublicado() && !Suport.isIntranet(request))
												continue;
											
									%>
											 
										<div class="linkItens linkItensProcess"  style="">
										
												<%	if (docDeps.getPossuiarqdigital()) { %>
												
												<a target="_blank"  href="<%=Urls.urlPortalDomain%><%=docDeps.getContent_type().indexOf("pdf")!=-1?"downloadFile.pdf":"downloadFile"%>?sv=2&id=<%=docDeps.getId() %>" target="_blank">
												<img src="<%=Urls.urlPortalDomain%>img/<%=docDeps.getContent_type().indexOf("pdf")!=-1?"pdf_16.gif":"file.png" %>" alt="Download do arquivo digital - <%=docDeps.getName_file() %>" title="Download do arquivo digital - <%=docDeps.getName_file() %>" border=0></a>
												
												<%} %>
												
											<a target="_blank"  class="link" href="<%=Urls.urlPortalDomain%>seeker?iddoc=<%=docDeps.getId()%>" onmouseover="overInItemDoc('id_<%=docDeps.getId()%>')" onmouseout="overOutItemDoc('id_<%=docDeps.getId()%>')"><%=docDeps.getEpigrafe() %></a>
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
					else {
						out.println("</div><br><br>Nenhum documento nesta categoria...");
					}
							%>	
			</div>