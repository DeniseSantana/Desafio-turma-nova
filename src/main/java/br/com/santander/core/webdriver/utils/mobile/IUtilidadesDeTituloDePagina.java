package br.com.santander.core.webdriver.utils.mobile;

public interface IUtilidadesDeTituloDePagina extends IUtilidadesDeElemento
{
	/**Retorna verdadeiro se e quando a pesquisa for bem-sucedida.*/
	default boolean tituloDaPaginaComecaCom( final String frase )
	{
		return getWebDriver().getTitle().toLowerCase().startsWith( frase.toLowerCase() );
	}

	/**Retorna verdadeiro se e quando a pesquisa for bem-sucedida.*/
	default boolean tituloDaPaginaTerminaCom( final String frase )
	{
		return getWebDriver().getTitle().toLowerCase().endsWith( frase.toLowerCase() );
	}

	/**Retorna verdadeiro se e quando a pesquisa for bem-sucedida.*/
	default boolean tituloDaPaginaContem( final String frase )
	{
		return getWebDriver().getTitle().toLowerCase().contains( frase.toLowerCase() );
	}
}
