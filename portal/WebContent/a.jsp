<%@page import="java.util.GregorianCalendar"%>
<%@page import="br.gov.go.camarajatai.sislegisold.suport.Urls"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="no-cache">
<meta http-equiv="Expires" content="0">
<meta http-equiv="Cache-Control" content="no-cache">
<title>Insert title here</title>
</head>
<body>
<object type="application/x-java-applet;version=1.6" height="40" width="500">
  <param name="cache_option" value="No"/>
  <param name="codebase" value="<%=Urls.urlPortalSislegisBase%>applets"/>
  <param name="code" value="br.gov.go.camarajatai.sislegis.applets.FileSignerApplet.class" />
  <param name="archive" value="Teste.jar"/>
  
  <param name="cache_version" value="1.0.<%=new GregorianCalendar().getTimeInMillis()%>">
  <param name="testeRandom" value="<%=Math.random()%>"/>
 <param name="java_arguments" value="-Xmx512m" />
 <param name="classloader_cache" value="false" />
  Applet failed to run.  No Java plug-in was found.
</object>
</body>
</html>