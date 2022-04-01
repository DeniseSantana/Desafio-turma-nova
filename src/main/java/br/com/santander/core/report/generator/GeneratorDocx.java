package br.com.santander.core.report.generator;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.jaxb.Context;
import org.docx4j.model.table.TblFactory;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.Drawing;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Tc;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;

import br.com.santander.core.report.model.ReportFileStructure;
import br.com.santander.core.report.model.Header;
import br.com.santander.core.report.model.Report;
import br.com.santander.core.report.model.Step;

/**
 * Implementation of Generator to docx files
 */
public class GeneratorDocx extends Generator {

	private MainDocumentPart doc;
	private WordprocessingMLPackage wordPackage;
	private String scenarioName;
	private String id;
	private boolean scenarioStatus;

	public GeneratorDocx(Report report) {
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
			byte[] image = step.getImage();
			String sub = step.getSub();

			BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordPackage, image);
			Inline inline = imagePart.createImageInline("Step " + i + " image", "Step " + i + " image", i, i, false);
			P imageParagraph = addImageToParagraph(inline);
			doc.getContent().add(imageParagraph);
			doc.addParagraphOfText(sub);

			// TODO apparently not necessary to add explicit page breaks, here if its needed

		}

	}

	private P addImageToParagraph(Inline inline) {
		// TODO add frame to image
		ObjectFactory factory = new ObjectFactory();
		P p = factory.createP();
		R r = factory.createR();

		p.getContent().add(r);
		Drawing drawing = factory.createDrawing();

		r.getContent().add(drawing);
		drawing.getAnchorOrInline().add(inline);

		return p;
	}

	@Override
	public void addHeader() throws Exception {
		Header header = report.getHeader();
		// TODO check how to take off the extra line generated in table

		// create table object
		int rowNumber = header.getTotalLinesOfSections() + 1;
		int colNumber = 1;
		Tbl tbl = TblFactory.createTable(rowNumber, colNumber,
				wordPackage.getDocumentModel().getSections().get(0).getPageDimensions().getWritableWidthTwips()
						/ colNumber);
		// add title cell
		// TODO add centralization and bg color
		((Tc) ((Tr) tbl.getContent().get(0)).getContent().get(0)).getContent()
				.add(tableTitleParagraph(report.getHeader().getTitle()));

		// add header content
		// TODO not putting section title yet
		AtomicInteger i = new AtomicInteger(1);
		header.getSections().forEach((sectionTitle, sectionMap) -> sectionMap.forEach((key, value) -> {
			((Tc) ((Tr) tbl.getContent().get(i.get())).getContent().get(0)).getContent()
					.add(doc.createParagraphOfText(key + ": " + value));
			i.getAndIncrement();
		}));

		wordPackage.getMainDocumentPart().addObject(tbl);
	}

	private P tableTitleParagraph(String title) {
		ObjectFactory factory = Context.getWmlObjectFactory();
		P p = factory.createP();
		R r = factory.createR();
		Text t = factory.createText();
		t.setValue(title);

		r.getContent().add(t);
		p.getContent().add(r);

		RPr rpr = factory.createRPr();
		rpr.setB(new BooleanDefaultTrue());

		r.setRPr(rpr);

		return p;
	}
	
	@Override
	public void initDocument(String id, String scenarioName, boolean scenarioStatus, ReportFileStructure fileStructure) throws Exception {
		this.scenarioStatus = scenarioStatus;
		this.scenarioName = scenarioName;
		this.id = id;
		
		wordPackage = WordprocessingMLPackage.createPackage();
		doc = wordPackage.getMainDocumentPart();
	}

	@Override
	public File saveFile() {
		File reportFile = report.createReportFile(id, scenarioName, scenarioStatus, "docx");
		try {
			wordPackage.save(reportFile);
		} catch (Docx4JException e) {
			throw new RuntimeException("Error saving doc evidence.", e);
		}
		return reportFile;
	}

}
