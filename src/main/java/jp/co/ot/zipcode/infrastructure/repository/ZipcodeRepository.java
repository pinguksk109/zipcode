package jp.co.ot.zipcode.infrastructure.repository;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

@Repository
public class ZipcodeRepository {

	private static final String URL = "https://zip-cloud.appspot.com/api/search?zipcode={zipcode}";
	
	public String searchAddress(String zipCode) throws IOException {
	    
		// HttpClientオブジェクトを作成
		HttpRequestFactory factory = (new NetHttpTransport()).createRequestFactory();
	
		GenericUrl genericUrl = new GenericUrl("https://zipcloud.ibsnet.co.jp/api/search");
	
		genericUrl.put("zipcode", zipCode);
		
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
		
		return responseBody;
	}
}
