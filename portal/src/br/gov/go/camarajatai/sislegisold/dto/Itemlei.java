package br.gov.go.camarajatai.sislegisold.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei;
import br.gov.go.camarajatai.sislegisold.suport.RomanNumber;

/**
 * The persistent class for the itemlei database table.
 *
 * AUMENTANDO ATRIBUTOS: 1) Inserir o Atributo; 2) Gets and Sets 3)
 * configureNivel() dentros sets 3.1) configureNivel() 4) getCodigo() 5)
 * setCodigo() 6) increment() 7) organize() 8) getNomeclatura() 9)
 * getNomeclaturaCompleta() 10) getNomeClaturaCompetaParaPesquisa()
 * 
 * 11) Arq CollectionItensDeLei 12) Arq ItemleiDAO 13) cadastro.jsp 14)
 * InsertionItemLeiFacade
 * 
 */
public class Itemlei extends Dto implements Serializable {

	public static final int STATE_ENABLED = 1;
	public static final int STATE_NOVALEI = 2;
	public static final int STATE_ALTERADO = 4;
	public static final int STATE_REVOGADO = 8;
	public static final int STATE_INCLUIDO = 16;
	public static final int STATE_INVISIBLE = 32;
	public static final int STATE_DISABLED = 64;
	public static final int STATE_ERRO = 128;

	private int id_alterador;
	private int id_lei;
	private int id_dono;

	private int articulacao;
	private int numero;
	private int anexo;
	private int parte;
	private int livro;
	private int titulo;
	private int capitulo;
	private int capitulovar;
	private int secao;
	private int secaovar;

	private int subsecao;
	private int itemsecao;
	private int artigo;
	private int artigovar;
	private int caput;
	private int paragrafo;
	private int inciso;
	private int incisovar;
	private int incisovarvar;
	private int alinea;
	private int item;

	private int subitem;
	private int subsubitem;

	private int ementa;

	private int nivel;



	private boolean revogado;
	private boolean alterado;
	private boolean incluido;

	private String link_alterador;

	private Timestamp data_inclusao;
	private Timestamp data_alteracao;

	private String texto;

	public Itemlei() {
	}

	private void configureNivel() {

		if (ementa != 0)
			nivel = 2000;
		if (subsubitem != 0)
			nivel = 1500;
		else if (subitem != 0)
			nivel = 1400;
		else if (item != 0)
			nivel = 1300;
		else if (alinea != 0)
			nivel = 1200;
		else if (incisovarvar != 0)
			nivel = 1175;
		else if (incisovar != 0)
			nivel = 1150;
		else if (inciso != 0)
			nivel = 1100;
		else if (paragrafo != 0)
			nivel = 1000;
		else if (caput != 0)
			nivel = 950;
		else if (artigovar != 0)
			nivel = 900;
		else if (artigo != 0)
			nivel = 800;
		else if (itemsecao != 0)
			nivel = 750;
		else if (subsecao != 0)
			nivel = 700;
		else if (secaovar != 0)
			nivel = 650;
		else if (secao != 0)
			nivel = 600;
		else if (capitulovar != 0)
			nivel = 550;
		else if (capitulo != 0)
			nivel = 500;
		else if (titulo != 0)
			nivel = 400;
		else if (parte != 0)
			nivel = 300;
		else if (livro != 0)
			nivel = 200;
		else if (anexo != 0)
			nivel = 150;
		else if (articulacao != 0)
			nivel = 50;

	}

	public Object getCodigo() {

		switch (nivel) {
		case 50:
			return articulacao;
		case 100:
			return numero;
		case 150:
			return anexo;
		case 200:
			return livro;
		case 300:
			return parte;
		case 400:
			return titulo;
		case 500:
			return capitulo;
		case 550:
			return capitulovar;
		case 600:
			return secao;
		case 650:
			return secaovar;
		case 700:
			return subsecao;
		case 750:
			return itemsecao;
		case 800:
			return artigo;
		case 900:
			return artigovar;
		case 950:
			return caput;
		case 1000:
			return paragrafo;
		case 1001:
			return paragrafo;
		case 1100:
			return inciso;
		case 1150:
			return incisovar;
		case 1175:
			return incisovarvar;
		case 1200:
			return alinea;
		case 1300:
			return item;
		case 1400:
			return subitem;
		case 1500:
			return subsubitem;
		case 2000:
			return ementa;
		default:
			return item;
		}
	}

