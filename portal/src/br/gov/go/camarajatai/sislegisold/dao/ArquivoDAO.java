package br.gov.go.camarajatai.sislegisold.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import br.gov.go.camarajatai.sislegisold.dto.Arquivo;

public class ArquivoDAO extends DAO<Arquivo> {

	public Statement stm = PostgreConnection.getInstance().newStatement();
	
	String sqlSelectAll = "select * from arquivo order by nome";
	
	public ArquivoDAO() {
	 
	}
	
	
	public List<Arquivo> listaArquivos() {
			
		List<Arquivo> lista = new ArrayList<Arquivo>();
		try {
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			Arquivo arquivo = null;
			while (rs.next()) {
			
				arquivo = new Arquivo();
				
				arquivo.setId(rs.getInt("id"));
				arquivo.setNome(rs.getString("nome"));
				arquivo.setDescr(rs.getString("descr"));
				arquivo.setCodArquivo(rs.getString("cod_arquivo"));
				
				lista.add(arquivo);				
			}			
		
		} catch (SQLException e) {
		
		}
		return lista;
	}
	


	public Arquivo selectOne(Arquivo _arquivo) {
		if (_arquivo == null)
			return null;
		try {
				ResultSet rs = stm.executeQuery("select * from arquivo where id ="+_arquivo.getId());
				while (rs.next()) {

					_arquivo.setId(rs.getInt("id"));
					_arquivo.setNome(rs.getString("nome"));
					_arquivo.setDescr(rs.getString("descr"));
					_arquivo.setCodArquivo(rs.getString("cod_arquivo"));

				}	

			} catch (SQLException e) {}

		return _arquivo;

	}
 


}
