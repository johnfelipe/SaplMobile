package br.gov.go.camarajatai.sislegisold.dto;

import java.sql.Timestamp;
import java.util.ArrayList;

import br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei;

/**
 * The persistent class for the lei database table.
 * 
 */
public class Documento extends Dto {

    private Timestamp data_alteracao;

    private Timestamp data_inclusao;

    private Timestamp data_lei;

    private String ementa = "";

    private String enunciado = "";

    private String epigrafe = "";

    private String indicacao = "";

    private int numero;

    private String preambulo = "";

    private CollectionItensDeLei itemleis = new CollectionItensDeLei();

    private ArrayList<Tipolei> id_tipolei;

    private Arquivo id_arquivo;

    private Documento id_revogador = null;

    private Documento id_doc_principal = null;

    private String link_revogador = "";

    private String texto_final = "";

    private String assinatura = "";

    private String cargo_assinante = "";

    private boolean publicado = false;

    private String content_type = "";

    private int size_bytes_files = 0;

    private String name_file = "";

    private int consultas = 0;
    private byte[] arqdigital = null;
    private boolean possuiarqdigital = false;
    private String hash_arqdigital = "";

    private int cod_certidao = 0;

    private boolean onLine = false;

    public Documento() {
    }

    public Documento(int id) {
        this.id = id;
    }

    public Timestamp getData_alteracao() {
        return this.data_alteracao;
    }

    public void setData_alteracao(Timestamp data_alteracao) {
        this.data_alteracao = data_alteracao;
    }

    public Timestamp getData_inclusao() {
        return this.data_inclusao;
    }

    public void setData_inclusao(Timestamp data_inclusao) {
        this.data_inclusao = data_inclusao;
    }

    public Timestamp getData_lei() {
        return this.data_lei;
    }

    public void setData_lei(Timestamp data_lei) {
        this.data_lei = data_lei;
    }

    public String getEmenta() {
        return this.ementa;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getEnunciado() {
        return this.enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getEpigrafe() {
        return this.epigrafe;
    }

    public void setEpigrafe(String epigrafe) {
        this.epigrafe = epigrafe;
    }

    public String getIndicacao() {
        return this.indicacao;
    }

    public void setIndicacao(String indicacao) {
        this.indicacao = indicacao;
    }

    public int getNumero() {
        return this.numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getPreambulo() {
        return this.preambulo;
    }

    public void setPreambulo(String preambulo) {
        this.preambulo = preambulo;
    }

    public CollectionItensDeLei getItemleis() {
        return this.itemleis;
    }

    public void setItemleis(CollectionItensDeLei itemleis) {
        this.itemleis = itemleis;
    }

    /*
     * public Tipolei getId_tipolei() { return this.id_tipolei; }
     * 
     * public void setId_tipolei(Tipolei tipolei) { if (tipolei != null &&
     * tipolei.getId() == 0) this.id_tipolei = null; else this.id_tipolei =
     * tipolei; }
     */

    public String getTexto_final() {
        return texto_final;
    }

    public void setTexto_final(String textoFinal) {
        texto_final = textoFinal;
    }

    public String getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(String assinatura) {
        this.assinatura = assinatura;
    }

    public String getCargo_assinante() {
        return cargo_assinante;
    }

    public void setCargo_assinante(String cargoAssinante) {
        cargo_assinante = cargoAssinante;
    }

    /**
     * @param consultas
     *            the consultas to set
     */
    public void setConsultas(int consultas) {
        this.consultas = consultas;
    }

    public synchronized void incrConsultas() {
        this.consultas++;
    }

    /**
     * @return the consultas
     */
    public int getConsultas() {
        return consultas;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param onLine
     *            the onLine to setid, numero, epigrafe, e
     */
    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }

    /**
     * @return the onLine
     */
    public boolean getOnLine() {
        return onLine;
    }

    public int compareTo1(Dto o) {
        Documento lei = (Documento) o;
        int leiConsulta = lei.getConsultas();
        if (this.getConsultas() == leiConsulta)
            return 0;
        else if (this.getConsultas() > leiConsulta)
            return 1;
        else
            return -1;

    }

    public int compareTo2(Dto o) {
        Documento lei = (Documento) o;
        int leinumero = lei.getNumero();
        if (this.numero == leinumero)
            return 0;
        else if (this.numero > leinumero)
            return 1;
        else
            return -1;
    }

    public boolean equals(Dto o) {
        Documento lei = (Documento) o;
        if (id == 0 || lei.id == 0) {
            return numero == lei.numero;
        }
        return id == o.id;
    }

    public void setPossuiarqdigital(boolean possuiarqdigital) {
        this.possuiarqdigital = possuiarqdigital;
    }

    public boolean getPossuiarqdigital() {
        return possuiarqdigital;
    }

    public void setArqdigital(byte[] arqdigital) {
        this.arqdigital = arqdigital;
    }

    public byte[] getArqdigital() {
        return arqdigital;
    }

    public void setId_arquivo(Arquivo id_arquivo) {
        this.id_arquivo = id_arquivo;
    }

    public Arquivo getId_arquivo() {
        return id_arquivo;
    }

    public void setId_revogador(Documento id_revogador) {
        this.id_revogador = id_revogador;
    }

    public Documento getId_revogador() {
        return id_revogador;
    }

    public void setLink_revogador(String link_revogador) {
        this.link_revogador = link_revogador;
    }

    public String getLink_revogador() {
        return link_revogador;
    }

    public Documento getId_doc_principal() {
        return id_doc_principal;
    }

    public void setId_doc_principal(Documento id_doc_principal) {
        this.id_doc_principal = id_doc_principal;
    }

    public boolean getPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    public int getSize_bytes_files() {
        return size_bytes_files;
    }

    public void setSize_bytes_files(int size_bytes_files) {
        this.size_bytes_files = size_bytes_files;
    }

    public String getName_file() {
        return name_file;
    }

    public void setName_file(String name_file) {
        this.name_file = name_file;
    }

    public String getHash_arqdigital() {
        return hash_arqdigital;
    }

    public void setHash_arqdigital(String hash_arqdigital) {
        this.hash_arqdigital = hash_arqdigital;
    }

    public int getCod_certidao() {
        return cod_certidao;
    }

    public void setCod_certidao(int cod_certidao) {
        this.cod_certidao = cod_certidao;
    }

    public ArrayList<Tipolei> getId_tipolei() {

        if (id_tipolei == null)
            id_tipolei = new ArrayList<Tipolei>();

        return id_tipolei;
    }

    public void setId_tipolei(ArrayList<Tipolei> id_tipolei) {
        this.id_tipolei = id_tipolei;
    }

}