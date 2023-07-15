package jp.co.ot.zipcode.presentation.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.ot.zipcode.application.service.ZipcodeService;


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
	public void searchAddress_7桁の数字を受け取った場合_HTTPステータス200を返すこと() throws Exception {
		String zipcode = "0790177";
		
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/zipcode")
                .param("zipcode", zipcode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        
        resultActions.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void searchAddress_7桁の数字以外を受け取った場合_HTTPステータス400を返すこと() throws Exception {
        String zipcode = "12345678";
        mvc.perform(MockMvcRequestBuilders.get("/zipcode")
        		.param("zipcode", zipcode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void searchAddress_エラーを受け取った場合_HTTPステータス500を返すこと() throws Exception {
		String zipcode = "0790177";
        String expectedErrorMessage = "問題が発生しました";
        when(zipcodeService.searchAddress(any())).thenThrow(new IOException(expectedErrorMessage));
        mvc.perform(MockMvcRequestBuilders.get("/zipcode")
                .param("zipcode", zipcode)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
	}

}
