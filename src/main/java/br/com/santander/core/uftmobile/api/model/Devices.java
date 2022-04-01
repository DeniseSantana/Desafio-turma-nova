package br.com.santander.core.uftmobile.api.model;

import java.util.List;

public class Devices {
	
	private List<Failure> failure;
	
	private List<Success> success;
	
	public List<Failure> getFailure() {
		return failure;
	}

	public void setFailure(List<Failure> failure) {
		this.failure = failure;
	}

	public List<Success> getSuccess() {
		return success;
	}

	public void setSuccess(List<Success> success) {
		this.success = success;
	}
}