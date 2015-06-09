package br.gov.go.camarajatai.sislegisold.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import br.gov.go.camarajatai.sislegisold.dto.DocDestaque;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Tipodoc;

public class DocDestaqueDAO extends DAO<DocDestaque> {

	public Statement stm = PostgreConnection.getInstance().newStatement();
	
	private TreeSet<DocDestaque> collection = new TreeSet<DocDestaque>();
	
	String sqlSelectAll = "select * from docdestaque where ordem >= 0 order by ordem";
	
	public DocDestaqueDAO() {
		selectAll();
	}
	
	public TreeSet<DocDestaque> getCollection() {
		selectAll();
		return collection;
	}
	
	public void selectAll() {
	
		
		
		try {
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			DocDestaque docDest = null;
			while (rs.next()) {
			
				docDest = new DocDestaque();
				
				docDest.setId(rs.getInt("id"));
				docDest.setTitulo(rs.getString("titulo"));
				docDest.setOrdem(rs.getInt("ordem"));
				docDest.setId_doc(new Documento(rs.getInt("id_doc")));
				
				collection.add(docDest);
				
			}	
		
		
		} catch (SQLException e) {
		
		}
	}
	
	public List<DocDestaque> selectAllForAdm() {
	
		List<DocDestaque> lista = new LinkedList<DocDestaque>();
		
		try {
			ResultSet rs = stm.executeQuery("select * from docdestaque order by ordem");
			DocDestaque docDest = null;
			while (rs.next()) {
			
				docDest = new DocDestaque();
				
				docDest.setId(rs.getInt("id"));
				docDest.setTitulo(rs.getString("titulo"));
				docDest.setOrdem(rs.getInt("ordem"));
				docDest.setId_doc(new Documento(rs.getInt("id_doc")));
				
				lista.add(docDest);
				
			}	
		
		
		} catch (SQLException e) {
		
		}
		
		return lista;
	}
	

}
