<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="br.gov.go.camarajatai.dto.Assunto"%>
<%@page import="br.gov.go.camarajatai.dao.AssuntoDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%


AssuntoDAO assuntoDAO = new AssuntoDAO();

Assunto assunto, assunto2;
assunto = new Assunto();
assunto.setDescricao("teste de inserção de descrição de assunto");

assuntoDAO.inserir(assunto);

out.println(assunto.getId()+"<br>");

List<Assunto> listAssunto = assuntoDAO.list();

Iterator<Assunto> itAss = listAssunto.iterator();


itAss = listAssunto.iterator();

while (itAss.hasNext()) {
	
	assunto = itAss.next();
	
	out.println(assunto.getId()+" - "+assunto.getDescricao()+"<br>");
	assuntoDAO.atualizar(assunto);
	
}
/*
assunto2 =  itAss.next();

long i = assunto2.getId();

assunto2.setId(assunto.getId());
assuntoDAO.atualizar(assunto2);
assunto.setId(i);
assuntoDAO.atualizar(assunto);

*/

%>
</body>
</html>