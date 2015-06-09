package br.gov.go.camarajatai.sislegisold.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import br.gov.go.camarajatai.sislegisold.dto.Tipodoc;

public class TipodocDAO extends DAO<Tipodoc> {

	public Statement stm = PostgreConnection.getInstance().newStatement();
	
	private TreeSet<Tipodoc> collection = new TreeSet<Tipodoc>();
	
	String sqlSelectAll = "select * from tipodoc where ordem >= 0 order by ordem";
	
	String sqlSelectTips = "select * from tipodoc where ordem >= 0 and id > 2 order by ordem";
	
	public TipodocDAO() {
		selectAll();
	}
	
	public TreeSet<Tipodoc> getCollection() {
		return collection;
	}

	public void selectAll() {
	
		
		try {
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			Tipodoc tipodoc = null;
			while (rs.next()) {
			
				tipodoc = new Tipodoc();
				
				tipodoc.setId(rs.getInt("id"));
				tipodoc.setDescr(rs.getString("descr"));
				tipodoc.setOrdem(rs.getInt("ordem"));

				
				collection.add(tipodoc);
				
			}	
		
		
		} catch (SQLException e) {
		
		}
	}
	

	public List<Tipodoc> selectTips() {
	
		
		List<Tipodoc> lista = new ArrayList<Tipodoc>();
		try {
			ResultSet rs = stm.executeQuery(sqlSelectTips);
			Tipodoc tipodoc = null;
			while (rs.next()) {
			
				tipodoc = new Tipodoc();
				
				tipodoc.setId(rs.getInt("id"));
				tipodoc.setDescr(rs.getString("descr"));
				tipodoc.setOrdem(rs.getInt("ordem"));

				
				lista.add(tipodoc);
				
			}	
		
		
		} catch (SQLException e) {
		
		}
		return lista;
	}
	


	public Tipodoc selectOne(Tipodoc _tipodoc) {
		Tipodoc tipodoc = null;

		if (collection.contains(_tipodoc)) {
			tipodoc = collection.ceiling(_tipodoc);
		}
		else {
			try {
				ResultSet rs = stm.executeQuery("select * from tipodoc where id ="+_tipodoc.getId());
				while (rs.next()) {

					tipodoc = new Tipodoc();

					tipodoc.setId(rs.getInt("id"));
					tipodoc.setDescr(rs.getString("descr"));
					tipodoc.setOrdem(rs.getInt("ordem"));


					collection.add(tipodoc);

				}	

			} catch (SQLException e) {}
		}	


		return tipodoc;

	}


}
