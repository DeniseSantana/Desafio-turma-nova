package br.com.santander.test.support;

import br.com.santander.core.leanft.ConfigurationLeanFT;
import br.com.santander.core.leanft.object.ObjectLeanFT;
import br.com.santander.core.leanft.utils.StoredActionsStandardWindows;
import br.com.santander.core.report.ReportGenerator;
import br.com.santander.core.rest.RestActions;
import br.com.santander.core.sheet.TestData;
import br.com.santander.core.utils.Tag;
import br.com.santander.core.utils.json.Device;
import br.com.santander.core.webdriver.driver.DriverManager;
import br.com.santander.core.webdriver.driver.DriverManagerFactory;
import br.com.santander.core.webdriver.driver.DriverType;
import br.com.santander.core.webdriver.utils.MobileActions;
import br.com.santander.core.webdriver.utils.WebActions;
import br.com.santander.test.sheet.service.DataSheetManager;
import lombok.extern.slf4j.Slf4j;

/**
 * Classe com objetos utilizados durante o teste
 *
 */
@Slf4j
public class Context {
	private static ThreadLocal<Tag> tag = new ThreadLocal<Tag>();
	private static ThreadLocal<DriverManager> driverManager = new ThreadLocal<DriverManager>();
	private static ThreadLocal<WebActions> web = new ThreadLocal<WebActions>();
	private static ThreadLocal<MobileActions> mobile = new ThreadLocal<MobileActions>();
	private static ThreadLocal<ReportGenerator> r = new ThreadLocal<ReportGenerator>();
	private static ThreadLocal<TestData> testData = new ThreadLocal<TestData>();
	private static ThreadLocal<StoredActionsStandardWindows> stdwin = new ThreadLocal<>();
	private static ThreadLocal<ObjectLeanFT> objLeanFT = new ThreadLocal<ObjectLeanFT>();
	private static ThreadLocal<String> sheetName = new ThreadLocal<String>();
	private static ThreadLocal<DriverType> driverType = new ThreadLocal<DriverType>();
	private static ThreadLocal<String> idScenario = new ThreadLocal<String>();
	private static ThreadLocal<Device> device = new ThreadLocal<Device>();
	private static ThreadLocal<DriverManagerFactory> driverManagerFactory = new ThreadLocal<DriverManagerFactory>();
	private static ThreadLocal<RestActions> rest = new ThreadLocal<RestActions>();
	
	private Context() {
	}		
	
	private static void setup(Tag tag, DriverType driverType, Device device) {
		log.info("Initializing context " + driverType.name());
		setTags(tag);
		setIdScenario();
		setDriverType(driverType);		
		setDevice(driverType, device);
		setDriverManagerFactory();
		setDriverManager();
		setDriverActions(driverType);
		setTestData();
		setReportGenerator();
	}
	
	public static void setup(Tag tag, DriverType driver) {
		setup(tag, driver, null);
	}
	
	public static void setup(Tag tag, Device device) {
		setup(tag, DriverType.MOBILE, device);
	}
	
	public static void setupRest(Tag tag) {
		log.info("Initializing context rest");
		setTags(tag);
		setIdScenario();
		setRestActions();
		setTestData();
	}
	
	public static void setTags(Tag tags) {
		tag.set(tags);
	}
	
	public static void setIdScenario() {
		idScenario.set(tag.get().convertTagToSimpleName(tag.get().getLastTag()));
	}
	
	public static void setDriverType(DriverType driverType_) {
		driverType.set(driverType_);
	}	
	
	public static void setDevice(Device device_) {
		device.set(device_);
	}
	
	public static void setDriverManagerFactory(){
		driverManagerFactory.set(new DriverManagerFactory());
	}
	
	public static void setDriverManager() {
		driverManager.set(driverManagerFactory.get().getManager(driverType.get(), device.get()));
		driverManager.get().createDriver();
	}
	
	public static DriverManagerFactory getDriverManagerFactory(){
		return driverManagerFactory.get();
	}
		
	public static DriverManager getDriverManager() {
		return driverManager.get();
	}
	
	public static String getIdScenario() {
		return idScenario.get();
	}
	
	public static DriverType getDriverType() {
		return driverType.get();
	}
	
	public static void setReportGenerator() {
		if (r.get() == null)
			r.set(new ReportGenerator());
	}
	
	public static Device getDevice() {
		return device.get();
	}	
			
	public static ReportGenerator rGen() {
		if (r.get() == null)
			throw new RuntimeException("Context was not setup, call Context.setup(<?>)");
		return r.get();
	}
	
	public static void setWebActions() {
		web.set(new WebActions(driverManager.get().getDriver()));
	}
	
	public static WebActions web() {
		return web.get();
	}
	
	public static void setMobileActions() {
		mobile.set(new MobileActions(driverManager.get().getDriver()));
	}
	
	public static MobileActions mobile() {
		return mobile.get();
	}

	public static void setRestActions() {
		rest.set(new RestActions());
	}
	
	public static RestActions rest() {
		return rest.get();
	}
	
	public static void setTestData() {
		testData.set((TestData) new DataSheetManager().getTestData(tag.get())[0][0]);
	}	

	public static TestData testData() {
		if (testData.get() == null)
			throw new RuntimeException("Test data is empty");
		return testData.get();
	}
	
	public static void setObjLeanFT () {
		if(objLeanFT.get() == null)
			objLeanFT.set(new ObjectLeanFT());
	}

	public static StoredActionsStandardWindows stdWin() {
		if (stdwin.get() == null)
			throw new RuntimeException("Context was not setup, call Context.setup()");
		return stdwin.get();
	}
	
	public static void setStdWin() {
		if(stdwin.get() == null)
			stdwin.set(new StoredActionsStandardWindows());
	}

	public static ObjectLeanFT objLeanFT() {
		if (objLeanFT.get() == null)
			throw new RuntimeException("Context was not setup, call Context.setup()");
		return objLeanFT.get();
	}

	public static void startLeanFT() {
		ConfigurationLeanFT.start();
	}

	public static void finishLeanFT() {
		ConfigurationLeanFT.finish();
	}

	public static String getSheetName() {
		return sheetName.get();
	}

	public static void setSheetName(String name) {
		sheetName.set(name);
	}
	
	private static void setDevice(DriverType driverType, Device device) {
		if (driverType == DriverType.MOBILE)
			setDevice(device);
	}
		
	private static void setDriverActions(DriverType driverType) {
		if (driverType == DriverType.MOBILE)
			setMobileActions();
		else
			setWebActions();
	}
	
	public static void tearDown() {
		log.info("Finalizing context");
		tag.set(null);
		driverManager.set(null);
		driverManagerFactory.set(null);
		web.set(null);
		mobile.set(null);
		r.set(null);
		testData.set(null);
		stdwin.set(null);
		objLeanFT.set(null);
		sheetName.set(null);
		driverType.set(null);
		idScenario.set(null);
		device.set(null);
		rest.set(null);
	}
}
