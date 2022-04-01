package br.com.santander.core.leanft;

import java.net.URI;
import java.net.URISyntaxException;
import com.hp.lft.sdk.ModifiableSDKConfiguration;
import com.hp.lft.sdk.SDK;

public class ConfigurationLeanFT {

	public static void start() {
		try {
			ModifiableSDKConfiguration config = new ModifiableSDKConfiguration();
			config.setServerAddress(new URI("ws://localhost:5095"));
			SDK.init(config);

		} catch (URISyntaxException e) {
			System.out.println("Erro: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Erro: " + e.getMessage());
		}
	}
	
	public static void finish()
	{
		SDK.cleanup();
	}
}
