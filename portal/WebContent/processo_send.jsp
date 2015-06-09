<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<jsp:include page="../../admin/_cabec.jsp"></jsp:include>  

<script type="text/javascript">
</script>

<body>
  <jsp:include page="../../admin/topo.jsp"></jsp:include>  
       
  <div id="corpo">           

    <jsp:include page="../../admin/menu.jsp"></jsp:include>

      <div id="mainCol">

		<span id="titulo_sections">Envio de Email da Cesta de Fotos</span>

<%
Suport.getMessageWarning(request);
%>

	  			
        <form action="midia.do" method="get">
             	<fieldset>
             	
            	<fieldset style="float: left;">
            		<label>Compactar Fotos<br><input name="compactar" type="checkbox" value="compactar"/></label>
            	</fieldset>	
            	
             	
             	<fieldset>
            		<label>Título do Email<br><input type="text" name="titulo" value="" size="78"/></label>
            	</fieldset>
            	
            	<fieldset>
            		<label>Enviar para: (separe vários endereços por vírgula)<br><textarea  name="emails"  rows="4" cols="80"></textarea></label>
            	</fieldset>	
            	<fieldset>
            		<label class="labTextArea">Mensagem<br><textarea  name="mensagem"  rows="6" cols="40"></textarea></label>
            	</fieldset>	
            		
            		<label><input type="hidden" name="action" value="midia_sendmail_cesta">
	    	   		<input type="submit" name="submit" value="Enviar"></label>
	    	   		
	    	   		
	    	   		<br style="clear: both;" ><br>

<ul id="cestaFotos" style="clear: right; min-height: 300px;">

<%

   Object obCesta = session.getAttribute("cesta");
   List<Midia> cestaMidias = null;

	if (obCesta != null && obCesta instanceof List) {
		cestaMidias = (ArrayList<Midia>)obCesta;
		
		if (cestaMidias.size() > 0) {
			%>
			<script  type="text/javascript">
			 
			 $('.linkDownloadCesta').css('display','block');
			</script>
			
			<%
		}
		
		for (Midia midia: cestaMidias) {
%>          <li id="id<%=midia.getId()%>">
				<a class="removeItemCesta" style="background-position: -18px 0px;" href="javascript:removeCesta('midia_remove_cesta','<%=midia.getId()%>');">&nbsp;</a>
	        		
				<a style="display: block;" name="id<%=midia.getId()%>" id="id<%=midia.getId()%>" href="javascript:showImage('<%=midia.getId()%>')">
					<img src="midia.do?action=midia_view&escala=Pequeno&idImage=<%=midia.getId()%>"/>
				</a>
			</li>			
<%
		}	
	}
%>

</ul>
	    	   		<br style="clear:both;">
           		
           		</fieldset>  
           		
           		
           		      
        	</form>




           
      </div><!-- Final mainCol -->
    </div>    

  <jsp:include page="../../admin/rodape.jsp"></jsp:include>


</body>
</html>
