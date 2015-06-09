package br.gov.go.camarajatai.olerite;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.ServletOutputStream;

import br.gov.go.camarajatai.olerite.dto.Descontos;
import br.gov.go.camarajatai.olerite.dto.Funcionario;
import br.gov.go.camarajatai.olerite.dto.Liquidacao;
import br.gov.go.camarajatai.olerite.dto.Proventos;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

public class OleriteController {

	public static SortedMap<String, ArrayList<Funcionario>> folpags = new TreeMap<String, ArrayList<Funcionario>>();
	public static SortedMap<String, Long> vLoadFiles = new TreeMap<String, Long>();

	private static String chaveForUpdate = "";




	public static ArrayList<Funcionario> getFolha(String ano, String mes, String pathFile, boolean prov_expandir, ServletOutputStream out) throws Exception {

		String _chave = "/Olerite"+mes+ano+".Txt";

		File file = null;
		long dateFile = 0;

		file = new File(pathFile+_chave);
		dateFile = file.lastModified();

		if (dateFile == 0)
			return null;

		synchronized (vLoadFiles) {

			synchronized (folpags) {

				Long testValidFile = vLoadFiles.get(_chave);

				//if (testValidFile == null || testValidFile.longValue() > dateFile) {
					vLoadFiles.put(_chave, new Long(dateFile));
					folpags.put(_chave, loadFile(file,_chave, prov_expandir, out));
				//}	
			}		
		}		
		return folpags.get(_chave);
	}

	private static ArrayList<Funcionario> loadFile(File file, String _chave, boolean prov_expandir, ServletOutputStream out) throws Exception {

		DataInputStream olerite =null;

		FileInputStream fIn = new FileInputStream(file);
		olerite = new DataInputStream(fIn);	

		String linha;

		ArrayList<String> linhas = new ArrayList<String>(); 
		Iterator<String> l = null;

		while ((linha = olerite.readLine())!= null)
			linhas.add(linha);

		l=linhas.iterator();
		ArrayList<String> listadecampos;
		int tiporeg;
		//Variaveis Tipo 1
		String cpf;
		String matricula;
		String nome;
		String matIpasgo;
		String tipoPagamento;
		String secretaria;
		String setor;
		String agenciaBancaria;
		String digtVerifAgBancaria;
		String cc;
		String digtVerifConta;
		String cargo;
		String tipoCargo;
		String tipoAdmissao;
		String salarioBase;
		String bcPrevidencia;
		String bcIR;
		String faixaAplAliquota;
		String senha;

		//Variaveis tipo 2
		String descricaoVlrPositivo;
		String referenciaVlrPositivo;
		String valorPositivo;

		//Variaveis tipo 3
		String descricaoVlrNegativo;
		String referenciaVlrNegativo;
		String valorNegativo;

		//Variaveis tipo 4
		String mensagem;
		String descricaoDeAfastamento;
		String valorBruto;
		String totalDesconto;
		String valorLiquido;

		Funcionario f=null;
		Proventos p=null;
		Liquidacao lq=null;
		Descontos d=null;

		ArrayList<Funcionario> listafuncionario=new ArrayList<Funcionario>();

		while(l.hasNext()){

			linha= l.next();


			if (linha.length() != 393)
				linha = new String(linha.getBytes("ISO-8859-1"),0,linha.getBytes("ISO-8859-1").length,"UTF-8");

			//System.out.println(linha.length());
			//out.println(linha);
			//linha = new String(linha.getBytes("ISO-8859-1"),0,linha.getBytes("ISO-8859-1").length,"UTF-8");

			//out.println(linha);

			tiporeg=Integer.parseInt(linha.substring(0,1));
			linha=linha.substring(1);

			if(tiporeg==1){

				f=new Funcionario();
				listafuncionario.add(f);//adiciona na lista

				cpf=linha.substring(0,11);
				f.setCpf(cpf);
				linha=linha.substring(11);

				matricula=linha.substring(0,6);
				f.setMatricula(Integer.parseInt(matricula));
				linha=linha.substring(6);

				nome=linha.substring(0,60);
				f.setNome(nome);
				linha=linha.substring(60);

				matIpasgo=linha.substring(0,10);
				f.setMatIpasgo(matIpasgo);
				linha=linha.substring(10);

				tipoPagamento=linha.substring(0,11);
				f.setTipoPagamento(tipoPagamento);
				linha=linha.substring(11);

				secretaria=linha.substring(0,60);
				f.setSecretaria(secretaria);
				linha=linha.substring(60);

				setor=linha.substring(0,60);
				f.setSetor(setor);
				linha=linha.substring(60);

				agenciaBancaria=linha.substring(0,5);
				f.setAgenciaBancaria(agenciaBancaria);
				linha=linha.substring(5);

				digtVerifAgBancaria=linha.substring(0,1);
				f.setDigitoVerifAgencia(digtVerifAgBancaria);
				linha=linha.substring(1);

				cc=linha.substring(0,10);
				f.setCc(cc);
				linha=linha.substring(10);

				digtVerifConta=linha.substring(0,1);
				f.setDigitoVerifConta(digtVerifConta);
				linha=linha.substring(1);

				cargo=linha.substring(0,60);
				f.setCargo(cargo);
				linha=linha.substring(60);

				tipoCargo=linha.substring(0,35);
				f.setTipoCargo(tipoCargo);
				linha=linha.substring(35);

				tipoAdmissao=linha.substring(0,15);
				f.setTipoAdmissao(tipoAdmissao);
				linha=linha.substring(15);

				salarioBase=linha.substring(0,10);
				f.setSalarioBase(salarioBase);
				linha=linha.substring(10);

				bcPrevidencia=linha.substring(0,10);
				f.setBaseCalcPrev(bcPrevidencia);
				linha=linha.substring(10);

				bcIR=linha.substring(0,10);
				f.setBaseCalcIR(bcIR);
				linha=linha.substring(10);

				faixaAplAliquota=linha.substring(0,10);
				f.setFaixaApliAliqIR(faixaAplAliquota);
				linha=linha.substring(10);

				senha=linha.substring(0,6);
				f.setSenha(senha);

			}
			if(tiporeg==2){

				p=new Proventos();
				f.getProventos().add(p);

				descricaoVlrPositivo=linha.substring(0,63);
				p.setDescricao(descricaoVlrPositivo);
				linha=linha.substring(63);

				referenciaVlrPositivo=linha.substring(0,18);
				p.setReferencia(referenciaVlrPositivo);
				linha=linha.substring(18);

				valorPositivo=linha.substring(0,10);
				p.setValor(valorPositivo);

			}

			if(tiporeg==3){

				d=new Descontos();
				f.getDescontos().add(d);


				descricaoVlrNegativo=linha.substring(0,63);
				d.setDescricao(descricaoVlrNegativo);
				linha=linha.substring(63);

				referenciaVlrNegativo=linha.substring(0,18);
				d.setReferencia(referenciaVlrNegativo);
				linha=linha.substring(18);

				valorNegativo=linha.substring(0,10);
				d.setValor(valorNegativo);


			}
			if(tiporeg==4){

				lq=new Liquidacao();
				f.getLiquidacao().add(lq);

				mensagem=linha.substring(0,120);
				lq.setMensagem(mensagem);
				linha=linha.substring(120);

				descricaoDeAfastamento=linha.substring(0,60);
				lq.setDescricaoAfastamento(descricaoDeAfastamento);
				linha=linha.substring(60);

				valorBruto=linha.substring(0,10);
				lq.setValorBruto(valorBruto);
				linha=linha.substring(10);

				totalDesconto=linha.substring(0,10);
				lq.setTotalDesconto(totalDesconto);
				linha=linha.substring(10);

				valorLiquido=linha.substring(0,10);
				lq.setValorLiquido(valorLiquido);

			}

		}

		Comparator comp = new Comparator<Funcionario>() {

			public int compare(Funcionario o1, Funcionario o2) {

				/*int testComp = o1.getProventos().get(0).getReferencia().compareTo(o2.getProventos().get(0).getReferencia());

				if (testComp == 0) {					
					testComp = o1.getCargo().compareTo(o2.getCargo());
				}
				if (testComp == 0) {					
					testComp = o1.getNome().compareTo(o2.getNome());
				}
				return testComp;*/
				return o1.getNome().compareTo(o2.getNome());
				//return o2.getLiquidacao().get(0).getValorBruto().compareTo(o1.getLiquidacao().get(0).getValorBruto());

			}

		};


		Collections.sort(listafuncionario, comp);

		organizarProventosDescontos(listafuncionario, prov_expandir);

		return listafuncionario;

	}




