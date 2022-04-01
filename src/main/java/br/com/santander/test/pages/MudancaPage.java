package br.com.santander.test.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.santander.core.webdriver.page.PageMaker;
import lombok.Getter;

@Getter
public class MudancaPage implements PageMaker {
	@FindBy(xpath= "//a[text()='Começar Automação Web']")
	private WebElement paginainicial;
	@FindBy(xpath= "(//a[@class='collapsible-header waves-teal'])[2]")
	private WebElement iconemudanca ;
	@FindBy(xpath= "//a[text()='Alert']")
	private WebElement btnalert;
	@FindBy(xpath= "(//button[@class='btn  waves-light green'])[1]")
	private WebElement btnjsalert;
	@FindBy(xpath= "(//button[@class='btn  waves-light green'])[2]")
	private WebElement btnconfirm;
	@FindBy(xpath= "(//button[@class='btn  waves-light green'])[3]")
	private WebElement btnprompt;
	@FindBy(xpath= "//a[@class='btn red']")
	private WebElement validarpagina;
	
	//CT=11
	@FindBy(xpath= "//a[text()='Iframe']")
	private WebElement btniframe;
	@FindBy(xpath= "//iframe[@id='id_do_iframe']")
	private WebElement btniframe2;
	@FindBy(xpath= "//input[@id='first_name']")
	private WebElement firstname;
	@FindBy(xpath= "//input[@id='last_name']")
	private WebElement lastname ;
	@FindBy(xpath= "//input[@id='password']")
	private WebElement password;
	@FindBy(xpath= "//input[@id='email']")
	private WebElement email;
	@FindBy(xpath= "//textarea[@id='textarea1']")
	private WebElement txtarea;
	@FindBy(xpath= "//a[@class='btn red']")
	private WebElement btnvoltar;
	@FindBy(xpath= "//a[@class='btn red']")
	private WebElement validartela;
	
	
	//CT=12
	@FindBy(xpath= "(//a[text()='Janela'])[1]")
	private WebElement btnjanela;
	@FindBy(xpath= "//a[@class='btn waves-light red']")
	private WebElement cliqueaqui;
	@FindBy(xpath= "//img[@class='center']")
	private WebElement validandopagina;
	
	//CT=13
	@FindBy(xpath= "(//a[text()='Modal'])[1]")
	private WebElement btnmodal;
	@FindBy(xpath= "//a[@class='waves-light btn modal-trigger']")
	private WebElement btnabrir;
	@FindBy(xpath= "//h4[text()='Modal Teste']")
	private WebElement btnmodalteste;
	@FindBy(xpath= "//a[@class='modal-close waves-green btn-flat']")
	private WebElement btnfechar;
	@FindBy(xpath= "//h4[text()='Modal Teste']")
	private WebElement validarmodal;
}
