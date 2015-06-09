<%@page import="br.gov.go.camarajatai.sislegisold.business.PesquisaDeDocumentos"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipolei"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
			
<%
	PesquisaDeDocumentos pesquisa = (PesquisaDeDocumentos)request.getSession().getAttribute("pesquisa");
%>	
				
<div class="ContainersLeft" style="margin-right: 0px;">
	<div class="iface_titulo">Pesquisa</div>

	<form method="get" action="<%=Urls.urlPortalSislegisOldBase%>seeker">
			
				Tipo de Documento:<br>							
				<select name="tl">
					<option value="0">--- Todos os Tipos ---</option>
						<%
						Iterator<Tipolei> it = Seeker.getInstance().tlDao.getCollection().iterator();
						Tipolei tl;
						while(it.hasNext()){										
							tl=it.next();
							
							if (!tl.getClassif_assuntos())
								continue;
								
							
						%><option value="<%=tl.getId() %>" <%=pesquisa != null && pesquisa.getTl() != null && pesquisa.getTl().getId() == tl.getId() ?"selected=\"selected\"":""%>><%=tl.getDescr()%></option>	<% }%>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
				<input type="checkbox" name="tp" <%=pesquisa != null && pesquisa.getTp() != null?"value=true":"" %>>Pesquisar apenas na ementa do documento</input>
				<br><br>

		Nº Doc:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Por palavra:<br>
		<input type="text" value="" name="numDoc" size=6>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="text" name="busca" size="30"/>
		
		
		
		<input type="submit" value="Buscar"></form>						
</div>