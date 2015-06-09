<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>
<%@page import="java.util.LinkedList"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>
<%@page
	import="br.gov.go.camarajatai.sislegisold.business.Digitalization"%>
<%@page import="java.util.ArrayList"%>
<%@page
	import="br.gov.go.camarajatai.sislegisold.business.Digitalization.Page"%>
<%@page
	import="br.gov.go.camarajatai.sislegisold.business.Digitalization.Page"%><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Câmara Municipal de Jataí - Portal da Transparência Pública</title>

	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/jquery.js"></script>
			
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>	
	<script type="text/javascript" src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>	


	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/styleGeral.css">
	<link rel="stylesheet" href="<%=Urls.urlPortalSislegisBase%>lib/menu.css">

<%



int iddoc = 0;

try {
	iddoc = Integer.parseInt(request.getParameter("iddoc"));
} catch (Exception e) {
	response.sendRedirect("error.jsp");
}



%>

<script type="text/javascript"> 

	var scale = 0.3;

	
	function aumenta(objeto) {
		if (scale >= 0.99) {
			return;
		}
		scale += 0.1;

		var nd = document.getElementsByName(objeto);

		var i;
		var j;
		j = scale * 700;
		j = j + 'px';
		//alert(j);
		for (i = 0; i < nd.length; i++) {
			nd.item(i).style.width = j;
		}
	}

	function diminui(objeto) {
		if (scale <= 0.10) {
			return;
		}
		scale -= 0.1;

		var nd = document.getElementsByName(objeto);

		var i;
		var j;
		j = scale * 700;
		j = j + 'px';
		//alert(j);
		for (i = 0; i < nd.length; i++) {
			nd.item(i).style.width = j;
		}
	}

	function onChangeCheckPage(event) {

		{
		 $.post("<%=Urls.urlPortalSislegisOldBase%>includeArqDigital",{sv: "2", pospag: $(event).val()}, function(data){

			 var idRemove;
			 idRemove = '#textopag'+$(event).val();
			 $(idRemove).remove(); 
			 idRemove = '#img'+$(event).val();
			 $(idRemove).remove(); 
			 $(event).remove(); 
			 
		   $("#preview").html(data);
		 });

		 

		}
	}
	
	function onProcessPDF() {

		{
		 $.post("<%=Urls.urlPortalSislegisOldBase%>includeArqDigital",{sv: "1"}, function(data){

			 window.open("<%=Urls.urlPortalSislegisOldBase%>seeker?iddoc=<%=iddoc%>","_self");
		   $("#preview").html(data);
		 });	 

		}
	}
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


	<%
	
	
	Suport.validIntranet(request, response);
	
	
	Object ob = Suport
			.getObjectSession(
					"digitalization",
					"br.gov.go.camarajatai.sislegisold.business.Digitalization",
					request);
	Digitalization digit = (Digitalization) ob;

	
	Documento lei = new Documento();
	lei.setId(iddoc);

	lei = Seeker.getInstance().selectOneDoc(lei);
	request.getSession().setAttribute("lei",lei);

	%>

	<div class="iface_titulo"><span>Inclusão de Arquivo digital</span></div>

	<a name="form"></a>
	<div class="linkItens">

		<a class="link"
			href="seeker?iddoc=<%=lei.getId()%>"
			onmouseover="javascript:getElementById('id_<%=lei.getId()%>').style.fontSize = '12pt';"
			onmouseout="javascript:getElementById('id_<%=lei.getId()%>').style.fontSize = '8pt';">
			<%=lei.getEpigrafe()%></a><br>
	
	
		<div class="coment" id="id_<%=lei.getId()%>">
			<%=lei.getEmenta()%></div>
	
		<div style="text-align: right;">
			<span class="contadores" 
					title="Quantidade de acessos a este documento">
						(<%=lei.getConsultas()%>)</span>
			<br>Pendências para este documento.<br>
	
			<%
			if (!lei.getPossuiarqdigital()) {%>
	
			<a href="inserirArqDigital.jsp?iddoc=<%=lei.getId()%>">
	
			<img
				src="<%=Urls.urlPortalSislegisBase%>img/pdf_16.gif"
				alt="Adicionar arquivo digital para esse documento"
				title="Adicionar arquivo digital para esse documento" border=0
				align="middle"></a>
		
			<%
			}
			 %>
	
		</div>
	</div>
	<form 	style="text-align: right;"	
			action="<%=Urls.urlPortalSislegisOldBase%>includeArqDigital" 
			method="post"
			enctype="multipart/form-data" 
			name="foto_up" 
			id="foto_up">
		<%
		Iterator<Digitalization.Page> it = digit.iterator();
		Digitalization.Page pagina;
		int pg = -1;
		while (it.hasNext()) {
			pagina = it.next();
			pg++;
		%> 

		<span id="textopag<%=pg%>"><br>"<%=pagina.nameFile%>" - Pág <%=pg + 1%></span>

		<input type="checkbox" name="img" 
				value="<%=pg%>" 
				checked="checked" 
				title="Página <%=pg + 1%>"
				onchange="onChangeCheckPage(this);">
		<%
		} %>
		<br><br>
		 <input type="button" value="Processar PDF" onclick="onProcessPDF();"/> &nbsp;&nbsp;&nbsp;&nbsp;
		 <input type="file" name="imagem" size="1"/>
		 <input type="submit" value="enviar">
	</form>
	<br>
	<div style="text-align: right;">
		<img title="Aumentar tamanho do preview da página"
				src="img/zoommais.png"	
				onclick="aumenta('pg');" 
				width="16px"> 
		<img title="Diminuir tamanho do preview da página" 
				src="img/zoommenos.png"
				onclick="diminui('pg');" width="16px">
	</div>
	
	<div style="text-align: center; background-color: #aaaaaa;"><br>
		<%
		it = digit.iterator();
		pg = -1;
		while (it.hasNext()) {
			pagina = it.next();
			pg++;
		%>
		<img title="<%=pagina.nameFile %>"
				name="pg" 
				id="img<%=pg%>" 
				alt="pagina"	
				src="<%=Urls.urlPortalSislegisOldBase%>generateFile.pdf?sv=1&id=<%=pg%>" 
				style="width: 210px;"><%= ((pg+1)%2)==0?"<br><br>":"" %>
		<%
		} %>
		
		
		



			
			
			</div>
		
		</div>

</div>

</div>





</body>
</html> 
