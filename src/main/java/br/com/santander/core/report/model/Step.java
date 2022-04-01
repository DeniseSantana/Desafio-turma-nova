package br.com.santander.core.report.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder //(setterPrefix = "with")
public class Step {
	private String sub;
	private byte[] image;
}
