package br.com.santander.test.utils.zgen;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;

import br.com.rsi.zgen.client.ZGenClient;
import br.com.rsi.zgen.client.exception.ZGenClientException;
import br.com.rsi.zgen.client.model.AttachmentResponse;
import br.com.rsi.zgen.client.model.TestResultRequest;
import br.com.rsi.zgen.client.model.TestResultResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZGenCalls {
	private static ZGenClient client;

	private static ZGenPropertyReader prop;

	private ZGenCalls() {
	}

	private static ZGenClient getClient() throws ZGenClientException {
		if (client == null) {
			client = new ZGenClient();
		}

		return client;
	}

	private static ZGenPropertyReader getProp() throws Exception {
		if (prop == null) {
			prop = new ZGenPropertyReader();
		}

		return prop;
	}

	public static void registerResultOnZGen(String executionCode, Boolean status, List<File> attachments)
			throws Exception {
		try {
			if (isUsingZGen()) {
				log.info("Registering test execution on ZGen...");

				TestResultRequest testResult = buildTestResultRequest(executionCode, status);
				log.info(testResult.toString());

				TestResultResponse testResultResponse = getClient().postTestExecution(testResult);
				log.info(testResultResponse.toString());

				if (!attachments.isEmpty()) {
					for (File file : attachments) {
						AttachmentResponse attachmentResponse = client.postAttachment(testResultResponse, file);
						log.info(attachmentResponse.toString());
					}
				} else {
					log.info("No attachments were found for this test.");
				}

				log.info("Done.");
			} else {
				log.info("Properties set to not register result for this execution.");
			}

		} catch (Exception e) {
			log.error("Falha ao enviar resultado para o Zgen: " + e.getMessage());
		}

	}

	private static boolean isUsingZGen() throws Exception {
		return getProp().useZGen();
	}

	private static TestResultRequest buildTestResultRequest(String executionCode, Boolean status) throws Exception {
		ZGenPropertyReader p = getProp();

		return TestResultRequest.builder() //
				.projectID(p.getProjectId()) //
				.executionCode(executionCode) //
				.cycleStartDate(getCycleDate(DayOfWeek.MONDAY)) //
				.testResultOK(status) //
				.testerUserID(p.getUserId()) //
				.date(LocalDateTime.now().toString()) //
				.status(status.toString()) //
				.statusDate(LocalDateTime.now().toString()) //
				.testResultComments("") //
				.build();
	}

	public static String getCycleDate(DayOfWeek dayOfWeek) {
		LocalDate now = LocalDate.now();
		TemporalField fieldISO = WeekFields.of(dayOfWeek, 7).dayOfWeek();
		return now.with(fieldISO, 1).toString();
	}
}
