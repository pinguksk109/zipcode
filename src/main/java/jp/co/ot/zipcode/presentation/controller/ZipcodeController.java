package jp.co.ot.zipcode.presentation.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jp.co.ot.zipcode.application.service.ZipcodeService;

@RestController
@RequestMapping("/zipcode")
public class ZipcodeController {

	@Autowired
	private ZipcodeService zipcodeService;
	
	@GetMapping
	public ResponseEntity<?> searchAddress(@RequestParam("zipcode") String zipcode, BindingResult bindingResult, HttpServletRequest request) throws IOException {
		String response = zipcodeService.searchAddress(zipcode);
		
		return ResponseEntity.ok().body(response);
	}
}
