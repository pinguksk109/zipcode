package jp.co.ot.zipcode.presentation.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.ot.zipcode.application.service.ZipcodeService;
import jp.co.ot.zipcode.domain.model.request.AddressEntity;
import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;

@Controller
public class WebZipcodeController {
	
	@Autowired
	private ZipcodeService zipcodeService;

	@GetMapping("zipcode/search/web")
	public String searchAddressWeb(Model model, AddressEntity addressForm) throws IOException {
		
		List<ZipcodeDataDto> dtoList = zipcodeService.searchAddress(addressForm);
		
		model.addAttribute("dtoList", dtoList);
		return "index";
	}
	
}
