package br.gov.go.camarajatai.sislegisold.business;

import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.gov.go.camarajatai.sislegisold.dao.ArquivoDAO;
import br.gov.go.camarajatai.sislegisold.dao.DocDestaqueDAO;
import br.gov.go.camarajatai.sislegisold.dao.ItemleiDAO;
import br.gov.go.camarajatai.sislegisold.dao.LeiDAO;
import br.gov.go.camarajatai.sislegisold.dao.TipodocDAO;
import br.gov.go.camarajatai.sislegisold.dao.TipoleiDAO;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Tipodoc;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.suport.Suport;

public class Seeker implements Runnable {

	private static Seeker seeker = null; 
	private Thread thread = null;

	public TipodocDAO tdDao = new TipodocDAO();
	public TipoleiDAO tlDao = new TipoleiDAO();
	public LeiDAO leiDao = new LeiDAO();
	public ItemleiDAO itemleiDao = new ItemleiDAO();
	public ArquivoDAO arquivoDao = new ArquivoDAO();
	public DocDestaqueDAO docDestDao = new DocDestaqueDAO();


	private CollectionItensDeLei collection = new CollectionItensDeLei();


	private int timer = 0;

	private Seeker() {

		//	thread = new Thread(this);
		//	thread.start();

		//	collection = itemleiDao.selectLei(leiDao.getCollection().first());
		//	leiDao.getCollection().first().setOnLine(true);

	}

	private synchronized static void sync() {

		if (seeker == null) {
			seeker = new Seeker();
			//System.out.println("entrou aqui..");
		}

	}

	public static Seeker getInstance() {

		if (seeker == null)
			sync();

		return seeker;
	}


	public void run() {


		while (true) {

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println(timer++);					
		}
	}

	public Documento selectSearch(Documento _lei, String busca) {
		Documento lei;
		if (_lei == null)
			lei = new Documento();
		else 
			lei = _lei;
		lei.completo = true;
		lei.setItemleis(itemleiDao.selectSearch(lei, busca));
		return lei;
	}

	public Documento selectOneDoc(Documento _doc) {		
		return leiDao.selectOne(_doc);
	}



