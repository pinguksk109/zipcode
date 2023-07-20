package jp.co.ot.zipcode.application.service;

import java.util.List;

import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;
import lombok.Value;

@Value
public class AddressDto {

	private List<ZipcodeDataDto> results;

//	public AddressDto(List<ZipcodeData> results) {
//		results.
//	}
}
