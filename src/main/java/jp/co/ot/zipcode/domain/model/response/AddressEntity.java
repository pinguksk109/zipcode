package jp.co.ot.zipcode.domain.model.response;

import lombok.Data;

@Data
public class AddressEntity {

	private String address1;
	private String address2;
	private String address3;
	private String kana1;
	private String kana2;
	private String kana3;
	private String prefcode;
	private String zipcode;
	
//	public AddressEntity();
}
