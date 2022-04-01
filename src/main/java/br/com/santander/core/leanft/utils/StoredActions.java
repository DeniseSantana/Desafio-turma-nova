package br.com.santander.core.leanft.utils;

import com.hp.lft.sdk.Clickable;
import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.SupportSendKeys;
import com.hp.lft.sdk.TestObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class StoredActions {

	/**
	 * Aguarda até que um determinado texto seja exibido no elemento.
	 * 
	 * @param object
	 *            Elemento a ser verificado.
	 * @param text
	 *            Texto esperado.
	 * @param timeOut
	 *            Tempo limite para verificação.
	 */
	public void waitText(TestObject object, String text, int timeOut) {
		try {
			while (!object.getVisibleText().equals(text)) {
				if (timeOut == 0)
					break;

				wait(1);
				timeOut--;
			}
		} catch (Exception e) {
			log.error("Erro ao esperar o texto", e);
		}
	}

	/**
	 * Realiza ações de clique no elemento desejado.
	 * 
	 * @param object
	 *            Elemento a ser executado a ação.
	 */
	public void click(TestObject object) {
		try {
			((Clickable) object).click();

		} catch (GeneralLeanFtException e) {
			log.error("Erro ao clicar", e);
		}
	}

	/**
	 * Verificar se um determinado elemento está visível na aplicação.
	 * 
	 * @param object
	 *            Elemento a ser verificado.
	 * @return Retorna verdadeiro ou falso.
	 */
	public boolean exist(TestObject object) {

		try {
			if (object.exists(1))
				return true;
		} catch (GeneralLeanFtException e) {
			log.error("Erro checando objeto", e);
		}

		return false;
	}

	/**
	 * Aguarda até que o elemento esteja visivel por um determinado tempo
	 * 
	 * @param object
	 *            Elemento a ser verificado.
	 * @param timeoutInSeconds
	 *            Tempo limite de verificação.
	 * @return Retorna o elemento.
	 */
	public TestObject waitForElement(TestObject object, int timeoutInSeconds) {
		try {
			for (int i = 0; i < timeoutInSeconds; i++) {
				if (object.exists(1))
					return object;
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			log.error("Erro aguardando elemento", e);
		}

		return null;
	}

	/**
	 * Aguarda até que o elemento esteja visivel por um determinado tempo
	 * 
	 * @param object
	 *            Elemento a ser verificado.
	 * @param timeoutInSeconds
	 *            Tempo limite de verificação.
	 * @return Retorna verdadeiro ou falso.
	 */
	public Boolean waitUntilExist(TestObject object, int timeoutInSeconds) {
		try {
			for (int i = 0; i < timeoutInSeconds; i++) {
				if (object.exists(1))
					return true;
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			log.error("Erro aguardando objeto", e);
		}

		return false;
	}

	/**
	 * Preenche um texto em um determinado elemento do tipo inputText.
	 * 
	 * @param object
	 *            Elemento a ser preenchido.
	 * @param text
	 *            Texto a ser escrito.
	 */
	public void sendKeys(TestObject object, String text) {
		try {
			((SupportSendKeys) object).sendKeys(text);
		} catch (GeneralLeanFtException e) {
			log.error("Erro ao digitar o texto", e);
		}
	}

	public void findChildren(TestObject parent, TestObject child) {
		try {
			parent.findChildren(child.getClass(), child.getDescription());
		} catch (Exception e) {
			log.error("Erro ao localizar os filhos", e);
		}
	}

	/**
	 * Obter um determinado texto em um elemento.
	 * 
	 * @param object
	 *            Elemento a ser verificado.
	 * @return Retorna o texto visível no elemento ou retornará nulo caso nenhum
	 *         texto esteja disponível.
	 */
	public String getText(TestObject object) {
		try {
			return object.getVisibleText();
		} catch (GeneralLeanFtException e) {
			log.error("Erro ao capturar o texto", e);
		}

		return null;
	}

	public void waitBySeconds(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (Exception e) {
			log.error("Erro ao aguardar", e);
		}
	}
}