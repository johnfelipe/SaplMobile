package br.gov.go.camarajatai.sislegisold.dao;

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

public class DAOConnection {
	
    private Connection con = null;

    
    
    private DAOConnection() { }
    
    
    public static DAOConnection newInstance() throws Exception {
    
    	DAOConnection connection = new DAOConnection();
    
		connection.connect();
    	
    	return connection;
    		
    }
    

    private void connect() throws ClassNotFoundException, SQLException {

            Class.forName("org.postgresql.Driver");
           // con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/portal", "postgres", "serversteel");
            //con = DriverManager.getConnection("jdbc:postgresql://10.3.163.1:5432/portal", "postgres", "admserver");
           con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/portal", "postgres", "123");
           // con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sislegis", "postgres", "123");
            
  
        
    }
    
    public PreparedStatement createPreparedStatement(String sql) {
    	
    	try {
			return con.prepareStatement(sql);
		} catch (SQLException e) {
			return null;
		}
    	
    }
    /*
    public int execute() {

        if (!connected) {
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
    }*/

    
    public boolean close() {
    	
    	try {
			if (!con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return false;
    }
}
