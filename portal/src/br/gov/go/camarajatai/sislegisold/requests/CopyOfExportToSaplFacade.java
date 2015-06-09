package br.gov.go.camarajatai.sislegisold.requests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.CharsetUtils;
import org.apache.http.util.EntityUtils;

import com.itextpdf.testutils.ITextTest;
import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import br.gov.go.camarajatai.sislegisold.business.Seeker;
import br.gov.go.camarajatai.sislegisold.dao.MySqlConnection;
import br.gov.go.camarajatai.sislegisold.dao.PostgreConnection;
import br.gov.go.camarajatai.sislegisold.dto.Arquivo;
import br.gov.go.camarajatai.sislegisold.dto.Documento;
import br.gov.go.camarajatai.sislegisold.dto.Tipolei;
import br.gov.go.camarajatai.sislegisold.suport.Suport;
import br.gov.go.camarajatai.sislegisold.suport.Urls;

/**
 * Servlet implementation class ExportToSaplFacade
 */
public class CopyOfExportToSaplFacade extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CopyOfExportToSaplFacade() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CloseableHttpClient httpclient;
		try {

			BasicCookieStore cookieStore = new BasicCookieStore();
			httpclient = HttpClients.custom()
					.setDefaultCookieStore(cookieStore)
					.build();
			HttpUriRequest login;

			login = RequestBuilder.post()
					.setUri(new URI("http://sapl.camarajatai.go.gov.br/sapl?retry=1"))
					.addParameter("__ac_name", "leandro")
					.addParameter("__ac_password", "infoCmj")
					.build();

			CloseableHttpResponse response2 = httpclient.execute(login);

			List<Cookie> cookies = cookieStore.getCookies();
			if (cookies.isEmpty()) {
				System.out.println("None");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					System.out.println("- " + cookies.get(i).toString());
				}
			}




			ServletOutputStream out = response.getOutputStream();
			ArrayList<Documento> lDocs = Seeker.getInstance().leiDao.selectAll();
			Statement stm = MySqlConnection.getInstance().newStatement();

			for (Documento doc : lDocs) {


				Seeker.getInstance().tlDao.refreshTipos(doc);


				if (doc.getId_tipolei().size() == 0)
					continue;

				Tipolei tl = doc.getId_tipolei().get(0);

				if (tl.getId() != 1 &&
						tl.getId() != 20 &&
						tl.getId() != 2 &&
						tl.getId() != 3 &&
						tl.getId() != 19 &&
						tl.getId() != 21 &&
						tl.getId() != 26
						)
					continue;


				ResultSet rs = stm.executeQuery("select * from norma_juridica where cod_norma = "+doc.getId()); 

				if (rs.next()){
					//update


				}
				else {

					GregorianCalendar g = new GregorianCalendar();
					g.setTimeInMillis(doc.getData_lei().getTime());


					String sql = "INSERT INTO `norma_juridica` (`cod_norma`, `tip_norma`, `cod_materia`, `num_norma`, `ano_norma`, `tip_esfera_federacao`, `dat_norma`, `dat_publicacao`, `des_veiculo_publicacao`, `num_pag_inicio_publ`, `num_pag_fim_publ`, `txt_ementa`, `txt_indexacao`, `txt_observacao`, `ind_complemento`, `cod_assunto`, `ind_excluido`, `dat_vigencia`, `timestamp`) VALUES " +
							"("+doc.getId()+", "+tl.getId()+", null, "+doc.getNumero()+", "+g.get(GregorianCalendar.YEAR)+", 'M', '"+doc.getData_lei().toString()+"', '"+doc.getData_alteracao().toString()+"', '', null, null, '"+doc.getEmenta()+"', null, null, 0, '1', 0, null, '"+doc.getData_lei().toString()+"');";
					stm.execute(sql);

					//insert
				}


				if (doc.getPossuiarqdigital()) {

					//Seeker.getInstance().leiDao.selectArqDigital(doc);

					//byte[] f = doc.getArqdigital();

					URL url = new URL("http://localhost:8080/portal/seeker?iddoc=281&print=true&anon=true");

					URLConnection uc = url.openConnection();
					
					URL urlCss = new URL("http://localhost:8080/portal/lib/styleGeral.css");

					URLConnection ucCss = urlCss.openConnection();
					//			        BufferedReader in = new BufferedReader(new InputStreamReader(
					//		                                    uc.getInputStream()));
                    

					byte[] f = new byte[1000000];
				/*	

					f = Suport.toByteVector(uc.getInputStream());
					
					System.out.write(f);*/

                    
                    try {
						FileOutputStream file = new FileOutputStream(new File("/tmp/Test.pdf"));
						Document document = new Document();
						PdfWriter writer = PdfWriter.getInstance(document, file);
						document.open(); 
						XMLWorkerHelper.getInstance().parseXHtml(writer, document, uc.getInputStream(),ucCss.getInputStream(),CharsetUtils.get("ISO-8859-1"));
						document.close();
						file.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

					if (f != null || f == null)
						return;

                    
 
					String nome = doc.getName_file();

					if (nome == null || nome.length() == 0) {
						nome = String.valueOf(doc.getNumero());

						try {
							String initFile = new String(f,0,4);
							if (initFile.toUpperCase().equals("%PDF"))
								nome += ".pdf";
						}
						catch (Exception e) {

						}

					}


					FileOutputStream fout = new FileOutputStream("/tmp/arquivoSapl.pdf");
					fout.write(f);
					fout.flush();
					fout.close();



					HttpPost httppost = new HttpPost("http://sapl.camarajatai.go.gov.br/sapl/cadastros/norma_juridica/norma_juridica_salvar_proc_sislegis");


					FileBody bin = new FileBody(new File("/tmp/arquivoSapl.pdf"));

					StringBody comment = new StringBody(String.valueOf(doc.getId()), ContentType.TEXT_PLAIN);



					HttpEntity reqEntity = MultipartEntityBuilder.create()						
							.addPart("file_nom_arquivo", bin)
							.addPart("hdn_cod_norma", comment)
							.build();




					httppost.setEntity(reqEntity); 

					System.out.println("executing request " + httppost.getRequestLine());
					CloseableHttpResponse resp = httpclient.execute(httppost);
					try {
						System.out.println("----------------------------------------");
						System.out.println(resp.getStatusLine());
						HttpEntity resEntity = resp.getEntity();
						if (resEntity != null) {

							System.out.println("Response content length: " + resEntity.getContentLength());

							//InputStream is = resEntity.getContent();

							//is.read(f);

							for (int i = 0; i < resp.getAllHeaders().length; i++)
								System.out.println(resp.getAllHeaders()[i]);

							System.out.println(EntityUtils.toString(resEntity));
						}
						EntityUtils.consume(resEntity);							
					} finally {
						resp.close();
					}

				}

			}
			httpclient.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		} 
	}
}