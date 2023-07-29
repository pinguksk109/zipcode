package jp.co.ot.zipcode.domain.model.response;

import java.util.UUID;

import lombok.Value;

@Value
public class ZipcodeDataDto {

	private String id;
	private String address;
	private String kana;
	private String zipcode;
	
	public ZipcodeDataDto(String address1, String address2, String address3, String kana1, String kana2, String kana3, String zipcode) {
		this.id = UUID.randomUUID().toString();
		this.address = address1+address2+address3;
		this.kana = kana1+kana2+kana3;
		this.zipcode = zipcode;
	}
}
