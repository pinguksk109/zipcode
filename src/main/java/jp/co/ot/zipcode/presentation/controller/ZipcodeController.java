package jp.co.ot.zipcode.presentation.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ot.zipcode.application.service.ZipcodeService;
import jp.co.ot.zipcode.domain.model.ErrorDetail;
import jp.co.ot.zipcode.domain.model.ErrorResponse;
import jp.co.ot.zipcode.domain.model.request.AddressEntity;
import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;

@RestController
@RequestMapping("/zipcode")
public class ZipcodeController {

	@Autowired
	private ZipcodeService zipcodeService;
	
	Logger logger = LoggerFactory.getLogger(ZipcodeController.class);
	
	@GetMapping
	public ResponseEntity<?> searchAddress(AddressEntity addressForm) throws IOException {
		
		 ZipcodeDataDto dto;
		
        if (!addressForm.getZipcode().matches("\\d{7}")) {
//        	throw new BadRequestException("7桁数字以外の値が指定されています");
        	ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "000", "7桁数字以外の値が指定されています", "7桁数字以外の値が指定されています", "");
        	ErrorResponse errorResponse = new ErrorResponse(errorDetail);
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
		
		try {
			dto = zipcodeService.searchAddress(addressForm);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "000", "問題が発生しました", "問題が発生しました", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
		
		return ResponseEntity.ok().body(dto);
	}
}
