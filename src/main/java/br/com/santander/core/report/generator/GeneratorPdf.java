package br.com.santander.core.report.generator;

import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.awt.geom.Dimension;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.santander.core.report.model.Header;
import br.com.santander.core.report.model.Report;
import br.com.santander.core.report.model.ReportFileStructure;
import br.com.santander.core.report.model.Step;

/**
 * Implementation of Generator to pdf files
 */
public class GeneratorPdf extends Generator {

	private Document doc;
	private File reportFile;

	public GeneratorPdf(Report report) {
		super(report);
	}

	@Override
	public void addSteps() throws Exception {
		if (report.getSteps() == null || report.getSteps().isEmpty()) {
			return;
		}

		Integer i = 0;
		for (Step step : report.getSteps()) {
			i++;
			Image image = createImage(step.getImage());
			Paragraph sub = createParagraph(step.getSub());

			// add two steps per page then a new page
//			if (i % 2 == 0) {
//				doc.newPage();
//			} else {
//				doc.add(new Paragraph("\n"));
//			}
			doc.newPage();

			doc.add(image);
			doc.add(sub);

		}

	}

	private Paragraph createParagraph(String description) {
		Font font = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
		Paragraph p = new Paragraph(description, font);
		p.setAlignment(Element.ALIGN_LEFT);
		return p;
	}

	private Image createImage(byte[] imageBase64) throws Exception {
		Image img = Image.getInstance(imageBase64);

		Dimension d = new Dimension(img.getWidth(), img.getHeight());
		img.setAlignment(Image.MIDDLE);
		img.setBorder(Rectangle.BOX);
		img.setBorderWidth(1);
		float largura = (float) (d.getWidth() * 0.80);
		float altura = (float) (d.getHeight() * 0.80);
		if (img.getWidth() >= 720) {
			largura = largura / 3;
			altura = altura / 3;
		}
		img.scaleAbsolute(largura, altura);
		return img;
	}

	@Override
	public void addHeader() throws Exception {
		Header header = report.getHeader();

		// create table
		PdfPTable table = new PdfPTable(1);
		table.setWidthPercentage(100);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);

		// title
		table.addCell(createHeaderCell());

		// body
		if (header.getSections() == null || header.getSections().isEmpty()) {
			throw new RuntimeException("No information was found on header");
		}

		// TODO not putting section title yet
		header.getSections().forEach((sectionTitle, sectionMap) -> sectionMap
				.forEach((key, value) -> table.addCell(createInfoCell(key, value))));

		// adding header to doc
		doc.add(table);
		doc.add(new Paragraph("\n\n"));
	}

	private PdfPCell createInfoCell(String name, String info) {
		PdfPCell cell = new PdfPCell();
		cell.setPhrase(new Phrase(name + ": " + info));
		return cell;
	}

	private PdfPCell createHeaderCell() {
		PdfPCell headerTitle = new PdfPCell();
		headerTitle.setBackgroundColor(BaseColor.LIGHT_GRAY);
		headerTitle.setBorderWidth(2);
		headerTitle.setPhrase(new Phrase("Test Report"));
		headerTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
		headerTitle.setVerticalAlignment(Element.ALIGN_CENTER);
		return headerTitle;
	}

	@Override
	public void initDocument(String id, String scenarioName, boolean scenarioStatus, ReportFileStructure fileStructure)
			throws Exception {
		reportFile = report.createReportFile(id, scenarioName, scenarioStatus, "pdf");

		if (doc == null) {
			doc = new Document();

			PdfWriter.getInstance(doc, new FileOutputStream(reportFile));
			doc.open();
		}
	}

	@Override
	public File saveFile() throws Exception {

		if (doc != null) {
			doc.close();
			doc = null;
		}

		return reportFile;
	}
}