	public int getTipoSapl() {

		switch (nivel) {
		case 50:
			return 1;
		case 100:
			return 1;
		case 150:
			return 11;
		case 200:
			return 13;
		case 300:
			return 12;
		case 400:
			return 14;
		case 500:
			return 15;
		case 550:
			return 15;
		case 600:
			return 16;
		case 650:
			return 16;
		case 700:
			return 17;
		case 750:
			return 18;
		case 800:
			return 19;
		case 900:
			return 19;
		case 950:
			return 20;
		case 1000:
			return 21;
		case 1001:
			return 21;
		case 1100:
			return 22;
		case 1150:
			return 22;
		case 1175:
			return 22;
		case 1200:
			return 23;
		case 1300:
			return 24;
		case 1400:
			return 24;
		case 1500:
			return 24;
		case 2000:
			return 2;
		default:
			return 6;
		}
	}

	public void setCodigo(int codItem) {

		switch (nivel) {
		case 50:
			articulacao = codItem;
			break;
		case 100:
			numero = codItem;
			break;
		case 150:
			anexo = codItem;
			break;
		case 200:
			livro = codItem;
			break;
		case 300:
			parte = codItem;
			break;
		case 400:
			titulo = codItem;
			break;
		case 500:
			capitulo = codItem;
			break;
		case 550:
			capitulovar = codItem;
			break;
		case 600:
			secao = codItem;
			break;
		case 650:
			secaovar = codItem;
			break;
		case 700:
			subsecao = codItem;
			break;
		case 750:
			itemsecao = codItem;
			break;
		case 800:
			artigo = codItem;
			break;
		case 900:
			artigovar = codItem;
			break;
		case 950:
			caput = codItem;
			break;
		case 1000:
			paragrafo = codItem;
			break;
		case 1001:
			paragrafo = codItem;
			break;
		case 1100:
			inciso = codItem;
			break;
		case 1150:
			incisovar = codItem;
			break;
		case 1175:
			incisovarvar = codItem;
			break;
		case 1200:
			alinea = codItem;
			break;
		case 1300:
			item = codItem;
			break;
		case 1400:
			subitem = codItem;
			break;
		case 1500:
			subsubitem = codItem;
		case 2000:
			ementa = codItem;
			break;
		default:
			;
		}
	}

	public void increment() {
		organize();
		switch (nivel) {
		case 50:
			articulacao++;
			break;
		case 100:
			numero++;
			break;
		case 150:
			anexo++;
			break;
		case 200:
			livro++;
			break;
		case 300:
			parte++;
			break;
		case 400:
			titulo++;
			break;
		case 500:
			capitulo++;
			break;
		case 550:
			capitulovar++;
			break;
		case 600:
			secao++;
			break;
		case 650:
			secaovar++;
			break;
		case 700:
			subsecao++;
			break;
		case 750:
			itemsecao++;
			break;
		case 800:
			artigo++;
			break;
		case 900:
			artigovar++;
			break;
		case 950:
			caput++;
			break;
		case 1000:
			paragrafo++;
			break;
		case 1001:
			paragrafo = 999999;
			break;
		case 1100:
			inciso++;
			break;
		case 1150:
			incisovar++;
			break;
		case 1175:
			incisovarvar++;
			break;
		case 1200:
			alinea++;
			break;
		case 1300:
			item++;
			break;
		case 1400:
			subitem++;
			break;
		case 1500:
			subsubitem++;
		case 2000:
			ementa++;
			break;
		default:
			;
		}
	}

