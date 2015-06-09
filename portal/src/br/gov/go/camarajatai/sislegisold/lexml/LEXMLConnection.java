package br.gov.go.camarajatai.sislegisold.lexml;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import br.gov.go.camarajatai.sislegisold.dto.Dto;

public class LEXMLConnection {

	public static final int IGUAL = 1;
	public static final int CONTEM = 2;
	public static final int INICIA = 3;
	public static final int TERMINA = 4;

	public static final int ERROR_EXECUTE = -1;
	public static final int OK_EXECUTE = -2;
	public static final int UNIQUE_ERROR_EXECUTE = -3;
	private Connection con = null;
	private boolean connected = false;

	private static LEXMLConnection lexmlCon = null;

	private LEXMLConnection() { }

	private synchronized static void sync() {

		if (lexmlCon == null)
			lexmlCon = new LEXMLConnection();
	}

	public static LEXMLConnection getInstance() {

		if (lexmlCon == null)
			sync();

		return lexmlCon;    	
	}


	public boolean connect() {

		try {
			Class.forName("org.postgresql.Driver");
			// con = DriverManager.getConnection("jdbc:postgresql://10.3.163.1:5432/lexmldatabase", "lexmluser", "lexmlpassword");
			 con = DriverManager.getConnection("jdbc:postgresql://10.3.161.2:5432/lexmldatabase", "lexmluser", "lexmlpassword");

		} catch (Exception e) {
		
			e.printStackTrace();
			connected = false;
			return false;
		}
		connected = true;
		return true;
	}

	public Statement newStatement() {

		if (!isConnected()) {
			connect();
		}
		try {
			Statement stm;
			stm = con.createStatement();
			return stm;
		} catch (SQLException ex) {
			return null;
		}
	}

	public PreparedStatement newPreparedStatement(String sql) throws SQLException {


		if (!isConnected()) {
			connect();
		}
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			return ps;
		} catch (SQLException ex) {
			return null;
		}
	

	}


	public boolean isConnected() {
		return connected;
	}

	public void close() {
		try {
			con.close();
		} catch (SQLException e) {

		}
		connected = false;
	}
	
	public int execute(Statement stm, String str) {

		if (!isConnected()) {
			connect();
		}


		try {
			if (!connected) {
				return ERROR_EXECUTE;
			}
			try {
				stm.execute(str);
			} catch (SQLException e) {
				System.out.println(str);
				System.out.println(e);
				return ERROR_EXECUTE;
			}
			if (str.indexOf("RETURNING") != -1) {
				ResultSet rs = stm.getResultSet();
				rs.next();
				int r = rs.getInt("returnId");
				return r;
			} else {
				return OK_EXECUTE;
			}
		} catch (SQLException ex) {
			return ERROR_EXECUTE;
		}
	}

	public ResultSet select(Statement stm, String str) {

		if (!isConnected()) {
			connect();
		}
		if (!connected) {
			return null;
		}
		try {
			return stm.executeQuery(str);

		} catch (SQLException e) {
			return null;
		}
	}

	public static String TransformCodding(String s) {

		String tipo = "UTF-8";
		String tipoconverte = "ISO-8859-1";

		try {
			s = new String(s.getBytes(tipoconverte),0,s.getBytes(tipoconverte).length,tipo);
			//s = s.toUpperCase();
			return s;
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}	
}
