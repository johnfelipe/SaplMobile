package br.gov.go.camarajatai.sislegisold.lexml;

import java.sql.Timestamp;
import java.util.Calendar;

public class RegistroItem {
	
	
	private String idRegistroItem = null;
	private String cdStatus = null;
	private String cdValidacao = null;
	private Timestamp tsRegistroGmt = null;
	private String txMatadadoXml = null;
	
	
	
	
	
	public String getIdRegistroItem() {
		return idRegistroItem;
	}
	public void setIdRegistroItem(String idRegistroItem) {
		this.idRegistroItem = idRegistroItem;
	}
	public String getCdStatus() {
		return cdStatus;
	}
	public void setCdStatus(String cdStatus) {
		this.cdStatus = cdStatus;
	}
	public String getCdValidacao() {
		return cdValidacao;
	}
	public void setCdValidacao(String cdValidacao) {
		this.cdValidacao = cdValidacao;
	}
	public Timestamp getTsRegistroGmt() {
		return tsRegistroGmt;
	}
	public void setTsRegistroGmt(Timestamp tsRegistroGmt) {
		this.tsRegistroGmt = tsRegistroGmt;
	}
	public String getTxMatadadoXml() {
		return txMatadadoXml;
	}
	public void setTxMatadadoXml(String txMatadadoXml) {
		this.txMatadadoXml = txMatadadoXml;
	}
	
	

}
