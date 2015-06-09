<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><%
Suport.validIntranet(request, response);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page
	import="br.gov.go.camarajatai.sislegisold.dao.PostgreConnection"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.TreeSet"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipolei"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Itemlei"%>
<%@page
	import="br.gov.go.camarajatai.sislegisold.business.IteratorLeis"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>
<%@page
	import="br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei"%>
<%@page import="java.util.Map.Entry"%>


<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%><html>

<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Câmara Municipal de Jataí - Portal da Transparência Pública</title>

	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/jquery.js"></script>
		
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>	
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>	

    <script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/ckeditor/ckeditor.js"></script>

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
			
<div class="iface_titulo"><span>Alteração de Itens de Lei</span></div>
<div>

<%

	int idItemLei = Integer.parseInt(request.getParameter("itemlei"));

	Itemlei il = new Itemlei();
	il.setId(idItemLei);
	
	Seeker.getInstance().itemleiDao.selectItem(il);
	
	
	Documento _lei = new Documento();
	_lei.setId(il.getId_lei());
	_lei = Seeker.getInstance().leiDao.selectOne(_lei);
	
	String editor = request.getParameter("editor");
	
	
	
%>

<div class="linkItens">
<span class="link"><%=_lei.getEpigrafe() %></span><br>
<%="<a class=\"coment\">"  +  il.getNomeclaturaCompletaParaPesquisa() +"</a>"%><br>
</div>


<div class="linkItens">

<a class="link_alterador" href="<%=Urls.urlPortalSislegisOldBase%>alterarItemLei.jsp?itemlei=<%=il.getId()%>&editor=1">[Editar com CKEditor]</a>
			
		<form action="<%=Urls.urlPortalSislegisOldBase%>alterarItemLei" method="post">
		Informe o número do dispositivo alterador:&nbsp;&nbsp;<input type="text" name="id_dono" size="10"/><br>
		Nivel:&nbsp;&nbsp;<input type="text" name="nivel" size="10" value="<%=il.getNivel() %>"/><br><br>
		Informe a nova redação para:<br><br>
		<%=il.getNomeclaturaCompleta()%><br>
		<input type="hidden" name="id" value = "<%=il.getId() %>"/>
		<!-- textarea rows="15" cols="80" name="texto" onkeyup="Javacript:document.getElementById('preview').innerHTML = this.value"><%=il.getTexto() %></textarea-->
		
		
		
		
		
		<textarea id="texto" name="texto" tabindex="10" rows="15" cols="80" style="width:100%;" rows="6"><%=il.getTexto() %></textarea>
            		 
            		 <% if (editor != null) { %>
            		    	        <script>
				
											// Replace the <textarea id="editor"> with an CKEditor
											// instance, using default configurations.
											CKEDITOR.replace( 'texto', {
												uiColor: '#eeddaa', 
												
												height: '400'
											});
								
										</script>
						<% } %>
		
		
		
		
		
		<input type="submit" value ="Enviar"/>
		</form>
		<div id="preview"></div>
		
		
</div>

			
			</div>
		
		</div>

</div>
</div>

	<jsp:include page="admin/rodape.jsp"></jsp:include>


</body>
</html> 