	public void clickLei(Documento lei, HttpServletRequest request, HttpServletResponse response) {

		String strUserAgents = "Googlebot|AdsBot-Google|Crawler|Yahoo! Slurp|Yahoo! Slurp|YahooSeeker|YahooSeeker-Testing|JikeSpider|proximic|Sosospider|Baiduspider|BecomeBot|BeslistBot|Bimbot|Sogou|spider|OpenVAS|msnbot";
		strUserAgents += "|Bingbot|bingbot|AhrefsBot|baiduspider|seznambot|Slurp|teoma|Yandex|Yeti|ia_archiver|DotBo";
		strUserAgents += "|Yahoo|Slurp|008|ABACHOBot|Accoona-AI-Agent|AddSugarSpiderBot|AnyApexBot|Arachmo|B-l-i-t-z-B-O-T|Baiduspider|BecomeBot";
		strUserAgents += "|BeslistBot|BillyBobBot|Bimbot|Bingbot|BlitzBOT|boitho.com-dc|boitho.com-robot|btbot|CatchBot|Cerberian Drtrs|Charlotte|cosmos";
		strUserAgents += "|Covario IDS|DataparkSearch|DiamondBot|Discobot|Dotbot|EARTHCOM.info|EmeraldShield|envolk[ITS]spider|EsperanzaBot|Exabot|FDSE robot";
		strUserAgents += "|FindLinks|FurlBot|FyberSpider|Gaisbot|GalaxyBot|genieBot|Gigabot|Girafabot|GurujiBot|HappyFunBot|hl_ftien_spider|Holmes";
		strUserAgents += "|htdig|iaskspider|ia_archiver|ichiro|igdeSpyder|IRLbot|Jaxified Bot|Jyxobot|KoepaBot|L.webis|LapozzBot|Larbin|LDSpider";
		strUserAgents += "|LexxeBot|Linguee Bot|LinkWalker|lmspider|lwp-trivial|mabontland|Mediapartners-Google|MJ12bot|MLBot|Mnogosearch|mogimogi|MojeekBot";
		strUserAgents += "|Moreoverbot|Morning Paper|msnbot|MSRBot|MVAClient|mxbot|NetResearchServer|NewsGator|NG-Search|nicebot|noxtrumbot|Nusearch Spider";
		strUserAgents += "|NutchCVS|Nymesis|obot|oegp|omgilibot|OmniExplorer_Bot|OOZBOT|Orbiter|PageBitesHyperBot|Peew|polybot|Pompos|PostPost|Psbot|PycURL|Qseero|Radian6";
		strUserAgents += "|RAMPyBot|RufusBot|SBIder|ScoutJet|Scrubby|SearchSight|Seekbot|semanticdiscovery|SEOChat::Bot|SeznamBot";
		strUserAgents += "|ShopWiki|Shoula robot|silk|Sitebot|Snappy|sogou spider|Sosospider|Speedy Spider|Sqworm|StackRambler|suggybot|SurveyBot|SynooBot";
		strUserAgents += "|Teoma|TerrawizBot|TheSuBot|Thumbnail.CZ robot|TinEye|truwoGPS|TurnitinBot|TweetedTimes Bot|TwengaBot|updated|Urlfilebot|Vagabondo|VoilaBot";
		strUserAgents += "|Vortex|voyager|VYU2webbot|webcollage|Websquash.com|wf84|WoFindeIch Robot|WomlpeFactory|Xaldon_WebSpider|yacy|YandexBot|YandexImages|YandexMetrika";
		strUserAgents += "|Yasaklibot|Yeti|YodaoBot|yoogliFetchAgent|YoudaoBot|Zao|Zealbot|zspider|ZyBorg";

		String vtUserAgents[] =  strUserAgents.toLowerCase().split("\\|");

		//Enumeration<String> headers = 
		String http_ua =  request.getHeader("user-agent"); 
		http_ua = http_ua.toLowerCase();

		for (String ua : vtUserAgents) {
			if (http_ua.contains(ua))
				return;
		}
		
		if (Suport.getCookie(request, "click_lei_"+lei.getId()) == null) {
			leiDao.clickLei(lei);
			
			Suport.setCookie(response, "click_lei_"+lei.getId(), "1", 600, "Já acessou nos últimos 10 Minutos");
			
			
			
		}
	}

	public TreeSet<Tipolei> getCollectionTipoLei() {

		return tlDao.getCollection();

	}
	/*
	public List<Documento> getCollectionLei(Tipolei tipolei, int pontoInicial, int qtdDocumentos) {

		if (qtdDocumentos == Integer.MAX_VALUE)
			return leiDao.getCollection(tipolei);

		int ultimoRegistro = pontoInicial+qtdDocumentos;

		if (ultimoRegistro > leiDao.getCountCollectionLei(tipolei))
			ultimoRegistro = leiDao.getCountCollectionLei(tipolei);

		List<Documento> lista = leiDao.getCollection(tipolei).subList(pontoInicial, ultimoRegistro);	

		return lista;			
	}*/
	/*
	public List<Documento> getTopList(Documento docBase, int pontoInicial, int qtdDocumentos) {

		if (qtdDocumentos == Integer.MAX_VALUE)
			return leiDao.getTopList();

	    int ultimoRegistro = pontoInicial+qtdDocumentos;

		if (ultimoRegistro > leiDao.getCountLastIncludes())
			ultimoRegistro = leiDao.getCountLastIncludes();

		List<Documento> lista = leiDao.getTopList().subList(pontoInicial, ultimoRegistro);	

		return lista;	

	}	*/
	/*
	public List<Documento> getLastIncludes(int pontoInicial, int qtdDocumentos) {

		if (qtdDocumentos == Integer.MAX_VALUE)
			return leiDao.getLastIncludes();

		int ultimoRegistro = pontoInicial+qtdDocumentos;

		if (ultimoRegistro > leiDao.getCountLastIncludes())
			ultimoRegistro = leiDao.getCountLastIncludes();

		List<Documento> lista = leiDao.getLastIncludes().subList(pontoInicial, ultimoRegistro);	

		return lista;		
	}	*/

