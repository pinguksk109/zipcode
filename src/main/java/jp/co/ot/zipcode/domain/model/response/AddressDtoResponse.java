package jp.co.ot.zipcode.domain.model.response;

import java.util.List;

import lombok.Data;

@Data
public class AddressDtoResponse {

	private String message;
	private List<ZipcodeData> results;
	private Integer status;

}
