package br.com.santander.core.report.generator;

import br.com.santander.core.report.model.Report;
import lombok.extern.slf4j.Slf4j;

/**
 * Factory of test report generators
 *
 */
@Slf4j
public class GeneratorFactory {
	private GeneratorFactory() {
	}

	// TODO melhorar extrair para enum
	public static Generator getGenerator(String type, Report report) {
		switch (type) {
		case "pdf":
			return new GeneratorPdf(report);
		case "docx":
			return new GeneratorDocx(report);
		default:
			log.info("Generator type not found. Returning a pdf generator.");
			return new GeneratorPdf(report);
		}
	}
}
