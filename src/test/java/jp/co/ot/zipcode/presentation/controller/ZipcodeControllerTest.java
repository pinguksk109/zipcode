package jp.co.ot.zipcode.presentation.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.ot.zipcode.application.service.ZipcodeService;
import jp.co.ot.zipcode.domain.model.Zipcode;
import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;


@ExtendWith(MockitoExtension.class)
class ZipcodeControllerTest extends ZipcodeController {

	@InjectMocks
	private ZipcodeController sut;
	
	private MockMvc mvc;
	
	@Mock
	private ZipcodeService zipcodeService;
	
    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	mvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

	
	@Test
	public void GET_searchAddress_処理が正常に行われた場合_HTTPステータス200を返すこと() throws Exception {
		List<ZipcodeDataDto> dtoList = new ArrayList();
		ZipcodeDataDto dto = new ZipcodeDataDto("東京都", "千代田区", "千代田", "ﾄｳｷｮｳﾄ", "ﾁﾖﾀﾞｸ", "ﾁﾖﾀ", "1000001"); 
		dtoList.add(dto);
		when(zipcodeService.searchAddress(any())).thenReturn(dtoList);
		
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/zipcode/search")
                .param("zipcode", "1000001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
	}
	
	@Test
	public void GET_searchAddress_処理が正常に行われデータがないと返ってきた場合_HTTPステータス204を返すこと() throws Exception {
		List<ZipcodeDataDto> dtoList = new ArrayList();
		ZipcodeDataDto dto = new ZipcodeDataDto("指定された郵便番号はありませんでした", "", "", "", "", "", "9999999"); 
		dtoList.add(dto);
		when(zipcodeService.searchAddress(any())).thenReturn(dtoList);
		
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/zipcode/search")
                .param("zipcode", "9999999")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
	}
	
	@Test
	public void GET_searchAddress_7桁の数字以外を受け取った場合_HTTPステータス400を返すこと() throws Exception {
        String zipcode = "12345678";
        mvc.perform(MockMvcRequestBuilders.get("/zipcode/search")
        		.param("zipcode", zipcode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void GET_searchAddress_エラーを受け取った場合_HTTPステータス500を返すこと() throws Exception {
		String zipcode = "0790177";
        String expectedErrorMessage = "問題が発生しました";
        when(zipcodeService.searchAddress(any())).thenThrow(new IOException(expectedErrorMessage));
        mvc.perform(MockMvcRequestBuilders.get("/zipcode/search")
                .param("zipcode", zipcode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}
	
	@Test
	public void POST_searchAddress_処理が正常に行われた場合_HTTPステータス200を返すこと() throws Exception {
		List<ZipcodeDataDto> dtoList = new ArrayList();
		ZipcodeDataDto dto = new ZipcodeDataDto("東京都", "千代田区", "千代田", "ﾄｳｷｮｳﾄ", "ﾁﾖﾀﾞｸ", "ﾁﾖﾀ", "1000001"); 
		dtoList.add(dto);
		when(zipcodeService.saveAddress(any())).thenReturn(dtoList);
		
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/zipcode/search")
                .param("zipcode", "1000001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
	}
	
	@Test
	public void POST_saveAddress_処理が正常に行われデータがないと返ってきた場合_HTTPステータス204を返すこと() throws Exception {
		List<ZipcodeDataDto> dtoList = new ArrayList();
		ZipcodeDataDto dto = new ZipcodeDataDto("指定された郵便番号はありませんでした", "", "", "", "", "", "9999999"); 
		dtoList.add(dto);
		when(zipcodeService.saveAddress(any())).thenReturn(dtoList);
		
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/zipcode/search")
                .param("zipcode", "1000001")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
	}
	
	@Test
	public void POST_searchAddress_7桁の数字以外を受け取った場合_HTTPステータス400を返すこと() throws Exception {
        String zipcode = "12345678";
        mvc.perform(MockMvcRequestBuilders.post("/zipcode/search")
        		.param("zipcode", zipcode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void POST_searchAddress_エラーを受け取った場合_HTTPステータス500を返すこと() throws Exception {
		String zipcode = "0790177";
        String expectedErrorMessage = "問題が発生しました";
        when(zipcodeService.saveAddress(any())).thenThrow(new IOException(expectedErrorMessage));
        mvc.perform(MockMvcRequestBuilders.post("/zipcode/search")
                .param("zipcode", zipcode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}
	
	@Test
	public void getList_処理が正常に行われた場合_HTTPステータス200を返すこと() throws Exception {
		List<Zipcode> dtoList = new ArrayList();
		Zipcode dto1 = new Zipcode();
		dto1.setId("xxx");
		dto1.setZipcode("1000001");
		dto1.setAddress("東京都千代田区千代田");
		dto1.setKana("ﾄｳｷｮｳﾄﾁﾖﾀﾞｸﾁﾖﾀ");
		dtoList.add(dto1);
		Zipcode dto2 = new Zipcode();
		dto2.setId("yyy");
		dto2.setZipcode("1000001");
		dto2.setAddress("東京都千代田区千代田");
		dto2.setKana("ﾄｳｷｮｳﾄﾁﾖﾀﾞｸﾁﾖﾀ");
		dtoList.add(dto2);
		when(zipcodeService.getList()).thenReturn(dtoList);
		
		mvc.perform(MockMvcRequestBuilders.get("/zipcode/get")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void getList_エラーを受け取った場合_HTTPステータス500を返すこと() throws Exception {

		when(zipcodeService.getList()).thenThrow(new IOException());
		
		mvc.perform(MockMvcRequestBuilders.get("/zipcode/get")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

}
