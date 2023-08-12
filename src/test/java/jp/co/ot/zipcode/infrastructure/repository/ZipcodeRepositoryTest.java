package jp.co.ot.zipcode.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.web.server.ResponseStatusException;

import jp.co.ot.zipcode.domain.model.request.AddressEntity;
import jp.co.ot.zipcode.domain.model.response.AddressDtoResponse;

@ExtendWith(MockitoExtension.class)
public class ZipcodeRepositoryTest extends ZipcodeRepository {

	@InjectMocks
    private ZipcodeRepository sut;
    private ClientAndServer mockServer;
    private Integer mockPort = 1080;
    private String mockServerUrl = "http://localhost:" + mockPort;
    
    @Mock
    private HttpRequest httpRequestMock;

    @Mock
    private HttpResponse httpResponseMock;
    
    @BeforeEach
    public void setUp() {
    	mockServer = ClientAndServer.startClientAndServer(mockPort);
        MockitoAnnotations.openMocks(this); // モックの初期化を行う
    	sut = new ZipcodeRepository();
    }

    @AfterEach
    public void afterEach() {
        mockServer.stop();
    }

    @Test
    public void searchAddress_HTTPステータスが200の場合_郵便番号を取得できること() throws IOException {
        mockServer.when(HttpRequest.request().withMethod("GET")
                .withPath("https://zipcloud.ibsnet.co.jp/api/search").withQueryStringParameter("zipcode", "0790177")).respond(HttpResponse.response().withStatusCode(200)
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
        AddressEntity addressForm = new AddressEntity();
        addressForm.setZipcode("0790177");
        AddressDtoResponse dto = sut.searchAddress(addressForm);
        
        assertEquals(null, dto.getMessage());

        assertEquals("北海道", dto.getResults().get(0).getAddress1());
        assertEquals("美唄市", dto.getResults().get(0).getAddress2());
        assertEquals("上美唄町協和", dto.getResults().get(0).getAddress3());
        assertEquals("ﾎｯｶｲﾄﾞｳ", dto.getResults().get(0).getKana1());
        assertEquals("ﾋﾞﾊﾞｲｼ", dto.getResults().get(0).getKana2());
        assertEquals("ｶﾐﾋﾞﾊﾞｲﾁｮｳｷｮｳﾜ", dto.getResults().get(0).getKana3());
        assertEquals(1, dto.getResults().get(0).getPrefcode());
        assertEquals("0790177", dto.getResults().get(0).getZipcode());
        
        assertEquals("北海道", dto.getResults().get(1).getAddress1());
        assertEquals("美唄市", dto.getResults().get(1).getAddress2());
        assertEquals("上美唄町南", dto.getResults().get(1).getAddress3());
        assertEquals("ﾎｯｶｲﾄﾞｳ", dto.getResults().get(1).getKana1());
        assertEquals("ﾋﾞﾊﾞｲｼ", dto.getResults().get(1).getKana2());
        assertEquals("ｶﾐﾋﾞﾊﾞｲﾁｮｳﾐﾅﾐ", dto.getResults().get(1).getKana3());
        assertEquals(1, dto.getResults().get(1).getPrefcode());
        assertEquals("0790177", dto.getResults().get(1).getZipcode());
        
        assertEquals("北海道", dto.getResults().get(2).getAddress1());
        assertEquals("美唄市", dto.getResults().get(2).getAddress2());
        assertEquals("上美唄町", dto.getResults().get(2).getAddress3());
        assertEquals("ﾎｯｶｲﾄﾞｳ", dto.getResults().get(2).getKana1());
        assertEquals("ﾋﾞﾊﾞｲｼ", dto.getResults().get(2).getKana2());
        assertEquals("ｶﾐﾋﾞﾊﾞｲﾁｮｳ", dto.getResults().get(2).getKana3());
        assertEquals(1, dto.getResults().get(2).getPrefcode());
        assertEquals("0790177", dto.getResults().get(2).getZipcode());
        
        assertEquals(200, dto.getStatus());
    }
    
    
    @Test
    public void searchAddress_200以外を受け取った場合_ResponseStatusExceptionが発生すること() throws IOException { 
    	
        AddressEntity addressForm = new AddressEntity();
        
        mockServer.when(HttpRequest.request().withMethod("GET")
                .withPath("https://zipcloud.ibsnet.co.jp/api/search").withQueryStringParameter("zipcode", "99999"))
        .respond(HttpResponse.response().withStatusCode(400)
                        .withHeader(new Header("Content-Type", "application/json; charset=utf-8"))
                        .withBody("{\n" +
                                "    \"message\": \"パラメータ「郵便番号」の桁数が不正です。\",\n" +
                                "    \"results\": null,\n" +
                                "    \"status\": 400\n" +
                                "}"));

        assertThrows(ResponseStatusException.class, () -> sut.searchAddress(addressForm));
    }
}
