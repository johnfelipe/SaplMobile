<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.ManipulaHTML"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Itemlei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>
<%

		boolean isIntranet = Suport.isIntranet(request);
		
		Documento lei = null;	
		
		Object ob = request.getAttribute("lei");
		
		if (ob == null) {
			ob = request.getSession().getAttribute("lei");
		}
		
		if (ob != null) {
			
			lei = (Documento)ob;	
		
		
		if (lei.getId() != 0) {
		 	Seeker.getInstance().upDateCollectionItensDeLei(lei);
		 	
		 	Seeker.getInstance().leiDao.selectHashArqDigital(lei, "sha512");
		}
		
		CollectionItensDeLei collection = lei.getItemleis();
		Itemlei il;
				
		String classe = "";
		String texto = "";
		int stateOld = 0;
		int stateNew = 0;
		boolean desativado = false;
		boolean link = false;
		boolean novalei = false;	
		
		if (lei.getId() != 0 && lei.completo) {
			
			lei.setId_arquivo(Seeker.getInstance().arquivoDao.selectOne(lei.getId_arquivo()));
		%>
<div class=epigrafe><%=lei.getEpigrafe() %></div>
<div class=indicacao><%=lei.getId_arquivo()!= null && false ?"<span title=\""+lei.getId_arquivo().getNome()+"\">Arquivo: "+lei.getId_arquivo().getCodArquivo()+"</span>":"" %></div>
<% if (lei.getPreambulo() != null && lei.getPreambulo().length() > 0) { %>
<div class=preambulo><%=lei.getPreambulo()!= null?lei.getPreambulo():"" %></div>
<% } %>
<% if (lei.getEmenta() != null && lei.getEmenta().length() > 0) { %>
<div class=ementa><%=lei.getEmenta() %></div>
<% } %>
<% if (lei.getIndicacao() != null && lei.getIndicacao().length() > 0) { %>
<div class=indicacao><%=lei.getIndicacao()!= null?lei.getIndicacao():"" %></div>
<% } %>
<% if (lei.getEnunciado() != null && lei.getEnunciado().length() > 0) { %>
<div class=enunciado><%=lei.getEnunciado()!= null?lei.getEnunciado():"" %></div>
<% } %>
<%
		} 
		else  {
		%>
<div class=iface_titulo>
   <span> SUA PESQUISA </span>
</div>
<%
		}
		collection.firstItem();
		
		if (!lei.completo) {
		%>
<table cellpadding="0" cellspacing="0">
   <%
		}
		int count = 0;
		while (collection.hasNext()) {
			il = collection.next();
			
         
         
           if (count++ == 40) {
               
              // break;
               
           }
           //System.out.println(il.getId());
			
			stateNew = il.getStateRefLei(lei);
			novalei = (stateNew & Itemlei.STATE_NOVALEI) != 0;
				
			if ((stateNew & Itemlei.STATE_INVISIBLE) != 0) {
				stateOld = stateNew;
				//out.println("invisivel<br/>");
				continue;
			}
			
			classe = CollectionItensDeLei.niveis.get(il.getNivel());
			
			desativado = (stateNew & Itemlei.STATE_ALTERADO) != 0 || (stateNew & Itemlei.STATE_REVOGADO) != 0;
			link = !novalei && il.getLink_alterador() != null && il.getLink_alterador().length() > 0 ;
			
			if (novalei && (stateOld & Itemlei.STATE_NOVALEI) == 0)
				out.print("<div class=\"novalei\">");
			else if (!novalei && (stateOld & Itemlei.STATE_NOVALEI) != 0)
				out.println("</div>");
			
			if (lei.completo && isIntranet  && !novalei) {
				%>
   <hr>
   <span style="color: #ff0000;">Código do dispositivo alterador: <%=il.getId() %></span>
   <%
			}
		%>
   <%= 
			
			
			
			(lei.completo?"<div class=\""+classe+"\"":"<tr " 
				+ "style=\"padding: 10px 5px 10px 5px; background-color:"
				+ (  (collection.nextIndex()%2)  == 0 ? "#d2fcff"  :  "#f3ffff")+"\"")
				+  ">"	
				
				
		%><%=desativado?"<div class=desativado>":"" %><%=
			
			
			
			lei.completo?"":"<td>" %>
   <% /*
					
					
					
			*  configura os links dos dispositivos 			
			*			para referência a alteração e inclusão em uma lei antiga 
			*			ou para lei que se consulta a nomeação para que alguma outra futura lei possa referenciar a este dispositivo.			
			*			
			*/ %><%=
			"<a name="    + 	 "\""+il.getId()+"\""+
			
						(novalei ? 
								(" href=\"norma_juridica_mostrar_proc?cod_norma="  +  il.getId_lei()  +  "#"  +  il.getId()  +  "\" title=\""+il.getNomeclaturaCompleta()+"\">"  +  il.getNomeclatura())
							 
							 :  ">"+il.getNomeclatura())+"</a>"+ManipulaHTML.limparTable(il.getTexto())						 
			%><%= !link ?
			""
			:
				"<a href=\"norma_juridica_mostrar_proc?cod_norma="
						+ il.getId_alterador()
						+ "#"
						+ il.getId_dono()
						+ "\" class=\"link_alterador\">"
						+ il.getLink_alterador()
						+ "</a>" %><%=desativado?"</div>":"" %><%=
						
			lei.completo ? "":("<br/> <a class=\"link_alterador\" href=\"norma_juridica_mostrar_proc?cod_norma="  +  il.getId_lei()  +  "#"  +  il.getId() + "\">"+"Acesse o documento a que este item se refere</a><br/><br/>")
						
			%>
   <%

			if (isIntranet  && !novalei) {
				%>
   <br />
   <a class="link_alterador" href="<%=Urls.urlPortalSislegisOldBase%>alterarItemLei.jsp?itemlei=<%=il.getId()%>">[alterar dispositivo]</a> &nbsp;&nbsp;&nbsp;
   <a class="link_alterador" href="<%=Urls.urlPortalSislegisOldBase%>revogarItemLei.jsp?itemlei=<%=il.getId()%>">[Revogar dispositivo]</a>
   <%
			}
			%><%=
				
				
				lei.completo?"</div>":"</td></tr>" %>
   <%	
									
				stateOld = stateNew;			
			//}
		}
		
		if (!lei.completo) {
			%>
</table>
<%
			}
		if (lei.getId() != 0) {
		%>
<% if (lei.getTexto_final().length() > 0) { %>
<br />
<div class=texto_final><%=lei.getTexto_final() %></div>
<% } %>
<% if (lei.getAssinatura().length() > 0) { %>
<div class=assinatura><%=lei.getAssinatura() %><br /> <span class=assinante><%=lei.getCargo_assinante() %></span>
</div>
<% } 			

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
								
                        
								if (itDocsDeps.hasNext()) {
                        
									%>
<div class="linkItens" style="background-color: #f2f2e9;">
   <div id="docs<%=lei.getId()%>">
      <%	
										while (itDocsDeps.hasNext()) {
											docDeps = itDocsDeps.next();	
											
											if (!docDeps.getPublicado() && !Suport.isIntranet(request))
												continue;
											
									%>
      <div class="linkItens linkItensProcess">
         <%	if (docDeps.getPossuiarqdigital()) { %>
         <a target="_blank" href="<%=Urls.urlPortalDomain%><%=docDeps.getContent_type().indexOf("pdf")!=-1?"downloadFile.pdf":"downloadFile"%>?sv=2&id=<%=docDeps.getId() %>" target="_blank"> <img src="<%=Urls.urlPortalDomain%>img/<%=docDeps.getContent_type().indexOf("pdf")!=-1?"pdf_16.gif":"file.png" %>" alt="Download do arquivo digital - <%=docDeps.getName_file() %>" title="Download do arquivo digital - <%=docDeps.getName_file() %>" border=0></a>
         <%} %>
         <a target="_blank" class="link" href="norma_juridica_mostrar_proc?cod_norma=<%=docDeps.getId()%>" onmouseover="overInItemDoc('id_<%=docDeps.getId()%>')" onmouseout="overOutItemDoc('id_<%=docDeps.getId()%>')"><%=docDeps.getEpigrafe() %></a> <span class="contadores" title="Quantidade de acessos a este documento"> (<%=docDeps.getConsultas() %>)
         </span>
         <% if (Suport.isIntranet(request)) { %>
         <span class="<%= docDeps.getPublicado() ? "docPub":"docPriv"%>"> - <%= docDeps.getPublicado()?"[Documento Público]":"[Documento Privado]"%></span>
         <% } %>
         <div class="coment">
            Data de Publicação:
            <%=Suport.timeStampToStr(docDeps.getData_inclusao()) %></div>
         <div class="coment" id="id_<%=docDeps.getId() %>"><%=docDeps.getEmenta() %></div>
      </div>
      <%
									    
									}
									%>
   </div>
</div>
<% }
		
		
		
		if (lei.getPossuiarqdigital() && Urls.appBase.equals("publicacoes") && request.getHeader("user-agent").indexOf("Android") == -1) { %>
<table style="width: 100%" cellpadding="10">
   <tr>
      <td><iframe width="100%" height="400px" frameborder="0" src="<%=Urls.urlPortalSislegisBase %><%=lei.getContent_type().indexOf("pdf")!=-1?"downloadFile.pdf":"downloadFile"%>?sv=2&id=<%=lei.getId() %>"></iframe></td>
   </tr>
</table>
<br />
<div class="hashFile" style="width: 450px; word-wrap: break-word; font-family: 'Courier New'; font-size: 9pt; background-color: #ff0; border: 1px red solid; padding: 7px;">
   <span style="border-bottom: 1px red solid; margin-bottom: 7px; display: block; font-weight: bold;">Hash SHA512:</span><%=lei.getHash_arqdigital()%>
</div>
<% } %>
<%
		}
		
		}
		%>
