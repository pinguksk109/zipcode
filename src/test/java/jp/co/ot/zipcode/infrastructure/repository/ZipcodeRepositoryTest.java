package jp.co.ot.zipcode.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import jp.co.ot.zipcode.domain.model.request.AddressForm;

public class ZipcodeRepositoryTest extends ZipcodeRepository {

	@InjectMocks
    private ZipcodeRepository sut;
    private ClientAndServer mockServer;
    private Integer mockPort = 1080;
    private String mockServerUrl = "http://localhost:" + mockPort;
    
    @BeforeEach
    public void setUp() {
    	mockServer = ClientAndServer.startClientAndServer(mockPort);
    	sut = new ZipcodeRepository();
    }

    @AfterEach
    public void afterEach() {
        mockServer.stop();
    }

    @Test
    public void searchAddress_HTTPステータスが200の場合_郵便番号を取得できること() throws IOException {
        mockServer.when(HttpRequest.request().withMethod("GET")
                .withPath("https://zipcloud.ibsnet.co.jp/api/search")).respond(HttpResponse.response().withStatusCode(200)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8")).withBody("{\n"
                        		+ "	\"message\": null,\n"
                        		+ "	\"results\": [\n"
                        		+ "		{\n"
                        		+ "			\"address1\": \"北海道\",\n"
                        		+ "			\"address2\": \"美唄市\",\n"
                        		+ "			\"address3\": \"上美唄町協和\",\n"
                        		+ "			\"kana1\": \"ﾎｯｶｲﾄﾞｳ\",\n"
                        		+ "			\"kana2\": \"ﾋﾞﾊﾞｲｼ\",\n"
                        		+ "			\"kana3\": \"ｶﾐﾋﾞﾊﾞｲﾁｮｳｷｮｳﾜ\",\n"
                        		+ "			\"prefcode\": \"1\",\n"
                        		+ "			\"zipcode\": \"0790177\"\n"
                        		+ "		},\n"
                        		+ "		{\n"
                        		+ "			\"address1\": \"北海道\",\n"
                        		+ "			\"address2\": \"美唄市\",\n"
                        		+ "			\"address3\": \"上美唄町南\",\n"
                        		+ "			\"kana1\": \"ﾎｯｶｲﾄﾞｳ\",\n"
                        		+ "			\"kana2\": \"ﾋﾞﾊﾞｲｼ\",\n"
                        		+ "			\"kana3\": \"ｶﾐﾋﾞﾊﾞｲﾁｮｳﾐﾅﾐ\",\n"
                        		+ "			\"prefcode\": \"1\",\n"
                        		+ "			\"zipcode\": \"0790177\"\n"
                        		+ "		},\n"
                        		+ "		{\n"
                        		+ "			\"address1\": \"北海道\",\n"
                        		+ "			\"address2\": \"美唄市\",\n"
                        		+ "			\"address3\": \"上美唄町\",\n"
                        		+ "			\"kana1\": \"ﾎｯｶｲﾄﾞｳ\",\n"
                        		+ "			\"kana2\": \"ﾋﾞﾊﾞｲｼ\",\n"
                        		+ "			\"kana3\": \"ｶﾐﾋﾞﾊﾞｲﾁｮｳ\",\n"
                        		+ "			\"prefcode\": \"1\",\n"
                        		+ "			\"zipcode\": \"0790177\"\n"
                        		+ "		}\n"
                        		+ "	],\n"
                        		+ "	\"status\": 200\n"
                        		+ "}"));
        AddressForm addressForm = new AddressForm();
        addressForm.setZipcode("0790177");
        String responseBody = sut.searchAddress(addressForm);
        System.out.println(responseBody);

        assertEquals("{\n"
        		+ "	\"message\": null,\n"
        		+ "	\"results\": [\n"
        		+ "		{\n"
        		+ "			\"address1\": \"北海道\",\n"
        		+ "			\"address2\": \"美唄市\",\n"
        		+ "			\"address3\": \"上美唄町協和\",\n"
        		+ "			\"kana1\": \"ﾎｯｶｲﾄﾞｳ\",\n"
        		+ "			\"kana2\": \"ﾋﾞﾊﾞｲｼ\",\n"
        		+ "			\"kana3\": \"ｶﾐﾋﾞﾊﾞｲﾁｮｳｷｮｳﾜ\",\n"
        		+ "			\"prefcode\": \"1\",\n"
        		+ "			\"zipcode\": \"0790177\"\n"
        		+ "		},\n"
        		+ "		{\n"
        		+ "			\"address1\": \"北海道\",\n"
        		+ "			\"address2\": \"美唄市\",\n"
        		+ "			\"address3\": \"上美唄町南\",\n"
        		+ "			\"kana1\": \"ﾎｯｶｲﾄﾞｳ\",\n"
        		+ "			\"kana2\": \"ﾋﾞﾊﾞｲｼ\",\n"
        		+ "			\"kana3\": \"ｶﾐﾋﾞﾊﾞｲﾁｮｳﾐﾅﾐ\",\n"
        		+ "			\"prefcode\": \"1\",\n"
        		+ "			\"zipcode\": \"0790177\"\n"
        		+ "		},\n"
        		+ "		{\n"
        		+ "			\"address1\": \"北海道\",\n"
        		+ "			\"address2\": \"美唄市\",\n"
        		+ "			\"address3\": \"上美唄町\",\n"
        		+ "			\"kana1\": \"ﾎｯｶｲﾄﾞｳ\",\n"
        		+ "			\"kana2\": \"ﾋﾞﾊﾞｲｼ\",\n"
        		+ "			\"kana3\": \"ｶﾐﾋﾞﾊﾞｲﾁｮｳ\",\n"
        		+ "			\"prefcode\": \"1\",\n"
        		+ "			\"zipcode\": \"0790177\"\n"
        		+ "		}\n"
        		+ "	],\n"
        		+ "	\"status\": 200\n"
        		+ "}", responseBody);
    }

}
