package br.com.santander.core.report.generator;

import java.io.File;

import br.com.santander.core.report.model.ReportFileStructure;
import br.com.santander.core.report.model.Report;

/**
 * Class describing a test report generator
 *
 */
public abstract class Generator {

	protected Report report;

	public Generator(Report report) {
		this.report = report;
	}

	public abstract void addSteps() throws Exception;

	public abstract void addHeader() throws Exception;
	
	public abstract void initDocument(String id, String scenarioName, boolean scenarioStatus, ReportFileStructure fileStructure) throws Exception;

	public abstract File saveFile() throws Exception;

}