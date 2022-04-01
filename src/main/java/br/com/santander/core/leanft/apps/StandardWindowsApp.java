package br.com.santander.core.leanft.apps;

import com.hp.lft.sdk.java.WindowDescription;

import br.com.santander.core.leanft.object.ObjectLeanFT;
import br.com.santander.core.leanft.utils.StoredActionsStandardWindows;

public abstract class StandardWindowsApp extends ObjectLeanFT {

	protected StoredActionsStandardWindows actions = null;

	public StandardWindowsApp() {
		actions = new StoredActionsStandardWindows();
		inicializeObject(new WindowDescription.Builder().build());
	}

}
