package jp.co.ot.zipcode.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.ot.zipcode.infrastructure.repository.ZipcodeRepository;

@ExtendWith(MockitoExtension.class)
class ZipcodeServiceTest extends ZipcodeService {

	@Mock
	private ZipcodeRepository zipcodeRepository;
	
	@InjectMocks
	private ZipcodeService sut;
	
    @Before
    public void setUp() {
      MockitoAnnotations.initMocks(this);
    }

	@Test
	void searchAddress_データを取得できること() throws IOException {
		
		String responseBody = "{\n"
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
        		+ "}";
		when(zipcodeRepository.searchAddress(any())).thenReturn(responseBody);
		
        String zipcode = "0790177";
		String actual = sut.searchAddress(zipcode);
		
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
        		+ "}", actual);
	}
	
//	@Test
//	void searchAddress_データ取得で500エラーが発生した場合_IOExceptionが発生すること() throws IOException {
//		
//		when(sut.searchAddress(any())).thenThrow(IOException.class);
//		
//		assertThrows(IOException.class, () -> {
//			sut.searchAddress(any());
//		});
//	}
}