	public void organize() {

		if (nivel <= 1500)
			ementa = 0;

		if (nivel <= 1400)
			subsubitem = 0;

		if (nivel <= 1300)
			subitem = 0;

		if (nivel <= 1200)
			item = 0;

		if (nivel <= 1175)
			alinea = 0;

		if (nivel <= 1150)
			incisovarvar = 0;

		if (nivel <= 1100)
			incisovar = 0;

		if (nivel == 1001) {
			paragrafo = 0;
			inciso = 0;
		}
		if (nivel <= 1000)
			inciso = 0;

		if (nivel <= 950)
			paragrafo = 0;

		if (nivel <= 900)
			caput = 0;

		if (nivel <= 800)
			artigovar = 0;

		if (nivel <= 750) {
			artigo = 0;
		}
		if (nivel <= 700) {
			itemsecao = 0;
		}
		if (nivel <= 650)
			subsecao = 0;
		if (nivel <= 600)
			secaovar = 0;
		if (nivel <= 550)
			secao = 0;
		if (nivel <= 500)
			capitulovar = 0;
		if (nivel <= 400)
			capitulo = 0;
		if (nivel <= 300)
			titulo = 0;
		if (nivel <= 200)
			parte = 0;
		if (nivel <= 150)
			livro = 0;
		if (nivel <= 100)
			anexo = 0; 

	}

	public String getNomeclatura() {

		switch (nivel) {
		case 50:
			return "";
		case 100:
			return "";
		case 150:
			return "";// "ANEXO "+RomanNumber.getRoman(anexo)+"<br>";
		case 200:
			return "LIVRO " + livro + "<br>";
		case 300:
			return "PARTE " + parte + "<br>";
		case 400:
			return titulo == 0 ? "" : "<br><br>TÍTULO " + RomanNumber.getRoman(titulo) + "<br>";
		case 500:
			return capitulo == 0 ? "" : "<br><br>CAPÍTULO " + RomanNumber.getRoman(capitulo) + "<br>";
		case 550:
			return capitulovar == 0 ? "" : "<br><br>CAPÍTULO " + RomanNumber.getRoman(capitulo)
					+ " - "
					+ String.valueOf((char) (capitulovar + 64))
					+ "<br>";
		case 600:
			return secao == 0 ? "" : "<br>SEÇÃO " + RomanNumber.getRoman(secao) + "<br>";
		case 650:
			return secaovar == 0 ? "" : "<br>SEÇÃO " + RomanNumber.getRoman(secao) + " - " + String.valueOf((char) (secaovar + 64)) + "<br>";
		case 700:
			return subsecao == 0 ? "" : "<br>SUBSEÇÃO " + RomanNumber.getRoman(subsecao) + "<br>";
		case 750:
			return itemsecao == 0 ? "" : "<br>Item " + RomanNumber.getRoman(itemsecao) + "<br>";
		case 800:
			return "Art. " + artigo + (artigo < 10 ? "º" : ".") + " - ";
		case 900:
			return "Art. " + artigo
					+ (artigo < 10 ? "º-" + String.valueOf((char) (artigovar + 64)) : "-" + String.valueOf((char) (artigovar + 64)) + ".")
					+ " - ";
		case 1000:
			return "§ " + paragrafo + (paragrafo < 10 ? "º" : ".") + " - ";
		case 1001:
			return "Parágrafo Único - ";
		case 1100:
			return (inciso == 0 ? "" : RomanNumber.getRoman(inciso) + " - ");
		case 1150:
			return (inciso == 0 ? "" : RomanNumber.getRoman(inciso) + "." + incisovar + " - ");
		case 1175:
			return (inciso == 0 ? "" : RomanNumber.getRoman(inciso) + "." + incisovar + "." + incisovarvar + " - ");
		case 1200:
			return (alinea == 0 ? "" : CollectionItensDeLei.alinea[alinea - 1] + ") ");
		case 1300:
			return item + " - ";
		case 1400:
			return item + "." + subitem + " - ";
		case 1500:
			return item + "." + subitem + "." + subsubitem + " - ";
		case 2000:
			return "";
		default:
			return "";
		}
	}

