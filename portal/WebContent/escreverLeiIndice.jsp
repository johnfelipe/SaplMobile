<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Itemlei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>


    
		<%
		
		boolean isIntranet = Suport.isIntranet(request);
		
		if (!isIntranet)
		   return;
		
		Documento lei = null;	
		
		Object ob = request.getAttribute("lei");
		
		if (ob == null) {
			ob = request.getSession().getAttribute("lei");
		}
		
		if (ob != null) {
			
			lei = (Documento)ob;	
		
		
		if (lei.getId() != 0)
		 	Seeker.getInstance().upDateCollectionItensDeLei(lei);
		
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
		
		} 
		else  {
		%>
		<div class=iface_titulo><span> SUA PESQUISA </span></div>
		<%
		}
		collection.firstItem();
		
		while (collection.hasNext()) {				
			il = collection.next();		
			
			stateNew = il.getStateRefLei(lei);
			novalei = (stateNew & Itemlei.STATE_NOVALEI) != 0;
				
			if ((stateNew & Itemlei.STATE_INVISIBLE) != 0) {
				stateOld = stateNew;
				//out.println("invisivel<br>");
				continue;
			}

			if (il.getNivel() >= 1000 || novalei)
				continue;
			
			classe = CollectionItensDeLei.niveis.get(il.getNivel());
			
			desativado = (stateNew & Itemlei.STATE_ALTERADO) != 0 || (stateNew & Itemlei.STATE_REVOGADO) != 0;
			link = !novalei && il.getLink_alterador() != null && il.getLink_alterador().length() > 0 ;
						
			
			if (novalei && (stateOld & Itemlei.STATE_NOVALEI) == 0)
				out.print("<div class=\"novalei\">");
			else if (!novalei && (stateOld & Itemlei.STATE_NOVALEI) != 0)
				out.println("</div>");
			
			
			if (desativado)
				continue;
		%>
			
			<% /*
			*  configura os links dos dispositivos 			
			*			para referência a alteração e inclusão em uma lei antiga 
			*			ou para lei que se consulta a nomeação para que alguma outra futura lei possa referenciar a este dispositivo.			
			*			
			*/ %>
			<%=	"<a href=\"#"  +  il.getId()  + "\" "+  (il.getTexto().indexOf("Revogado")==-1?"title=\""+il.getTexto()+"\"":"")      +">"+il.getNomeclatura()+"</a>"%>
					
			<%						
				stateOld = stateNew;			
			}				
		}
		%>
<hr>