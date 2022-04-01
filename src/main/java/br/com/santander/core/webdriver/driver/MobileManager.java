package br.com.santander.core.webdriver.driver;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;

import br.com.santander.core.exception.CoreException;
import br.com.santander.core.uftmobile.client.UFTMobileClient;
import br.com.santander.core.uftmobile.client.UFTMobileClientProperties;
import br.com.santander.core.utils.json.Device;
import br.com.santander.core.utils.properties.ApplicationProperties;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MobileManager extends DriverManager {
	
	private static String PLATFORM_ANDROID = "Android";
	private static String PLATFORM_IOS = "iOS";

	private UFTMobileClientProperties uftMobileProperties;
	private ApplicationProperties applicationProperties;
	private DesiredCapabilities desiredCapabilities;
	private UFTMobileClient uftMobileClient;
	private boolean uftMobileDeviceUsing = false;
	private Device device;
	private URL url;
	
	public MobileManager(Device device) {
		uftMobileProperties = new UFTMobileClientProperties();
		applicationProperties = new ApplicationProperties();
		desiredCapabilities = new DesiredCapabilities();
		uftMobileClient = new UFTMobileClient();
		this.device = device;
	}

	@Override
	public void createDriver() {
		setupConncetionServer();
		setCapabilitesDefault();

		if (device.getPlatformName().equalsIgnoreCase(PLATFORM_ANDROID)) {
			setCapabilitesAndroid();
			webDriver = new AndroidDriver<AndroidElement>(url, desiredCapabilities);
		} 
		else
			if (device.getPlatformName().equalsIgnoreCase(PLATFORM_IOS)) {
			setCapabilitesIOS();
			webDriver = new IOSDriver<IOSElement>(url, desiredCapabilities);
		}else {
			throw new CoreException("The capabilitie 'platformName' is invalid: " + device.getPlatformName() );
		}
	}

	private void setCapabilitesDefault() {
		if (applicationProperties.getExecutionMobileUftMobile()) {
			desiredCapabilities.setCapability("userName", uftMobileProperties.getUserName());
			desiredCapabilities.setCapability("password", uftMobileProperties.getUserPassword());
		}

		desiredCapabilities.setCapability("platformName", device.getPlatformName());
		desiredCapabilities.setCapability("deviceName", device.getDeviceName());
		desiredCapabilities.setCapability("udid", device.getUdid());
		desiredCapabilities.setCapability("automationName", device.getAutomationName());
		desiredCapabilities.setCapability("noReset", true);
		desiredCapabilities.setCapability("fullReset", false);
	}

	private void setCapabilitesAndroid() {
		desiredCapabilities.setCapability("appPackage", applicationProperties.getExecutionMobileAndroidAppPackage());
		desiredCapabilities.setCapability("appActivity", applicationProperties.getExecutionMobileAndroidAppActivity());
	}

	private void setCapabilitesIOS() {
		desiredCapabilities.setCapability("bundleId", applicationProperties.getExecutionMobileIosBundleId());
	}

	private void setupConncetionServer() {
		String host;
		Integer port;
		
		if (!applicationProperties.getExecutionMobileUftMobile()) {			
			host = applicationProperties.getExecutionMobileLocalHost();
			port = applicationProperties.getExecutionMobileLocalPort();
		} else {
			waitingDeviceToFreeUFTMobile();
			host = uftMobileProperties.getServerAddress();
			port = Integer.valueOf(uftMobileProperties.getServerPort());
		}
		
		try {
			url = new URL(String.format("http://%s:%d/wd/hub", host, port));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	private void waitingDeviceToFreeUFTMobile() {
		log.info("Waiting device free in UFT Mobile: " + device.getUdid());
		
		boolean free = false;
		int retryCountProp = new ApplicationProperties().getExecutionMobileUftRetryCount();
		int retryCount = retryCountProp;
		
		while (retryCount > 0) {
			if (uftMobileClient.verifyDeviceIsFree(device.getUdid())) {
				log.info("Device is free in UFT Mobile: " + device.getUdid());
				uftMobileClient.reserveDevice(device.getUdid());
				uftMobileDeviceUsing = true;
				free = true;
				break;
			}
			// Thread.sleep(1_000);
			retryCount--;
		}

		if (!free) {
			throw new SkipException("Device is not free in UFT Mobile: " + device.getUdid() + " | Retry count: " + retryCountProp);
		}
	}
	
	@Override
	public void tearDown() {
		super.tearDown();
		if(uftMobileDeviceUsing) {
			uftMobileClient.removeDeviceReservedByUser(device.getUdid());
			uftMobileDeviceUsing = false;
		}
	}
}