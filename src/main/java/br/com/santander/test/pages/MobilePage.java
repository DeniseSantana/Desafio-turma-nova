package br.com.santander.test.pages;

import org.openqa.selenium.WebElement;

import br.com.santander.core.webdriver.page.PageMaker;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import lombok.Getter;

@Getter
public class MobilePage implements PageMaker {

	@AndroidFindBy(xpath = "//*[contains(@text,'Acessar minha conta')]")
	@iOSXCUITFindBy(xpath = "//*[contains(@name,'Acessar minha conta')]")
	private WebElement lblAcesseMinhaConta;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.santandermovelempresarial.app:id/select_environment_hk_radio_button']")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='HK1']")
	private WebElement rdbHomologacao;
	
	@AndroidFindBy(xpath = "//*[@resource-id='com.santandermovelempresarial.app:id/select_environment_continue_button']")
	private WebElement btnContinuar;
	
	@AndroidFindBy(id = "com.santandermovelempresarial.app:id/edittext_agencia")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[1]")
	private WebElement txtAgencia;
	
	@AndroidFindBy(id = "com.santandermovelempresarial.app:id/edittext_conta")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[2]")
	private WebElement txtConta;
	
	@AndroidFindBy(id = "com.santandermovelempresarial.app:id/edittext_usuario")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeTextField[3]")
	private WebElement txtUsuario;
	
	@AndroidFindBy(id = "com.santandermovelempresarial.app:id/edittext_password")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeSecureTextField[1]")
	private WebElement txtSenha;
	
	@AndroidFindBy(id = "com.santandermovelempresarial.app:id/button_login")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Go' or @name='OK']")
	private WebElement btnEntrar;
	
	@AndroidFindBy(id = "com.santandermovelempresarial.app:id/menu_bottom_menu")
	@iOSXCUITFindBy(xpath = "//XCUIElementTypeButton[@name='Menu']")
	private WebElement btnMenu;
}