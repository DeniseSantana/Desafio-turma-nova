package br.com.santander.test.logic;

import static br.com.santander.test.support.Context.rest;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import br.com.santander.test.pages.RestPage;
import br.com.santander.test.sheet.model.RestData;
import br.com.santander.test.support.Context;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RestLogic {

	private static String bookId;

	private RestPage restPage;
	private RestData restData;

	public RestLogic() {
		restPage = new RestPage();
		restData = (RestData) Context.testData();
		
		rest().setBaseURI(RestPage.BASE_URL);
	}

	public void realizarAutorizacaoDoUsuario() {
		log.info("eu sou um usuario autorizado");
		rest().newRequest();
		rest().header(restPage.getTipoConteudo(), restPage.getConteudoJson());
		rest().setResponse(rest().body(restPage.getUsuario(), restData.getUserName()) //
				.post(restPage.getLinkGerarToken()));
	}

	public void validarListaLivrosDisponiveis() {
		log.info("valido a lista de livros disponivel");

		rest().newRequest();
		rest().setResponse(rest().getRequest().get(restPage.getLinkLivrosDisponiveis()));
		
		List<Map<String, String>> books = rest().getResponseValues(restPage.getLivros());		
		assertTrue(books.size() > 0);
		
		bookId = books.get(0).get(restPage.getIsbn());
		log.info("coleto o id do livro (isbn): " + bookId);
	}
}