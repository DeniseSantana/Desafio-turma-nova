package br.com.santander.test.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.santander.core.webdriver.page.PageMaker;
import lombok.Getter;
@Getter
public class IteracoesPage implements PageMaker {
	
	
	@FindBy(xpath = "(//a[text()='Começar Automação Web'])")
	private WebElement btnacessandoautomacao;
	
	@FindBy(xpath = "(//a[text()='Iterações'])")
	private WebElement btnIteracoes;
	
	@FindBy(xpath = "(//a[text()='Drag And Drop'])")
	private WebElement btndraganddrop;
	
	@FindBy(xpath = "(//img[@id='winston'])")
	private WebElement btnwinston;	
	
	@FindBy(xpath = "(//div[@id='dropzone'])")
	private WebElement btndropzone;	
	
	@FindBy(xpath = "(//h5[text()='Arraste a carinha para dento do quadrado!!'])")
	private WebElement validacaodrnagdrop;	

///////////////////////////////////////// ct 17 ///////////////////////////////////////////////////////////	
	
	@FindBy(xpath = "(//a[text()='Mousehover'])")
	private WebElement btnmousehover;
	
	@FindBy(xpath = "(//p[text()='Passe o mouse aqui'])")
	private WebElement btninteracaomouse;
	
	@FindBy(xpath = "(//p[text()='Você usou o mouse hover!'])")
	private WebElement validandomousehover;

////////////////////////////////////////////////// ct 18 ////////////////////////	
}
