package br.com.santander.core.report.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.santander.core.report.utils.DateTimeUtils;
import br.com.santander.core.report.utils.ReportProperties;
import lombok.Getter;
import lombok.Setter;

public class Report {

	private static final String DATE_FORMAT_FILENAME = "ddMMyy_HHmmss";
	private static final String SEPARATOR = "_";
	private static final String FOLDER_SUCCESS = "success";
	private static final String FOLDER_FAILURE = "failure";
	private ReportProperties prop;

	@Getter
	@Setter
	private ReportFileStructure fileStructure;
	@Getter
	private List<Step> steps;
	@Getter
	private Header header;

	public Report() {
		try {
			prop = new ReportProperties();
		} catch (Exception e) {
			throw new RuntimeException(
					"Could not load report properties. Check if you have report.properties in resources.", e);
		}

		steps = new ArrayList<>();
	}

	public void addStep(byte[] image, String description) {
		steps.add(Step.builder().image(image).sub(description).build());
	}

	public boolean stepsAreEmpty() {
		return steps.isEmpty();
	}

	public void addHeader(Header header) {
		this.header = header;
	}

	public void clearSteps() {
		steps.clear();
	}
		
	private File createFolder(boolean scenarioStatus, String reportFileName) {
		String nameFileStatus = scenarioStatus ? FOLDER_SUCCESS : FOLDER_FAILURE;
		File fileReport = new File(prop.getReportDirectory() + File.separator + nameFileStatus);
		
		if(!fileReport.exists())
			fileReport.mkdirs();		
		
		return new File("./" + fileReport + File.separator + reportFileName);
	}

	// TODO improve this method
	public File createReportFile(String id, String scenarioName, boolean scenarioStatus, String fileExtension) {
		String reportFileName = id //
				+ SEPARATOR //
				+ DateTimeUtils.getFormattedDate(DATE_FORMAT_FILENAME) //
				+ SEPARATOR //
				+ scenarioName.replace(" ", SEPARATOR) //
				+ "." //
				+ fileExtension; //

		if (fileStructure.getUserInfo() == null || fileStructure.getGroup() == null) {
			// evidencias serão gravadas sem separacao de pastas.			
			return createFolder(scenarioStatus, reportFileName);
		} else {
			// estrutura de pastas conforme solicitado
			String cenario = fileStructure.getFunctionality().replaceAll(fileStructure.getGroup(), "");
			if (cenario.equals("")) {
				cenario = "";
			} else {
				cenario += File.separator;
			}
			String nameAndId = fileStructure.getUserInfo();
			String nomePasta = fileStructure.getGroup() + File.separator + cenario + nameAndId;

			String startingDir = "./";
			String filePath = prop.getReportDirectory() + File.separator + nomePasta;

			File file = null;
			String[] folders = filePath.split(File.separator);
			for (String folder : folders) {
				startingDir += folder;
				file = new File(startingDir);
				if (!file.exists()) {
					file.mkdir();
				}
				startingDir += File.separator;
			}

			return new File("./" + prop.getReportDirectory() + File.separator + nomePasta + File.separator + reportFileName);
		}
	}

}
