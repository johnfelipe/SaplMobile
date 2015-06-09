package br.gov.go.camarajatai.olerite.dto;

import java.util.ArrayList;
import java.util.Iterator;

import br.gov.go.camarajatai.sislegisold.suport.Suport;

public class Funcionario  {

	private String cpf;
	private int matricula;
	private String nome;
	private String matIpasgo;
	private String tipoPagamento;
	private String secretaria;
	private String setor;
	private String agenciaBancaria;
	private String digitoVerifAgencia;
	private String cc;
	private String digitoVerifConta;
	private String cargo;
	private String tipoCargo;
	private String tipoAdmissao;
	private String salarioBase;
	private String baseCalcPrev;
	private String baseCalcIR;
	private String faixaApliAliqIR;
	private String senha;
	private ArrayList<Proventos> proventos=new ArrayList<Proventos>();
	private ArrayList<Descontos> descontos=new ArrayList<Descontos>();
	private ArrayList<Liquidacao> liquidacao=new ArrayList<Liquidacao>();
	
	
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public int getMatricula() {
		return matricula;
	}
	public void setMatricula(int matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatIpasgo() {
		return matIpasgo;
	}
	public void setMatIpasgo(String matIpasgo) {
		this.matIpasgo = matIpasgo;
	}
	public String getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public String getSecretaria() {
		return secretaria;
	}
	public void setSecretaria(String secretaria) {
		this.secretaria = secretaria;
	}
	public String getSetor() {
		return setor;
	}
	public void setSetor(String setor) {
		this.setor = setor;
	}
	public String getAgenciaBancaria() {
		return agenciaBancaria;
	}
	public void setAgenciaBancaria(String agenciaBancaria) {
		this.agenciaBancaria = agenciaBancaria;
	}
	public String getDigitoVerifAgencia() {
		return digitoVerifAgencia;
	}
	public void setDigitoVerifAgencia(String digitoVerifAgencia) {
		this.digitoVerifAgencia = digitoVerifAgencia;
	}
	public String getCc() {
		return cc;
	}
	public void setCc(String cc) {
		this.cc = cc;
	}
	public String getDigitoVerifConta() {
		return digitoVerifConta;
	}
	public void setDigitoVerifConta(String digitoVerifConta) {
		this.digitoVerifConta = digitoVerifConta;
	}
	public String getCargo() {
		return cargo;
	}
	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	public String getTipoCargo() {
		return tipoCargo;
	}
	public void setTipoCargo(String tipoCargo) {
		this.tipoCargo = tipoCargo;
	}
	public String getTipoAdmissao() {
		return tipoAdmissao;
	}
	public void setTipoAdmissao(String tipoAdmissao) {
		this.tipoAdmissao = tipoAdmissao;
	}
	public String getSalarioBase() {
		return salarioBase;
	}
	public void setSalarioBase(String salarioBase) {
		this.salarioBase = salarioBase;
	}
	public String getBaseCalcPrev() {
		return baseCalcPrev;
	}
	public void setBaseCalcPrev(String baseCalcPrev) {
		this.baseCalcPrev = baseCalcPrev;
	}
	public String getBaseCalcIR() {
		return baseCalcIR;
	}
	public void setBaseCalcIR(String baseCalcIR) {
		this.baseCalcIR = baseCalcIR;
	}
	public String getFaixaApliAliqIR() {
		return faixaApliAliqIR;
	}
	public void setFaixaApliAliqIR(String faixaApliAliqIR) {
		this.faixaApliAliqIR = faixaApliAliqIR;
	}
	public ArrayList<Proventos> getProventos() {
		return proventos;
	}
	public void setProventos(ArrayList<Proventos> proventos) {
		this.proventos = proventos;
	}
	public ArrayList<Descontos> getDescontos() {
		return descontos;
	}
	public void setDescontos(ArrayList<Descontos> descontos) {
		this.descontos = descontos;
	}
	public ArrayList<Liquidacao> getLiquidacao() {
		return liquidacao;
	}
	public void setLiquidacao(ArrayList<Liquidacao> liquidacao) {
		this.liquidacao = liquidacao;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSenha() {
		return senha;
	}
	
	public Descontos getDesconto(String buscar) {
		
		if (descontos == null)
			return null;
		
		Iterator<Descontos> itdesc = descontos.iterator();
		
		Descontos desc = null;
		
		while (itdesc.hasNext()) {
			desc = itdesc.next();
			if (desc.getDescricao().indexOf(buscar) != -1) {
				return desc;
			}
		}
		
		return null;
		
	}
		
}
