package jp.co.ot.zipcode.domain.model.response;

import lombok.Value;

@Value
public class ZipcodeData {

	private String address1;
	private String address2;
	private String address3;
	private String kana1;
	private String kana2;
	private String kana3;
	private Integer prefcode;
	private Integer zipcode;
	
}
