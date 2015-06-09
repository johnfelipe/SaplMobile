package br.gov.go.camarajatai.sislegisold.dao;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.GregorianCalendar;

import br.gov.go.camarajatai.sislegisold.dto.Dto;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

public class MySqlConnection {

    public static final int IGUAL = 1;
    public static final int CONTEM = 2;
    public static final int INICIA = 3;
    public static final int TERMINA = 4;

    public static final int ERROR_EXECUTE = -1;
    public static final int OK_EXECUTE = -2;
    public static final int UNIQUE_ERROR_EXECUTE = -3;
    public Connection con = null;
    private boolean connected = false;

    private static MySqlConnection dbCon = null;

    private MySqlConnection() {
    }

    private synchronized static void sync() {

        if (dbCon == null)
            dbCon = new MySqlConnection();
    }

    public static MySqlConnection getInstance() {

        if (dbCon == null)
            sync();

        return dbCon;
    }

    public boolean connect() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            // con =
            // DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+Urls.appBase,
            // "postgres", "infoCmj");
            // con =
            // DriverManager.getConnection("jdbc:postgresql://10.3.163.199:5432/p15",
            // "postgres", "PostSky2.34");
            // con =
            // DriverManager.getConnection("jdbc:postgresql://10.3.161.2:5432/p03",
            // "postgres", "PostSky2.34");

            con = DriverManager.getConnection("jdbc:mysql://10.3.161.2:3306/interlegis", "root", "MySqlSky1.23");

