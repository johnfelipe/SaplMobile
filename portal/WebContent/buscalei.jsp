<%@page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipodoc"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipolei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedList"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>
<%
		Documento lei = null;	
		Object ob = request.getAttribute("lei");
		lei = null;
		if (ob != null) {
			lei = (Documento)ob;
		}			
		
		
		String print = request.getParameter("print");
	
if ( print == null) {
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
	<title><%=lei != null ? lei.getEpigrafe():"" %> - Portal da Transparência Pública</title>

	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/jquery.js"></script>
		
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>	
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>	
	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/menu.css"/>
    <link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/styleGeral.css"/>
<% } else { %>
    <style>
        
        <%@ include file="lib/styleGeral.css"%>
    
    </style>
<% }


if ( print == null) {%>
<script type="text/javascript">

var RecaptchaOptions = {
	    theme : 'clean'
	 };

function consolidarTotalmente() {
	$(".desativado").remove();
	$(".link_alterador").remove();
}
function consolidarParcialmente() {
	$(".desativado").remove();
}
function organizarImpressao() {
	window.print();
	return;
	$(".Topo").remove();
	$(".TopoBgForm").remove();
	$(".Rodape").remove();
	$(".MsgRodape").remove();
	$("div").removeClass("GlobalStyle");
	$("div").removeClass("Corpo");
	$("div").removeClass("sislegis_mainCol");
	$("div").removeClass("CorpoDocumento");

	$("body").css("background-color","#ffffff");
	window.print();
	}

/*
function consolidar() {
	$(".desativado").css("visibility","hidden");
	$(".link_alterador").css("visibility","hidden");
}*/
	
</script> 
</head>

<body>
		
<% } 

boolean comMenu = true;

%>


