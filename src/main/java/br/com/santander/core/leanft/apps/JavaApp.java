package br.com.santander.core.leanft.apps;

import java.io.IOException;

import com.hp.lft.sdk.java.WindowDescription;

import br.com.santander.core.leanft.object.ObjectLeanFT;
import br.com.santander.core.leanft.utils.StoredActionsJava;

public abstract class JavaApp extends ObjectLeanFT {

	protected StoredActionsJava actions = null;

	public JavaApp() {
		actions = new StoredActionsJava();
		inicializeObject(new WindowDescription.Builder().build());
	}

	protected void startApp(String executableFile) {

		try {
			Runtime.getRuntime().exec(executableFile);
			System.out.println("");
		} catch (IOException e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
}
