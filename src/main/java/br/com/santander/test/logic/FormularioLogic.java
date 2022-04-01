package br.com.santander.test.logic;

import static br.com.santander.test.support.Context.rGen;
import static br.com.santander.test.support.Context.web;

import br.com.santander.core.webdriver.page.PageMaker;
import br.com.santander.test.pages.FormularioPage;
import br.com.santander.test.sheet.model.FormularioData;
import br.com.santander.test.support.Context;
import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;

@Slf4j
public class FormularioLogic {
	private FormularioPage formularioPage;
	private FormularioData formularioData;

	public FormularioLogic() {
		formularioPage = PageMaker.getInstance(FormularioPage.class, web().getWebDriver());
		formularioData = (FormularioData) Context.testData();

	}

	public void navegacaoAutomacaoBatista() {
		web().navigateToUrl("https://automacaocombatista.herokuapp.com/");
		web().getWebDriver().manage().window().maximize();
		rGen().registerStep(web().getScreenshot(), "Que estou na tela Principal");
	}

	public void ListaFuncionalidades() {
		web().click(formularioPage.getBtnInicio());
		rGen().registerStep(web().getScreenshot(), "Pagina de Funcionalidades");
	}

	public void CadastrarUsuário() {

		web().click(formularioPage.getLblFormulario());
		web().click(formularioPage.getLblCriarUsuário());
		web().insertText(formularioPage.getCmpNome(), formularioData.getNome());
		web().insertText(formularioPage.getCmpUltimoNome(), formularioData.getUltimo_nome());
		web().insertText(formularioPage.getCmpEmail(), formularioData.getEmail());
		web().insertText(formularioPage.getCmpEndereco(), formularioData.getEndereco());
		web().insertText(formularioPage.getCmpUniversidade(), formularioData.getUniversidade());
		web().insertText(formularioPage.getCmpProfissao(), formularioData.getProfissao());
		web().insertText(formularioPage.getCmpGenero(), formularioData.getGenero());
		web().insertText(formularioPage.getCmpIdade(), formularioData.getIdade());
		rGen().registerStep(web().getScreenshot(), "Dados Preenchidos com Sucesso");
		web().click(formularioPage.getBtnCriar());

	}

	public boolean ValidarCriacaoUsuario() {
		web().elementIsVisible(formularioPage.getValidacaoCriarUsuario(), 5);
		rGen().registerStep(web().getScreenshot(), ("validação do formulario preenchido"));
		return web().elementIsVisible(formularioPage.getValidacaoCriarUsuario());
	}

	public boolean ValidarCriacaoUsuario1() {
		web().elementIsVisible(formularioPage.getValidacaoCriarUsuario1(), 5);
		rGen().registerStep(web().getScreenshot(), ("validação do formulario preenchido 1"));
		return web().elementIsVisible(formularioPage.getValidacaoCriarUsuario1());
	}

	public void ExcluirUsuário() {

		web().click(formularioPage.getLblFormulario());
		web().click(formularioPage.getLblListaUsuario());
		//percorre todo o site procurando elemento especifico
		for (int i = 2; i <= 6; i++) {
			if (web().elementIsVisible(formularioPage.getBtnusuarioexclussao())) {
				web().click(formularioPage.getBtnExcluir());
				Alert selecao = web().getWebDriver().switchTo().alert();
				selecao.accept();
				break;
			}
			WebElement pageCurrent = web().getWebDriver().findElement(By.xpath("//a[text()=" + i + "]"));
			web().elementIsVisible(pageCurrent);
			web().scroll(pageCurrent);
			web().click(pageCurrent);
		}
	}

	public boolean ValidarExclussãoUsuario() {

		web().elementIsVisible(formularioPage.getValidacaoExclussaoUsuario(), 5);
		rGen().registerStep(web().getScreenshot(), ("Usuário excluido"));
		return web().elementIsVisible(formularioPage.getValidacaoExclussaoUsuario());
	}
}
