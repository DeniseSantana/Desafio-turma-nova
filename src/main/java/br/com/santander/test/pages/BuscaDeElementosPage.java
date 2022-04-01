package br.com.santander.test.pages;

import br.com.santander.core.webdriver.page.PageMaker;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import lombok.Getter;

@Getter
public class BuscaDeElementosPage implements PageMaker {

	@FindBy(xpath = "(//a[text()='Começar Automação Web'])") // CT03
	private WebElement comecarautomacaoweb;

	@FindBy(xpath = "(//a[text()='Busca de elementos'])")
	private WebElement clicandobuscadelementos;
	
	@FindBy(xpath = "(//a[text()='Links'])")
	private WebElement clicandoemlinks;
	
	@FindBy(xpath = "(//a[text()='Ok 200 - Sucess'])")
	private WebElement clicandook200;
	
	@FindBy(xpath = "(//a[text()='Voltar'])")
	private WebElement voltargeral;
	
	@FindBy(xpath = "(//a[text()='Erro 400 - Bad Request'])")
	private WebElement erro400;
	
	@FindBy(xpath = "(//a[text()='Erro 404 - Page not found'])")
	private WebElement erro400pagenotfound;
	
	@FindBy(xpath = "(//a[text()='Erro 500 - Internal Server Error'])")
	private WebElement erro500;
	
	@FindBy(xpath = "(//h5[text()='Internal Server Error!!'])")
	private WebElement validartelainternal;
	
	@FindBy(xpath = "(//a[text()='Inputs e TextField'])") // CT04
	private WebElement clicandoinputsetextfield;
	
	@FindBy(xpath = "(//input[@id='first_name'])")
	private WebElement clicandofirstname;
	
	@FindBy(xpath = "(//input[@id='last_name'])")
	private WebElement clicandolastname;
	
	@FindBy(xpath = "(//input[@id='password'])")
	private WebElement clicandopass;
	
	@FindBy(xpath = "(//input[@id='email'])")
	private WebElement clicandoemail;
	
	@FindBy(xpath = "(//textarea[@id='textarea1'])") //  (//label[@for='textarea1'])
	private WebElement clicandotextarea;
	
	@FindBy(xpath = "(//h5[text()='Inputs, TextField e TextArea'])")
	private WebElement validandoinputstext;
	
	@FindBy(xpath = "(//a[text()='Botões'])") // CT05
	private WebElement clicandobotoes;
	
	@FindBy(xpath = "(//a[@id='teste'])")
	private WebElement clicandoraised;
	
	@FindBy(xpath = "(//i[text()='add'])")
	private WebElement clicandofloating;
	
	@FindBy(xpath = "(//a[text()='Button'])")
	private WebElement clicandoflat;
	
	@FindBy(xpath = "(//i[text()='send'])")
	private WebElement clicandosubmit;
	
	@FindBy(xpath = "(//h5[text()='Disable'])")
	private WebElement validandobotoes;
	
	@FindBy(xpath = "(//a[text()='Radio e Checkbox'])") // CT06
	private WebElement clicandoemradioecheck;
	
	@FindBy(xpath = "(//label[text()='Blue'])")
	private WebElement selecionandoblue;
	
	@FindBy(xpath = "(//label[text()='Yellow'])")
	private WebElement selecionandoyellow;
	
	@FindBy(xpath = "(//input[@id='grey'])")
	private WebElement selecionandogrey;
	
	@FindBy(xpath = "(//input[@id='black'])")
	private WebElement selecionandoblack;
	
	@FindBy(xpath = "(//h5[text()='CheckBox'])")
	private WebElement validandochecktela;
	
	@FindBy(xpath = "(//a[text()='Dropdown e Select'])") // CT07
	private WebElement clicandoemdrop;
	
	@FindBy(xpath = "(//span[text()='Naruto'])")
	private WebElement desenhofav;
	
	@FindBy(xpath = "(//span[text()='Ronaldo'])")
	private WebElement options;
	
	@FindBy(xpath = "(//span[text()='Brasil'])")
	private WebElement escolhapais;
	
	@FindBy(xpath = "(//span[text()='Homem'])")
	private WebElement escolhagenero;
	
	@FindBy(xpath = "(//span[text()='Masculino'])")
	private WebElement escolhasexo;
	
	@FindBy(xpath = "(//select[@id='dropdown'])")
	private WebElement escolhabrowser;
	
	@FindBy(xpath = "(//h5[text()='Selects'])")
	private WebElement validandodrop;
	
	@FindBy(xpath = "(//a[text()='Textos'])") // CT08
	private WebElement clicandoemtextos;
	
	@FindBy(xpath = "(//i[text()='link'])")
	private WebElement primeirotexto;
	
	//@FindBy(xpath = "(//h1[text()='Debugando com o ByeBug'])")
	//private WebElement validandoteladotexto;
	
	@FindBy(xpath = "(//h1[@class='ct cu cv cw b cx cy cz da db dc dd de df dg dh di dj dk dl dm dn do dp dq dr ds'])")
	private WebElement validandoteladotextodois;
	
	@FindBy(xpath = "(//a[text()='Tabela'])") // CT08
	private WebElement clicandoemtabela;
	
	@FindBy(xpath = "(//h5[text()='Bem vindo ao Site de Automação do Batista.'])") // CT08
	private WebElement valindandotelavolta;
}
