package br.gov.go.camarajatai.sislegisold.suport;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Suport {

    public static final int LEFT = 37;
    public static final int RIGHT = 39;

    public static final int diasMes[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    public static final String monthNames[] = { "JANEIRO",
                                               "FEVEREIRO",
                                               "MARÇO",
                                               "ABRIL",
                                               "MAIO",
                                               "JUNHO",
                                               "JULHO",
                                               "AGOSTO",
                                               "SETEMBRO",
                                               "OUTUBRO",
                                               "NOVEMBRO",
                                               "DEZEMBRO" };

    public static float round(float value, int precision) {

        double vFloat = value;

        for (int i = 0; i < precision; i++) {
            vFloat *= 10;
        }

        long vInt = Math.round(vFloat);
        vFloat = (double) vInt;
        for (int i = 0; i < precision; i++) {
            vFloat /= 10;
        }

        return (float) vFloat;
    }

    public static String roundToString(float value, int size, int precision) {

        double vFloat = value;

        for (int i = 0; i < precision; i++) {
            vFloat *= 10;
        }

        long vInt = Math.round(vFloat);
        vFloat = (double) vInt;
        for (int i = 0; i < precision; i++) {
            vFloat /= 10;
        }

        String s = Float.toString((float) vFloat);
        if (s.length() == 0) {
            s = "0";
        }

        s = s.substring(0, s.indexOf(".")) + "," + s.substring(s.indexOf(".") + 1);
        if (s.length() - s.indexOf(",") < precision) {
            s = s.substring(0, s.indexOf(",") + precision);
        }

        while (s.length() - s.indexOf(",") < precision + 1) {
            s += "0";
        }

        while (s.length() < size) {
            s = " " + s;
        }
        return s;
    }

    public static String complete(String s, String complemento, int tamanho, int lado) {

        if (lado == LEFT)
            while (s.length() < tamanho)
                s = complemento + s;
        else if (lado == RIGHT)
            while (s.length() < tamanho)
                s += complemento;

        return s;
    }

    public static float strToFloat(String s) throws Exception {
        s = "0" + s.trim();

        s = s.replaceAll("[.&&[^0-9]]", "");
        s = s.replaceAll("[,]", ".");
        /*
         * int i = s.indexOf(","); if (i != -1) { String ss[] = s.split(",", 2);
         * s = ss[0] + "." + ss[1]; }
         */
        return Float.valueOf(s).floatValue();
    }

    public static boolean validaCpf(String cpf) {
        if (cpf.length() != 11)
            return false;

        String digVer = "";
        int soma1 = 0, soma2 = 0;
        int resto1 = 0, resto2 = 0;

        digVer = cpf.substring(9, 11);
        int c = 11;

        for (int i = 0; i < (cpf.length() - 1); i++, c--) {
            if (c == 2) {
                soma2 += (Integer.parseInt(cpf.substring(i, i + 1)) * c);
            }

            else {
                soma1 += (Integer.parseInt(cpf.substring(i, i + 1)) * (c - 1));
                soma2 += (Integer.parseInt(cpf.substring(i, i + 1)) * c);
            }
        }

        // Calculando os dígitos verificadores
        resto1 = soma1 % 11;
        resto2 = soma2 % 11;

        if (resto1 < 2) {
            resto1 = 0;
        }

        else {
            resto1 = 11 - resto1;
        }

        if (resto2 < 2) {
            resto2 = 0;
        }

        else {
            resto2 = 11 - resto2;
        }

        return (digVer.equals(String.valueOf(resto1) + "" + String.valueOf(resto2)));
    }

    public static String dateToStrBR(GregorianCalendar g) {
        if (g != null) {
            String s = Suport.complete(String.valueOf(g.get(GregorianCalendar.DAY_OF_MONTH)), "0", 2, Suport.LEFT) + "/";
            s += Suport.complete(String.valueOf(g.get(GregorianCalendar.MONTH) + 1), "0", 2, Suport.LEFT) + "/";
            s += String.valueOf(g.get(GregorianCalendar.YEAR));
            return s;
        }
        return "";
    }

    public static String dateToStrBROnlyTime(GregorianCalendar g) {
        if (g != null) {
            String s = Suport.complete(String.valueOf(g.get(GregorianCalendar.HOUR_OF_DAY)), "0", 2, LEFT) + ":";
            s += Suport.complete(String.valueOf(g.get(GregorianCalendar.MINUTE)), "0", 2, LEFT) + ":";
            s += Suport.complete(String.valueOf(g.get(GregorianCalendar.SECOND)), "0", 2, LEFT);
            return s;
        }
        return "";
    }

    public static GregorianCalendar strBRToDate(String s) {
        try {
            String dataHora[] = s.split(" ");

            String ss[] = dataHora[0].split("/");
            int a, m, d, h, mm, sss;
            a = Integer.parseInt(ss[2]);
            m = Integer.parseInt(ss[1]) - 1;
            d = Integer.parseInt(ss[0]);

            h = 0;
            mm = 0;
            sss = 0;
            if (dataHora.length > 1) {
                ss = dataHora[1].split(":");
                h = Integer.parseInt(ss[0]);
                mm = Integer.parseInt(ss[1]);
                sss = Integer.parseInt(ss[2]);
            }

            GregorianCalendar g = new GregorianCalendar(a, m, d, h, mm, sss);
            return g;
        } catch (Exception e) {
            return null;
        }
    }

    public static void setDayOfMonth(GregorianCalendar g, int day) {

        if (day > diasMes[g.get(GregorianCalendar.MONTH)])
            day = diasMes[g.get(GregorianCalendar.MONTH)];
        g.set(GregorianCalendar.DAY_OF_MONTH, day);
    }

    public static String dateTimeYYYYMMDDHHMMSS() {
        String arquivo = "";

        GregorianCalendar g = new GregorianCalendar();
        g.set(GregorianCalendar.MINUTE, 0);
        g.set(GregorianCalendar.SECOND, 0);

        String s = Suport.complete(String.valueOf(g.get(GregorianCalendar.YEAR)), "0", 2, Suport.LEFT) + Suport.complete(
                           String.valueOf(g.get(GregorianCalendar.MONTH) + 1), "0", 2, Suport.LEFT)
                   + String.valueOf(g.get(GregorianCalendar.DAY_OF_MONTH));

        arquivo += s +

                   Suport.complete(String.valueOf(g.get(GregorianCalendar.HOUR_OF_DAY)), "0", 2, LEFT)
                   + Suport.complete(String.valueOf(g.get(GregorianCalendar.MINUTE)), "0", 2, LEFT)
                   + Suport.complete(String.valueOf(g.get(GregorianCalendar.SECOND)), "0", 2, LEFT);

        return arquivo;
    }

    public static String dateTimeYYYYMMDDHHMMSS(Timestamp time) {
        String arquivo = "";

        GregorianCalendar g = new GregorianCalendar();
        if (time != null)
            g.setTime(time);

        String s = Suport.complete(String.valueOf(g.get(GregorianCalendar.YEAR)), "0", 2, Suport.LEFT) + Suport.complete(
                           String.valueOf(g.get(GregorianCalendar.MONTH) + 1), "0", 2, Suport.LEFT)
                   + Suport.complete(String.valueOf(g.get(GregorianCalendar.DAY_OF_MONTH)), "0", 2, Suport.LEFT);

        arquivo += s +

                   Suport.complete(String.valueOf(g.get(GregorianCalendar.HOUR_OF_DAY)), "0", 2, LEFT)
                   + Suport.complete(String.valueOf(g.get(GregorianCalendar.MINUTE)), "0", 2, LEFT)
                   + Suport.complete(String.valueOf(g.get(GregorianCalendar.SECOND)), "0", 2, LEFT);

        return arquivo;
    }

    public static String timeStampToStr(Timestamp time) {
        String s = "";
        GregorianCalendar c = new GregorianCalendar();
        if (time != null) {
            c.setTime(time);

            s = dateToStrBR(c);// + " - "+ dateToStrBROnlyTime(c);
        }

        return s;
    }

    public static String DateTimeToStr(Timestamp time) {
        String s = "";
        GregorianCalendar c = new GregorianCalendar();
        if (time != null) {
            c.setTime(time);

            s = dateToStrBR(c) + " " + dateToStrBROnlyTime(c);
        }

        return s;
    }

    public static void validIntranet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (!isIntranet(request))
            response.sendRedirect("/portal/sislegisold/errorIntranet.jsp");

    }

    public static boolean isIntranet(HttpServletRequest request) {
        /*
         * Enumeration<String> headers = request.getHeaderNames();
         * 
         * while (headers.hasMoreElements()) { String header =
         * headers.nextElement();
         * System.out.println(header+":  "+request.getHeader(header)); }
         */
        // System.out.println("remoteAddr:" + request.getRemoteAddr());

        if (request == null)
            return false;

        if (isAdmin(request)) {
            String anon = null;

            anon = request.getParameter("anon");
            if (anon != null) {
                if (anon.equals("true")) {
                    request.getSession().setAttribute("anon", "true");
                    return false;
                } else {
                    request.getSession().removeAttribute("anon");
                }
            } else {
                anon = (String) request.getSession().getAttribute("anon");

                if (anon != null) {
                    return false;
                }
            }
            return true;
        } else
            return false;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        /*
         * Enumeration<String> headers = request.getHeaderNames();
         * 
         * while (headers.hasMoreElements()) { String header =
         * headers.nextElement(); System.out.println(header + ":  " +
         * request.getHeader(header)); }
         */
        // System.out.println("remoteAddr:" + request.getRemoteAddr());

        if (request == null)
            return false;

        return (request.getRemoteAddr().equals("187.6.249.155") && request.getHeader("host") != null
                && request.getHeader("host").equals("10.3.163.1")
                && request.getHeader("x-forwarded-for") != null && (request.getHeader("x-forwarded-for").equals("10.3.163.200") || request.getHeader(
                                                                            "x-forwarded-for").equals("10.3.163.9")
                                                                    || request.getHeader("x-forwarded-for").equals("10.3.163.11") || request.getHeader(
                "x-forwarded-for").equals("10.3.163.12")

        ))     || request.getRemoteAddr().equals("127.0.0.1");

    }

    public static boolean isIntranetRoot(HttpServletRequest request) {
        /*
         * Enumeration<String> headers = request.getHeaderNames();
         * 
         * while (headers.hasMoreElements()) { String header =
         * headers.nextElement(); System.out.println(header + ":  " +
         * request.getHeader(header)); }
         */
        // System.out.println("remoteAddr:" + request.getRemoteAddr());

        if (request == null)
            return false;

        return (request.getRemoteAddr().equals("187.6.249.155") && request.getHeader("host") != null
                && request.getHeader("host").equals("10.3.163.1")
                && request.getHeader("x-forwarded-for") != null && (request.getHeader("x-forwarded-for").equals("10.3.163.200"))) || request.getRemoteAddr()
                       .equals("127.0.0.1");
    }

    public static boolean isIntranetold(HttpServletRequest request) {
        /*
         * Enumeration<String> headers = request.getHeaderNames();
         * 
         * while (headers.hasMoreElements()) { String header =
         * headers.nextElement();
         * System.out.println(header+":  "+request.getHeader(header)); }
         */
        // System.out.println("remoteAddr:" + request.getRemoteAddr());

        if (request == null)
            return false;

        if (isAdmin(request)) {
            String anon = null;

            anon = request.getParameter("anon");
            if (anon != null) {
                if (anon.equals("true")) {
                    request.getSession().setAttribute("anon", "true");
                    return false;
                } else {
                    request.getSession().removeAttribute("anon");
                }
            } else {
                anon = (String) request.getSession().getAttribute("anon");

                if (anon != null) {
                    return false;
                }
            }
            return true;
        } else
            return false;
    }

    public static boolean isAdminold(HttpServletRequest request) {
        /*
         * Enumeration<String> headers = request.getHeaderNames();
         * 
         * while (headers.hasMoreElements()) { String header =
         * headers.nextElement(); System.out.println(header + ":  " +
         * request.getHeader(header)); }
         */
        System.out.println("remoteAddr:" + request.getRemoteAddr());

        if (request == null)
            return false;

        return (
        /*
         * request.getRemoteAddr().equals("10.3.163.8") ||
         */
        request.getRemoteAddr().equals("10.3.163.9") || request.getRemoteAddr().equals("10.3.163.11")
                || request.getRemoteAddr().equals("10.3.163.12")
                || request.getRemoteAddr().equals("10.3.163.200")
        /*
         * || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")
         */

        || request.getRemoteAddr().equals("127.0.0.1")

        );
    }

    public static boolean isIntranetRootold(HttpServletRequest request) {
        /*
         * Enumeration<String> headers = request.getHeaderNames();
         * 
         * while (headers.hasMoreElements()) { String header =
         * headers.nextElement(); System.out.println(header + ":  " +
         * request.getHeader(header)); }
         */
        System.out.println("remoteAddr:" + request.getRemoteAddr());

        if (request == null)
            return false;

        return (
        /*
         * request.getRemoteAddr().equals("10.3.163.8") ||
         * request.getRemoteAddr().equals("10.3.163.28") ||
         */
        request.getRemoteAddr().equals("10.3.163.200")
        /*
         * || request.getRemoteAddr().equals("0:0:0:0:0:0:0:1")
         */
        || request.getRemoteAddr().equals("127.0.0.1")

        );
    }

    public static Object getObjectSession(String name, String classe, HttpServletRequest request) {

        Object ob = request.getSession().getAttribute(name);
        Class cl;

        if (ob != null)
            return ob;

        try {
            try {

                ob = Class.forName(classe).getConstructors()[0].newInstance();

            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            request.getSession().setAttribute(name, ob);

        } catch (ClassNotFoundException e) {
            ob = null;
        }

        return ob;
    }

    public static String utfToIso(String s) {
        try {
            return new String(s.getBytes("UTF-8"), 0, s.getBytes("UTF-8").length, "ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static String getMessageWarning(HttpServletRequest request) {

        String msg = (String) request.getAttribute("msg");

        if (msg == null) {

            msg = request.getParameter("msg");

            if (msg == null)
                return "<span class=\"message_warning\" style=\"display: none;\"></span>";
        }

        if (msg.length() == 0)
            return "<span class=\"message_warning\" style=\"display: none;\"></span>";

        msg = "<span class=\"message_warning\">" + msg + "</span>";
        // System.out.println(msg);

        return msg;
    }

    public static String getCookie(HttpServletRequest request, String parametro) {
        Cookie rc[] = request.getCookies();

        if (rc == null)
            return null;

        for (Cookie c : rc) {

            if (c.getName().equals(parametro)) {
                return c.getValue();
            }

        }
        return null;
    }

    public static void setCookie(HttpServletResponse response, String parametro, String value, int maxAge, String comment) {

        Cookie c = new Cookie(parametro, value);
        c.setComment(comment);

        if (maxAge <= 0)
            c.setMaxAge(60 * 24 * 60 * 60);
        else
            c.setMaxAge(maxAge);

        response.addCookie(c);

    }

    public static byte[] toByteVector(InputStream in) {

        ArrayList<byte[]> result = new ArrayList<byte[]>();

        byte[] buffer = new byte[1024];

        int countRead = 0;

        try {
            while ((countRead = in.read(buffer)) > 0) {

                if (countRead == buffer.length) {
                    result.add(buffer);
                    buffer = new byte[1024];
                    continue;
                }

                byte[] aux = new byte[countRead];
                result.add(aux);

                for (int i = 0; i < aux.length; i++) {
                    aux[i] = buffer[i];
                }

                buffer = new byte[1024];

            }

        } catch (IOException e) {
            return null;
        }

        if (result.size() == 0)
            return null;

        countRead = 0;
        for (byte[] buf : result)
            countRead += buf.length;

        buffer = new byte[countRead];

        int pos = 0;
        for (byte[] bs : result) {
            for (byte b : bs) {
                buffer[pos++] = b;
            }
        }
        return buffer;
    }

    public static String IsoToUtf(String s) {

        if (s == null)
            return null;
        try {
            return new String(s.getBytes("ISO-8859-1"), 0, s.getBytes("ISO-8859-1").length, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static boolean redirectSapl(HttpServletRequest request, HttpServletResponse response) {
        /*
         * Enumeration<String> e = request.getHeaderNames();
         * 
         * while (e.hasMoreElements()) {
         * 
         * String param = (String) e.nextElement(); // System.out.println(param
         * + ": " + request.getHeader(param)); System.out.println(param + ": " +
         * request.getHeader(param)); }
         */
        // System.out.println("remoteAddr:" + request.getRemoteAddr());

        if (request.getHeader("host").equals("sislegis.camarajatai.go.gov.br") && Urls.appBase.equals("portal"))

            try {

                String iddoc = request.getParameter("iddoc");

                if (iddoc == null)
                    response.sendRedirect("http://sapl.camarajatai.go.gov.br/sapl/");
                else
                    response.sendRedirect("http://sapl.camarajatai.go.gov.br/sapl/" + "consultas/norma_juridica/norma_juridica_mostrar_proc?cod_norma=" + iddoc);
                return true;
            } catch (IOException ee) { // TODO Auto-generated catch block
                return false;
            }
        return false;

    }
}