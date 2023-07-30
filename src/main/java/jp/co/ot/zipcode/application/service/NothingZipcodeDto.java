package jp.co.ot.zipcode.application.service;

import lombok.Data;

@Data
public class NothingZipcodeDto {

	private String zipcode;
	private String message;
	
	public NothingZipcodeDto(String zipcode, String message) {
		this.zipcode = zipcode;
		this.message = message;
	}
}
