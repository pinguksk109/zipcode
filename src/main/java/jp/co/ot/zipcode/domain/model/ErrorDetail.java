package jp.co.ot.zipcode.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetail {

	private Integer status;
	private String code;
	private String userMessage;
	private String devMessage;
	private String devInfo;
	
}
