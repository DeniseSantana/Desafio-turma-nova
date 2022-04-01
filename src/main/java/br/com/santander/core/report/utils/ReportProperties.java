package br.com.santander.core.report.utils;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import br.com.santander.core.utils.properties.PropertyReader;

public class ReportProperties extends PropertyReader {
	private static final String DEFAULT_REPORT_FORMAT = "pdf";
	private static final String EVIDENCES_DEFAULT_DIRECTORY = "evidences";
	private static final String JSON_PROPERTIES = "report.properties";
	private static final String DATE_FORMAT_FOLDER = "yyyy_MM_dd";

	public ReportProperties() throws Exception {
		super(JSON_PROPERTIES);
	}

	public boolean reportIsActive() {
		return Boolean.parseBoolean(getProperty("report.active"));
	}

	public String getProject() {
		return getProperty("report.project");
	}

	public String getTester() {
		return getProperty("report.tester");
	}

	public String getReportDirectory() {
		String reportDir = getProperty("report.directory");
		return ((reportDir.isEmpty()) ? EVIDENCES_DEFAULT_DIRECTORY : reportDir) + File.separator + DateTimeUtils.getFormattedDate(DATE_FORMAT_FOLDER);
	}

	public List<String> getReportFileTypes() {
		String property = getProperty("report.fileTypes");
		if ("".equals(property)) {
			return Arrays.asList(DEFAULT_REPORT_FORMAT);
		}

		return Arrays.asList(property.replace(" ", "").split(","));
	}
}
