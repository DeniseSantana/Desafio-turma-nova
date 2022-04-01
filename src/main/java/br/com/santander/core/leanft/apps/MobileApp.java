package br.com.santander.core.leanft.apps;

import com.hp.lft.sdk.GeneralLeanFtException;
import com.hp.lft.sdk.mobile.Application;
import com.hp.lft.sdk.mobile.ApplicationDescription;
import com.hp.lft.sdk.mobile.Device;
import com.hp.lft.sdk.mobile.MobileLab;

import br.com.santander.core.leanft.object.ObjectLeanFT;
import br.com.santander.core.leanft.utils.StoredActionsMobile;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class MobileApp extends ObjectLeanFT {

	protected StoredActionsMobile actions;
	protected Device device = null;
	protected Application application;

	public MobileApp() {
		actions = new StoredActionsMobile();
	}

	protected void iniciarApp(String appIdentifier, boolean packaged, boolean restart) {
		try {
			device = MobileLab.lockDeviceById("");
			application = device.describe(Application.class,
					new ApplicationDescription.Builder().identifier(appIdentifier).packaged(packaged).build());

			if (restart)
				application.restart();

			changeParentObject(application);

		} catch (GeneralLeanFtException e) {
			log.error("Erro iniciando o app", e);
		}
	}

	protected void closeApp() {
		try {
			device.unlock();
			application.kill();

		} catch (GeneralLeanFtException e) {
			log.error("Erro fechando o app", e);
		}

	}
}
