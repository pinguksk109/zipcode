package jp.co.ot.zipcode.domain.model.response;

import java.util.List;

import lombok.Value;

@Value
public class AddressDto {

	private String message;
	private List<ZipcodeData> results;
	private int status;
	
//	public AddressEntity();
}
