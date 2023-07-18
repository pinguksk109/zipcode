package jp.co.ot.zipcode.infrastructure.repository;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

import jp.co.ot.zipcode.domain.model.request.AddressForm;
import jp.co.ot.zipcode.domain.model.response.AddressDto;

@Repository
public class ZipcodeRepository {

	private static final String URL = "https://zip-cloud.appspot.com/api/search?zipcode={zipcode}";

	
	/**
	 * 郵便番号APIから郵便番号に紐づく住所を取得する
	 * @param zipCode
	 * @return
	 * @throws IOException
	 */
	public AddressDto searchAddress(AddressForm addressForm) throws IOException {
	    
		// HttpClientオブジェクトを作成
		HttpRequestFactory factory = (new NetHttpTransport()).createRequestFactory();
	
		GenericUrl genericUrl = new GenericUrl("https://zipcloud.ibsnet.co.jp/api/search");
	
		genericUrl.put("zipcode", addressForm.getZipcode());
		
		// Httpリクエストオブジェクトを設定
		HttpRequest request = factory.buildGetRequest(genericUrl);
		
		request.setThrowExceptionOnExecuteError(false);
		
		// レスポンス文字列だけ取得して接続を切断
		HttpResponse response = request.execute();
		
		if(!HttpStatus.valueOf(response.getStatusCode()).is2xxSuccessful()) {
			response.disconnect();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "郵便番号APIからレスポンスを取得できませんでした");
		}
		
		String responseBody = response.parseAsString();
		response.disconnect();
		
		ObjectMapper objectMapper = new ObjectMapper();
		AddressDto dto = objectMapper.readValue(responseBody, AddressDto.class);
		
		return dto;
	}
}