	public String getNomeclaturaToSapl() {

		switch (nivel) {
		case 50:
			return "";
		case 100:
			return "";
		case 150:
			return "";// "ANEXO "+RomanNumber.getRoman(anexo)+"<br>";
		case 200:
			return "LIVRO " + livro;
		case 300:
			return "PARTE " + parte;
		case 400:
			return titulo == 0 ? "" : "TÍTULO " + RomanNumber.getRoman(titulo);
		case 500:
			return capitulo == 0 ? "" : "CAPÍTULO " + RomanNumber.getRoman(capitulo);
		case 550:
			return capitulovar == 0 ? "" : "CAPÍTULO " + RomanNumber.getRoman(capitulo) + " - " + String.valueOf((char) (capitulovar + 64));
		case 600:
			return secao == 0 ? "" : "SEÇÃO " + RomanNumber.getRoman(secao);
		case 650:
			return secaovar == 0 ? "" : "SEÇÃO " + RomanNumber.getRoman(secao) + " - " + String.valueOf((char) (secaovar + 64));
		case 700:
			return subsecao == 0 ? "" : "SUBSEÇÃO " + RomanNumber.getRoman(subsecao);
		case 750:
			return itemsecao == 0 ? "" : "Item " + RomanNumber.getRoman(itemsecao);
		case 800:
			return "Art. " + artigo + (artigo < 10 ? "º" : ".");
		case 900:
			return "Art. " + artigo + (artigo < 10 ? "º-" + String.valueOf((char) (artigovar + 64)) : "-" + String.valueOf((char) (artigovar + 64)) + ".");
		case 950:
			return "";
		case 1000:
			return "§ " + paragrafo + (paragrafo < 10 ? "º" : ".");
		case 1001:
			return "Parágrafo Único";
		case 1100:
			return (inciso == 0 ? "" : RomanNumber.getRoman(inciso));
		case 1150:
			return (inciso == 0 ? "" : RomanNumber.getRoman(inciso) + "." + incisovar);
		case 1175:
			return (inciso == 0 ? "" : RomanNumber.getRoman(inciso) + "." + incisovar + "." + incisovarvar);
		case 1200:
			return (alinea == 0 ? "" : CollectionItensDeLei.alinea[alinea - 1] + ")");
		case 1300:
			return item + "";
		case 1400:
			return item + "." + subitem;
		case 1500:
			return item + "." + subitem + "." + subsubitem;		
		case 2000:
			return "";
		default:
			return item + "";
		}
	}

	public String getNomeclaturaCompleta() {
		String nomeclatura = "";

		if (ementa != 0)
			nomeclatura = ementa + nomeclatura;
		
		if (subsubitem != 0)
			nomeclatura = subsubitem + nomeclatura;

		if (subitem != 0 || nomeclatura.length() > 0)
			nomeclatura = subitem + nomeclatura;

		if (item != 0 || nomeclatura.length() > 0)
			nomeclatura = "&nbsp;&nbsp;&nbsp;" + String.valueOf(item) + nomeclatura;
		if (alinea != 0)
			nomeclatura = (alinea == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + CollectionItensDeLei.alinea[alinea - 1] + ") ") + nomeclatura;

		if (incisovarvar != 0)
			nomeclatura = (inciso == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + RomanNumber.getRoman(inciso)) + "." + incisovar + "." + incisovarvar + nomeclatura;
		if (incisovar != 0)
			nomeclatura = (inciso == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + RomanNumber.getRoman(inciso)) + "." + incisovar + nomeclatura;
		if (inciso != 0)
			nomeclatura = (inciso == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + RomanNumber.getRoman(inciso)) + nomeclatura;

		if (paragrafo != 0 && nivel == 1001)
			nomeclatura = "&nbsp;&nbsp;&nbsp;" + "Parágrafo Único" + nomeclatura;
		if (paragrafo != 0 && nivel == 1000)
			nomeclatura = "&nbsp;&nbsp;&nbsp;" + "§ " + paragrafo + (paragrafo < 10 ? "º" : ".") + nomeclatura;

		if (artigovar != 0)
			nomeclatura = "&nbsp;&nbsp;&nbsp;" + "Art. "
					+ artigo
					+ (artigo < 10 ? "º-" + String.valueOf((char) (artigovar + 64)) : "-" + String.valueOf((char) (artigovar + 64)) + ".")
					+ nomeclatura;
		if (artigo != 0)
			nomeclatura = "&nbsp;&nbsp;&nbsp;" + "Art. " + artigo + (artigo < 10 ? "º" : ".") + nomeclatura;
		if (itemsecao != 0)
			nomeclatura = itemsecao == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + "Item " + RomanNumber.getRoman(itemsecao) + nomeclatura;
		if (subsecao != 0)
			nomeclatura = subsecao == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + "SUBSEÇÃO " + RomanNumber.getRoman(subsecao) + nomeclatura;
		if (secaovar != 0)
			nomeclatura = secaovar == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + "SEÇÃO "
					+ RomanNumber.getRoman(secao)
					+ "-"
					+ String.valueOf((char) (secaovar + 64))
					+ nomeclatura;
		if (secao != 0)
			nomeclatura = secao == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + "SEÇÃO " + RomanNumber.getRoman(secao) + nomeclatura;
		if (capitulovar != 0)
			nomeclatura = capitulovar == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + "CAPÍTULO "
					+ RomanNumber.getRoman(capitulo)
					+ "-"
					+ String.valueOf((char) (capitulovar + 64))
					+ nomeclatura;
		if (capitulo != 0)
			nomeclatura = capitulo == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + "CAPÍTULO " + RomanNumber.getRoman(capitulo) + nomeclatura;
		if (titulo != 0)
			nomeclatura = titulo == 0 ? "" : "&nbsp;&nbsp;&nbsp;" + "TÍTULO " + RomanNumber.getRoman(titulo) + nomeclatura;
		if (parte != 0)
			nomeclatura = "&nbsp;&nbsp;&nbsp;" + "PARTE " + parte + nomeclatura;
		if (livro != 0)
			nomeclatura = "&nbsp;&nbsp;&nbsp;" + "LIVRO " + livro + nomeclatura;
		if (anexo != 0)
			nomeclatura = "";// "&nbsp;&nbsp;&nbsp;"+"ANEXO "+anexo+nomeclatura;
		return nomeclatura;
	}