            // con =
            // DriverManager.getConnection("jdbc:postgresql://localhost:5432/p15",
            // "postgres", "infoCmj");

        } catch (Exception e) {
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

    public boolean isConnected() {
        return connected;
    }

    public void close() {
        try {
            con.close();
        } catch (SQLException e) {

        }
    }

    public void upDateByteArray(String sql, byte[] dados) throws SQLException {

        PreparedStatement ps = con.prepareStatement(sql);

        ps.setBytes(1, dados);
        ps.setBoolean(2, dados != null);
        ps.executeUpdate();
        ps.close();

    }

    public byte[] selectByteArray(String sql) throws SQLException {

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getBytes(1);

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

    public static String constructSQLUpDate(Object ob) {

        try {
            String s = "update " + ob.getClass().getSimpleName().toLowerCase() + " set ";

            Field[] f = ob.getClass().getDeclaredFields();
            Method[] mt = ob.getClass().getDeclaredMethods();

            for (int i = 0; i < f.length; i++) {

                if (f[i].getName().indexOf("arqdigital") != -1)
                    continue;
                else if (f[i].getName().indexOf("STATE") != -1)
                    continue;
                else if (f[i].getName().indexOf("onLine") != -1)
                    continue;
                else if (f[i].getType().getName().indexOf("Collection") != -1)
                    continue;
                else if (f[i].getType().getName().indexOf("ArrayList") != -1)
                    continue;
                else if (f[i].getType().getName().startsWith("tmp"))
                    continue;

                s += "\"" + f[i].getName() + "\" = ";

                if (isString(f[i].getType())) {
                    s += "'" + mt[getPos(f[i], mt)].invoke(ob, new Object[0]) + "'";
                } else if (isBoolean(f[i].getType())) {
                    s += "'" + mt[getPos(f[i], mt)].invoke(ob, new Object[0]).toString() + "'";
                } else if (isGregorianCalendar(f[i].getType())) {
                    GregorianCalendar g = (GregorianCalendar) (mt[getPos(f[i], mt)].invoke(ob, new Object[0]));
                    if (g != null) {
                        s += "'" + g.getTime().toString() + "'";
                    } else {
                        s += "null";
                    }
                } else if (isTimeStamp(f[i].getType())) {
                    Timestamp g = (Timestamp) (mt[getPos(f[i], mt)].invoke(ob, new Object[0]));
                    if (g != null) {
                        // s += "'" + new Date(g.getTime()) + " "
                        // Suport.DateTimeToStr(g)+ "'";
                        s += "'" + Suport.DateTimeToStr(g) + "'";
                    } else {
                        s += "null";
                    }
                } else if (isFloatORInteger(f[i].getType())) {
                    s += mt[getPos(f[i], mt)].invoke(ob, new Object[0]);
                } else if (isDto(f[i].getType())) {
                    Dto dto = (Dto) mt[getPos(f[i], mt)].invoke(ob, new Object[0]);
                    if (dto != null && dto.getId() != 0)
                        s += (dto).getId();
                    else
                        s += "null";
                }
                s += ", ";
            }
            s = s.substring(0, s.length() - 2);

            s += " where \"id\" = " + ((Dto) ob).getId() + " RETURNING \"id\" AS \"returnID\";";

            return s;
        } catch (Exception ex) {
            return null;
        }
    }

    public static String constructSQLInsert(Object ob) {
        try {
            String s = "insert into " + ob.getClass().getSimpleName().toLowerCase() + " ( ";

            String dados = "";

            Field[] f = ob.getClass().getDeclaredFields();
            Method[] mt = ob.getClass().getDeclaredMethods();

            for (int i = 0; i < f.length; i++) {

                if (f[i].getName().indexOf("arqdigital") != -1)
                    continue;
                else if (f[i].getName().indexOf("STATE") != -1)
                    continue;
                else if (f[i].getName().indexOf("onLine") != -1)
                    continue;
                else if (f[i].getType().getName().indexOf("Collection") != -1)
                    continue;

                s += "\"" + f[i].getName() + "\"";

                if (mt[getPos(f[i], mt)].invoke(ob, new Object[0]) == null) {
                    dados += "null";
                } else if (isString(f[i].getType())) {
                    dados += "'" + mt[getPos(f[i], mt)].invoke(ob, new Object[0]) + "'";
                } else if (isBoolean(f[i].getType())) {
                    dados += "'" + mt[getPos(f[i], mt)].invoke(ob, new Object[0]).toString() + "'";
                } else if (isGregorianCalendar(f[i].getType())) {
                    GregorianCalendar g = (GregorianCalendar) (mt[getPos(f[i], mt)].invoke(ob, new Object[0]));
                    if (g != null) {
                        dados += "'" + (g).getTime().toString() + "'";
                    } else {
                        dados += "null";
                    }
                } else if (isTimeStamp(f[i].getType())) {
                    Timestamp g = (Timestamp) (mt[getPos(f[i], mt)].invoke(ob, new Object[0]));
                    if (g != null) {
                        // dados += "'" + new Date(g.getTime()).toString() +
                        // "'";
                        dados += "'" + Suport.DateTimeToStr(g) + "'";
                    } else {
                        dados += "null";
                    }
                } else if (isFloatORInteger(f[i].getType())) {
                    dados += mt[getPos(f[i], mt)].invoke(ob, new Object[0]);
                } else if (isDto(f[i].getType())) {
                    Dto dto = (Dto) mt[getPos(f[i], mt)].invoke(ob, new Object[0]);
                    if (dto != null && dto.getId() != 0)
                        dados += (dto).getId();
                    else
                        dados += "null";
                } else {
                    dados += "null";
                }

                s += ", ";
                dados += ", ";
            }
            dados = dados.substring(0, dados.length() - 2);
            s = s.substring(0, s.length() - 2);

            s += ") values (" + dados + ") RETURNING \"id\" AS \"returnID\";";

            return s;
        } catch (Exception ex) {
            return null;
        }

    }

    private static boolean isString(Class ob) {

        String s = ob.getSimpleName();

        if (s.compareTo("String") == 0) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean isBoolean(Class ob) {

        String s = ob.getSimpleName();

        if (s.compareTo("boolean") == 0) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean isGregorianCalendar(Class ob) {

        String s = ob.getSimpleName();

        if (s.compareTo("GregorianCalendar") == 0) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean isTimeStamp(Class ob) {

        String s = ob.getSimpleName();

        if (s.compareTo("Timestamp") == 0) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean isFloatORInteger(Class ob) {

        String s = ob.getSimpleName();

        if (s.compareTo("float") == 0 || s.compareTo("int") == 0) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean isDto(Class ob) {

        String s = ob.getName().substring(0, 7);

        if (ob.getName().indexOf("dto") != -1) {
            return true;
        } else {
            return false;
        }

    }

    private static boolean isUsuario(Class ob) {

        String s = ob.getName();

        if (s.compareTo("pax.dto.Usuario") == 0) {
            return true;
        } else {
            return false;
        }

    }

    private static int getPos(Field field, Method[] mt) {

        String s = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
        for (int i = 0; i < mt.length; i++) {
            if (mt[i].getName().compareTo(s) == 0) {
                return i;
            }

        }
        return -1;
    }

    public static String TransformCodding(String s) {

        String tipo = "UTF-8";
        String tipoconverte = "ISO-8859-1";

        try {
            s = new String(s.getBytes(tipoconverte), 0, s.getBytes(tipoconverte).length, tipo);
            // s = s.toUpperCase();
            return s;
        } catch (UnsupportedEncodingException e) {
            return "";
        }

    }
}
