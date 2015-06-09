package br.gov.go.camarajatai.sislegisold.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.dto.Documento;

public class ItemleiDAO extends DAO<CollectionItensDeLei> {

	public Statement stm = PostgreConnection.getInstance().newStatement();
	
	
	
	public ItemleiDAO() {		
		
	}
	
	public void insert(Itemlei item) {
		
		String sql = PostgreConnection.constructSQLInsert(item);
		int result = PostgreConnection.getInstance().execute(stm, sql);
		
		if (result > 0)
			item.setId(result);
		
	}
	
	public void update(Itemlei item) {

		PostgreConnection db = PostgreConnection.getInstance();

		String sql = "update itemlei set texto = ?, " +
				"alterado = ?, "+
				"revogado = ?, "+
				"nivel = ?, " +
				"data_inclusao = ? " +

				" where id = ?;";

		PreparedStatement ps;
		try {
			ps = db.con.prepareStatement(sql);
			ps.setString(1, item.getTexto());
			ps.setBoolean(2, item.getAlterado());
			ps.setBoolean(3, item.getRevogado());
			ps.setInt(4, item.getNivel());
			ps.setTimestamp(5, item.getData_inclusao());
			ps.setInt(6, item.getId());
			ps.execute(); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*
		DBConnection.getInstance().execute(stm, "update itemlei set texto = '"+item.getTexto()+"', " +
				"alterado = '"+item.getAlterado()+"', "+
				"revogado = '"+item.getRevogado()+"',"+
				"nivel = "+item.getNivel()+", " +
				"data_inclusao = '"+item.getData_inclusao().toString()+"' " +
				
				" where id = "+item.getId()+";");	*/	
	}
	
	public void delete(Itemlei item) {
		
		PostgreConnection.getInstance().execute(stm, "delete from itemlei where id = "+item.getId()+";");
		
	}
	
	
	public CollectionItensDeLei selectSearch(Documento doc, String busca) {
			
		String sqlSelectAll = "select " +
		" il.* " +
		"from itemlei il";
		
		if (doc.getId_tipolei() != null )
			sqlSelectAll += ", documento d, assuntos a";
				
		sqlSelectAll += " where ";

		busca = busca.trim();
			
		if (doc.getId() == 0 || busca.length()>0) {
			doc.completo = false;
		}
		
		
		if (busca.length() != 0) {			
			//			
			if (busca.indexOf(" ") != -1) {

				String stext[] = busca.split(" ");
				int i;
				for ( i = 0; i < stext.length; i++) {
					sqlSelectAll +=	"texto ~* '"+stext[i]+"' and "; 
				} 
			}
			else {
				sqlSelectAll +=	"texto ~* '"+busca+"' and ";
			}
		}
		
		sqlSelectAll += (doc.getId()==0?"":"(id_alterador = "+doc.getId()+" or id_lei = "+doc.getId()+") and ");
		
		sqlSelectAll += "il.id_lei = d.id and d.id = a.documento and ";
		
		if (doc.getId_tipolei().size() > 0 && doc.getId_tipolei().get(0).getId() != 0)
		   sqlSelectAll += "a.tipo = " + doc.getId_tipolei().get(0).getId()+" and ";
		
		
		
		if (doc.getNumero() != 0)
			sqlSelectAll += "il.numero = "+doc.getNumero()+" ";
		
		if (sqlSelectAll.endsWith("and ")) {
			sqlSelectAll = sqlSelectAll.substring(0, sqlSelectAll.length()-4);
		}
		
		sqlSelectAll += "order by il.data_inclusao desc, il.numero, anexo, livro, parte, titulo, capitulo, capitulovar, secao, secaovar, subsecao, itemsecao,artigo, artigovar, paragrafo, inciso, incisovar, incisovarvar, alinea, item, subitem, subsubitem, nivel";
	 
		CollectionItensDeLei collection = new CollectionItensDeLei();		

		if (busca.indexOf(";") != -1) 
			return collection;
		
		//System.out.println ( sqlSelectAll );
		
		try {
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			Itemlei itemlei = null;
			String campo = "";
			while (rs.next()) {
			
				itemlei = new Itemlei();				
				itemlei.setId(rs.getInt("id"));
				itemlei.setId_alterador(rs.getInt("id_alterador"));
				itemlei.setId_lei(rs.getInt("id_lei"));
				itemlei.setId_dono(rs.getInt("id_dono"));
				itemlei.setNumero(rs.getInt("numero"));
				itemlei.setAnexo(rs.getInt("anexo"));
				itemlei.setParte(rs.getInt("parte"));
				itemlei.setLivro(rs.getInt("livro"));
				itemlei.setTitulo(rs.getInt("titulo"));
				itemlei.setCapitulo(rs.getInt("capitulo"));
				itemlei.setCapitulovar(rs.getInt("capitulovar"));
				itemlei.setSecao(rs.getInt("secao"));
				itemlei.setSecaovar(rs.getInt("secaovar"));
				itemlei.setSubsecao(rs.getInt("subsecao"));
				itemlei.setItemsecao(rs.getInt("itemsecao"));
				itemlei.setArtigo(rs.getInt("artigo"));
				itemlei.setArtigovar(rs.getInt("artigovar"));
				itemlei.setParagrafo(rs.getInt("paragrafo"));
				itemlei.setInciso(rs.getInt("inciso"));
				itemlei.setIncisovar(rs.getInt("incisovar"));
				itemlei.setIncisovarvar(rs.getInt("incisovarvar"));
				itemlei.setAlinea(rs.getInt("alinea"));
				itemlei.setItem(rs.getInt("item"));
				itemlei.setSubitem(rs.getInt("subitem"));
				itemlei.setSubsubitem(rs.getInt("subsubitem"));
				
				itemlei.setNivel(rs.getInt("nivel"));
				
				itemlei.setRevogado(rs.getBoolean("revogado"));
				itemlei.setAlterado(rs.getBoolean("alterado"));
				itemlei.setIncluido(rs.getBoolean("incluido"));
				
				itemlei.setLink_alterador(rs.getString("link_alterador"));
				
				itemlei.setData_inclusao(rs.getTimestamp("data_inclusao"));
				itemlei.setData_alteracao(rs.getTimestamp("data_alteracao"));
				
				itemlei.setTexto(rs.getString("texto"));
				
				
				//campo = niveis.get(itemlei.getNivel());
				//itemlei.setCodigo(rs.getInt(campo));			
				
				collection.add(itemlei);				
			}	
			
			if (doc.getId() != 0)
				collection.newOrganize(doc);
		
		
		} catch (SQLException e) {
			e.printStackTrace(System.out);
		}
		return collection;
	}

	
	public synchronized void selectLei(Documento doc) {
	
		CollectionItensDeLei collection = doc.getItemleis();

		
		
		String sqlSelectAll = "select " +
		"id_dono!=0 as prop, * " +
		"from itemlei " +
		"where id_alterador = "+doc.getId()+" or id_lei = "+doc.getId()+" " +
		"order by data_inclusao desc, numero, anexo, livro, parte, titulo, capitulo, capitulovar, secao, secaovar, subsecao, itemsecao,artigo, artigovar, paragrafo, inciso, incisovar, incisovarvar, alinea, item, subitem, subsubitem, nivel;";
	 
		
		collection.clear();
		doc.setOnLine(true);
		doc.completo = true;
		
		try {
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			Itemlei itemlei = null;
			String campo = "";
			while (rs.next()) {
			
				itemlei = new Itemlei();
				
				itemlei.setId(rs.getInt("id"));
				itemlei.setId_alterador(rs.getInt("id_alterador"));
				itemlei.setId_lei(rs.getInt("id_lei"));
				itemlei.setId_dono(rs.getInt("id_dono"));
				itemlei.setNumero(rs.getInt("numero"));
				itemlei.setAnexo(rs.getInt("anexo"));
				itemlei.setParte(rs.getInt("parte"));
				itemlei.setLivro(rs.getInt("livro"));
				itemlei.setTitulo(rs.getInt("titulo"));
				itemlei.setCapitulo(rs.getInt("capitulo"));
				itemlei.setCapitulovar(rs.getInt("capitulovar"));
				itemlei.setSecao(rs.getInt("secao"));
				itemlei.setSecaovar(rs.getInt("secaovar"));
				itemlei.setSubsecao(rs.getInt("subsecao"));
				itemlei.setItemsecao(rs.getInt("itemsecao"));
				itemlei.setArtigo(rs.getInt("artigo"));
				itemlei.setArtigovar(rs.getInt("artigovar"));
				itemlei.setParagrafo(rs.getInt("paragrafo"));
				itemlei.setInciso(rs.getInt("inciso"));
				itemlei.setIncisovar(rs.getInt("incisovar"));
				itemlei.setIncisovarvar(rs.getInt("incisovarvar"));
				itemlei.setAlinea(rs.getInt("alinea"));
				itemlei.setItem(rs.getInt("item"));
				itemlei.setSubitem(rs.getInt("subitem"));
				itemlei.setSubsubitem(rs.getInt("subsubitem"));
				
				itemlei.setNivel(rs.getInt("nivel"));
				
				itemlei.setRevogado(rs.getBoolean("revogado"));
				itemlei.setAlterado(rs.getBoolean("alterado"));
				itemlei.setIncluido(rs.getBoolean("incluido"));
				
				itemlei.setLink_alterador(rs.getString("link_alterador"));
				
				itemlei.setData_inclusao(rs.getTimestamp("data_inclusao"));
				itemlei.setData_alteracao(rs.getTimestamp("data_alteracao"));
				
				itemlei.setTexto(rs.getString("texto"));
				
				
				//campo = niveis.get(itemlei.getNivel());
				//itemlei.setCodigo(rs.getInt(campo));			
				
				collection.add(itemlei);				
			}	
			
			collection.newOrganize(doc);
		
		
		} catch (SQLException e) {
		
		}
		
	}



	public Itemlei selectItem(Itemlei itemlei) {
	
		String sqlSelectAll = "select " +
		"id_dono!=0 as prop, * " +
		"from itemlei " +
		"where id ="+itemlei.getId()+" " +
		"order by id_dono desc, id_lei, numero, anexo, livro, parte, titulo, capitulo, capitulovar, secao, secaovar, subsecao, itemsecao,artigo, artigovar, paragrafo, inciso, incisovar, incisovarvar, alinea, item, subitem, subitem, nivel,id_alterador limit 1;";
	 
		try {
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			String campo = "";
			while (rs.next()) {
			
				
				itemlei.setId(rs.getInt("id"));
				itemlei.setId_alterador(rs.getInt("id_alterador"));
				itemlei.setId_lei(rs.getInt("id_lei"));
				itemlei.setId_dono(rs.getInt("id_dono"));
				itemlei.setNumero(rs.getInt("numero"));
				itemlei.setAnexo(rs.getInt("anexo"));

				itemlei.setParte(rs.getInt("parte"));
				itemlei.setLivro(rs.getInt("livro"));
				itemlei.setTitulo(rs.getInt("titulo"));
				itemlei.setCapitulo(rs.getInt("capitulo"));
				itemlei.setCapitulovar(rs.getInt("capitulovar"));
				itemlei.setSecao(rs.getInt("secao"));
				itemlei.setSecaovar(rs.getInt("secaovar"));
				itemlei.setSubsecao(rs.getInt("subsecao"));
				itemlei.setItemsecao(rs.getInt("itemsecao"));
				itemlei.setArtigo(rs.getInt("artigo"));
				itemlei.setArtigovar(rs.getInt("artigovar"));
				itemlei.setParagrafo(rs.getInt("paragrafo"));
				itemlei.setInciso(rs.getInt("inciso"));
				itemlei.setIncisovar(rs.getInt("incisovar"));
				itemlei.setIncisovarvar(rs.getInt("incisovarvar"));
				itemlei.setAlinea(rs.getInt("alinea"));
				itemlei.setItem(rs.getInt("item"));
				itemlei.setSubitem(rs.getInt("subitem"));
				itemlei.setSubsubitem(rs.getInt("subsubitem"));
				
				itemlei.setNivel(rs.getInt("nivel"));
				
				itemlei.setRevogado(rs.getBoolean("revogado"));
				itemlei.setAlterado(rs.getBoolean("alterado"));
				itemlei.setIncluido(rs.getBoolean("incluido"));
				
				itemlei.setLink_alterador(rs.getString("link_alterador"));
				
				itemlei.setData_inclusao(rs.getTimestamp("data_inclusao"));
				itemlei.setData_alteracao(rs.getTimestamp("data_alteracao"));
				
				itemlei.setTexto(rs.getString("texto"));
								
			}	
		} catch (SQLException e) {
		
		}
		return itemlei;
		
	}

	

	public Itemlei selectItemByData(Itemlei itemlei) {
	
		String sqlSelectAll = "select " +
		"* " +
		"from itemlei " +
		"where numero  = "+itemlei.getNumero() +
		" and anexo = "+itemlei.getAnexo()+
		" and livro = "+itemlei.getLivro()+
		" and parte = "+itemlei.getParte()+
		" and titulo = "+itemlei.getTitulo()+
		" and capitulo = "+itemlei.getCapitulo()+
		" and capitulovar = "+itemlei.getCapitulovar()+
		" and secao = "+itemlei.getSecao()+
		" and secaovar = "+itemlei.getSecaovar()+
		" and subsecao = "+itemlei.getSubsecao()+
		" and itemsecao = "+itemlei.getItemsecao()+
		" and artigo = "+itemlei.getArtigo()+
		" and artigovar = "+itemlei.getArtigovar()+
		" and paragrafo = "+itemlei.getParagrafo()+
		" and inciso = "+itemlei.getInciso()+
		" and alinea = "+itemlei.getAlinea()+
		" and item = "+itemlei.getItem()+
		" and subitem = "+itemlei.getSubitem()+
		" and subsubitem = "+itemlei.getSubsubitem()+
		" and nivel = "+itemlei.getNivel()+
		" and id_alterador = "+itemlei.getId_alterador()+		
		"order by numero, anexo, livro, parte, titulo, capitulo, capitulovar, secao, secaovar, subsecao, itemsecao,artigo, artigovar, paragrafo, inciso, incisovar, incisovarvar, alinea, item, subitem, subitem, nivel,id_alterador limit 1;";
	 
		try {
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			String campo = "";
			while (rs.next()) {
			
				
				itemlei.setId(rs.getInt("id"));
				itemlei.setId_alterador(rs.getInt("id_alterador"));
				itemlei.setId_lei(rs.getInt("id_lei"));
				itemlei.setId_dono(rs.getInt("id_dono"));
				itemlei.setNumero(rs.getInt("numero"));
				itemlei.setAnexo(rs.getInt("anexo"));				
				itemlei.setParte(rs.getInt("parte"));
				itemlei.setLivro(rs.getInt("livro"));
				itemlei.setTitulo(rs.getInt("titulo"));
				itemlei.setCapitulo(rs.getInt("capitulo"));
				itemlei.setCapitulovar(rs.getInt("capitulovar"));
				itemlei.setSecao(rs.getInt("secao"));
				itemlei.setSecaovar(rs.getInt("secaovar"));
				itemlei.setSubsecao(rs.getInt("subsecao"));
				itemlei.setItemsecao(rs.getInt("itemsecao"));
				itemlei.setArtigo(rs.getInt("artigo"));
				itemlei.setArtigovar(rs.getInt("artigovar"));
				itemlei.setParagrafo(rs.getInt("paragrafo"));
				
				itemlei.setInciso(rs.getInt("inciso"));
				itemlei.setIncisovar(rs.getInt("incisovar"));
				itemlei.setIncisovarvar(rs.getInt("incisovarvar"));
				
				itemlei.setAlinea(rs.getInt("alinea"));
				itemlei.setItem(rs.getInt("item"));
				itemlei.setSubitem(rs.getInt("subitem"));
				itemlei.setSubsubitem(rs.getInt("subsubitem"));
				
				itemlei.setNivel(rs.getInt("nivel"));
				
				itemlei.setRevogado(rs.getBoolean("revogado"));
				itemlei.setAlterado(rs.getBoolean("alterado"));
				itemlei.setIncluido(rs.getBoolean("incluido"));
				
				itemlei.setLink_alterador(rs.getString("link_alterador"));
				
				itemlei.setData_inclusao(rs.getTimestamp("data_inclusao"));
				itemlei.setData_alteracao(rs.getTimestamp("data_alteracao"));
				
				itemlei.setTexto(rs.getString("texto"));
								
			}	
		} catch (SQLException e) {
		
		}
		return itemlei;
		
	}

	
	public Itemlei selectItemForLanc(Documento lei) {
	
		String sqlSelectAll = "select " +
		"id_dono!=0 as prop, * " +
		"from itemlei " +
		"where texto = '' and (id_alterador = "+lei.getId()+" or id_lei = "+lei.getId()+") " +
		"order by data_inclusao desc, numero, anexo, livro, parte, titulo, capitulo, capitulovar, secao, secaovar, subsecao, itemsecao,artigo, artigovar, paragrafo, inciso, incisovar, incisovarvar, alinea, item, subitem, subitem, nivel limit 1;";
	 
		Itemlei itemlei = null;
		try {
			ResultSet rs = stm.executeQuery(sqlSelectAll);
			String campo = "";
			while (rs.next()) {
			
				itemlei = new Itemlei();
				
				itemlei.setId(rs.getInt("id"));
				itemlei.setId_alterador(rs.getInt("id_alterador"));
				itemlei.setId_lei(rs.getInt("id_lei"));
				itemlei.setId_dono(rs.getInt("id_dono"));
				itemlei.setNumero(rs.getInt("numero"));
				itemlei.setAnexo(rs.getInt("anexo"));
				itemlei.setParte(rs.getInt("parte"));
				itemlei.setLivro(rs.getInt("livro"));
				itemlei.setTitulo(rs.getInt("titulo"));
				itemlei.setCapitulo(rs.getInt("capitulo"));
				itemlei.setCapitulovar(rs.getInt("capitulovar"));
				itemlei.setSecao(rs.getInt("secao"));
				itemlei.setSecaovar(rs.getInt("secaovar"));
				itemlei.setSubsecao(rs.getInt("subsecao"));
				itemlei.setItemsecao(rs.getInt("itemsecao"));
				itemlei.setArtigo(rs.getInt("artigo"));
				itemlei.setArtigovar(rs.getInt("artigovar"));
				itemlei.setParagrafo(rs.getInt("paragrafo"));
				itemlei.setInciso(rs.getInt("inciso"));
				itemlei.setIncisovar(rs.getInt("incisovar"));
				itemlei.setIncisovarvar(rs.getInt("incisovarvar"));
				itemlei.setAlinea(rs.getInt("alinea"));
				itemlei.setItem(rs.getInt("item"));
				itemlei.setSubitem(rs.getInt("subitem"));
				itemlei.setSubsubitem(rs.getInt("subsubitem"));
				
				itemlei.setNivel(rs.getInt("nivel"));
				
				itemlei.setRevogado(rs.getBoolean("revogado"));
				itemlei.setAlterado(rs.getBoolean("alterado"));
				itemlei.setIncluido(rs.getBoolean("incluido"));
				
				itemlei.setLink_alterador(rs.getString("link_alterador"));
				
				itemlei.setData_inclusao(rs.getTimestamp("data_inclusao"));
				itemlei.setData_alteracao(rs.getTimestamp("data_alteracao"));
				
				itemlei.setTexto(rs.getString("texto"));
								
			}	
		} catch (SQLException e) {
		
		}
		return itemlei;
		
	}
 

}