package br.com.santander.test.steps.hooks;

import static br.com.santander.test.support.Context.mobile;
import static br.com.santander.test.support.Context.rGen;
import static br.com.santander.test.support.Context.web;

import java.io.File;
import java.util.List;

import br.com.santander.core.report.model.ReportFileStructure;
import br.com.santander.core.webdriver.driver.DriverType;
import br.com.santander.test.support.Context;
import br.com.santander.test.utils.zgen.ZGenCalls;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Hooks {

	private static final String DIVIDER = "-----------------------";

	@Before
	public void setup(Scenario s) {
		log.info(DIVIDER);
		log.info("Executing scenario id.....: '" + Context.getIdScenario() + "'");
		log.info("Executing scenario name...: '" + s.getName() + "'");
		if(Context.getDriverType() == DriverType.MOBILE) {
			log.info("Executing scenario device.: '" + Context.getDevice().getUdid() + "' | id: '" + Context.getDevice().getId() + "' | platform: '" + Context.getDevice().getPlatformName() + "'");
		}
		log.info(DIVIDER);
	}

	@After
	public void tearDown(Scenario s) throws Exception {
		log.info(DIVIDER);
		log.info("Scenario result....: '" + s.getStatus() + "'");
		if (Context.rest() == null) {
			log.info("Finishing report....");
			List<File> reportFiles = finishReport(s);
			log.info("Files registered to this test: " + reportFiles.size());
			log.info(DIVIDER);
			
			String executionCode = Context.getIdScenario();
			log.info("Sending " + executionCode + " to ZGen...");
			ZGenCalls.registerResultOnZGen(executionCode, !s.isFailed(), reportFiles);
			log.info(DIVIDER);
		}
	}

	private List<File> finishReport(Scenario s) throws Exception {
		boolean mobileDriver = Context.getDriverType() == DriverType.MOBILE;
		
		//necessario verificar se o driver do dispositivo nao eh nulo, pois podem ocorrer erros ao criar o driver no contexto
		if(s.isFailed() && Context.getDriverManager().getDriver() != null) {
			rGen().registerStep(mobileDriver ? mobile().getScreenshot() : web().getScreenshot(), "Error :(");
		}
		
		String idCenario = Context.getIdScenario();
		idCenario += "_" + (mobileDriver ? Context.getDevice().getId() : Context.getDriverType());
				
		return rGen().buildReport(idCenario, s.getName(), s.getStatus().toString(), ReportFileStructure.empty());
	}
}
