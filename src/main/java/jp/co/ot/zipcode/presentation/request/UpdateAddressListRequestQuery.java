package jp.co.ot.zipcode.presentation.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UpdateAddressListRequestQuery {

	@NotBlank(message="郵便番号は必須です")
    @Size(min = 7, max = 7, message = "7桁の数字を入力してください")
	String zipcode;
	@NotBlank(message="住所は必須です")
	String address;
	@NotBlank(message="ふりがなは必須です")
	String kana;
	
}
