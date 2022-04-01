package br.com.santander.test.sheet.model;

import br.com.santander.core.sheet.TestData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder //(setterPrefix = "with")
public class RestData implements TestData {
	private String userId;
	private String userName;
	private String password;
}
