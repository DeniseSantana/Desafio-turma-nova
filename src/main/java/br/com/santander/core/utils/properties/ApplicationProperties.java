package br.com.santander.core.utils.properties;

import java.util.ArrayList;
import java.util.List;

import br.com.santander.core.exception.CoreException;
import br.com.santander.core.utils.json.Device;
import br.com.santander.core.utils.json.DevicesJson;
import br.com.santander.core.webdriver.driver.DriverType;

public class ApplicationProperties extends PropertyReader {

	private static final String FILE_LOCATION = "application.properties";
	private static final String SEPARATOR = ",";

	public ApplicationProperties() {
		super(FILE_LOCATION);
	}

	public String getExecutionWebProxyHost() {
		return getProperty("execution.web.proxy.host");
	}

	public String getExecutionWebProxyUser() {
		return getProperty("execution.web.proxy.user");
	}

	public Boolean getExecutionWebProxyActivate() {
		return Boolean.parseBoolean(getProperty("execution.web.proxy.activate"));
	}

	public Boolean getExecutionMobileUftMobile() {
		return Boolean.parseBoolean(getProperty("execution.mobile.uftMobile"));
	}

	public String getExecutionMobileLocalHost() {
		return getProperty("execution.mobile.local.host");
	}

	public Integer getExecutionMobileLocalPort() {
		return Integer.parseInt(getProperty("execution.mobile.local.port"));
	}

	public String getExecutionMobileAndroidAppPackage() {
		return getProperty("execution.mobile.android.appPackage");
	}

	public String getExecutionMobileAndroidAppActivity() {
		return getProperty("execution.mobile.android.appActivity");
	}

	public String getExecutionMobileIosBundleId() {
		return getProperty("execution.mobile.ios.bundleID");
	}

	public List<Device> getExecutionMobileDevices() {
		List<Device> executionDevices = new ArrayList<Device>();
		List<Device> allDevices = new DevicesJson().getAllDevices();
		
		String property = "execution.mobile.devices";		
		String[] ids = getProperty(property).split(SEPARATOR);

		for (String id : ids) {
			for (Device device : allDevices) {
				if (device.getId().equalsIgnoreCase(id.trim())) {
					executionDevices.add(device);
					break;
				}
			}
		}

		if (executionDevices.isEmpty()) 
			errorPropertie(property);		

		return executionDevices;
	}
	
	public List<DriverType> getExecutionWebDriverTypes(String property){
		List<DriverType> executionDriverTypes = new ArrayList<DriverType>();
		DriverType[] allDriverTypesEnum = DriverType.values();
		
		String[] driverTypesProp = getProperty(property).split(SEPARATOR);
		
		for(DriverType driverTypeEnum : allDriverTypesEnum) {
			for(String driverTypeProp : driverTypesProp) {
				if(driverTypeProp.trim().equalsIgnoreCase(driverTypeEnum.name())) {
					executionDriverTypes.add(driverTypeEnum);
				}
			}
		}
		
		if (executionDriverTypes.isEmpty())
			errorPropertie(property);
		
		return executionDriverTypes;
	}
	
	public List<DriverType> getExecutionWebLocalDriverTypes(){		
		return getExecutionWebDriverTypes("execution.web.local.driverTypes");
	}
	
	public List<DriverType> getExecutionWebSauceLabsDriverTypes(){		
		return getExecutionWebDriverTypes("execution.web.sauceLabs.driverTypes");
	}
	
	public Object[][] getArrayObjectsExecutionMobileDevices() {		
		return getArrayObjects(getExecutionMobileDevices());
	}
	
	public Object[][] getArrayObjectsExecutionWebLocalDriverTypes() {		
		return getArrayObjects(getExecutionWebLocalDriverTypes());
	}
	
	public Object[][] getArrayObjectsExecutionWebSauceLabsDriverTypes() {		
		return getArrayObjects(getExecutionWebSauceLabsDriverTypes());
	}

	public boolean getExecutionWebSauceLabs() {
		return Boolean.parseBoolean(getProperty("execution.web.sauceLabs"));
	}

	public String getExecutionWebTag() {
		return getProperty("execution.web.tag");
	}
	
	public String getExecutionMobileTag() {
		return getProperty("execution.mobile.tag");
	}
	
	public Integer getExecutionMobileUftRetryCount() {
		return Integer.parseInt(getProperty("execution.mobile.uft.retryCount"));
	}
	
	public Integer getExecutionWebDataProviderThreadCount() {
		return Integer.parseInt(getProperty("execution.web.dataProvider.threadCount"));
	}
	
	public Integer getExecutionMobileDataProviderThreadCount() {
		return Integer.parseInt(getProperty("execution.mobile.dataProvider.threadCount"));
	}
	
	public Integer getExecutionRestDataProviderThreadCount() {
		return Integer.parseInt(getProperty("execution.rest.dataProvider.threadCount"));
	}
	
	private void errorPropertie(String property) {
		throw new CoreException("'" + property + "' is empty");
	}
	
	private Object[][] getArrayObjects(List<?> list) {
		Object[][] array = new Object[list.size()][];

		for (int i = 0; i < list.size(); i++)
			array[i] = new Object[] { list.get(i) };

		return array;
	}
	
}