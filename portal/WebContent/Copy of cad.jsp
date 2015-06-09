<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Arquivo"%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipodoc"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@page import="br.gov.go.camarajatai.sislegisold.suport.Suport"%>
<%
Suport.validIntranet(request, response);
%>
<%@page import="br.gov.go.camarajatai.sislegisold.dto.Tipolei"%>
<%@page import="java.util.TreeSet"%>
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


$(document).ready(function(){//inicio o jQuery
	
        $("select[name='id_tipodoc']").change(function(){
        var selectTipo = $(this).val();//pegando o value do option selecionado
        //alert(idCombo1);//apenas para debugar a variável
        
                $.getJSON(//esse método do jQuery, só envia GET
                        '<%=Urls.urlPortalSislegisOldBase%>mediadorTipos',//script server-side que deverá retornar um objeto jSON
                        {selectTipo: selectTipo, tipo: 2},//enviando a variável

                        function(data){
                        //alert(data);//apenas para debugar a variável
                                
                                var option = new Array();//resetando a variável
                                
                                resetaCombo('id_tipolei');//resetando o combo
                                $.each(data, function(i, obj){
                                        
                                        option[i] = document.createElement('option');//criando o option
                                        $( option[i] ).attr( {value : obj.id} );//colocando o value no option
                                        $( option[i] ).append( obj.nome );//colocando o 'label'

                                        $("select[name='id_tipolei']").append( option[i] );//jogando um à um os options no próximo combo
                        });
                });
        });
});     

/* função pronta para ser reaproveitada, caso queira adicionar mais combos dependentes */
function resetaCombo( el )
{
        $("select[name='"+el+"']").empty();//retira os elementos antigos
        var option = document.createElement('option');                                  
        $( option ).attr( {value : '0'} );
        $( option ).append( '' );
        $("select[name='"+el+"']").append( option );
}


//Filtro de Busca de Publicação
function filterPublic(inputSearch) {
    
   var textSearch = $('#search_'+inputSearch).val();
   var options = $('#'+inputSearch).data();

   if (options == null || options.length == undefined) {
      $('#'+inputSearch).data($('#'+inputSearch+' option'));
   }

   $('#'+inputSearch).empty();
   
   $.each(options, function(i) {
      var option = options[i];
      if (option.text.indexOf(textSearch) >= 0) {
         $('#'+inputSearch).append(
            $('<option>').text(option.text).val(option.value)
         );
      }
   });
}



</script>

</head>

<body>

<div class="GlobalStyle">

<%
boolean comMenu = true;
Iterator<Tipodoc> it= Seeker.getInstance().tdDao.selectTips().iterator();
Iterator<Arquivo> itArq= Seeker.getInstance().arquivoDao.listaArquivos().iterator();
Iterator<Documento> itDoc = Seeker.getInstance().prepararPesquisa(null, null, 0, 2000, null).getLista().iterator();
Tipodoc tipodoc;
Arquivo arq;
Documento doc = new Documento();
Documento aux_doc = null;
int iddoc = 0; 
try {
	iddoc = Integer.parseInt(request.getParameter("iddoc"));
}
catch (NumberFormatException e) {
	
}
if (iddoc != 0) {
	 doc.setId(iddoc);
	 doc = Seeker.getInstance().selectOneDoc(doc);
	 
	 doc.setId_tipolei(Seeker.getInstance().tlDao.selectOne(doc.getId_tipolei()));
	 doc.getId_tipolei().setIdTipoDoc(Seeker.getInstance().tdDao.selectOne(doc.getId_tipolei().getIdTipoDoc()));
}

	%>


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
			
			<br><br>