	public String getNomeclaturaCompletaParaPesquisa() {
		String nomeclatura = "";

		if (ementa != 0)
			nomeclatura = ementa + nomeclatura;
		
		if (subsubitem != 0)
			nomeclatura = subsubitem + nomeclatura;

		if (subitem != 0 || nomeclatura.length() > 0)
			nomeclatura = subitem + nomeclatura;
		if (item != 0 || nomeclatura.length() > 0)
			nomeclatura = String.valueOf(item) + nomeclatura;
		if (alinea != 0)
			nomeclatura = (alinea == 0 ? "" : " " + CollectionItensDeLei.alinea[alinea - 1] + ")") + nomeclatura;

		if (incisovarvar != 0)
			nomeclatura = (inciso == 0 ? "" : RomanNumber.getRoman(inciso)) + "." + incisovar + "." + incisovarvar + nomeclatura;
		if (incisovar != 0)
			nomeclatura = (inciso == 0 ? "" : RomanNumber.getRoman(inciso)) + "." + incisovar + nomeclatura;
		if (inciso != 0)
			nomeclatura = (inciso == 0 ? "" : RomanNumber.getRoman(inciso)) + nomeclatura;

		if (paragrafo != 0 && nivel == 1001)
			nomeclatura = " Parágrafo Único" + nomeclatura;
		if (paragrafo != 0 && nivel == 1000)
			nomeclatura = " § " + paragrafo + (paragrafo < 10 ? "º" : ".") + nomeclatura;

		if (artigovar != 0)
			nomeclatura = " Art. " + artigo
			+ (artigo < 10 ? "º-" + String.valueOf((char) (artigovar + 64)) : "-" + String.valueOf((char) (artigovar + 64)) + ".")
			+ nomeclatura;
		if (artigo != 0)
			nomeclatura = " Art. " + artigo + (artigo < 10 ? "º" : ".") + nomeclatura;
		if (itemsecao != 0)
			nomeclatura = itemsecao == 0 ? "" : " ItemSeção " + RomanNumber.getRoman(itemsecao) + nomeclatura;
		if (subsecao != 0)
			nomeclatura = subsecao == 0 ? "" : " subseção " + RomanNumber.getRoman(subsecao) + nomeclatura;
		if (secaovar != 0)
			nomeclatura = secaovar == 0 ? "" : " seção " + RomanNumber.getRoman(secao) + "-" + String.valueOf((char) (secaovar + 64)) + nomeclatura;
		if (secao != 0)
			nomeclatura = secao == 0 ? "" : " seção " + RomanNumber.getRoman(secao) + nomeclatura;
		if (capitulovar != 0)
			nomeclatura = capitulovar == 0 ? "" : " CAPÍTULO " + RomanNumber.getRoman(capitulo) + "-" + String.valueOf((char) (capitulovar + 64)) + nomeclatura;
		if (capitulo != 0)
			nomeclatura = capitulo == 0 ? "" : " capítulo " + RomanNumber.getRoman(capitulo) + nomeclatura;
		if (titulo != 0)
			nomeclatura = titulo == 0 ? "" : " título " + RomanNumber.getRoman(titulo) + nomeclatura;
		if (parte != 0)
			nomeclatura = " parte " + parte + nomeclatura;
		if (livro != 0)
			nomeclatura = " livro " + livro + nomeclatura;
		if (anexo != 0)
			nomeclatura = "";// " anexo "+anexo+nomeclatura;
		return nomeclatura;
	}

