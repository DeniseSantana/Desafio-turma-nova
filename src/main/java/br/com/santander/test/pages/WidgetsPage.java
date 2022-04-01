package br.com.santander.test.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import br.com.santander.core.webdriver.page.PageMaker;
import lombok.Getter;

@Getter
public class WidgetsPage  implements PageMaker { 
	
	@FindBy(xpath = "(//div[@class='row center']/a[text()='Começar Automação Web'])")
	private WebElement Btncomecar;
	
	@FindBy(xpath = "(//a[@class='collapsible-header waves-teal'])[3]")
	private WebElement Btnwidgets;
	
	@FindBy(xpath = "//a[text()='Accordion']")
	private WebElement Btnaccordion; 
	
	@FindBy(xpath = "(//div[@class='collapsible-header'])[1]")
	private WebElement Btnfirst; 
	
	@FindBy(xpath = "(//div[@class='collapsible-header'])[2]")
	private WebElement Btnsecond; 
	
	@FindBy(xpath = "(//div[@class='collapsible-header'])[3]")
	private WebElement Btnthird; 
	
	@FindBy(xpath = "//div[@class='col s9']")
	private WebElement Validaraccordion; 
	////
	
	@FindBy(xpath = "//a[text()='Autocomplete']")
	private WebElement Clicautocomplete; 
	
	@FindBy(xpath = "//input[@id='autocomplete-input']")
	private WebElement Selecionaracre; 
	
	@FindBy(xpath = "//h5[@class='red-text text-darken-1 center']")
	private WebElement Valaidarautocomplete; 
	//////
	

	@FindBy(xpath = "//a[text()='Datapicker']")
	private WebElement Btndatapicker; 
	
	@FindBy(xpath = "//input[@id='datepicker']")
	private WebElement Btndata; 
	
	@FindBy(xpath = "(//input[@class='select-dropdown dropdown-trigger'])[1]")
	private WebElement Btnmarch; 
	
	@FindBy(xpath = "(//span[text()='March'])")
	private WebElement march; 
	
	@FindBy(xpath = "(//input[@class='select-dropdown dropdown-trigger'])[2]")
	private WebElement Btnano; 
	
	@FindBy(xpath = "//span[text()='2022']")
	private WebElement Btn2022; 
	
	@FindBy(xpath = "(//button[@class='datepicker-day-button'])[6]")
	private WebElement Dia; 
	
	@FindBy(xpath = "//button[@class='btn-flat datepicker-done waves-effect']")
	private WebElement Btnok; 
	
	@FindBy(xpath = "//div[@class='col s8 tamanhodiv2 center']")
	private WebElement Validardatapicker; 
	
}
