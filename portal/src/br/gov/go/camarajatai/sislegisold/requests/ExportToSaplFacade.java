package br.gov.go.camarajatai.sislegisold.requests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import br.gov.go.camarajatai.sislegisold.business.CollectionItensDeLei;
import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dao.MySqlConnection;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Itemlei;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class ExportToSaplFacade
 */
public class ExportToSaplFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ExportToSaplFacade() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		final String minNum = request.getParameter("minNum");
		final String dataMin = request.getParameter("dataMin");
		final String minID = request.getParameter("minID");
		String tip = request.getParameter("tipo");
		final String iddoc = request.getParameter("iddoc");
		// C - Completo
		// A - Apenas os Arquivos Digitais
		// T - Apenas os textos
		// N - Nenhum - Apenas Banco de Dados
		if (tip == null)
			tip = "C";

		final String saplReq = request.getParameter("sapl");

		final String tipo = tip;



		Thread th = new Thread(new Runnable() {




			TreeMap<Integer, Integer> codOrdemBlocos = new TreeMap<Integer, Integer>();

			@Override
			public void run() {

				CloseableHttpClient httpclient;
				try {
					String sapl = "sapl";
					if (saplReq != null)
						sapl = saplReq;

					BasicCookieStore cookieStore = new BasicCookieStore();
					httpclient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
					HttpUriRequest login;

					login = RequestBuilder.post().setUri(new URI("http://" + sapl + ".camarajatai.go.gov.br/sapl?retry=1"))
							.addParameter("__ac_name", "leandro").addParameter("__ac_password", "infoCmj").build();

					CloseableHttpResponse response2 = httpclient.execute(login);

					List<Cookie> cookies = cookieStore.getCookies();
					if (cookies.isEmpty()) {
						System.out.println("None");
					} else {
						for (int i = 0; i < cookies.size(); i++) {
							System.out.println("- " + cookies.get(i).toString());
						}
					}

					ArrayList<Documento> lDocs = Seeker.getInstance().leiDao.selectAll();
					Statement stm = MySqlConnection.getInstance().newStatement();

					for (Documento doc : lDocs) {
						// System.out.println("doc: " + doc.getEpigrafe());

						if (iddoc != null) {
							if (doc.getId() != Integer.parseInt(iddoc))
								continue;
						} else {

							if (minID != null)
								if (doc.getId() < Integer.parseInt(minID))
									continue;
							if (minNum != null)
								if (doc.getId() < Integer.parseInt(minNum))
									continue;
							if (dataMin != null) {
								long tt = Suport.strBRToDate(dataMin).getTimeInMillis();
								if (doc.getData_lei().getTime() < tt)
									continue;
							}
						}

						if (!doc.getPublicado())
							continue;

						Seeker.getInstance().tlDao.refreshTipos(doc);

						if (doc.getId_tipolei().size() == 0)
							continue;

						Tipolei tl = doc.getId_tipolei().get(0);

						if (tl.getId() != 1 && tl.getId() != 20   /* tipos que serão exportados */
								&& tl.getId() != 2
								&& tl.getId() != 3
								&& tl.getId() != 19
								&& tl.getId() != 21
								&& tl.getId() != 26
								&& tl.getId() != 27
								&& tl.getId() != 10)
							continue;

						ResultSet rs = stm.executeQuery("select * from norma_juridica where cod_norma = " + doc.getId());

						if (rs.next()) {

							GregorianCalendar g = new GregorianCalendar();
							g.setTimeInMillis(doc.getData_lei().getTime());

							String assuntos = "";

							for (int iAss = 1; iAss < doc.getId_tipolei().size(); iAss++) {
								assuntos += doc.getId_tipolei().get(iAss).getId();

								if (iAss + 1 != doc.getId_tipolei().size())
									assuntos += ",";
							}

							if (assuntos.length() == 0)
								assuntos = "1";

							// byte[] s = "".getBytes();

							doc.setEmenta(doc.getEmenta().replace("", "\""));
							doc.setEmenta(doc.getEmenta().replace("", "\""));

							String sql = "update `norma_juridica` set `cod_norma` = " + doc.getId()
									+ ", "
									+ "`tip_norma` = "
									+ tl.getId()
									+ ", "
									+ "`num_norma` = "
									+ doc.getNumero()
									+ ", "
									+ "`ano_norma` = "
									+ g.get(GregorianCalendar.YEAR)
									+ ", "
									+ "`tip_esfera_federacao` = 'M', "
									+ "`dat_norma` = '"
									+ doc.getData_lei().toString()
									+ "', "
									+ "`dat_publicacao` = '"
									+ doc.getData_alteracao().toString()
									+ "', "
									+ "`des_veiculo_publicacao` = '', "
									+ "`num_pag_inicio_publ` = null, "
									+ "`num_pag_fim_publ` = null, "
									+ "`txt_ementa` = '"
									+ doc.getEmenta()
									+ "', "
									+ "`txt_indexacao` = null, "
									+ "`txt_observacao` = null, "
									+ "`ind_complemento` = 0, "
									+ "`cod_assunto` = '"
									+ assuntos
									+ "', "
									+ "`ind_excluido` = 0, `cod_situacao` = 0, `dat_vigencia` = null, `timestamp` = CURRENT_TIMESTAMP "
									+ "where `cod_norma` = "
									+ doc.getId()
									+ ";";
							stm.execute(sql);

							if (doc.getId_revogador() != null) {
								try {
									sql = "INSERT INTO vinculo_norma_juridica (cod_norma_referente, cod_norma_referida, tip_vinculo, ind_excluido) values " + "("
											+ doc.getId_revogador().getId()
											+ ","
											+ doc.getId()
											+ ",'R', '');";
									stm.execute(sql);
								} catch (Exception e) {
									// System.out.println("erro 1");
									// e.printStackTrace();
								}
							}

						} else {

							GregorianCalendar g = new GregorianCalendar();
							g.setTimeInMillis(doc.getData_lei().getTime());

							String assuntos = "";

							for (int iAss = 1; iAss < doc.getId_tipolei().size(); iAss++) {
								assuntos += doc.getId_tipolei().get(iAss).getId();

								if (iAss + 1 != doc.getId_tipolei().size())
									assuntos += ",";
							}
							if (assuntos.length() == 0)
								assuntos = "1";

							String sql = "INSERT INTO `norma_juridica` (`cod_norma`, `tip_norma`, `cod_materia`, `num_norma`, `ano_norma`, `tip_esfera_federacao`, `dat_norma`, `dat_publicacao`, `des_veiculo_publicacao`, `num_pag_inicio_publ`, `num_pag_fim_publ`, `txt_ementa`, `txt_indexacao`, `txt_observacao`, `ind_complemento`, `cod_assunto`, `ind_excluido`, `dat_vigencia`, `timestamp`, `cod_situacao`) VALUES " + "("
									+ doc.getId()
									+ ", "
									+ tl.getId()
									+ ", null, "
									+ doc.getNumero()
									+ ", "
									+ g.get(GregorianCalendar.YEAR)
									+ ", 'M', '"
									+ doc.getData_lei().toString()
									+ "', '"
									+ doc.getData_alteracao().toString()
									+ "', '', null, null, '"
									+ doc.getEmenta()
									+ "', null, null, 0, '"
									+ assuntos
									+ "', 0, null, CURRENT_TIMESTAMP, 0);";
							stm.execute(sql);

							if (doc.getId_revogador() != null) {
								try {
									sql = "INSERT INTO vinculo_norma_juridica (cod_norma_referente, cod_norma_referida, tip_vinculo, ind_excluido) values " + "("
											+ doc.getId_revogador().getId()
											+ ","
											+ doc.getId()
											+ ",'R', '');";
									stm.execute(sql);
								} catch (Exception e) {
									// System.out.println("erro 2");
									// e.printStackTrace();
								}
							}
							// insert
						}

						ArrayList<Documento> lDocsAlteradores = Seeker.getInstance().leiDao.selectNormasAlteradoras(doc);

						for (Documento docc : lDocsAlteradores) {
							try {
								String sql = "INSERT INTO vinculo_norma_juridica (cod_norma_referente, cod_norma_referida, tip_vinculo, ind_excluido) values " + "("
										+ docc.getId()
										+ ","
										+ doc.getId()
										+ ",'A', '');";
								stm.execute(sql);
							} catch (Exception e) {

								// System.out.println("erro 3");
								// e.printStackTrace();
							}
						} 

						exportarDispositivosV2(stm, doc, codOrdemBlocos);

						if (tipo.equals("C") || tipo.equals("T")) {
							// URL url = new
							// URL("http://10.3.163.1/portal/seeker?iddoc=" +
							// doc.getId() + "&anon=true&export=true");
							URL url = new URL("http://localhost:8080/portal/seeker?iddoc=" + doc.getId() + "&anon=true&export=true&sapl=" + sapl);

							byte[] f = Suport.toByteVector(url.openStream());
							System.gc();

							FileOutputStream fout = new FileOutputStream("/tmp/doc.html");
							fout.write(f);
							fout.flush();
							fout.close();
							// HttpPost httppost = new
							// HttpPost("http://187.6.249.154:8080/sapl/cadastros/norma_juridica/norma_juridica_salvar_proc_sislegis");

							HttpPost httppost = new HttpPost("http://" + sapl
									+ ".camarajatai.go.gov.br/sapl/cadastros/norma_juridica/norma_juridica_salvar_proc_sislegis");

							FileBody bin = new FileBody(new File("/tmp/doc.html"));

							StringBody comment = new StringBody(String.valueOf(doc.getId()), ContentType.TEXT_PLAIN);
							StringBody comment1 = new StringBody("texto_html", ContentType.TEXT_PLAIN);
							StringBody comment2 = new StringBody("text/html", ContentType.TEXT_PLAIN);
							StringBody comment3 = new StringBody(String.valueOf(doc.getData_lei().getTime()), ContentType.TEXT_PLAIN);
							StringBody comment4 = new StringBody(String.valueOf(doc.getNumero()), ContentType.TEXT_PLAIN);
							StringBody comment5 = new StringBody(String.valueOf(doc.getId_tipolei().get(0).getId()), ContentType.TEXT_PLAIN);
							StringBody comment6 = new StringBody(String.valueOf(doc.getData_lei().getYear()), ContentType.TEXT_PLAIN);
							// System.out.println(String.valueOf(doc.getData_lei().getTime()));
							HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file_nom_arquivo", bin).addPart("hdn_cod_norma", comment)
									.addPart("hdn_sfx_norma", comment1).addPart("hdn_content_type", comment2).addPart("hdn_data_doc", comment3)
									.addPart("hdn_num_norma", comment4).addPart("hdn_tip_norma", comment5).addPart("hdn_ano_norma", comment6).build();

							httppost.setEntity(reqEntity);

							// System.out.println("executing request " +
							// httppost.getRequestLine());
							CloseableHttpResponse resp = httpclient.execute(httppost);
							try {
								// System.out.println("----------------------------------------");
								System.out.println("html: " + doc.getEpigrafe()
										+ "  --->"
										+ resp.getStatusLine()
										+ " ----> Memória:"
										+ (Runtime.getRuntime().freeMemory() / 1024)
										+ "MB");
								HttpEntity resEntity = resp.getEntity();
								// if (resEntity != null) {

								// System.out.println("Response content length: "
								// + resEntity.getContentLength());

								// InputStream is = resEntity.getContent();

								// is.read(f);

								// for (int i = 0; i <
								// resp.getAllHeaders().length; i++)
								// System.out.println(resp.getAllHeaders()[i]);

								// System.out.println(EntityUtils.toString(resEntity));
								// }
								EntityUtils.consume(resEntity);
							} finally {
								resp.close();
							}
							// Thread.sleep(500);
						}

						if (doc.getPossuiarqdigital() && (tipo.equals("C") || tipo.equals("A"))) {

							Seeker.getInstance().leiDao.selectArqDigital(doc);

							byte[] f = doc.getArqdigital();
							System.gc();

							String nome = doc.getName_file();

							if (nome == null || nome.length() == 0) {
								nome = String.valueOf(doc.getNumero());

								try {
									String initFile = new String(f, 0, 4);
									if (initFile.toUpperCase().equals("%PDF"))
										nome += ".pdf";
								} catch (Exception e) {

									System.out.println("erro 4");
								}

							}

							FileOutputStream fout = new FileOutputStream("/tmp/arquivoSapl.pdf");
							fout.write(f);
							fout.flush();
							fout.close();

							doc.setArqdigital(null);

							// HttpPost httppost = new
							// HttpPost("http://187.6.249.154:8080/sapl/cadastros/norma_juridica/norma_juridica_salvar_proc_sislegis");
							HttpPost httppost = new HttpPost("http://" + sapl
									+ ".camarajatai.go.gov.br/sapl/cadastros/norma_juridica/norma_juridica_salvar_proc_sislegis");

							FileBody bin = new FileBody(new File("/tmp/arquivoSapl.pdf"));

							StringBody comment = new StringBody(String.valueOf(doc.getId()), ContentType.TEXT_PLAIN);
							StringBody comment1 = new StringBody("texto_integral", ContentType.TEXT_PLAIN);

							StringBody comment2 = null;

							if (nome.endsWith("pdf"))
								comment2 = new StringBody("application/pdf", ContentType.TEXT_PLAIN);
							else
								comment2 = new StringBody("application/msword", ContentType.TEXT_PLAIN);
							StringBody comment3 = new StringBody(String.valueOf(doc.getData_lei().getTime()), ContentType.TEXT_PLAIN);
							StringBody comment4 = new StringBody(String.valueOf(doc.getNumero()), ContentType.TEXT_PLAIN);
							StringBody comment5 = new StringBody(String.valueOf(doc.getId_tipolei().get(0).getId()), ContentType.TEXT_PLAIN);
							StringBody comment6 = new StringBody(String.valueOf(doc.getData_lei().getYear()), ContentType.TEXT_PLAIN);
							// System.out.println(String.valueOf(doc.getData_lei().getTime()));
							HttpEntity reqEntity = MultipartEntityBuilder.create().addPart("file_nom_arquivo", bin).addPart("hdn_cod_norma", comment)
									.addPart("hdn_sfx_norma", comment1).addPart("hdn_content_type", comment2).addPart("hdn_data_doc", comment3)
									.addPart("hdn_num_norma", comment4).addPart("hdn_tip_norma", comment5).addPart("hdn_ano_norma", comment6).build();

							httppost.setEntity(reqEntity); // localhost:8080/portal/toSapl?dataMin=01/03/2015

							// System.out.println("executing request " +
							// httppost.getRequestLine());
							CloseableHttpResponse resp = httpclient.execute(httppost);
							try {
								// System.out.println("----------------------------------------");
								System.out.println("pdf: " + doc.getEpigrafe()
										+ "  --->"
										+ resp.getStatusLine()
										+ " ----> Memória:"
										+ (Runtime.getRuntime().freeMemory() / 1024)
										+ "MB");
								HttpEntity resEntity = resp.getEntity();
								// if (resEntity != null) {

								// System.out.println("Response content length: "
								// + resEntity.getContentLength());

								// InputStream is = resEntity.getContent();

								// is.read(f);

								// for (int i = 0; i <
								// resp.getAllHeaders().length; i++)
								// System.out.println(resp.getAllHeaders()[i]);

								// System.out.println(EntityUtils.toString(resEntity));
								// }
								EntityUtils.consume(resEntity);
							} finally {
								resp.close();
							}
							// Thread.sleep(500);
							// break;
						}

					}
					httpclient.close();

					File f = new File("/tmp/arquivoSapl.pdf");
					f.delete();
					f = new File("/tmp/doc.html");
					f.delete();

				} catch (Exception e) {
					System.out.println("erro 5");
					System.out.println(e.toString());
				}

				System.out.println();
			}

			private void exportarDispositivosV2(Statement stm, Documento doc, TreeMap<Integer, Integer> codOrdemBlocos)
					throws SQLException {


				// Segunda versão
				Seeker.getInstance().upDateCollectionItensDeLei(doc);

				CollectionItensDeLei collection = doc.getItemleis();

				Itemlei il = null;

				LinkedList<Itemlei> paiAtual = new LinkedList<Itemlei>();
				int ordem = 0;
				int codordem = 0;
				int codordembloco = 0;
				int nivelOld = 0;
				int articulacao = 0;

				stm.execute("delete from dispositivo where cod_norma = " + doc.getId());
				System.out.print("  :" + doc.getNumero());

				if (doc.getNumero() % 10 == 0)
					System.out.println();

				if (collection.isEmpty())
					return;


 
				
				collection.firstItem();
				while (collection.hasNext()) {

					il = collection.next();

					if (il.getId_lei() != doc.getId())
						continue;
					
					if (articulacao == 0) {
						Itemlei ilArticulacao = null;
						ilArticulacao = Itemlei.clone(il);
						ilArticulacao.setId(0);
						ilArticulacao.setNivel(50);  // nivel virtual para articulação
						ilArticulacao.organize();
						ilArticulacao.setCodigo(2);
						ilArticulacao.setTexto("");
						ilArticulacao.setAlterado(false);
						ilArticulacao.setIncluido(false);
						ilArticulacao.setRevogado(false);
						ilArticulacao.setId_alterador(doc.getId());
						ilArticulacao.setId_lei(doc.getId());
						ilArticulacao.setData_inclusao(doc.getData_lei());
						ilArticulacao.setData_alteracao(doc.getData_lei());
						ilArticulacao.setId_dono(0); 
						collection.addFirst(ilArticulacao);
						

						Itemlei ilEmenta = null;
						ilEmenta = Itemlei.clone(collection.getFirst());
						ilEmenta.setId(0);
						ilEmenta.setNivel(2000);  // nivel virtual para articulação
						ilEmenta.organize();
						ilEmenta.setCodigo(1);

						ilEmenta.setTexto(doc.getEmenta());
						ilEmenta.setAlterado(false);
						ilEmenta.setIncluido(false);
						ilEmenta.setRevogado(false);
						ilEmenta.setId_alterador(doc.getId());
						ilEmenta.setId_lei(doc.getId());
						ilEmenta.setData_inclusao(doc.getData_lei());
						ilEmenta.setData_alteracao(doc.getData_lei());
						ilEmenta.setId_dono(0); 
						collection.addFirst(ilEmenta);
						
						ilArticulacao = null;
						ilArticulacao = Itemlei.clone(collection.getFirst());
						ilArticulacao.setId(0);
						ilArticulacao.setNivel(50);  // nivel virtual para articulação
						ilArticulacao.organize();
						ilArticulacao.setCodigo(1);
						ilArticulacao.setTexto("");
						ilArticulacao.setAlterado(false);
						ilArticulacao.setIncluido(false);
						ilArticulacao.setRevogado(false);
						ilArticulacao.setId_alterador(doc.getId());
						ilArticulacao.setId_lei(doc.getId());
						ilArticulacao.setData_inclusao(doc.getData_lei());
						ilArticulacao.setData_alteracao(doc.getData_lei());
						ilArticulacao.setId_dono(0); 
						collection.addFirst(ilArticulacao);
						
						
						collection.firstItem();
						articulacao = 2;
						continue;
					}
					else if (articulacao - 2 != il.getAnexo()  && il.getAnexo() == 1) {
						Itemlei ilArticulacao = null;
						ilArticulacao = Itemlei.clone(il);
						ilArticulacao.setId(0);
						ilArticulacao.setNivel(50);  // nivel virtual para articulação
						ilArticulacao.organize();
						ilArticulacao.setCodigo(++articulacao);
						ilArticulacao.setTexto("");
						ilArticulacao.setAlterado(false);
						ilArticulacao.setIncluido(false);
						ilArticulacao.setRevogado(false);
						ilArticulacao.setId_alterador(doc.getId());
						ilArticulacao.setId_lei(doc.getId());
						ilArticulacao.setData_inclusao(doc.getData_lei());
						ilArticulacao.setData_alteracao(doc.getData_lei());
						ilArticulacao.setId_dono(0);
						collection.previous();
						collection.getIteratorAtual().add(ilArticulacao); 
						collection.previous();
						continue;
					}
					
					
					
					
					

					if (il.getNivelToSapl() <= nivelOld) {
						while (!paiAtual.isEmpty() && paiAtual.getLast().getNivelToSapl() >= il.getNivelToSapl())
							paiAtual.pollLast();

						if (paiAtual.size() > 0)
							nivelOld = paiAtual.getLast().getNivelToSapl();
					}
					String sql = "";
					try {
						++ordem;
						codordem = codordem + 1000;

						if (il.getId_lei() != il.getId_alterador()) { 						
							il.setData_inclusao(Seeker.getInstance().leiDao.selectOne(new Documento(il.getId_alterador())).getData_lei());


							Integer idDono = new Integer(il.getId_dono());
							Integer value = 0;
							if (codOrdemBlocos.containsKey(idDono)) {

								value = new Integer(codOrdemBlocos.get(idDono) + 1000);
								codOrdemBlocos.replace(idDono, value);
							}
							else {

								value = new Integer(1000);
								codOrdemBlocos.put(idDono, value);
							}

							codordembloco = value;

						}
						else {  
							codordembloco = 0;
							il.setData_inclusao(doc.getData_lei());
						}

						Seeker.getInstance().itemleiDao.update(il);

						String fimVigencia = "";

						if ((il.getRevogado() || il.getAlterado()) && ordem < collection.size()) {

							Timestamp data = Seeker.getInstance().leiDao.selectOne(new Documento(collection.get(ordem).getId_alterador()))
									.getData_lei();

							GregorianCalendar g = new GregorianCalendar();
							g.setTimeInMillis(data.getTime());
							g.add(GregorianCalendar.DAY_OF_MONTH, -1);

							fimVigencia = "'" + new Timestamp(g.getTimeInMillis()) + "'"; 
						} else
							fimVigencia = "NULL";

						if (il.getNivelToSapl() == 800) { 
							
							Itemlei caput = Itemlei.clone(il);
							collection.getIteratorAtual().add(caput);
							collection.previous();
							
							caput.setId(0);
							caput.setNivel(950);
							caput.setCodigo(1);
							
							il.setTexto("");
							sql = insertItem(stm, doc, il, paiAtual, codordem, codordembloco, fimVigencia);
 
							
						} else {

							sql = insertItem(stm, doc, il, paiAtual, codordem, codordembloco, fimVigencia);
 
							//collection.getIteratorAtual()
							
						}
						

						if (paiAtual.size() == 0) {
							nivelOld = il.getNivelToSapl();
							paiAtual.add(il);
						} else {
							if (il.getNivelToSapl() > nivelOld) {
								nivelOld = il.getNivelToSapl();
								paiAtual.add(il);
							}
						}
						
						

					} catch (Exception e) {

						System.out.println();
						System.out.println("******************** erro INSERT DISPOSITIVOS:" + il.getId()
								+ " ::: "
								+ e.getLocalizedMessage()
								+ "--->"
								+ sql);
						System.out.println();
						// e.printStackTrace();
					}
				}


			}

			private String insertItem(Statement stm, Documento doc, Itemlei il,
					LinkedList<Itemlei> paiAtual, int codordem,
					int codordembloco, String fimVigencia) throws SQLException {
				String sql;
				sql = "INSERT INTO dispositivo (" 
						+ (il.getId() != 0 ?"`cod_dispositivo`, ":"") 
						+ "`cod_norma`, " 
						+ "`num_ordem`, "
						+ "`num_ordem_bloco_atualizador`, "
						+ "`num_nivel`, "
						+ "`num_dispositivo_0`, "
						+ "`num_dispositivo_1`, "
						+ "`num_dispositivo_2`, "
						+ "`num_dispositivo_3`, "
						+ "`num_dispositivo_4`, "
						+ "`num_dispositivo_5`, "
						+ "`cod_dispositivo_pai`, "
						+ "`txt_rotulo`, "
						+ "`txt_texto`, "
						+ "`cod_tipo_dispositivo`, " 
						+ "`dat_inicio_vigencia`, "
						+ "`dat_fim_vigencia`, "
						+ "`dat_inicio_eficacia`, "
						+ "`dat_fim_eficacia`, "
						/*+ "`cod_disp_substituido`, "
						+ "`cod_disp_sequente`, "*/
						+ "`cod_norma_publicada`, "
						+ "`cod_dispositivo_atualizador` ) values "
						+ "(" +  (il.getId() != 0 ? il.getId()+ ", ": "")

						+ (il.getId_alterador() == il.getId_lei() ? doc.getId() : il.getId_lei())
						+ ", "
						+ (codordem)
						+ ", "
						+ (codordembloco)
						+ ", "
						+ paiAtual.size()
						+ ", "
						+ il.getVar(0)
						+ ", "
						+ il.getVar(1)
						+ ", "
						+ il.getVar(2)
						+ ", "
						+ il.getVar(3)
						+ ", "
						+ il.getVar(4)
						+ ", "
						+ il.getVar(5)
						+ ", "
						+ (paiAtual.size() == 0 ? 0 : paiAtual.getLast().getId())
						+ ", "
						+ "'"
						+ il.getNomeclaturaToSapl()
						+ "'"
						+ ", "
						+ "''"
						+ ", "
						+ il.getTipoSapl()
						+ ", '" 
						+ il.getData_inclusao()
						+ "',  "
						+ fimVigencia 
						+ ", '" 
						+ il.getData_inclusao()
						+ "',  "
						+ fimVigencia
						+ " , "
						/*+ (il.getId_alterador() != il.getId_lei() && !il.getIncluido() && ordem >= 2 ? collection.get(ordem - 2).getId() : 0)
				      + " , "
				      + (((il.getRevogado() || il.getAlterado()) && ordem < collection.size()) ? collection.get(ordem).getId() : 0)
				      + " , "
						 */+ (il.getId_alterador() == il.getId_lei() ? 0 : il.getId_alterador())
						 + ", "
						 + il.getId_dono()
						 /* + ", "
				      + (il.getLink_alterador() == null ? "NULL" : "'" + il.getLink_alterador() + "'")*/
						 + ");";
				// System.out.println(sql);
				stm.execute(sql);


				if (il.getId() == 0) { 

					ResultSet rs = stm.executeQuery("SELECT LAST_INSERT_ID();");
					rs.next();
					il.setId(rs.getInt(1));
					
				}

				if (il.getId() != 0) {
					il.setTexto(il.getTexto().replace("", "\""));
					il.setTexto(il.getTexto().replace("", "\""));

					sql = "update dispositivo set txt_texto = ? " + "where cod_dispositivo = " + il.getId() + ";";
					PreparedStatement pStm = MySqlConnection.getInstance().con.prepareStatement(sql);
					pStm.setString(1, il.getTexto());
					pStm.execute();
				} 
				
				
				return sql;
			}
			
			private void exportarDispositivosV1(Statement stm, Documento doc)
					throws SQLException {
				// Primeira versão de exportação
				Seeker.getInstance().upDateCollectionItensDeLei(doc);

				CollectionItensDeLei collection = doc.getItemleis();

				Itemlei il = null;

				LinkedList<Itemlei> paiAtual = new LinkedList<Itemlei>();
				int ordem = 0;
				int nivelOld = 0;

				stm.execute("delete from dispositivo where cod_norma = " + doc.getId());
				System.out.print("  :" + doc.getNumero());

				if (doc.getNumero() % 10 == 0)
					System.out.println();

				while (collection.hasNext()) {

					il = collection.next();

					if (il.getId_lei() != doc.getId())
						continue;

					if (il.getNivelToSapl() <= nivelOld) {
						while (!paiAtual.isEmpty() && paiAtual.getLast().getNivelToSapl() >= il.getNivelToSapl())
							paiAtual.pollLast();

						if (paiAtual.size() > 0)
							nivelOld = paiAtual.getLast().getNivelToSapl();
					}
					String sql = "";
					try {
						++ordem;
						if (il.getId_lei() != il.getId_alterador())
							il.setData_inclusao(Seeker.getInstance().leiDao.selectOne(new Documento(il.getId_alterador())).getData_lei());
						else {
							il.setData_inclusao(doc.getData_lei());
						}
						Seeker.getInstance().itemleiDao.update(il);

						String fimVigencia = "";

						if ((il.getRevogado() || il.getAlterado()) && ordem < collection.size()) {

							Timestamp data = Seeker.getInstance().leiDao.selectOne(new Documento(collection.get(ordem).getId_alterador()))
									.getData_lei();

							GregorianCalendar g = new GregorianCalendar();
							g.setTimeInMillis(data.getTime());
							g.add(GregorianCalendar.DAY_OF_MONTH, -1);

							fimVigencia = "'" + new Timestamp(g.getTimeInMillis()) + "'";

						} else
							fimVigencia = "NULL";

						sql = "INSERT INTO dispositivo (" + "`cod_dispositivo`, "
								+ "`cod_norma`, "
								+ "`cod_ordem`, "
								+ "`disp_nivel`, "
								+ "`disp_num0`, "
								+ "`disp_num1`, "
								+ "`disp_num2`, "
								+ "`disp_num3`, "
								+ "`disp_num4`, "
								+ "`disp_num5`, "
								+ "`disp_vinculado`, "
								+ "`disp_rotulo`, "
								+ "`disp_texto`, "
								+ "`tip_dispositivo`, "
								+ "`disp_dat_publicacao`, "
								+ "`disp_dat_ini_vigencia`, "
								+ "`disp_dat_fim_vigencia`, "
								+ "`cod_disp_substituido`, "
								+ "`cod_disp_sequente`, "
								+ "`cod_norma_atualizador`, "
								+ "`cod_disp_atualizador`, "
								+ "`link_disp_atualizador` ) values "
								+ "(" + il.getId()
								+ ", "
								+ doc.getId()
								+ ", "
								+ (ordem)
								+ ", "
								+ paiAtual.size()
								+ ", "
								+ (((Integer) il.getCodigo()) >= 9999 ? 1 : il.getCodigo())
								+ ", "
								+ (il.getNivelToSapl() == 900 ? il.getArtigovar() : 0)
								+ ", "
								+ 0
								+ ", "
								+ 0
								+ ", "
								+ 0
								+ ", "
								+ 0
								+ ", "
								+ (paiAtual.size() == 0 ? 0 : paiAtual.getLast().getId())
								+ ", "
								+ "'"
								+ il.getNomeclaturaToSapl()
								+ "'"
								+ ", "
								+ "''"
								+ ", "

				              + il.getTipoSapl()
				              + ", '"
				              + il.getData_inclusao()
				              + "', '"
				              + il.getData_inclusao()
				              + "',  "
				              + fimVigencia
				              + " , "
				              + (il.getId_alterador() != il.getId_lei() && !il.getIncluido() && ordem >= 2 ? collection.get(ordem - 2).getId() : 0)
				              + " , "
				              + (((il.getRevogado() || il.getAlterado()) && ordem < collection.size()) ? collection.get(ordem).getId() : 0)
				              + " , "
				              + (il.getId_alterador() == il.getId_lei() ? 0 : il.getId_alterador())
				              + ", "
				              + il.getId_dono()
				              + ", "
				              + (il.getLink_alterador() == null ? "NULL" : "'" + il.getLink_alterador() + "'")
				              + ");";
						// System.out.println(sql);
						stm.execute(sql);

						il.setTexto(il.getTexto().replace("", "\""));
						il.setTexto(il.getTexto().replace("", "\""));

						sql = "update dispositivo set disp_texto = ? " + "where cod_dispositivo = " + il.getId() + ";";
						PreparedStatement pStm = MySqlConnection.getInstance().con.prepareStatement(sql);
						pStm.setString(1, il.getTexto());
						pStm.execute();

						if (paiAtual.size() == 0) {
							nivelOld = il.getNivelToSapl();
							paiAtual.add(il);
						} else {
							if (il.getNivelToSapl() > nivelOld) {
								nivelOld = il.getNivelToSapl();
								paiAtual.add(il);
							}
						}

					} catch (Exception e) {

						System.out.println();
						System.out.println("******************** erro INSERT DISPOSITIVOS:" + il.getId()
								+ " ::: "
								+ e.getLocalizedMessage()
								+ "--->"
								+ sql);
						System.out.println();
						// e.printStackTrace();
					} 
				} 
			}
		});
		th.start();
		try {
			th.join();
			/*
			if (iddoc != null) {
				response.sendRedirect("http://10.3.163.1/portal/seeker?iddoc=" + iddoc);
			} else
				response.sendRedirect("http://10.3.163.1/portal/seeker");
			 */
			if (iddoc != null) {
				response.sendRedirect("http://localhost:8080/portal/seeker?iddoc=" + iddoc);
			} else
				response.sendRedirect("http://localhost:8080/portal/seeker");

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			System.out.println("erro 6");
			e.printStackTrace();
		}
	}

	public static void exportDoc(final int[] iddoc, final String tipo) {

		if (!Urls.appBase.equals("portal"))
			return;

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				if (iddoc == null)
					return;

				URL url;

				for (int i = 0; i < iddoc.length; i++)
					try {
						url = new URL("http://localhost:8080/portal/toSapl?iddoc=" + iddoc[i] + "&tipo=" + tipo);
						// url = new
						// URL("http://10.3.163.1/portal/toSapl?iddoc=" +
						// iddoc + "&tipo=" + tipo);
						byte[] f = Suport.toByteVector(url.openStream());
						System.gc();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		th.start();
	}

	public static void exportDoc(final int iddoc, final String tipo) {

		if (iddoc == 0 || !Urls.appBase.equals("portal"))
			return;

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				URL url;
				try {
					// url = new
					// URL("http://localhost:8080/portal/toSapl?iddoc=" + iddoc
					// + "&tipo=" + tipo);
					url = new URL("http://localhost:8080/portal/toSapl?iddoc=" + iddoc + "&tipo=" + tipo);
					byte[] f = Suport.toByteVector(url.openStream());
					System.gc();
				} catch (Exception e) {
					System.out.println("erro export");
					e.printStackTrace();
				}
			}
		});
		th.start();
	}
}