<div class="iface_titulo"><span>Cadastro de Documentos</span></div>
<div>
<form action="<%=Urls.urlPortalSislegisOldBase%>insertionLeis">
	<input type="hidden" name="id"  value="<%=doc.getId() %>">
	<table cellpadding="10" cellspacing="0">
		<tr>
			<td style="width: 50%;" colspan="2">
					Tipo de Documento:<br>
					<select name="id_tipodoc" style="width: 100%;">
						<option value="0" > </option>
							<%
							while(it.hasNext()) {
							
								tipodoc = it.next();	
							%>
								<option value="<%=tipodoc.getId() %>" <%=(doc.getId() != 0 && doc.getId_tipolei().getIdTipoDoc().getId() == tipodoc.getId())?"selected":"" %>><%=tipodoc.getDescr()%></option> 
							<%
							}
							%>
					</select>
			</td>
			<td align="right" colspan="2" style="width: 50%;">
					Sub-Tipo:<br>
					<select name="id_tipolei" style="width: 100%;">
					
					    <%
					    if (iddoc != 0) {
					    %>
   					    <option value="<%=doc.getId_tipolei().getId() %>" selected>Selecionado: <%=doc.getId_tipolei().getDescr()%></option> 
					    <%
					    }					    
					    %>					
						<option value="0"></option>				
					</select>
			</td>
		</tr>
		<tr>
			<td style="width: 100%;" colspan="4">
			       <input type="text" name="search_id_doc_principal"   id="search_id_doc_principal"  onkeyup="javascript:filterPublic('id_doc_principal');"/>
			<br>
					Anexar a outro documento:<br>
					<select name="id_doc_principal"  id="id_doc_principal" style="width: 100%;">
						<option value="0"> </option>
							<%
							while(itDoc.hasNext()) {
							
								aux_doc = itDoc.next();	
								if (aux_doc.getId_doc_principal() != null)
									continue;
								
							%>
								<option value="<%=aux_doc.getId() %>" <%=(doc.getId() != 0 && doc.getId_doc_principal() != null && doc.getId_doc_principal().getId() == aux_doc.getId())?"selected":"" %>><%=aux_doc.getEpigrafe()%></option> 
							<%
							}
							%>
					</select>
			</td>
		
		</tr>
		<tr>
			<td style="width: 25%;">
				Número: <br><input type="text" name="numero" size="7" maxlength="10" value="<%=doc.getNumero() %>">
			</td>
			<td style="width: 25%;">
				Data do documento: <br> <input type="text" name="data_lei" size="15" value="<%=Suport.timeStampToStr(doc.getData_lei())%>">
			</td>
			<td style="width: 25%;" >
					Status de Publicação:<br>
					<select name="doc_status_pub"  id="doc_status_pub" style="width: 100%;">
						<option value="False" <%=!doc.getPublicado()?"selected":"" %>>Privado</option>
						<option value="True" <%=doc.getPublicado()?"selected":"" %>>Público</option>
							
					</select>
			</td>
			<td>	Arquivamento:<br>
					<select name="id_arquivo" style="width: 100%;">
						<option value="0">Não Especificado </option>
							<%
							while(itArq.hasNext()){
							
								arq=itArq.next();	
							%>
								<option value="<%=arq.getId()%>" <%=(doc.getId_arquivo() != null && doc.getId_arquivo().getId() == arq.getId())?"selected=\"selected\"":""%> > <%=arq.getCodArquivo()+" - "+arq.getNome()%></option> 
							<%
							
							}
							%>
					</select>
			</td>
			
		</tr>
		<tr>
			<td style="width: 50%;" colspan="4">
				Epígrafe: <span class="coment">(Título do documento)</span><br>
				<input type="text" name="epigrafe" style="width: 100%;" value="<%=doc.getEpigrafe() %>">					
			</td>
		</tr>
		<tr>
			<td style="width: 50%;" colspan=2>
			Ementa: <span class="coment">(Breve descrição)</span><br>
			<textarea name="ementa" rows="5" style="width: 100%;"><%=doc.getEmenta() %></textarea>
			</td>
			<td style="width: 50%;" colspan=2>
			Enunciado: <span class="coment">(Texto Inicial)</span><br>
			<textarea name="enunciado" style="width: 100%;" rows="5"><%=doc.getEnunciado() %></textarea>
			</td>
		</tr>
		<tr>
			<td style="width: 50%;" colspan=2>
			Indicação: <span class="coment">(Observação em destaque)</span><br>
			<textarea name="indicacao" rows="5" style="width: 100%;"><%=doc.getIndicacao() %></textarea>
			</td>
			<td style="width: 50%;" colspan=2>
			Preâmbulo:<br>
			<textarea name="preambulo" style="width: 100%;" rows="5"><%=doc.getPreambulo() %></textarea>
			</td>
		</tr>
		
		<tr>
			<td style="width: 100%;" colspan="4">
			Texto Final:<br>
			<textarea name="texto_final" style="width: 100%;" rows="5"><%=doc.getTexto_final() %></textarea>
			</td>
		</tr>
		
		<tr>
			<td style="width: 50%;" colspan=2>
				Autoria: <span class="coment">(Assinam o documento)</span><br>
				<input type="text" name="assinatura" style="width: 100%;" value="<%=doc.getAssinatura() %>">					
			</td>
			<td style="width: 50%;" colspan=2>
				Cargo Assinante:<br>
				<input type="text" name="cargo_assinante" style="width: 100%;" value="<%=doc.getCargo_assinante() %>">					
			</td>
		</tr>		
	</table>
	<div align="center"> <input type="submit" value="<%=doc.getId()==0?"Cadastrar":"Gravar Alterações" %>"> </div>
	</form> 
	</div>

						
			</div>
		
		</div>

</div>

	<jsp:include page="admin/rodape.jsp"></jsp:include>




</body>
</html> 
