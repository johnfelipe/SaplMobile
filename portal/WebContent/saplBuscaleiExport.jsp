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
	
	String export = request.getParameter("export");	
 %> 
			<% 
if (lei != null) {
	
 
			
			if (lei.getPublicado() || Suport.isIntranet(request)) {
			 %>		
			 <div class="dispositivos">			 
				<jsp:include page="saplEscreverLeiExport.jsp"></jsp:include>				
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