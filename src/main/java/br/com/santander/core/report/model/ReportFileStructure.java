package br.com.santander.core.report.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class ReportFileStructure {
	String userInfo;
	String functionality;
	String group;
	String idScenario;
	String idUser;

	public static ReportFileStructure empty() {
		return builder().build();
	}

	public String getScenarioName(String scenarioNameFeature) {
		if (idScenario != null && !idScenario.isEmpty() && idUser != null && !idUser.isEmpty()) {
			return idScenario + "_" + idUser;
		}
		return scenarioNameFeature;
	}
}