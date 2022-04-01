package br.com.santander.test.utils.zgen;

import br.com.santander.core.utils.properties.PropertyReader;
import lombok.NonNull;

public class ZGenPropertyReader extends PropertyReader {
	private static final String FILE_NAME = "zgen.properties";

	public ZGenPropertyReader() throws Exception {
		super(FILE_NAME);
	}

	public @NonNull Integer getUserId() {
		return Integer.parseInt(getProperty("zgen.local.user.id"));
	}

	public @NonNull Integer getProjectId() {
		return Integer.parseInt(getProperty("zgen.local.project.id"));
	}

	public @NonNull Boolean useZGen() {
		return Boolean.valueOf(getProperty("zgen.local.on"));
	}

}