	public static Itemlei clone(Itemlei item) {
		Itemlei itemlei = new Itemlei();

		itemlei.setId(item.getId());
		itemlei.setId_alterador(item.getId_alterador());
		itemlei.setId_lei(item.getId_lei());
		itemlei.setId_dono(item.getId_dono());
		itemlei.setArticulacao(item.getArticulacao());
		itemlei.setNumero(item.getNumero());
		itemlei.setAnexo(item.getAnexo());
		itemlei.setParte(item.getParte());
		itemlei.setLivro(item.getLivro());
		itemlei.setTitulo(item.getTitulo());
		itemlei.setCapitulo(item.getCapitulo());
		itemlei.setCapitulovar(item.getCapitulovar());
		itemlei.setSecao(item.getSecao());
		itemlei.setSecaovar(item.getSecaovar());
		itemlei.setSubsecao(item.getSubsecao());
		itemlei.setItemsecao(item.getItemsecao());
		itemlei.setArtigo(item.getArtigo());
		itemlei.setArtigovar(item.getArtigovar());
		itemlei.setCaput(item.getCaput());
		itemlei.setParagrafo(item.getParagrafo());
		itemlei.setInciso(item.getInciso());
		itemlei.setIncisovar(item.getIncisovar());
		itemlei.setIncisovarvar(item.getIncisovarvar());
		itemlei.setAlinea(item.getAlinea());
		itemlei.setItem(item.getItem());
		itemlei.setSubitem(item.getSubitem());
		itemlei.setSubsubitem(item.getSubsubitem());

		itemlei.setNivel(item.getNivel());
		itemlei.setEmenta(item.getEmenta());

		itemlei.setRevogado(item.getRevogado());
		itemlei.setAlterado(item.getAlterado());
		itemlei.setIncluido(item.getIncluido());

		itemlei.setLink_alterador(item.getLink_alterador());

		itemlei.setData_inclusao(item.getData_inclusao());
		itemlei.setData_alteracao(item.getData_alteracao());

		itemlei.setTexto(item.getTexto());

		return itemlei;

	}

