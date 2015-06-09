package br.gov.go.camarajatai.sislegisold.pdf;

import java.io.OutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class Relatorio {

	public Font font8 = new Font(FontFamily.COURIER, 8);
	public Font font7 = new Font(FontFamily.COURIER, 7);
	public Font font5 = new Font(FontFamily.COURIER, 5);
	public Font font10 = new Font(FontFamily.COURIER, 10);
	public Font font15 = new Font(FontFamily.COURIER, 15);

	private void run(OutputStream out) throws DocumentException {

		Document document = new Document(PageSize.A4,20,15,20,15);
		document.setPageCount(1);

		PdfWriter pd = PdfWriter.getInstance(document, out);
		pd.setFullCompression();
		document.open();

		LineSeparator line;
		Paragraph p;
		PdfPTable tab = null;
		PdfPCell ca;

		int numCols = 30;
		int widthCols = 7;
		tab = new PdfPTable(numCols);			
		tab.setExtendLastRow(false);
		tab.setTotalWidth(numCols * widthCols);
		tab.setLockedWidth(true);

		int[] cols = new int[numCols];
		for (int i = 0; i < numCols; i++)
			cols[i] = widthCols;
		tab.setWidths(cols);

		int jj = 0;
		int numLinhas = 43;
		float bottomLine = 0f;		

		for (int i = 0; i < numCols*3; i++) {

			ca = newCell(new Font(FontFamily.COURIER, 8, Font.NORMAL), "-", 1, 1, 12f, PdfPCell.ALIGN_RIGHT);
			setBordersCell(ca, 0.3f, 0f, bottomLine, 0.3f);
			tab.addCell(ca);
		}


		document.add(tab);
		document.close();

	}



	protected static void setBordersCell(PdfPCell ca,  float top, float right, float bottom, float left) {
		ca.setBorderWidthBottom(bottom);
		ca.setBorderWidthTop(top);
		ca.setBorderWidthRight(right);
		ca.setBorderWidthLeft(left);
	}

	protected static PdfPCell newCell(Font font, String texto, int colunas, int linhas, float height, int align) {

		PdfPCell cell;

		Paragraph p = new Paragraph(texto,font);


		p.setAlignment(align);


		cell = new PdfPCell(p);

		if (colunas > 1)
			cell.setColspan(colunas);
		if (linhas > 1)
			cell.setRowspan(linhas);

		cell.setBorderWidth(0.1f);		
		cell.setHorizontalAlignment(align);
		cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
		cell.setPaddingLeft(2);
		cell.setPaddingRight(2);

		cell.setMinimumHeight(0f);
		cell.setFixedHeight(height);
		return cell;

	}

	protected static PdfPCell newCell(float fontSize, boolean isBold, String texto, int colunas, int linhas, float height, int align) {

		PdfPCell cell;

		if (height < fontSize && height > -0.1f)
			height = fontSize * 1.5f;
		else if (height < 0f)
			height = 0f;
		height = (float)Math.ceil(height);
		
		
		Paragraph p = new Paragraph(texto, new Font(FontFamily.COURIER, fontSize, isBold ? Font.BOLD: Font.NORMAL));


		p.setAlignment(align);


		cell = new PdfPCell(p);

		if (colunas > 1)
			cell.setColspan(colunas);
		if (linhas > 1)
			cell.setRowspan(linhas);

		cell.setBorderWidth(0.1f);		
		cell.setHorizontalAlignment(align);
		cell.setVerticalAlignment(PdfPCell.ALIGN_TOP);
		cell.setPaddingLeft(2);
		cell.setPaddingRight(2);
		if (height <= 0.1)
			cell.setPaddingBottom(5);

		cell.setMinimumHeight(0f);
		cell.setFixedHeight(height);
		return cell;

	}

}

