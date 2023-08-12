package jp.co.ot.zipcode.domain.model.response;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class AddressDtoResponse {

	private String message;
	private List<ZipcodeData> results;
	private Integer status;

	private static ObjectMapper mapper = new ObjectMapper();
	
	public static AddressDtoResponse parse(String jsonStr) throws JsonMappingException, JsonProcessingException {
		return mapper.readValue(jsonStr, AddressDtoResponse.class);
	}
	
}