	public int compare(Dto o) {
		Itemlei ob = (Itemlei) o;

		if (anexo > ob.anexo)
			return 1;
		else if (anexo < ob.anexo)
			return -1;

		else if (livro > ob.livro)
			return 1;
		else if (livro < ob.livro)
			return -1;

		else if (parte > ob.parte)
			return 1;
		else if (parte < ob.parte)
			return -1;

		else if (titulo > ob.titulo)
			return 1;
		else if (titulo < ob.titulo)
			return -1;

		else if (capitulo > ob.capitulo)
			return 1;
		else if (capitulo < ob.capitulo)
			return -1;

		else if (capitulovar > ob.capitulovar)
			return 1;
		else if (capitulovar < ob.capitulovar)
			return -1;

		else if (secao > ob.secao)
			return 1;
		else if (secao < ob.secao)
			return -1;

		else if (secaovar > ob.secaovar)
			return 1;
		else if (secaovar < ob.secaovar)
			return -1;

		else if (subsecao > ob.subsecao)
			return 1;
		else if (subsecao < ob.subsecao)
			return -1;

		else if (itemsecao > ob.itemsecao)
			return 1;
		else if (itemsecao < ob.itemsecao)
			return -1;

		else if (artigo > ob.artigo)
			return 1;
		else if (artigo < ob.artigo)
			return -1;

		else if (artigovar > ob.artigovar)
			return 1;
		else if (artigovar < ob.artigovar)
			return -1;

		else if (caput > ob.caput)
			return 1;
		else if (caput < ob.caput)
			return -1;

		else if (paragrafo > ob.paragrafo)
			return 1;
		else if (paragrafo < ob.paragrafo)
			return -1;

		else if (inciso > ob.inciso)
			return 1;
		else if (inciso < ob.inciso)
			return -1;

		else if (incisovar > ob.incisovar)
			return 1;
		else if (incisovar < ob.incisovar)
			return -1;

		else if (inciso > ob.inciso)
			return 1;
		else if (inciso < ob.inciso)
			return -1;

		else if (alinea > ob.alinea)
			return 1;
		else if (alinea < ob.alinea)
			return -1;

		else if (item > ob.item)
			return 1;
		else if (item < ob.item)
			return -1;

		else if (subitem > ob.subitem)
			return 1;
		else if (subitem < ob.subitem)
			return -1;

		else if (subsubitem > ob.subsubitem)
			return 1;
		else if (subsubitem < ob.subsubitem)
			return -1;
		
		else if (ementa > ob.ementa)
			return 1;
		else if (ementa < ob.ementa)
			return -1;

		return 0;

	}

	public int getStateRefLei(Documento lei) {

		int resultado = 0;

		// resultado |= data_inclusao.equals(lei.getData_lei()) ? 0:
		// STATE_NOVALEI;

		resultado |= id_lei == lei.getId() ? 0 : STATE_NOVALEI;

		resultado |= alterado ? STATE_ALTERADO : 0;
		resultado |= revogado ? STATE_REVOGADO : 0;
		resultado |= incluido ? STATE_INCLUIDO : 0;

		if (resultado == 0 && id_dono != 0 && link_alterador == null) {
			resultado |= STATE_INVISIBLE;
		}

		return resultado;

	}

	public boolean getAlterado() {
		return this.alterado;
	}

