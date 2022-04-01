package br.com.santander.test.pages;

import lombok.Getter;

@Getter
public class RestPage {

	public static final String BASE_URL = "https://bookstore.toolsqa.com";

	private String usuario = "userName";
	private String senha = "password";
	
	private String linkGerarToken = "/Account/v1/GenerateToken";
	private String token = "token";
	
	private String tipoConteudo = "Content-Type";
	private String conteudoJson = "application/json";
	
	private String linkLivrosDisponiveis = "/BookStore/v1/Books";
	private String livros = "books";
	private String isbn = "isbn";
}