	private static void organizarProventosDescontos(
			ArrayList<Funcionario> lFunc, boolean prov_expandir) throws Exception {


		Iterator<Funcionario> it=lFunc.iterator();
		Funcionario f;

		while (it.hasNext()) {

			f = it.next();


			Iterator<Proventos> itprov= f.getProventos().iterator();
			Iterator<Descontos> itdesc= f.getDescontos().iterator();


			ArrayList<Proventos> proventos=new ArrayList<Proventos>();
			ArrayList<Descontos> descontos=new ArrayList<Descontos>();

			Proventos pNew = null;


			Proventos pOld;
			Descontos dOld;

			if (!prov_expandir)
				if (itprov.hasNext()) {
					pNew = itprov.next();
					itprov.remove();
				}

			/*if (f.getNome().indexOf("LEANDRO") != -1) {
				int i = 0;
				i++;
			}*/
				
				
				
			while (itprov.hasNext()) {

				pOld = itprov.next();
				itprov.remove();

				if (!prov_expandir)
					pNew.setValor(   Suport.roundToString(   Suport.strToFloat(pNew.getValor().trim()) + Suport.strToFloat(pOld.getValor().trim())   ,10,2)   );
				else 
					proventos.add(pOld);

			}

			if (!prov_expandir)
				proventos.add(pNew);

			f.setProventos(proventos);

			Descontos dPessoais = new Descontos();
			dPessoais.setDescricao("Outros Descontos");
			dPessoais.setValor("0,00");
			dPessoais.setReferencia("");

			while (itdesc.hasNext()) {


				dOld = itdesc.next();
				itdesc.remove();


				if (dOld.getDescricao().indexOf("JATAIPREV") != -1 ||
						dOld.getDescricao().indexOf("Renda") != -1 ||
						dOld.getDescricao().indexOf("INSS") != -1) {
					
					dOld.setValor(   Suport.roundToString(   Suport.strToFloat(dOld.getValor().trim())   ,10,2)   );

					descontos.add(dOld);
					continue;
				}


				dPessoais.setValor(   Suport.roundToString(   Suport.strToFloat(dPessoais.getValor().trim()) + Suport.strToFloat(dOld.getValor().trim())   ,10,2)   );


			}
			
			// Libere esta linha para permitir mostrar outros descontos
			//descontos.add(dPessoais);
			
			
			Iterator<Liquidacao> itLiq = f.getLiquidacao().iterator();
			
			while (itLiq.hasNext()) {
				
				Liquidacao liq = itLiq.next();
				
						
				liq.setValorLiquido(   Suport.roundToString(   Suport.strToFloat(liq.getValorLiquido().trim()) + Suport.strToFloat(dPessoais.getValor().trim())   ,10,2)  );

			}
			
			
			f.setDescontos(descontos);

		}
	}
	
	
}