	public synchronized void upDateCollectionItensDeLei(Documento lei) {
		lei.completo = true;
		if (!lei.getOnLine()) {			
			itemleiDao.selectLei(lei);			
			lei.setOnLine(true);
		}		
	}	

	public PesquisaDeDocumentos prepararPesquisa(HttpServletRequest request,
			Tipodoc td, int pagina, int limite, Boolean publico) {

		PesquisaDeDocumentos p;

		if (request != null) {
			HttpSession session = request.getSession();

			p = (PesquisaDeDocumentos) session.getAttribute("pesquisa");

			if (p == null) {		
				p = new PesquisaDeDocumentos();
				session.setAttribute("pesquisa", p);
			}
		}
		else {
			p = new PesquisaDeDocumentos();
		}

		p.setTd(td);

		try {
			p.setPagina(Integer.parseInt(request.getParameter("p")));
			p.setQtdDocumentos(Integer.parseInt(request.getParameter("q")));
		}
		catch (Exception e) {
			p.resetValues();
		}			

		if (pagina != 0)
			p.setPagina(pagina);
		if (limite != 0)
			p.setQtdDocumentos(limite);

		try {
			Tipolei tl = new Tipolei(Integer.parseInt(request.getParameter("tl")));
			tl = tlDao.selectOne(tl);
			p.setTl(tl);
		}
		catch (Exception e) {
			p.setTl(null);
		}

		if (p.getTl() != null)
			p.setTd(null);

		try {
			p.setTextoSearch(request.getParameter("busca"));
		}
		catch (Exception e) {
			p.setTextoSearch("");
		}

		try {
			p.setNumDoc(Integer.parseInt(request.getParameter("numDoc")));
		}
		catch (Exception e) {
			p.setNumDoc(0);
		}

		try {
			p.setTp(request.getParameter("tp"));
		}
		catch (Exception e) {
			p.setTp(null);
		}

		p.setPublico(publico);


		leiDao.getDocs(p, false);		

		if (p.getTl() == null) {
			if (p.getTd() == null)
				p.setTitulo("Documentos Diversos: "+p.getTextoSearch());
			else {
				p.setTitulo(p.getTd().getDescr());

				if (p.getTextoSearch().length() > 0)
					p.setTitulo(p.getTitulo()+": " +p.getTextoSearch());

			}
		}
		else { 
			p.setTitulo((p.getTd()==null?"":p.getTd().getDescr()+": ")+p.getTl().getDescr());

			if (p.getTextoSearch().length() > 0)
				p.setTitulo(p.getTitulo()+": " +p.getTextoSearch());

		}
		p.calcularValores();

		//	p.setTl(null);
		//	p.setTd(null);

		return p;

	}

	/*
	public PesquisaDeDocumentos prepararPesquisaTextualEmenta(Documento filterTypeDoc) {

		PesquisaDeDocumentos p;

		if (request != null) {
			HttpSession session = request.getSession();

			p = (PesquisaDeDocumentos) session.getAttribute("pesquisa");

			if (p == null) {		
				p = new PesquisaDeDocumentos();
				session.setAttribute("pesquisa", p);
			}
		}
		else {
			p = new PesquisaDeDocumentos();
		}

		p.setTd(td);

		try {
			p.setPagina(Integer.parseInt(request.getParameter("p")));
			p.setQtdDocumentos(Integer.parseInt(request.getParameter("q")));
		}
		catch (Exception e) {
			p.resetValues();
		}			

		try {
			Tipolei tl = new Tipolei(Integer.parseInt(request.getParameter("tl")));
			tl = tlDao.selectOne(tl);
			p.setTl(tl);
		}
		catch (Exception e) {
			p.setTl(null);
		}

		leiDao.getDocs(p, false);		

		if (p.getTl() == null)
			p.setTitulo(p.getTd().getDescr());
		else 
			p.setTitulo(p.getTl().getDescr());

		p.calcularValores();

		return p;

	}
	 */
	public Documento selectOneLeiForNum(Documento lei) {
		return leiDao.selectOneForNum(lei);
	}
}
