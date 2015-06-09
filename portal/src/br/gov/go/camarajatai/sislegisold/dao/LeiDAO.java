package br.gov.go.camarajatai.sislegisold.dao;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

import br.gov.go.camarajatai.sislegisold.business.PesquisaDeDocumentos;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dto.Arquivo;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

public class LeiDAO extends DAO<Documento> {

    public static final String backupFileSystem = "/var/storage/databases/" + Urls.appBase + "/";

    public Statement stm = PostgreConnection.getInstance().newStatement();
    /*
     * private TreeSet<Documento> collection = new TreeSet<Documento>(); private
     * LinkedList<Documento> collectionTop = null; private LinkedList<Documento>
     * collectionLast = null; private long timeUpDateCollectionTop = 0; private
     * long timeUpDateCollectionLast = 0;
     */
    private long contador = 0;

    String sqlSelectAll = "select id, numero, epigrafe, ementa, preambulo, enunciado, indicacao, id_arquivo, data_inclusao, data_lei, data_alteracao, consultas, texto_final, assinatura, cargo_assinante, possuiarqdigital, id_revogador, link_revogador, id_doc_principal, publicado, content_type, size_bytes_files, name_file, cod_certidao from documento";

    public LeiDAO() {
        // selectAll();
        // getCollection();
    }

    /*
     * public synchronized TreeSet<Documento> getCollection() {
     * 
     * // selectAll();
     * 
     * TreeSet<Documento> col = new TreeSet<Documento>();
     * col.addAll(collection);
     * 
     * Iterator<Documento> it = getLastIncludes().iterator(); Documento lei;
     * while (it.hasNext()) { lei = it.next();
     * 
     * if (lei.getId_tipolei().getId() == 1)
     * lei.setEpigrafe("Lei nº "+lei.getNumero
     * ()+" de "+lei.getData_lei().getDate
     * ()+" de "+Suport.monthNames[lei.getData_lei ().getMonth()].toLowerCase(
     * )+" de "+(lei.getData_lei().getYear()+1900)); update(lei); }
     * 
     * return col; }
     */
    /*
     * public synchronized LinkedList<Documento> getCollection(Tipolei tipolei)
     * { // selectAll(); System.out.println(++contador); LinkedList<Documento>
     * col = new LinkedList<Documento>(); Iterator<Documento> it =
     * getLastIncludes().iterator();
     * 
     * if (tipolei == null) return col;
     * 
     * Documento lei; while (it.hasNext()) { lei = it.next();
     * 
     * 
     * if (lei.getId_tipolei().getId() == tipolei.getId()) { col.add(lei); } }
     * 
     * return col; }
     * 
     * 
     * 
     * public synchronized LinkedList<Documento> getTopList() {
     * 
     * if (collectionTop != null) {
     * 
     * if ((new GregorianCalendar().getTimeInMillis() - timeUpDateCollectionTop)
     * < 60000) return collectionTop;
     * 
     * } timeUpDateCollectionTop = new GregorianCalendar().getTimeInMillis();
     * 
     * LinkedList<Documento> list = new LinkedList<Documento>();
     * 
     * Iterator<Documento> itSet = collection.iterator(); Documento lei,
     * leiNovo; ListIterator<Documento> itList; while (itSet.hasNext()) {
     * leiNovo = itSet.next();
     * 
     * if (list.size() != 0) { itList = list.listIterator(); while
     * (itList.hasNext()) { lei = itList.next();
     * 
     * if (leiNovo.getConsultas() > lei.getConsultas()) { itList.previous();
     * itList.add(leiNovo); break; }
     * 
     * if (!itList.hasNext()) { itList.add(leiNovo); break; }
     * 
     * } } else { list.add(leiNovo); } } collectionTop = list;
     * 
     * return list;
     * 
     * }
     */

    public void insert(Documento lei) {

        String sql = PostgreConnection.constructSQLInsert(lei);
        int result = PostgreConnection.getInstance().execute(stm, sql);
        if (result > 0) {
            lei.setId(result);
        }

        // lei.setOnLine(false);
        // collection.add(lei);

        alterarXMLDocumento(lei);
    }

    public void update(Documento lei) {

        String sql = PostgreConnection.constructSQLUpDate(lei);
        int result = PostgreConnection.getInstance().execute(stm, sql);
        // lei.setOnLine(false);
        // collection.add(lei);

        alterarXMLDocumento(lei);

    }

