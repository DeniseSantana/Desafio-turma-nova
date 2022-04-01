package br.com.santander.test.sheet.model;

import br.com.santander.core.sheet.TestData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder //(setterPrefix = "with")
public class MobileData implements TestData {
	private String agencia;
	private String conta;
	private String usuario;
	private String senha;
}
