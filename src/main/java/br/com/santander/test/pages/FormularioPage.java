package br.com.santander.test.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.santander.core.webdriver.page.PageMaker;
import lombok.Getter;

@Getter

public class FormularioPage implements PageMaker{

//CT=01
	
	@FindBy(xpath = "(//a[@class='btn waves-light orange'])[1]")
	private WebElement btnInicio;
	@FindBy(xpath = "//a[text() ='Formulário']")
	private WebElement lblFormulario;
	@FindBy(xpath = "//a[text()='Criar Usuários']")
	private WebElement lblCriarUsuário;
	
	//Preenchimento Formulário
	
	@FindBy(xpath = "//input[@id='user_name']")
	private WebElement cmpNome;
	@FindBy(xpath = "//input[@id = 'user_lastname']")
	private WebElement cmpUltimoNome;
	@FindBy(xpath = "//input[@id = 'user_email']")
	private WebElement cmpEmail;
	@FindBy(xpath = "//input[@id = 'user_address']")
	private WebElement cmpEndereco;
	@FindBy(xpath = "//input[@id = 'user_university']")
	private WebElement cmpUniversidade;
	@FindBy(xpath = "//input[@id = 'user_profile']")
	private WebElement cmpProfissao;
	@FindBy(xpath = "//input[@id = 'user_gender']")
	private WebElement cmpGenero;
	@FindBy(xpath = "//input[@id = 'user_age']")
	private WebElement cmpIdade;
	@FindBy(xpath = "//input[@name='commit']")
	private WebElement btnCriar;
	
	//Validação da Pagina
	
	@FindBy(xpath = "//p[@id='notice']")
	private WebElement ValidacaoCriarUsuario;
	@FindBy(xpath = "//a[@class='btn waves-light red']")
	private WebElement ValidacaoCriarUsuario1;
	
//CT=02
	
	//@FindBy(xpath = "(//a[@class='material-icons'])[24]")
	//private WebElement btnexcluir;
	@FindBy(xpath = "//table[@class='highlight striped responsive-table']//td[text()='felipemoreno@teste.com']")
	private WebElement btnusuarioexclussao;
	@FindBy(xpath = "//a[text()='Lista de Usuários']")
	private WebElement lblListaUsuario;
	@FindBy(xpath = "(//a[@class='material-icons'])[12]")
	private WebElement btnExcluir;
	@FindBy(xpath = "//a[text()='3']")
	private WebElement lblOpcao;
	
	//validação da Pagina
	
	@FindBy(xpath = "//h5[text()='Lista de Usuários!!']")
	private WebElement ValidacaoExclussaoUsuario;
	
	

	
	
	
	
}