    public void alterarXMLDocumento(Documento doc) {

        File dir = new File(LeiDAO.backupFileSystem);
        dir.mkdirs();
        File arq = new File(LeiDAO.backupFileSystem + doc.getId() + ".zip");

        // OutputStreamWriter fo;
        FileOutputStream fo;
        try {
            int n = 0;
            // fo = new OutputStreamWriter(new FileOutputStream(arq),
            // Charset.forName("UTF8"));
            fo = new FileOutputStream(arq);

            ZipArchiveOutputStream zOut = new ZipArchiveOutputStream(new BufferedOutputStream(fo));
            zOut.setLevel(9);
            zOut.setEncoding("UTF-8"); // This should handle your "special"
                                       // characters
            zOut.setFallbackToUTF8(true); // For "unknown" characters!
            zOut.setUseLanguageEncodingFlag(true);
            zOut.setCreateUnicodeExtraFields(ZipArchiveOutputStream.UnicodeExtraFieldPolicy.NOT_ENCODEABLE);
            // zOut.setMethod(ZipArchiveOutputStream.DEFLATED);

            ZipArchiveEntry ze = new ZipArchiveEntry("meta-dados.xml");
            zOut.putArchiveEntry(ze);

            wf(zOut, n, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
            wf(zOut, n, "<documento id=\"" + doc.getId() + "\" numero=\"" + doc.getNumero() + "\" data-inclusao=\"" + doc.getData_inclusao()
                    + "\" data-alteracao=\"" + doc.getData_alteracao() + "\" data-lei=\"" + doc.getData_lei() + "\">");
            n++;
            wf(zOut, n, "<epigrafe><![CDATA[" + doc.getEpigrafe() + "]]></epigrafe>");
            wf(zOut, n, "<ementa><![CDATA[" + doc.getEmenta() + "]]></ementa>");
            wf(zOut, n, "<enunciado><![CDATA[" + doc.getEnunciado() + "]]></enunciado>");
            wf(zOut, n, "<indicacao><![CDATA[" + doc.getIndicacao() + "]]></indicacao>");
            wf(zOut, n, "<preambulo><![CDATA[" + doc.getPreambulo() + "]]></preambulo>");

            for (Tipolei tl : doc.getId_tipolei())
                wf(zOut, n, "<tipolei id=\"" + tl.getId() + "\" descr=\"" + tl.getDescr() + "\"/>");

            if (doc.getId_revogador() != null)
                wf(zOut, n, "<id-revogador>" + doc.getId_revogador().getId() + "</id-revogador>");

            if (doc.getId_doc_principal() != null)
                wf(zOut, n, "<id-doc-principal>" + doc.getId_doc_principal().getId() + "</id-doc-principal>");

            wf(zOut, n, "<texto-final><![CDATA[" + doc.getTexto_final() + "]]></texto-final>");
            wf(zOut, n, "<assinatura><![CDATA[" + doc.getAssinatura() + "]]></assinatura>");
            wf(zOut, n, "<cargo-assinante><![CDATA[" + doc.getCargo_assinante() + "]]></cargo-assinante>");
            wf(zOut, n, "<publicado>" + doc.getPublicado() + "</publicado>");
            wf(zOut, n, "<namefile><![CDATA[" + doc.getName_file() + "]]></namefile>");
            wf(zOut, n, "<cod-certidao>" + doc.getCod_certidao() + "</cod-certidao>");
            wf(zOut, n, "<possuiarqdigital>" + doc.getPossuiarqdigital() + "</possuiarqdigital>");

            wf(zOut, n, "<hash-arqdigital>" + doc.getHash_arqdigital() + "</hash-arqdigital>");

            wf(zOut, n, "<itens>");

            Seeker.getInstance().upDateCollectionItensDeLei(doc);

            Iterator<Itemlei> it = doc.getItemleis().iterator();
            Itemlei item;
            n++;
            while (it.hasNext()) {
                item = it.next();

                wf(zOut,
                        n,
                        "<itemlei " + "id-alterador=\"" + item.getId_alterador() + "\" " + "id-lei=\"" + item.getId_lei() + "\" " + "id-dono=\""
                                + item.getId_dono() + "\" " + "numero=\"" + item.getNumero() + "\" " + "anexo=\"" + item.getAnexo() + "\" " + "parte=\""
                                + item.getParte() + "\" " + "livro=\"" + item.getLivro() + "\" " + "titulo=\"" + item.getTitulo() + "\" " + "capitulo=\""
                                + item.getCapitulo() + "\" " + "capitulovar=\"" + item.getCapitulovar() + "\" " + "secao=\"" + item.getSecao() + "\" "
                                + "secaovar=\"" + item.getSecaovar() + "\" " + "subsecao=\"" + item.getSubsecao() + "\" " + "itemsecao=\""
                                + item.getItemsecao() + "\" " + "artigo=\"" + item.getArtigo() + "\" " + "artigovar=\"" + item.getArtigovar() + "\" "
                                + "paragrafo=\"" + item.getParagrafo() + "\" " + "inciso=\"" + item.getInciso() + "\" " + "incisovar=\"" + item.getIncisovar()
                                + "\" " + "incisovarvar=\"" + item.getIncisovarvar() + "\" " + "alinea=\"" + item.getAlinea() + "\" " + "item=\""
                                + item.getItem() + "\" " + "subitem=\"" + item.getSubitem() + "\" " + "subsubitem=\"" + item.getSubsubitem() + "\" "
                                + "nivel=\"" + item.getNivel() + "\" " + "revogado=\"" + item.getRevogado() + "\" " + "alterado=\"" + item.getAlterado()
                                + "\" " + "incluido=\"" + item.getIncluido() + "\" " + "data-inclusao=\"" + item.getData_inclusao() + "\" "
                                + "data-alteracao=\"" + item.getData_alteracao() + "\">");

                if (item.getLink_alterador() != null)
                    wf(zOut, n, "<link-alterador><![CDATA[" + item.getLink_alterador() + "]]></link-alterador>");

                wf(zOut, n, "<texto><![CDATA[" + item.getTexto() + "]]></texto>");

                wf(zOut, n, "</itemlei>");

            }
            n--;
            wf(zOut, n, "</itens>");

            // wf(zOut,n, "  <>"+doc+"</>");

            n = 0;
            wf(zOut, n, "</documento>");

            zOut.closeArchiveEntry();

            if (doc.getPossuiarqdigital()) {

                Documento d = new Documento(doc.getId());
                selectArqDigital(d);
                byte[] file = d.getArqdigital();

                String nome = doc.getName_file();

                if (nome == null || nome.length() == 0) {
                    nome = String.valueOf(doc.getNumero());

                    try {
                        String initFile = new String(file, 0, 4);
                        if (initFile.toUpperCase().equals("%PDF"))
                            nome += ".pdf";
                    } catch (Exception e) {

                    }

                }

                ze = new ZipArchiveEntry(nome);
                zOut.putArchiveEntry(ze);

                zOut.write(file);

                zOut.closeArchiveEntry();
            }

            zOut.flush();
            zOut.close();

            fo.flush();
            fo.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void delete(Documento lei) {

        int result = PostgreConnection.getInstance().execute(stm, "delete from documento where id=" + lei.getId() + ";");
        // collection.remove(lei);

    }

    public ArrayList<Documento> selectAll() {

        ArrayList<Documento> result = new ArrayList<Documento>();

        try {
            ResultSet rs = stm.executeQuery(sqlSelectAll + " order by " + (Urls.appBase.equals("portal") ? "" : "data_alteracao desc, ")
                    + "data_lei asc, numero asc, id asc  ");
            Documento doc = null;
            while (rs.next()) {

                doc = new Documento();

                doc.setId(rs.getInt("id"));
                doc.setNumero(rs.getInt("numero"));
                doc.setEpigrafe(rs.getString("epigrafe"));
                doc.setEmenta(rs.getString("ementa"));
                doc.setPreambulo(rs.getString("preambulo"));
                doc.setEnunciado(rs.getString("enunciado"));
                doc.setIndicacao(rs.getString("indicacao"));

                if (rs.getInt("id_arquivo") == 0)
                    doc.setId_arquivo(null);
                else
                    doc.setId_arquivo(new Arquivo(rs.getInt("id_arquivo")));

                doc.setData_inclusao(rs.getTimestamp("data_inclusao"));
                doc.setData_alteracao(rs.getTimestamp("data_alteracao"));
                doc.setData_lei(rs.getTimestamp("data_lei"));
                doc.setConsultas(rs.getInt("consultas"));
                doc.setTexto_final(rs.getString("texto_final"));
                doc.setAssinatura(rs.getString("assinatura"));
                doc.setCargo_assinante(rs.getString("cargo_assinante"));
                doc.setPossuiarqdigital(rs.getBoolean("possuiarqdigital"));
                doc.setOnLine(false);

                if (rs.getInt("id_revogador") == 0)
                    doc.setId_revogador(null);
                else
                    doc.setId_revogador(new Documento(rs.getInt("id_revogador")));

                if (rs.getInt("id_doc_principal") == 0)
                    doc.setId_doc_principal(null);
                else
                    doc.setId_doc_principal(new Documento(rs.getInt("id_doc_principal")));
                doc.setLink_revogador(rs.getString("link_revogador"));

                doc.setPublicado(rs.getBoolean("publicado"));

                doc.setContent_type(rs.getString("content_type"));
                doc.setSize_bytes_files(rs.getInt("size_bytes_files"));
                doc.setName_file(rs.getString("name_file"));

                if (doc.getContent_type() == null || doc.getContent_type().length() == 0
                        || (doc.getName_file() != null && doc.getName_file().toLowerCase().endsWith(".pdf")))
                    doc.setContent_type("application/pdf");

                doc.setCod_certidao(rs.getInt("cod_certidao"));

                result.add(doc);
            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public ArrayList<Documento> selectNormasAlteradoras(Documento docAlterado) {

        ArrayList<Documento> result = new ArrayList<Documento>();

        try {

            ResultSet rs = stm.executeQuery("select distinct id_alterador, id_lei from itemlei where id_lei != id_alterador and id_lei = "
                    + docAlterado.getId() + ";");

            Documento doc = null;
            while (rs.next()) {
                doc = new Documento();
                doc.setId(rs.getInt("id_alterador"));

                result.add(doc);

            }

            int count = 0;
            for (Documento docc : result) {

                doc = selectOne(docc);
                result.set(count++, doc);

            }

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return result;
    }

    public Documento selectOne(Documento _doc) {
        Documento doc = null;

        try {
            ResultSet rs = stm.executeQuery("select * from documento where id =" + _doc.getId());
            while (rs.next()) {

                doc = new Documento();

                doc.setId(rs.getInt("id"));
                doc.setNumero(rs.getInt("numero"));
                doc.setEpigrafe(rs.getString("epigrafe"));
                doc.setEmenta(rs.getString("ementa"));
                doc.setPreambulo(rs.getString("preambulo"));
                doc.setEnunciado(rs.getString("enunciado"));
                doc.setIndicacao(rs.getString("indicacao"));

                if (rs.getInt("id_arquivo") == 0)
                    doc.setId_arquivo(null);
                else
                    doc.setId_arquivo(new Arquivo(rs.getInt("id_arquivo")));

                doc.setData_inclusao(rs.getTimestamp("data_inclusao"));
                doc.setData_alteracao(rs.getTimestamp("data_alteracao"));
                doc.setData_lei(rs.getTimestamp("data_lei"));
                doc.setConsultas(rs.getInt("consultas"));
                doc.setTexto_final(rs.getString("texto_final"));
                doc.setAssinatura(rs.getString("assinatura"));
                doc.setCargo_assinante(rs.getString("cargo_assinante"));
                doc.setPossuiarqdigital(rs.getBoolean("possuiarqdigital"));

                if (rs.getInt("id_revogador") == 0)
                    doc.setId_revogador(null);
                else
                    doc.setId_revogador(new Documento(rs.getInt("id_revogador")));

                if (rs.getInt("id_doc_principal") == 0)
                    doc.setId_doc_principal(null);
                else
                    doc.setId_doc_principal(new Documento(rs.getInt("id_doc_principal")));

                doc.setLink_revogador(rs.getString("link_revogador"));
                doc.setPublicado(rs.getBoolean("publicado"));

                doc.setContent_type(rs.getString("content_type"));
                doc.setSize_bytes_files(rs.getInt("size_bytes_files"));
                doc.setName_file(rs.getString("name_file"));

                if (doc.getContent_type() == null || doc.getContent_type().length() == 0
                        || (doc.getName_file() != null && doc.getName_file().toLowerCase().endsWith(".pdf")))
                    doc.setContent_type("application/pdf");
                doc.setCod_certidao(rs.getInt("cod_certidao"));

            }

        } catch (SQLException e) {
        }

        return doc;

    }

    public void clickLei(Documento lei) {

        PostgreConnection.getInstance().execute(stm, "update documento set consultas = consultas + 1 where id = " + lei.getId());

        lei.incrConsultas();

    }

    /*
     * public void turnOffLine(Documento _lei) {
     * 
     * Documento lei = null;
     * 
     * if (collection.contains(_lei)) { lei = collection.ceiling(_lei);
     * lei.getItemleis().clear(); lei.setOnLine(false); } }
     * 
     * public int getCountLastIncludes() {
     * 
     * return getLastIncludes().size();
     * 
     * }
     * 
     * public int getCountTopList() {
     * 
     * return getTopList().size();
     * 
     * }
     * 
     * public int getCountCollectionLei(Tipolei tipolei) { return
     * getCollection(tipolei).size(); }
     * 
     * public synchronized LinkedList<Documento> getLastIncludes() {
     * 
     * if (collectionLast != null) {
     * 
     * if ((new GregorianCalendar().getTimeInMillis() -
     * timeUpDateCollectionLast) < 60000) return collectionLast;
     * 
     * } timeUpDateCollectionLast = new GregorianCalendar().getTimeInMillis();
     * 
     * LinkedList<Documento> list = new LinkedList<Documento>();
     * 
     * Iterator<Documento> itSet = collection.iterator(); Documento lei,
     * leiNovo; ListIterator<Documento> itList; while (itSet.hasNext()) {
     * leiNovo = itSet.next();
     * 
     * 
     * if (!leiNovo.getPublicado()) continue;
     * 
     * 
     * if (list.size() != 0) { itList = list.listIterator();
     * 
     * while (itList.hasNext()) {
     * 
     * lei = itList.next();
     * 
     * if (lei.getData_alteracao() == null) lei.setData_alteracao(new
     * Timestamp(new GregorianCalendar().getTimeInMillis())); if
     * (leiNovo.getData_alteracao() == null) leiNovo.setData_alteracao(new
     * Timestamp(new GregorianCalendar().getTimeInMillis()));
     * 
     * if (leiNovo.getData_alteracao().getTime() >
     * lei.getData_alteracao().getTime()) {
     * 
     * //if (leiNovo.getNumero() > lei.getNumero()) { itList.previous();
     * itList.add(leiNovo); break; // } }
     * 
     * if (!itList.hasNext()) { itList.add(leiNovo); break; }
     * 
     * } } else { list.add(leiNovo); } } collectionLast = list;
     * 
     * return list;
     * 
     * }
     */

    public void upDateArqDigital(Documento lei) {

        String sql = "update documento set arqdigital = ?, possuiarqdigital = ?  where id=" + lei.getId() + ";";

        try {
            PostgreConnection.getInstance().upDateByteArray(sql, lei.getArqdigital());

            alterarXMLDocumento(lei);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void selectArqDigital(Documento lei) {

        selectOne(lei);

        if (lei.getArqdigital() != null)
            return;

        String sql = "select arqdigital from documento where id = " + lei.getId() + ";";

        try {
            lei.setArqdigital(PostgreConnection.getInstance().selectByteArray(sql));

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void selectHashArqDigital(Documento lei, String hash) {

        selectOne(lei);

        lei.setHash_arqdigital("");
        if (!lei.getPossuiarqdigital())
            return;

        String sql = "select encode(digest(arqdigital, '" + hash + "'), 'hex') from documento where id = " + lei.getId() + ";";

        try {
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next())
                lei.setHash_arqdigital(rs.getString(1));

        } catch (SQLException e) {

        }

    }

    public void getDocs(PesquisaDeDocumentos p, boolean intranet) {

        String sqlPesquisa = "select distinct l.id, " + "l.numero, " + "l.epigrafe, " + "l.ementa, " + "l.preambulo, " + "l.enunciado, " + "l.indicacao, "
                + "l.id_arquivo, " + "l.data_inclusao, " + "l.data_lei, " + "l.data_alteracao, " + "l.consultas, " + "l.texto_final, " + "l.assinatura, "
                + "l.cargo_assinante, " + "l.possuiarqdigital, " + "l.id_revogador, " + "l.link_revogador, " + "l.id_doc_principal, " + "l.publicado, "
                + "l.content_type," + " l.size_bytes_files," + " l.name_file," + " l.cod_certidao ";

        String sqlTotal = "select count(*) ";

        String sqlWhere = "from documento l, tipolei t, tipodoc d, assuntos a where ";

        if (p.getTextoSearch().length() > 0)

            if (p.getTextoSearch().indexOf(" ") != -1) {

                String stext[] = p.getTextoSearch().split(" ");
                int i;
                for (i = 0; i < stext.length - 1; i++) {
                    sqlWhere += "l.ementa ~* '" + stext[i] + "' and ";
                }
                sqlWhere += "l.ementa ~* '" + stext[i] + "' and ";

            } else {
                sqlWhere += "l.ementa ~* '" + p.getTextoSearch() + "' and ";
            }

        sqlWhere += "(l.id_doc_principal is null or l.id_doc_principal <= 0 ) and d.id = t.\"idTipoDoc\" and l.id = a.documento and t.id = a.tipo "
                + (intranet ? "" : " and d.intranet = false ") +

                (p.getTd() != null && p.getTd().getId() > 2 ? "and d.id = " + p.getTd().getId() + " " : " ");

        sqlWhere += (p.getTl() != null ? "and t.id = " + p.getTl().getId() + " " : " ");

        sqlWhere += (p.getNumDoc() != 0 ? "and l.numero = " + p.getNumDoc() + " " : " ");

        if (p.getPublico() != null)
            sqlWhere += "and publicado = " + p.getPublico() + " ";

        try {
            sqlTotal += sqlWhere;

            ResultSet rs = stm.executeQuery(sqlTotal);
            rs.next();
            p.setTotalDocumentos(rs.getInt(1));

            String sqlPage = "order by " + (p.getTd() != null && p.getTd().getId() == 1 ? "l.consultas desc, " : "")
                    + (Urls.appBase.equals("portal") ? "" : "l.data_alteracao desc, ") + "l.data_lei desc, l.numero desc, l.id desc  " + "offset "
                    + (p.getPagina()) * p.getQtdDocumentos() + " limit " + p.getQtdDocumentos();

            sqlPesquisa += sqlWhere + sqlPage;

            rs = stm.executeQuery(sqlPesquisa);

            final List<Documento> lista = new ArrayList<Documento>();
            p.setLista(lista);
            Documento lei = null;
            while (rs.next()) {

                lei = new Documento();

                lei.setId(rs.getInt("id"));
                lei.setNumero(rs.getInt("numero"));
                lei.setEpigrafe(rs.getString("epigrafe"));
                lei.setEmenta(rs.getString("ementa"));
                lei.setPreambulo(rs.getString("preambulo"));
                lei.setEnunciado(rs.getString("enunciado"));
                lei.setIndicacao(rs.getString("indicacao"));
                // lei.setId_tipolei(new Tipolei(rs.getInt("id_tipolei")));

                if (rs.getInt("id_arquivo") == 0)
                    lei.setId_arquivo(null);
                else
                    lei.setId_arquivo(new Arquivo(rs.getInt("id_arquivo")));

                lei.setData_inclusao(rs.getTimestamp("data_inclusao"));
                lei.setData_alteracao(rs.getTimestamp("data_alteracao"));
                lei.setData_lei(rs.getTimestamp("data_lei"));
                lei.setConsultas(rs.getInt("consultas"));
                lei.setTexto_final(rs.getString("texto_final"));
                lei.setAssinatura(rs.getString("assinatura"));
                lei.setCargo_assinante(rs.getString("cargo_assinante"));
                lei.setPossuiarqdigital(rs.getBoolean("possuiarqdigital"));
                lei.setOnLine(false);

                if (rs.getInt("id_revogador") == 0)
                    lei.setId_revogador(null);
                else
                    lei.setId_revogador(new Documento(rs.getInt("id_revogador")));

                if (rs.getInt("id_doc_principal") == 0)
                    lei.setId_doc_principal(null);
                else
                    lei.setId_doc_principal(new Documento(rs.getInt("id_doc_principal")));

                lei.setLink_revogador(rs.getString("link_revogador"));
                lei.setPublicado(rs.getBoolean("publicado"));

                lei.setContent_type(rs.getString("content_type"));
                lei.setSize_bytes_files(rs.getInt("size_bytes_files"));
                lei.setName_file(rs.getString("name_file"));

                if (lei.getContent_type() == null || lei.getContent_type().length() == 0
                        || (lei.getName_file() != null && lei.getName_file().toLowerCase().endsWith(".pdf")))
                    lei.setContent_type("application/pdf");

                lei.setCod_certidao(rs.getInt("cod_certidao"));

                lista.add(lei);
            }
            /*
             * new Thread(new Runnable() {
             * 
             * @Override public void run() { Documento doc; Iterator<Documento>
             * it = lista.iterator(); while (it.hasNext()) { doc = it.next();
             * 
             * alterarXMLDocumento(doc); doc.getItemleis().clear();
             * 
             * List<Documento> lDocs = getDocsBase(doc);
             * 
             * if (lDocs.size() > 0) for (Documento d : lDocs) {
             * alterarXMLDocumento(d); d.getItemleis().clear(); try {
             * Thread.sleep(1000); } catch (InterruptedException e) { // TODO
             * Auto-generated catch block e.printStackTrace(); } System.gc(); }
             * else { try { Thread.sleep(1000); } catch (InterruptedException e)
             * { // TODO Auto-generated catch block e.printStackTrace(); }
             * System.gc(); }
             * 
             * } // TODO Auto-generated method stub
             * 
             * } }).start();
             */

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public void getDocsOld(PesquisaDeDocumentos p, boolean intranet) {

        String sqlPesquisa = "select l.id, " + "l.numero, " + "l.epigrafe, " + "l.ementa, " + "l.preambulo, " + "l.enunciado, " + "l.indicacao, "
                + "l.id_arquivo, " + "l.data_inclusao, " + "l.data_lei, " + "l.data_alteracao, " + "l.consultas, " + "l.texto_final, " + "l.assinatura, "
                + "l.cargo_assinante, " + "l.possuiarqdigital, " + "l.id_revogador, " + "l.link_revogador, " + "l.id_doc_principal, " + "l.publicado, "
                + "l.content_type," + " l.size_bytes_files," + " l.name_file," + " l.cod_certidao ";

        String sqlTotal = "select count(*) ";

        String sqlWhere = "from documento l, tipolei t, tipodoc d, assuntos a where ";

        if (p.getTextoSearch().length() > 0)

            if (p.getTextoSearch().indexOf(" ") != -1) {

                String stext[] = p.getTextoSearch().split(" ");
                int i;
                for (i = 0; i < stext.length - 1; i++) {
                    sqlWhere += "l.ementa ~* '" + stext[i] + "' and ";
                }
                sqlWhere += "l.ementa ~* '" + stext[i] + "' and ";

            } else {
                sqlWhere += "l.ementa ~* '" + p.getTextoSearch() + "' and ";
            }

        sqlWhere += "(l.id_doc_principal is null or l.id_doc_principal <= 0 ) and d.id = t.\"idTipoDoc\" and l.id = a.documento and t.id = a.tipo "
                + (intranet ? "" : " and d.intranet = false ") +

                (p.getTd() != null && p.getTd().getId() > 2 ? "and d.id = " + p.getTd().getId() + " " : " ");

        sqlWhere += (p.getTl() != null ? "and t.id = " + p.getTl().getId() + " " : " ");

        sqlWhere += (p.getNumDoc() != 0 ? "and l.numero = " + p.getNumDoc() + " " : " ");

        if (p.getPublico() != null)
            sqlWhere += "and publicado = " + p.getPublico() + " ";

        try {
            sqlTotal += sqlWhere;

            ResultSet rs = stm.executeQuery(sqlTotal);
            rs.next();
            p.setTotalDocumentos(rs.getInt(1));

            String sqlPage = "order by " + (p.getTd() != null && p.getTd().getId() == 1 ? "l.consultas desc, " : "")
                    + (Urls.appBase.equals("portal") ? "" : "l.data_alteracao desc, ") + "l.data_lei desc, l.numero desc, l.id desc  " + "offset "
                    + (p.getPagina()) * p.getQtdDocumentos() + " limit " + p.getQtdDocumentos();

            sqlPesquisa += sqlWhere + sqlPage;

            rs = stm.executeQuery(sqlPesquisa);

            final List<Documento> lista = new ArrayList<Documento>();
            p.setLista(lista);
            Documento lei = null;
            while (rs.next()) {

                lei = new Documento();

                lei.setId(rs.getInt("id"));
                lei.setNumero(rs.getInt("numero"));
                lei.setEpigrafe(rs.getString("epigrafe"));
                lei.setEmenta(rs.getString("ementa"));
                lei.setPreambulo(rs.getString("preambulo"));
                lei.setEnunciado(rs.getString("enunciado"));
                lei.setIndicacao(rs.getString("indicacao"));
                // lei.setId_tipolei(new Tipolei(rs.getInt("id_tipolei")));

                if (rs.getInt("id_arquivo") == 0)
                    lei.setId_arquivo(null);
                else
                    lei.setId_arquivo(new Arquivo(rs.getInt("id_arquivo")));

                lei.setData_inclusao(rs.getTimestamp("data_inclusao"));
                lei.setData_alteracao(rs.getTimestamp("data_alteracao"));
                lei.setData_lei(rs.getTimestamp("data_lei"));
                lei.setConsultas(rs.getInt("consultas"));
                lei.setTexto_final(rs.getString("texto_final"));
                lei.setAssinatura(rs.getString("assinatura"));
                lei.setCargo_assinante(rs.getString("cargo_assinante"));
                lei.setPossuiarqdigital(rs.getBoolean("possuiarqdigital"));
                lei.setOnLine(false);

                if (rs.getInt("id_revogador") == 0)
                    lei.setId_revogador(null);
                else
                    lei.setId_revogador(new Documento(rs.getInt("id_revogador")));

                if (rs.getInt("id_doc_principal") == 0)
                    lei.setId_doc_principal(null);
                else
                    lei.setId_doc_principal(new Documento(rs.getInt("id_doc_principal")));

                lei.setLink_revogador(rs.getString("link_revogador"));
                lei.setPublicado(rs.getBoolean("publicado"));

                lei.setContent_type(rs.getString("content_type"));
                lei.setSize_bytes_files(rs.getInt("size_bytes_files"));
                lei.setName_file(rs.getString("name_file"));

                if (lei.getContent_type() == null || lei.getContent_type().length() == 0
                        || (lei.getName_file() != null && lei.getName_file().toLowerCase().endsWith(".pdf")))
                    lei.setContent_type("application/pdf");

                lei.setCod_certidao(rs.getInt("cod_certidao"));

                lista.add(lei);
            }
            /*
             * new Thread(new Runnable() {
             * 
             * @Override public void run() { Documento doc; Iterator<Documento>
             * it = lista.iterator(); while (it.hasNext()) { doc = it.next();
             * 
             * alterarXMLDocumento(doc); doc.getItemleis().clear();
             * 
             * List<Documento> lDocs = getDocsBase(doc);
             * 
             * if (lDocs.size() > 0) for (Documento d : lDocs) {
             * alterarXMLDocumento(d); d.getItemleis().clear(); try {
             * Thread.sleep(1000); } catch (InterruptedException e) { // TODO
             * Auto-generated catch block e.printStackTrace(); } System.gc(); }
             * else { try { Thread.sleep(1000); } catch (InterruptedException e)
             * { // TODO Auto-generated catch block e.printStackTrace(); }
             * System.gc(); }
             * 
             * } // TODO Auto-generated method stub
             * 
             * } }).start();
             */

        } catch (SQLException e) {
            System.out.println(e.toString());
        }
    }

    public List<Documento> getDocsBase(Documento docBase) {

        String sqlPesquisa = "select l.id, " + "l.numero, " + "l.epigrafe, " + "l.ementa, " + "l.preambulo, " + "l.enunciado, " + "l.indicacao, "
                + "l.id_arquivo, " + "l.data_inclusao, " + "l.data_lei, " + "l.data_alteracao, " + "l.consultas, " + "l.texto_final, " + "l.assinatura, "
                + "l.cargo_assinante, " + "l.possuiarqdigital, " + "l.id_revogador, " + "l.link_revogador, " + "l.id_doc_principal," + "l.publicado, "
                + "l.content_type, l.size_bytes_files, l.name_file, l.cod_certidao ";

        String sqlWhere = "from documento l where l.id_doc_principal = " + docBase.getId() + " ";

        List<Documento> lista = new ArrayList<Documento>();

        try {
            String sqlPage = "order by l.data_alteracao desc, l.data_lei desc, l.numero desc, l.id desc ";

            sqlPesquisa += sqlWhere + sqlPage;

            ResultSet rs = stm.executeQuery(sqlPesquisa);

            Documento doc = null;
            while (rs.next()) {

                doc = new Documento();

                doc.setId(rs.getInt("id"));
                doc.setNumero(rs.getInt("numero"));
                doc.setEpigrafe(rs.getString("epigrafe"));
                doc.setEmenta(rs.getString("ementa"));
                doc.setPreambulo(rs.getString("preambulo"));
                doc.setEnunciado(rs.getString("enunciado"));
                doc.setIndicacao(rs.getString("indicacao"));
                // doc.setId_tipolei(new Tipolei(rs.getInt("id_tipolei")));

                if (rs.getInt("id_arquivo") == 0)
                    doc.setId_arquivo(null);
                else
                    doc.setId_arquivo(new Arquivo(rs.getInt("id_arquivo")));

                doc.setData_inclusao(rs.getTimestamp("data_inclusao"));
                doc.setData_alteracao(rs.getTimestamp("data_alteracao"));
                doc.setData_lei(rs.getTimestamp("data_lei"));
                doc.setConsultas(rs.getInt("consultas"));
                doc.setTexto_final(rs.getString("texto_final"));
                doc.setAssinatura(rs.getString("assinatura"));
                doc.setCargo_assinante(rs.getString("cargo_assinante"));
                doc.setPossuiarqdigital(rs.getBoolean("possuiarqdigital"));
                doc.setOnLine(false);

                if (rs.getInt("id_revogador") == 0)
                    doc.setId_revogador(null);
                else
                    doc.setId_revogador(new Documento(rs.getInt("id_revogador")));

                if (rs.getInt("id_doc_principal") == 0)
                    doc.setId_doc_principal(null);
                else
                    doc.setId_doc_principal(new Documento(rs.getInt("id_doc_principal")));

                doc.setLink_revogador(rs.getString("link_revogador"));
                doc.setPublicado(rs.getBoolean("publicado"));

                doc.setContent_type(rs.getString("content_type"));
                doc.setSize_bytes_files(rs.getInt("size_bytes_files"));
                doc.setName_file(rs.getString("name_file"));

                if (doc.getContent_type() == null || doc.getContent_type().length() == 0
                        || (doc.getName_file() != null && doc.getName_file().toLowerCase().endsWith(".pdf")))
                    doc.setContent_type("application/pdf");

                doc.setCod_certidao(rs.getInt("cod_certidao"));

                lista.add(doc);

            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return lista;

    }

    public Documento selectOneForNum(Documento _lei) {
        Documento doc = null;

        try {

            ResultSet rs = stm.executeQuery("select * from documento l, tipolei tl where l.id_tipolei = tl.id and tl.ordem >= 0 and l.numero = "
                    + _lei.getNumero() + " and id_tipolei = " + (_lei.getId_tipolei().size() == 0 ? "1" : _lei.getId_tipolei().get(0).getId()));
            if (rs.next()) {

                doc = new Documento();

                doc.setId(rs.getInt("id"));
                doc.setNumero(rs.getInt("numero"));
                doc.setEpigrafe(rs.getString("epigrafe"));
                doc.setEmenta(rs.getString("ementa"));
                doc.setPreambulo(rs.getString("preambulo"));
                doc.setEnunciado(rs.getString("enunciado"));
                doc.setIndicacao(rs.getString("indicacao"));
                // doc.setId_tipolei(new Tipolei(rs.getInt("id_tipolei")));

                if (rs.getInt("id_arquivo") == 0)
                    doc.setId_arquivo(null);
                else
                    doc.setId_arquivo(new Arquivo(rs.getInt("id_arquivo")));

                doc.setData_inclusao(rs.getTimestamp("data_inclusao"));
                doc.setData_alteracao(rs.getTimestamp("data_alteracao"));
                doc.setData_lei(rs.getTimestamp("data_lei"));
                doc.setConsultas(rs.getInt("consultas"));
                doc.setTexto_final(rs.getString("texto_final"));
                doc.setAssinatura(rs.getString("assinatura"));
                doc.setCargo_assinante(rs.getString("cargo_assinante"));
                doc.setPossuiarqdigital(rs.getBoolean("possuiarqdigital"));

                if (rs.getInt("id_revogador") == 0)
                    doc.setId_revogador(null);
                else
                    doc.setId_revogador(new Documento(rs.getInt("id_revogador")));

                if (rs.getInt("id_doc_principal") == 0)
                    doc.setId_doc_principal(null);
                else
                    doc.setId_doc_principal(new Documento(rs.getInt("id_doc_principal")));

                doc.setLink_revogador(rs.getString("link_revogador"));
                doc.setPublicado(rs.getBoolean("publicado"));

                doc.setSize_bytes_files(rs.getInt("size_bytes_files"));
                doc.setName_file(rs.getString("name_file"));

                doc.setContent_type(rs.getString("content_type"));
                if (doc.getContent_type() == null || doc.getContent_type().length() == 0)
                    doc.setContent_type("application/pdf");

                doc.setCod_certidao(rs.getInt("cod_certidao"));
            }

        } catch (SQLException e) {
        }

        return doc;

    }

    public int nextCodCertidao() {

        ResultSet rs = PostgreConnection.getInstance().select(stm, "select nextval('documento_cod_certidao_seq'::regclass);");

        try {
            if (!rs.next())
                return 0;

            return rs.getInt(1);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 0;
    }

    public void updateListaTipoLei(Documento doc) {

        int result = PostgreConnection.getInstance().execute(stm, "update assuntos set excluir = true where documento = " + doc.getId() + ";");

        ResultSet rs;
        for (Tipolei tl : doc.getId_tipolei()) {
            rs = PostgreConnection.getInstance().select(stm, "select * from assuntos where documento = " + doc.getId() + " and tipo = " + tl.getId());

            try {
                if (rs.next()) {
                    result = PostgreConnection.getInstance().execute(stm,
                            "update assuntos set excluir = false, sugestoes = sugestoes + 1 where documento = " + doc.getId() + " and tipo = " + tl.getId());
                } else {
                    result = PostgreConnection.getInstance().execute(
                            stm,
                            "insert into assuntos (excluir, oficial, sugestoes, documento, tipo) values (false, true, 0, " + doc.getId() + ", " + tl.getId()
                                    + ");");
                }

                result = PostgreConnection.getInstance().execute(stm, "delete from assuntos where excluir = true;");

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void updateListaTipoLeiSugestoes(Documento doc) {

        ResultSet rs;
        int result;
        for (Tipolei tl : doc.getId_tipolei()) {
            rs = PostgreConnection.getInstance().select(stm, "select * from assuntos where documento = " + doc.getId() + " and tipo = " + tl.getId());

            try {
                if (rs.next()) {
                    result = PostgreConnection.getInstance().execute(stm,
                            "update assuntos set sugestoes = sugestoes + 1 where documento = " + doc.getId() + " and tipo = " + tl.getId());
                } else {
                    result = PostgreConnection.getInstance().execute(stm,
                            "insert into assuntos (oficial, sugestoes, documento, tipo) values (false, 1, " + doc.getId() + ", " + tl.getId() + ");");
                }

            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void wf(ZipArchiveOutputStream fo, int nivel, String str) throws IOException {

        while (nivel > 0) {
            str = "  " + str;
            nivel--;
        }

        fo.write((str + "\r\n").getBytes());
    }

}
