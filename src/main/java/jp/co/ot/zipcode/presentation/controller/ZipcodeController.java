package jp.co.ot.zipcode.presentation.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ot.zipcode.application.service.ZipcodeService;
import jp.co.ot.zipcode.domain.model.ErrorDetail;
import jp.co.ot.zipcode.domain.model.ErrorResponse;
import jp.co.ot.zipcode.domain.model.request.AddressEntity;

@RestController
@RequestMapping("/zipcode")
public class ZipcodeController {

	@Autowired
	private ZipcodeService zipcodeService;

	Logger logger = LoggerFactory.getLogger(ZipcodeController.class);

	@GetMapping("/search")
	public ResponseEntity<?> searchAddress(AddressEntity addressForm) throws IOException {

		if (!addressForm.getZipcode().matches("\\d{7}")) {
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "000", "7桁数字以外の値が指定されています",
					"7桁数字以外の値が指定されています", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		try {
			return ResponseEntity.ok().body(zipcodeService.searchAddress(addressForm));
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "000", "問題が発生しました",
					"問題が発生しました", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@PostMapping("/search")
	public ResponseEntity<?> saveAddress(AddressEntity addressForm) {
		
		if (!addressForm.getZipcode().matches("\\d{7}")) {
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "400", "7桁数字以外の値が指定されています",
					"7桁数字以外の値が指定されています", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		try {
			return ResponseEntity.ok().body(zipcodeService.saveAddress(addressForm));
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500", "問題が発生しました",
					"問題が発生しました", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	@GetMapping("/get")
	public ResponseEntity<?> getList() {
		try {
			return ResponseEntity.ok().body(zipcodeService.getList());
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500", "問題が発生しました", "問題が発生しました", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
}
