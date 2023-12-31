package jp.co.ot.zipcode.presentation.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jp.co.ot.zipcode.application.service.ZipcodeService;
import jp.co.ot.zipcode.domain.model.ErrorDetail;
import jp.co.ot.zipcode.domain.model.ErrorResponse;
import jp.co.ot.zipcode.domain.model.request.AddressEntity;
import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;
import jp.co.ot.zipcode.presentation.request.UpdateAddressListRequestQuery;

@RestController
@Validated
@RequestMapping("/zipcode")
public class ZipcodeController {

	@Autowired
	private ZipcodeService zipcodeService;

	Logger logger = LoggerFactory.getLogger(ZipcodeController.class);

	/**
	 * 住所を調べます
	 * @param addressForm
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/search")
	public ResponseEntity<?> searchAddress(AddressEntity addressForm) throws IOException {

		if(Objects.isNull(addressForm.getZipcode())) {
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "400", "郵便番号が入力されていません",
					"郵便番号が入力入力されていません", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);			
		}
		
		if (!addressForm.getZipcode().matches("\\d{7}")) {
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "400", "7桁数字以外の値が指定されています",
					"7桁数字以外の値が指定されています", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		try {
			List<ZipcodeDataDto> dtoList = zipcodeService.searchAddress(addressForm);
			if(dtoList.get(0).getAddress().equals("指定された郵便番号はありませんでした")) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok().body(dtoList);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "400", "問題が発生しました",
					"問題が発生しました", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	/**
	 * 住所を調べたうえで保存します
	 * @param addressForm
	 * @return
	 */
	@PostMapping("/search")
	public ResponseEntity<?> saveAddress(AddressEntity addressForm) {
		
		if(Objects.isNull(addressForm.getZipcode())) {
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "400", "郵便番号が入力されていません",
					"郵便番号が入力入力されていません", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);			
		}
		
		if (!addressForm.getZipcode().matches("\\d{7}")) {
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "400", "7桁数字以外の値が指定されています",
					"7桁数字以外の値が指定されています", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		try {
			List<ZipcodeDataDto> dtoList = zipcodeService.saveAddress(addressForm);
			if(dtoList.get(0).getAddress().equals("指定された郵便番号はありませんでした")) {
				return ResponseEntity.noContent().build();
			}
			return ResponseEntity.ok().body(dtoList);
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500", "問題が発生しました",
					"問題が発生しました", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}

	/**
	 * 今まで調べて保存された住所一覧を取得します
	 * @return
	 */
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
	
	@PutMapping("/put/id/{id}")
	public ResponseEntity<?> putList(@PathVariable("id") @NotBlank String id, @RequestBody @Valid UpdateAddressListRequestQuery query, BindingResult bindingResult) {
		try {
			if(bindingResult.hasErrors()) {
				ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), "400", "7桁数字以外の値が指定されています",
						"7桁数字以外の値が指定されています", "");
				ErrorResponse errorResponse = new ErrorResponse(errorDetail);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			}
			zipcodeService.putList(id, query);
			return ResponseEntity.ok().build();
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			ErrorDetail errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), "500", "問題が発生しました", "問題が発生しました", "");
			ErrorResponse errorResponse = new ErrorResponse(errorDetail);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
		}
	}
	
}