<div class="GlobalStyle">
	
		<% if ( print == null) {%>
		<jsp:include page="admin/topo.jsp"></jsp:include>
	    <%}%>
	
		<div class="Corpo">
									
				<% if (comMenu || lei == null) {%>
				
			
		<% if ( print == null) {%>			
			<div class="sislegis_leftCol">
			
				 
					<jsp:include page="admin/menu.jsp"></jsp:include>
				
				<% if (!(Suport.getCookie(request, "contrib_assuntos_"+lei.getId()) == null)) {
		%>	  	<jsp:include page="admin/menuAssuntos.jsp"></jsp:include>	
		<%	
				}	
				%>
			</div>
				<%	
				}	
				%>
				<%	
				}	
				%>
			
		
		<% if ( print == null) {%>	
			<div class="sislegis_mainCol">
			
			<%	
			}	
			%>
					<% 
		if (lei != null) {
			
		
			
		%>	
		
		
					
			<% if ( print == null) {%>		
				<div class="TopoBgForm" style="display:none;">
					<div class="iface_titulo">Documentos Diversos</div>
		
					<form method="post" action="<%=Urls.urlPortalSislegisOldBase%>seeker">
						<%
						if (lei.getId() != 0) {
							if (lei.getPossuiarqdigital()) { %>
							<a href="downloadFile.pdf?sv=2&id=<%=lei.getId() %>"><img src="<%=Urls.urlPortalSislegisBase%>img/pdf_icon.gif" alt="Download do arquivo digital" title="Download do arquivo digital" border=0></a>
							<%} }
							%>
						<a href="#" onclick="organizarImpressao();"><img src="<%=Urls.urlPortalSislegisBase%>img/print_icon.gif" alt="Preparar documento para impressão" title="Preparar documento para impressão" border=0></a>
						<!-- Por nº de Lei:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->Por palavra:<br>
						<input type="hidden" value="<%=lei.getId() %>" name="iddoc" size=6>
						<input type="text" name="busca" size="30" />
						<input type="submit" value="Buscar">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<br><br>
						Baixe o arquivo digital usando o icone de PDF ao lado.
					</form>
					
				</div>
                <%	
				}	
				%>
			
			
		<div class="CorpoDocumento">
		
				
			<%
		 if ( print == null) {
			 
			if (lei.getId() != 0) {
				
				if (Suport.getCookie(request, "contrib_assuntos_"+lei.getId()) == null && !Urls.appBase.equals("publicacoes") ) {
					
					//Suport.setCookie(response, "contrib_assuntos_"+lei.getId(), "true", "Controle simples básico para usuários que já contribuiram com a classificação de Normas.");
					
					%>
					<span class="message_warning">Estamos classificando nossas Normas quanto ao assunto tratado.
								Contamos com sua colaboração para realizar esta tarefa.
								No final desta página você pode contribuir com este processo.</span>
					<%
				}
			 
		   }
		%>
			
			<%= Suport.getMessageWarning(request)%>
			
				<div style="text-align: left;">
				<%
				
					if (Suport.isIntranet(request)) {
						if (Seeker.getInstance().itemleiDao.selectItemForLanc(lei) != null) {
						%>
						<form action="<%=Urls.urlPortalSislegisOldBase%>lancamentos" method="post">							
							<input type="hidden" size="10" name="iddoc" value="<%=lei.getId()%>" >
							<input type="submit" value ="Itens a lançar"/>
						</form>
						<%
						}
							%>
<!-- img src="<%=Urls.urlPortalSislegisBase%>img/pdf_16.gif" alt="Adicionar-Alterar arquivo digital para esse documento" title="Adicionar arquivo digital para esse documento" border=0 align="middle"-->
					<br><br>
						<a href="cad.jsp?iddoc=<%=lei.getId() %>">[Alterar Cabeçalho]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="inserirArqDigital.jsp?iddoc=<%=lei.getId() %>">[Adicionar Arquivo Digitalizado]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="seeker?sv=-1&iddoc=<%=lei.getId() %>">[Alterar Classificação]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="revogarLei.jsp?iddoc=<%=lei.getId() %>">[Revogar Documento]</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				
						<hr>
					<%	if (Suport.isIntranetRoot(request)) { %>
						
						<a href="excluirDoc.jsp?iddoc=<%=lei.getId() %>">[Excluir Documento]</a>
						<a href="certidao.do?iddoc=<%=lei.getId() %>&p=1&v=1">[Criar Certidão]</a>
						<a href="toSapl?iddoc=<%=lei.getId() %>">[Exportar para SAPL]</a>
                  	<hr>
						<%
						}
					}
 			if (lei.getPublicado() || Suport.isIntranet(request)) {
				%>
				
					
					<% if (lei.getId_revogador() != null) {
						
					%><br><br>
						<a style="font-size: 14pt; color: red;" href="<%=Urls.urlPortalDomain%>/seeker?iddoc=<%=lei.getId_revogador().getId() %>"  title=""><%=lei.getLink_revogador() %></a>
						<br><br>
					<% }
							
				}

		 }
			

			
			if (lei.getPublicado() || Suport.isIntranet(request)) {
			 %>
			 
					<div class="iface_titulo"><span><%=lei.getEpigrafe() %></span>
					</div>
			 	
			 
		<% if ( print == null) {%>	
			<div class="icons" >
			      <%
						if (lei.getId() != 0) {
							if (lei.getPossuiarqdigital()) { %>
								<a class="<%=lei.getContent_type().indexOf("pdf")!=-1?"pdf32":"file32"%>" href="<%=lei.getContent_type().indexOf("pdf")!=-1?"downloadFile.pdf":"downloadFile"%>?sv=2&id=<%=lei.getId() %>" title="Download do arquivo digital - <%=lei.getName_file() %>"> </a>
							<%} }
							%>
			      <a class="print32" href="#" onclick="organizarImpressao();">&nbsp;</a>
			      
			      <% if (Urls.appBase.equals("portal")) { %>
			      <div class="funcConsolida">
			<input type="button" value="Parcialmente" onclick="consolidarParcialmente();" title="Acionar essa opção elimina todos os itens revogados.">
						<input type="button" value="Totalmente" onclick="consolidarTotalmente();" title="Acionar essa opção limpa todo o texto e mostra apenas os dispositivos em vigor.">
						
						Para Imprimir esse documento, use o icone de impressão ao lado.<br>Consolide o documento para uma melhor visualização<br>
						</div>			
				<%} %>
				</div>	
			<%} %>	
				
				<jsp:include page="escreverLei.jsp"></jsp:include>
			
		</div>			
		<%
			}
			else {
				%>		
				Documento privado!!!
			<%	
			}
		
		}		else {
		%>		
			Nenhum documento nesta categoria...
		<%
		}		
		%>	
		
		
		
						
			</div>
 
 
		<% if ( print == null) {%>	
 
 
			<div class="sislegis_leftCol" style="width: 100%; background-color: transparent; border: 0px;">
					
		<% if (Suport.getCookie(request, "contrib_assuntos_"+lei.getId()) == null && !Urls.appBase.equals("publicacoes")) {
		%>	   
      
      
<div class="sugestoes">
 
	
<form action="<%=Urls.urlPortalSislegisOldBase%>insertionLeis" method="post" >
<div class="iface_titulo"><span>Sugestão de Classificação por assuntos</span></div>
	<input type="hidden" name="id"  value="<%=lei.getId() %>">	 
	<input type="hidden" name="classificacao"  value="1">	 
		  
<%
Tipolei tl;
Tipodoc td = null;

Iterator<Tipodoc> itTd = Seeker.getInstance().tdDao.getCollection().iterator();
Iterator<Tipolei> itTl = null;

//Seeker.getInstance().tlDao.refreshTipos(lei);
while (itTd.hasNext()) {
	 
	td = itTd.next();
	
	if (td.getId() < 5)
		continue;

	%>
	<ul class="onglets-vert InitMenu">
	<div id="iface_titulo"><%=td.getDescr() %></div>		
	<%
	itTl = Seeker.getInstance().tlDao.selectAllWith(td).iterator();	
	while (itTl.hasNext()) { 
		tl = itTl.next();		 
		
		
		boolean flagCheckbox = false;
		
		for (Tipolei tlDoc : lei.getId_tipolei()) {
			
			if (tlDoc.getId() == tl.getId()) {
				flagCheckbox = true;
				break;
			} 
		}		
		
		if (tl.getLinkAlternativo() != null) {
		%>
		<li><label><input type="checkbox" name="tipolei" value="<%=tl.getId()%>" <%=flagCheckbox?"checked":"" %>  /> <a href=""><%=tl.getDescr()%></a></label></li>		
	<%
	   }
		else { 
	%>
		<li><label><input style="float:left;" type="checkbox" name="tipolei" <%=flagCheckbox?"checked":"" %>  value="<%=tl.getId()%>"/><a><%=tl.getDescr()%></a></label></li>
	<%}
	}
	%>
	</ul>	
<%
}
ReCaptcha c = ReCaptchaFactory.newReCaptcha("6LdP5fsSAAAAAOjIPSuwevSR36SzLntlDA9AXeQ3", "6LdP5fsSAAAAALat73E4WJnSrb19PyJgyGk6lncp", false);
out.print(c.createRecaptchaHtml(null, null));


%> 
		<div align="center"> <input type="submit" value="Enviar Sugestão"> </div>
	 
		 </form>
		  
		<div align="center">
		 
		
		<br><br><br><br>
		</div>
		 
			
			
			</div>
		
        <%
		   }
		%>
		
		
		
		</div>
		
		
        <%
		   }
		%>
		
		<% if ( print == null) {%>	
		</div>

        <%
		   }
		%>
		
</div>


				
					
				


		<% if ( print == null) {%>	

	<jsp:include page="admin/rodape.jsp"></jsp:include>
 
</body>
</html> 

        <%
		   }
		%>
 