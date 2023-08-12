package jp.co.ot.zipcode.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.co.ot.zipcode.domain.model.Zipcode;
import jp.co.ot.zipcode.domain.model.request.AddressEntity;
import jp.co.ot.zipcode.domain.model.response.AddressDtoResponse;
import jp.co.ot.zipcode.domain.model.response.ZipcodeData;
import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;
import jp.co.ot.zipcode.infrastructure.repository.ZipcodeDbRepository;
import jp.co.ot.zipcode.infrastructure.repository.ZipcodeRepository;

@ExtendWith(MockitoExtension.class)
class ZipcodeServiceTest extends ZipcodeService {

	@Mock
	private ZipcodeRepository zipcodeRepository;
	
	@Mock
	private ZipcodeDbRepository zipcodeDbRepository;
	
	@InjectMocks
	private ZipcodeService sut;
	
    @Before
    public void setUp() {
      MockitoAnnotations.initMocks(this);
    }

	@Test
	void searchAddress_データを取得できること() throws IOException {
		
		AddressDtoResponse response = new AddressDtoResponse();
		
		List<ZipcodeData> results = new ArrayList<>();
		ZipcodeData zipcode = new ZipcodeData();
		zipcode.setAddress1("東京都");
		zipcode.setAddress2("千代田区");
		zipcode.setAddress3("千代田");
		zipcode.setKana1("ﾄｳｷｮｳﾄ");
		zipcode.setKana2("ﾁﾖﾀﾞｸ");
		zipcode.setKana3("ﾁﾖﾀ");
		zipcode.setPrefcode(1);
		zipcode.setZipcode("1000001");
		results.add(zipcode);
		
		response.setMessage(null);
		response.setResults(results);
		response.setStatus(200);

		when(zipcodeRepository.searchAddress(any())).thenReturn(response);
		
        AddressEntity addressForm = new AddressEntity();
        addressForm.setZipcode("0790177");
		List<ZipcodeDataDto> actual = sut.searchAddress(addressForm);
		
		assertEquals("東京都千代田区千代田", actual.get(0).getAddress());
		assertEquals("ﾄｳｷｮｳﾄﾁﾖﾀﾞｸﾁﾖﾀ", actual.get(0).getKana());
		assertEquals("1000001", actual.get(0).getZipcode());

	}
	
	@Test
	void searchAddress_データ取得で500エラーが発生した場合_IOExceptionが発生すること() throws IOException {

	    when(zipcodeRepository.searchAddress(any())).thenThrow(IOException.class);

	    assertThrows(IOException.class, () -> {
	        sut.searchAddress(any());
	    });
	}
	
	@Test
	void saveAddress_データを取得できること() throws IOException {
		
		AddressDtoResponse response = new AddressDtoResponse();
		
		List<ZipcodeData> results = new ArrayList<>();
		ZipcodeData zipcode = new ZipcodeData();
		zipcode.setAddress1("東京都");
		zipcode.setAddress2("千代田区");
		zipcode.setAddress3("千代田");
		zipcode.setKana1("ﾄｳｷｮｳﾄ");
		zipcode.setKana2("ﾁﾖﾀﾞｸ");
		zipcode.setKana3("ﾁﾖﾀ");
		zipcode.setPrefcode(1);
		zipcode.setZipcode("1000001");
		results.add(zipcode);
		
		response.setMessage(null);
		response.setResults(results);
		response.setStatus(200);

		when(zipcodeRepository.searchAddress(any())).thenReturn(response);
	    doNothing().when(zipcodeDbRepository).saveZipcode(any());
		
        AddressEntity addressForm = new AddressEntity();
        addressForm.setZipcode("0790177");
		List<ZipcodeDataDto> actual = sut.saveAddress(addressForm);
		
		assertEquals("東京都千代田区千代田", actual.get(0).getAddress());
		assertEquals("ﾄｳｷｮｳﾄﾁﾖﾀﾞｸﾁﾖﾀ", actual.get(0).getKana());
		assertEquals("1000001", actual.get(0).getZipcode());
		verify(zipcodeDbRepository, times(1)).saveZipcode(any());;
	}
	
	@Test
	void saveAddress_データ取得で500エラーが発生した場合_IOExceptionが発生すること() throws IOException {

		AddressDtoResponse response = new AddressDtoResponse();
		
		List<ZipcodeData> results = new ArrayList<>();
		ZipcodeData zipcode = new ZipcodeData();
		zipcode.setAddress1("東京都");
		zipcode.setAddress2("千代田区");
		zipcode.setAddress3("千代田");
		zipcode.setKana1("ﾄｳｷｮｳﾄ");
		zipcode.setKana2("ﾁﾖﾀﾞｸ");
		zipcode.setKana3("ﾁﾖﾀ");
		zipcode.setPrefcode(1);
		zipcode.setZipcode("1000001");
		results.add(zipcode);
		
		response.setMessage(null);
		response.setResults(results);
		response.setStatus(200);

		when(zipcodeRepository.searchAddress(any())).thenReturn(response);
		
		doAnswer(invocation -> {
		    throw new IOException("Serviceクラスでエラーが発生しました");
		}).when(zipcodeDbRepository).saveZipcode(any());

	    assertThrows(IOException.class, () -> {
	        sut.saveAddress(any());
	    });
	}
	
	@Test
	void getList_データを取得できること(){
	
		List<Zipcode> zipcodeList = new ArrayList<>();
		Zipcode zipcode = new Zipcode();
		
		zipcode.setId("hogehoge");
		zipcode.setZipcode("1000001");
		zipcode.setAddress("東京都千代田区千代田");
		zipcode.setKana("ﾄｳｷｮｳﾄﾁﾖﾀﾞｸﾁﾖﾀ");
		
		zipcodeList.add(zipcode);
		
		when(zipcodeDbRepository.getList()).thenReturn(zipcodeList);
		
		List<Zipcode> actual = zipcodeDbRepository.getList();
		
		assertEquals("hogehoge", actual.get(0).getId());
		assertEquals("東京都千代田区千代田", actual.get(0).getAddress());
		assertEquals("ﾄｳｷｮｳﾄﾁﾖﾀﾞｸﾁﾖﾀ", actual.get(0).getKana());
		assertEquals("1000001", actual.get(0).getZipcode());
	}
	
	@Test
	void getList_500エラーが発生した場合_Exceptionをスローすること(){
	
		doThrow(new RuntimeException("データ取得で500エラーが発生しました")).when(zipcodeDbRepository).getList();
	    
	    assertThrows(Exception.class, () -> {
	        sut.getList();
	    });
	}
}
