package br.gov.go.camarajatai.sislegisold.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Tipodoc;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;

public class TipoleiDAO extends DAO<Tipolei> {

	public Statement stm = PostgreConnection.getInstance().newStatement();
	
	private TreeSet<Tipolei> collection = new TreeSet<Tipolei>();
	
	String sqlSelectAll = "select * from tipolei where ordem >= 0 order by ordem";
	
	public TipoleiDAO() {
		selectAll();
	}
	
	public TreeSet<Tipolei> getCollection() {
		return collection;
	}
	
	public void selectAll() {		
		
		try {
			
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			Tipolei tipolei = null;
			while (rs.next()) {
			
				tipolei = new Tipolei();
				
				tipolei.setId(rs.getInt("id"));
				tipolei.setDescr(rs.getString("descr"));
				tipolei.setOrdem(rs.getInt("ordem"));
				
				tipolei.setLocalidade(rs.getString("localidade"));
				tipolei.setAutoridade(rs.getString("autoridade"));
				tipolei.setTipo(rs.getString("tipo"));
				tipolei.setLinkAlternativo(rs.getString("linkAlternativo"));
				tipolei.setIdTipoDoc(new Tipodoc(rs.getInt("idTipoDoc")));

				tipolei.setClassif_assuntos(rs.getBoolean("classif_assuntos"));

				collection.add(tipolei);
				
			}	
		
		
		} catch (SQLException e) {
		
		}
	}
	public Documento refreshTipos(Documento doc) {

		ArrayList<Tipolei> lista = new ArrayList<Tipolei>();

		try {
			String sql = "select t.*, td.descr descrtipodoc, a.oficial, a.sugestoes from documento d, assuntos a, tipolei t, tipodoc td where d.id = a.documento and t.id = a.tipo and t.\"idTipoDoc\" = td.id and d.id = "+doc.getId()+" order by t.ordem";
			ResultSet rs = stm.executeQuery(sql);
			Tipolei tipolei = null;
			while (rs.next()) {
			
				tipolei = new Tipolei();
				
				tipolei.setId(rs.getInt("id"));
				tipolei.setDescr(rs.getString("descr"));
				tipolei.setOrdem(rs.getInt("ordem"));
				
				tipolei.setLocalidade(rs.getString("localidade"));
				tipolei.setAutoridade(rs.getString("autoridade"));
				tipolei.setTipo(rs.getString("tipo"));

				tipolei.setLinkAlternativo(rs.getString("linkAlternativo"));
				
				tipolei.setClassif_assuntos(rs.getBoolean("classif_assuntos"));
				
				tipolei.setIdTipoDoc(new Tipodoc(rs.getInt("idTipoDoc")));
				tipolei.getIdTipoDoc().setDescr(rs.getString("descrtipodoc"));

				tipolei.tmp_oficial = rs.getBoolean("oficial");
				tipolei.tmp_sugestoes  = rs.getInt("sugestoes");
				lista.add(tipolei);
				
			}	
		
		
		} catch (SQLException e) {
		
		}
		doc.setId_tipolei(lista);
		
		
		return doc;
	}
	
	public List<Tipolei> selectAllForAdm() {
	
		List<Tipolei> lista = new LinkedList<Tipolei>();
		
		try {
			ResultSet rs = stm.executeQuery("select * from tipolei order by ordem");
			Tipolei tipolei = null;
			while (rs.next()) {
			
				tipolei = new Tipolei();
				
				tipolei.setId(rs.getInt("id"));
				tipolei.setDescr(rs.getString("descr"));
				tipolei.setOrdem(rs.getInt("ordem"));
				
				tipolei.setLocalidade(rs.getString("localidade"));
				tipolei.setAutoridade(rs.getString("autoridade"));
				tipolei.setTipo(rs.getString("tipo"));
				tipolei.setClassif_assuntos(rs.getBoolean("classif_assuntos"));

				tipolei.setLinkAlternativo(rs.getString("linkAlternativo"));
				tipolei.setIdTipoDoc(new Tipodoc(rs.getInt("idTipoDoc")));


				lista.add(tipolei);
				
			}	
		
		
		} catch (SQLException e) {
		
		}
		
		return lista;
	}
	


	public Tipolei selectOne(Tipolei _tipolei) {
		Tipolei tipolei = null;

		if (_tipolei == null)
			return null;
		
		
		if (collection.contains(_tipolei)) {
			tipolei = collection.ceiling(_tipolei);
		}
		else {
			try {
				ResultSet rs = stm.executeQuery("select * from tipolei where id ="+_tipolei.getId());
				while (rs.next()) {

					tipolei = new Tipolei();

					tipolei.setId(rs.getInt("id"));
					tipolei.setDescr(rs.getString("descr"));
					tipolei.setOrdem(rs.getInt("ordem"));

					tipolei.setLocalidade(rs.getString("localidade"));
					tipolei.setAutoridade(rs.getString("autoridade"));
					tipolei.setTipo(rs.getString("tipo"));
					tipolei.setLinkAlternativo(rs.getString("linkAlternativo"));
					tipolei.setClassif_assuntos(rs.getBoolean("classif_assuntos"));
					
					tipolei.setIdTipoDoc(new Tipodoc(rs.getInt("idTipoDoc")));

					collection.add(tipolei);

				}	

			} catch (SQLException e) {}
		}
		return tipolei;
	}
	
	public List<Tipolei> selectAllWith(Tipodoc td) {
		List<Tipolei> lista = new ArrayList<Tipolei>();
		try {
			ResultSet rs = stm.executeQuery("select * from tipolei where \"idTipoDoc\" ="+td.getId()+ " and ordem >= 0 order by ordem");
			Tipolei tl;
			
			while (rs.next()) {

				tl = new Tipolei();

				tl.setId(rs.getInt("id"));
				tl.setDescr(rs.getString("descr"));
				tl.setOrdem(rs.getInt("ordem"));
				
				tl.setLocalidade(rs.getString("localidade"));
				tl.setAutoridade(rs.getString("autoridade"));
				tl.setTipo(rs.getString("tipo"));
				tl.setLinkAlternativo(rs.getString("linkAlternativo"));
				tl.setClassif_assuntos(rs.getBoolean("classif_assuntos"));

				tl.setIdTipoDoc(new Tipodoc(rs.getInt("idTipoDoc")));


				lista.add(tl);

			}
		}
		catch (SQLException e) {}
		
		return lista; 
	}
	
	
	

}
