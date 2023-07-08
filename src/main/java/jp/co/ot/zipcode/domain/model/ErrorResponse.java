package jp.co.ot.zipcode.domain.model;

import java.util.Arrays;
import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {

	private List<ErrorDetail> errors;
	
	public ErrorResponse(ErrorDetail... errors) {
		this.errors = Arrays.asList(errors);
	}
	
}
