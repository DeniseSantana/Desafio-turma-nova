package br.com.santander.core.leanft.apps;

import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.TestObject;
import com.hp.lft.sdk.web.Browser;
import com.hp.lft.sdk.web.BrowserDescription;
import com.hp.lft.sdk.web.BrowserFactory;
import com.hp.lft.sdk.web.BrowserType;

import br.com.santander.core.leanft.object.ObjectLeanFT;
import br.com.santander.core.leanft.utils.StoredActionsWeb;

public abstract class WebApp extends ObjectLeanFT {

	protected static Browser browser = null;
	protected StoredActionsWeb actions = null;

	public WebApp() {
		actions = new StoredActionsWeb();
	}

	protected void iniciarApp(String url, BrowserType browserType, boolean restart) {
		try {
			if (restart) {
				browser = BrowserFactory.launch(browserType);
				browser.navigate(url);
			} else {
				browser = BrowserFactory.attach(new BrowserDescription.Builder().build());
			}

			changeParentObject(browser);

		} catch (GeneralLeanFtException e) {
			e.printStackTrace();
		}
	}

	protected void changeParentObjectBrowser(TestObject object) {
		changeParentObject(browser);
		changeParentObject(object);
	}

	protected void closeApp() {
		try {
			browser.close();
		} catch (GeneralLeanFtException e) {
			e.printStackTrace();
		}
	}

}
