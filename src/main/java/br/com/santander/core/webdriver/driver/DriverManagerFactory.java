package br.com.santander.core.webdriver.driver;

import br.com.santander.core.exception.CoreException;
import br.com.santander.core.utils.json.Device;
import br.com.santander.core.utils.properties.ApplicationProperties;

public class DriverManagerFactory {

	private ApplicationProperties applicationProperties;

	public DriverManagerFactory() {
		applicationProperties = new ApplicationProperties();
	}

	private DriverManager driverManager;
	private DriverType driverType;
	private Device device;

	public DriverManager getManager(DriverType type) {
		return getManager(type, null);
	}

	public DriverManager getManager(DriverType driverType_, Device device_) {
		driverType = driverType_;

		if (checkIfManagerOfTypeIsCreated(driverType_, device_)) {
			return driverManager;
		}

		tearDownCurrentManagerIfExistent();

		if (checkIfTestIsWebInSauceLabs()) {
			return new SauceLabsManager(driverType);
		}

		switch (driverType_) {
		case CHROME:
			driverManager = new ChromeManager();
			break;
		case FIREFOX:
			driverManager = new FirefoxManager();
			break;
		case IE:
			driverManager = new IEManager();
			break;
		case EDGE:
			driverManager = new EdgeManager();
			break;
		case OPERA:
			driverManager = new OperaManager();
			break;
		case MOBILE:
			device = device_;
			driverManager = new MobileManager(device);
			break;
		default:
			throw new CoreException("Manager not found");
		}
		return driverManager;
	}

	public void tearDownCurrentManagerIfExistent() {
		if (driverManager != null) {
			driverManager.tearDown();
		}
	}

	private boolean checkIfTestIsWebInSauceLabs() {
		return (driverType != DriverType.MOBILE && applicationProperties.getExecutionWebSauceLabs());
	}

	private boolean checkIfManagerOfTypeIsCreated(DriverType type) {
		return (driverType == type && driverManager != null);
	}

	private boolean checkIfManagerOfTypeIsCreated(DriverType type, Device device_) {
		if (device_ == null && checkIfManagerOfTypeIsCreated(type)) {
			return true;
		} else if (device != null && checkIfManagerOfTypeIsCreated(type) && device == device_) {
			return true;
		} else {
			return false;
		}
	}
}