	public void setAlterado(boolean alterado) {
		this.alterado = alterado;
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

	public void setId(int id) {
		this.id = id;
	}

	public int getId_alterador() {
		return this.id_alterador;
	}

	public void setId_alterador(int id_alterador) {
		this.id_alterador = id_alterador;
	}

	public boolean getIncluido() {
		return this.incluido;
	}

	public void setIncluido(boolean incluido) {
		this.incluido = incluido;
	}

	public String getLink_alterador() {
		return this.link_alterador;
	}

	public void setLink_alterador(String link_alterador) {
		this.link_alterador = link_alterador;
	}

	public int getNivel() {
		return this.nivel;
	}


	public int getNivelToSapl() {
		if (nivel == 1000 || nivel == 1001)
			return 1000;
		else if (nivel == 800 || nivel == 900)
			return 800;
		else
			return this.nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public boolean getRevogado() {
		return this.revogado;
	}

	public void setRevogado(boolean revogado) {
		this.revogado = revogado;
	}

	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
		if (this.texto == null)
			this.texto = "";
	}

	@Override
	public String toString() {
		return getNomeclaturaCompletaParaPesquisa() + " - "+ texto;
	}

	public int getArticulacao() {
		return articulacao;
	}

	public void setArticulacao(int articulacao) {
		this.articulacao = articulacao;
	}

	public void setNumero(int numero) {
		this.numero = numero;
		configureNivel();
	}

	public int getNumero() {
		return numero;
	}

	public int getId_lei() {
		return id_lei;
	}

	public void setId_lei(int idLei) {
		id_lei = idLei;
	}

	public int getParte() {
		return parte;
	}

	public void setParte(int parte) {
		this.parte = parte;
		configureNivel();
	}

	public int getLivro() {
		return livro;
	}

	public void setLivro(int livro) {
		this.livro = livro;
		configureNivel();
	}

	public int getTitulo() {
		return titulo;
	}

	public void setTitulo(int titulo) {
		this.titulo = titulo;
		configureNivel();
	}

	public int getCapitulo() {
		return capitulo;
	}

	public void setCapitulo(int capitulo) {
		this.capitulo = capitulo;
		configureNivel();
	}

	public int getSecao() {
		return secao;
	}

	public void setSecao(int secao) {
		this.secao = secao;
		configureNivel();
	}

	public int getSubsecao() {
		return subsecao;
	}

	public void setSubsecao(int subsecao) {
		this.subsecao = subsecao;
		configureNivel();
	}

	public int getArtigo() {
		return artigo;
	}

	public void setArtigo(int artigo) {
		this.artigo = artigo;
		configureNivel();
	}

	public int getArtigovar() {
		return artigovar;
	}

	public void setArtigovar(int artigovar) {
		this.artigovar = artigovar;
		configureNivel();
	}

	public int getParagrafo() {
		return paragrafo;
	}

	public void setParagrafo(int paragrafo) {
		this.paragrafo = paragrafo;
		configureNivel();
	}

	public int getInciso() {
		return inciso;
	}

	public void setInciso(int inciso) {
		this.inciso = inciso;
		configureNivel();
	}

	public int getAlinea() {
		return alinea;
	}

	public void setAlinea(int alinea) {
		this.alinea = alinea;
		configureNivel();
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
		configureNivel();
	}

	public int getId_dono() {
		return id_dono;
	}

	public void setId_dono(int idDono) {
		id_dono = idDono;
	}

	public void setAnexo(int anexo) {
		this.anexo = anexo;
		configureNivel();

	}

	public int getAnexo() {
		return anexo;
	}

	public void setCapitulovar(int capitulovar) {
		this.capitulovar = capitulovar;
		configureNivel();

	}

	public int getCapitulovar() {
		return capitulovar;
	}

	public int getSecaovar() {
		return secaovar;
	}

	public void setSecaovar(int secaovar) {
		this.secaovar = secaovar;
		configureNivel();

	}

	public int getSubitem() {
		return subitem;
	}

	public void setSubitem(int subitem) {
		this.subitem = subitem;
		configureNivel();
	}

	public int getSubsubitem() {
		return subsubitem;
	}

	public void setSubsubitem(int subsubitem) {
		this.subsubitem = subsubitem;
		configureNivel();
	}

	public void setItemsecao(int itemsecao) {
		this.itemsecao = itemsecao;
	}

	public int getItemsecao() {
		return itemsecao;
	}

	public void setIncisovar(int incisovar) {
		this.incisovar = incisovar;
	}

	public int getIncisovar() {
		return incisovar;
	}

	public void setIncisovarvar(int incisovarvar) {
		this.incisovarvar = incisovarvar;
	}

	public int getIncisovarvar() {
		return incisovarvar;
	}
	//
	public int getVar(int i) {

		if (i == 0) {
			if (nivel == 550 || nivel == 500) 
				return capitulo;
			else if (nivel == 650 || nivel == 600)
				return secao;
			else if (nivel == 900 || nivel == 800)
				return artigo;
			else if (nivel == 1000 || nivel == 1001)
				return paragrafo;
			else if (nivel == 1150 || nivel == 1175 || nivel == 1100)
				return inciso;
			else if (nivel == 1500 || nivel == 1400 || nivel == 1300)
				return item;					
			return (new Integer(getCodigo().toString())).intValue();	
		}
		else if (i == 1) {
			if (nivel == 550) 
				return capitulovar;
			else if (nivel == 650)
				return secaovar;
			else if (nivel == 900)
				return artigovar; 
			else if (nivel == 1150 )
				return incisovar;
			else if ( nivel == 1400 )
				return subitem; 
			return 0;
		}
		else if (i == 2) {  
			if ( nivel == 1175 )
				return incisovarvar;
			else if ( nivel == 1500 )
				return subsubitem; 					
		}
		return 0;		

	}

	public int getCaput() {
		return caput;
	}

	public void setCaput(int caput) {
		this.caput = caput;
	}

	public int getEmenta() {
		return ementa;
	}

	public void setEmenta(int ementa) {
		this.ementa = ementa;
	}
}
