<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>
<%@page import="br.gov.go.camarajatai.olerite.dto.Descontos"%>
<%@page import="br.gov.go.camarajatai.olerite.dto.Proventos"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.gov.go.camarajatai.olerite.dto.Funcionario"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.DocDestaque"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipodoc"%>
<%@page import="java.util.List"%>
<%@page import="br.gov.go.camarajatai.sislegisold.business.Seeker"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Documento"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Câmara Municipal de Jataí­ - Portal da Transparência
Pública</title>

<script type="text/javascript" src="/publicacoes/lib/jquery.js"></script>
<script type="text/javascript" src="/publicacoes/lib/jquery-latest.js"></script> 
<script type="text/javascript" src="/publicacoes/lib/jquery.tablesorter.min.js"></script>

<script type="text/javascript"
	src="<%=Urls.urlPortalSislegisBase%>lib/menuBox.js"></script>
<script type="text/javascript"
	src="<%=Urls.urlPortalSislegisBase%>lib/libSislegis.js"></script>


<link rel="stylesheet"
	href="<%=Urls.urlPortalSislegisBase%>lib/styleGeral.css">
<link rel="stylesheet"
	href="<%=Urls.urlPortalSislegisBase%>lib/menu.css">

<script type="text/javascript"> 


$(document).ready(function() {


	$('table tr').addClass('listaFuncs');
	$('table tr:odd').addClass('listaFuncsOdd');
	//$('table tr:odd').addClass('listaFuncsHover');
	
	

	$("#selectMes").change(function() {
		$("#formBusca").submit();
		});

	$("#selectAno").change(function() {
		$("#formBusca").submit();
		});
	
	$("#updateProvExpand").change(function() {
		$("#formBusca").submit();
		});
	$("#myTable").tablesorter();
});





</script>

</head>

<body>

<%

boolean comMenu = true;
//String all = request.getParameter("Todos");

ArrayList<Funcionario> lFunc = null;

Object ob = request.getAttribute("listaFuncionarios");

if (ob == null || !(ob instanceof ArrayList)) {
	lFunc = new ArrayList<Funcionario>();	
}
else { 
   lFunc = (ArrayList)ob;
}





Iterator<Funcionario> it=lFunc.iterator();
Funcionario f;

GregorianCalendar data = new GregorianCalendar();   

String anoinformado = (String)request.getAttribute("anoinformado");
String mesinformado = (String)request.getAttribute("mesinformado");

String matricula = (String)request.getAttribute("matricula");

String tipo = request.getParameter("tipo");

if (anoinformado == null) {

    anoinformado = request.getParameter("ano");
    mesinformado = request.getParameter("mes");
	
	if (anoinformado == null) {	
	   anoinformado = "";
	   mesinformado = "";
	}
}
//matricula = null;

boolean prov_expandir = request.getParameter("p")!=null;

String msg = (String)request.getAttribute("msg");

%>
<div class="GlobalStyle">
	
		<jsp:include page="admin/topo.jsp"></jsp:include>
	
	
		<div class="Corpo">
						
			<div class="sislegis_leftCol">
			
							
				<% if (comMenu) {				
					
					%> 
					<jsp:include page="admin/menu.jsp"></jsp:include>
				<%	
				}	
				%>
			
			</div>
		
			<div class="sislegis_mainCol">
			
			<form action="olerite.do" id="formBusca">
					
	<fieldset>
	    <legend> Escolher outro Mês </legend>
	    	 
				<label class="campoLabel">
				    Mês<br>
					<select name="mes" id="selectMes">
					
					<% int anoAtual = data.get(GregorianCalendar.YEAR); %>
					
					<% if (anoAtual > 2013) { %>
					    <option value="01" <%=mesinformado.equals("01") ? "selected":"" %>>Janeiro</option>
						<option value="02" <%=mesinformado.equals("02") ? "selected":"" %>>Fevereiro</option>
						<option value="03" <%=mesinformado.equals("03") ? "selected":"" %>>Março</option>
						<option value="04" <%=mesinformado.equals("04") ? "selected":"" %>>Abril</option>
						<option value="05" <%=mesinformado.equals("05") ? "selected":"" %>>Maio</option>
						<option value="06" <%=mesinformado.equals("06") ? "selected":"" %>>Junho</option>
						<option value="07" <%=mesinformado.equals("07") ? "selected":"" %>>Julho</option>
						<option value="08" <%=mesinformado.equals("08") ? "selected":"" %>>Agosto</option>
						<option value="09" <%=mesinformado.equals("09") ? "selected":"" %>>Setembro</option>
						<% } %>
						<option value="10" <%=mesinformado.equals("10") ? "selected":"" %>>Outubro</option>
						<option value="11" <%=mesinformado.equals("11") ? "selected":"" %>>Novembro</option>
						<option value="12" <%=mesinformado.equals("12") ? "selected":"" %>>Dezembro</option>
					</select>
				</label>
					
				<label class="campoLabel">
				    Ano<br>
					<select name="ano" id="selectAno">
							<%
							for (int i = anoAtual; i >= 2013; i-- ){
							%>
						<option value="<%=i%>" <%=anoinformado.equals(String.valueOf(i))?"selected":"" %>><%=i%></option>
						<%} %>
					</select>
				</label>
				
				<%				
				if (matricula != null) {
				%>				
					<label class="campoLabel">
					    <br>
					    <a href="olerite.do?ano=<%=anoinformado+"&mes="+mesinformado+(prov_expandir?"&p=1":"") %>">Voltar para lista completa</a>
					<input type="hidden" value="<%= matricula%>" name="matricula">
					<input type="hidden" value="olerite_func" name="action">
					</label>
				<%
				}
				%> 
				<label class="campoLabel" style="float:right; display:none;">
					    Expandir Proventos<br>
					<input type="checkbox" <%=prov_expandir?"checked":""%> name="p" id="updateProvExpand">
					
					</label> 
				</fieldset>
				
				<input type="hidden" name="tipo" value="<%=tipo%>">
			</form>	
			
			
			<%
			if (msg != null) {
			%>
			
			<div style="color:red;padding:20px; border: 1px solid red; margin: 20px; font-size:11pt; background-color: #fff;"><%=msg %></div>
			
			<%} %>
			 
				
				<div class="iface_titulo"><span style="font-size:11pt;">Quadro de Pessoal Mês: <%=mesinformado %> / <%=anoinformado %></span></div>
			
			
					<table style="border: 1px solid #aaa;" width=100% id="myTable" class="tablesorter">
					
					<thead>
					    <tr> 
					    <th style="padding:10px;" class="listaFuncsHead">Servidor(a)</th>
					    </tr>
					</thead>
					
					<%
					int i  = 1;
					
					String cargosPermitidos[] = new String[2];
					cargosPermitidos[0] = "PRESIDENTE";
					cargosPermitidos[1] = "VEREADOR";
					
					while (it.hasNext()) {
						
						f = it.next();
						
						/* if (all == null) {
							
							boolean isPerm = false;
							
							for (int k = 0; k < cargosPermitidos.length; k++) {
								
								if (f.getCargo().indexOf(cargosPermitidos[k]) != -1) {
									isPerm = true;
									break;
								}				
							}			
							
							if (!isPerm) {
								continue;		
							} 
						} */						
						
						%>
						<tr>
						<td><a href="#"><%=f.getNome().trim() %></a></td>
						</tr>
						<%
					}		
					%>
			
			</table> 
			<br>
			<br>
				</div>
		
		</div>

</div>

	<jsp:include page="admin/rodape.jsp"></jsp:include>

</body>
</html>
