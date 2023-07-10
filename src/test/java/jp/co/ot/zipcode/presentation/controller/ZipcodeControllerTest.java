package jp.co.ot.zipcode.presentation.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.model.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jp.co.ot.zipcode.application.service.ZipcodeService;


@ExtendWith(MockitoExtension.class)
class ZipcodeControllerTest extends ZipcodeController {

	@InjectMocks
	private ZipcodeController sut;
	
	private MockMvc mvc;
	
	@Mock
	private ZipcodeService zipcodeService;
	
    @Before
    public void setUp() {
      MockitoAnnotations.initMocks(this);
      mvc = MockMvcBuilders.standaloneSetup(sut).build();
    }

	
	@Test
	public void searchAddress_7桁の数字を受け取った場合_HTTPステータス200を返すこと() {
        String zipcode = "0790177";
        mvc.perform(MockMvcRequestBuilders.get("/zipcode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(zipcode))
                .andExpect(status().isOk());
	}
	
	

}
