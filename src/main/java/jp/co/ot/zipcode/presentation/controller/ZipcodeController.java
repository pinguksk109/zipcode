package jp.co.ot.zipcode.presentation.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ot.zipcode.application.service.ZipcodeService;
import jp.co.ot.zipcode.domain.model.ErrorDetail;
import jp.co.ot.zipcode.domain.model.ErrorResponse;

@RestController
@RequestMapping("/zipcode")
public class ZipcodeController {

	@Autowired
	private ZipcodeService zipcodeService;
	
	Logger logger = LoggerFactory.getLogger(ZipcodeController.class);
	
	@GetMapping
	public ResponseEntity<?> searchAddress(@RequestParam("zipcode") String zipcode) throws IOException {
		
		String response = new String();
		
		try {
			response = zipcodeService.searchAddress(zipcode);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "000", "問題が発生しました", "問題が発生しました", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}

		
		return ResponseEntity.ok().body(response);
	}
}
