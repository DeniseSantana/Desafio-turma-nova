package br.com.santander.test.sheet.model;

import br.com.santander.core.sheet.TestData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder //(setterPrefix = "with")
public class FormularioData implements TestData{
	
	private String idcenario;
	private String nome;
	private String ultimo_nome;
	private String email;
	private String endereco;
	private String universidade;
	private String profissao;
	private String genero;
	private String idade;
	
}
