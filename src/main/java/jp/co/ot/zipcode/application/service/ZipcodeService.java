package jp.co.ot.zipcode.application.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ot.zipcode.domain.model.Zipcode;
import jp.co.ot.zipcode.domain.model.request.AddressEntity;
import jp.co.ot.zipcode.domain.model.response.AddressDtoResponse;
import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;
import jp.co.ot.zipcode.infrastructure.repository.ZipcodeDbRepository;
import jp.co.ot.zipcode.infrastructure.repository.ZipcodeRepository;

@Service
public class ZipcodeService {

	@Autowired
	private ZipcodeRepository zipcodeRepository;
	
	@Autowired
	private ZipcodeDbRepository zipcodeDbRepository;

	/**
	 * 郵便番号に紐づく住所を取得する
	 * 
	 * @param zipcode
	 * @return
	 * @throws IOException
	 */
	public ZipcodeDataDto searchAddress(AddressEntity addressForm) throws IOException {
		try {
			// 取得処理実行
			AddressDtoResponse dtoResponse = zipcodeRepository.searchAddress(addressForm);
			if(Objects.isNull(dtoResponse.getResults())) {
				return new ZipcodeDataDto("指定された郵便番号はありませんでした", "", "", "", "", "", addressForm.getZipcode());
			}
			return new ZipcodeDataDto(dtoResponse.getResults().get(0).getAddress1(),
					dtoResponse.getResults().get(0).getAddress2(), dtoResponse.getResults().get(0).getAddress3(),
					dtoResponse.getResults().get(0).getKana1(), dtoResponse.getResults().get(0).getKana2(),
					dtoResponse.getResults().get(0).getKana3(), dtoResponse.getResults().get(0).getZipcode());
		} catch (IOException e) {
			throw new IOException("Serviceクラスでエラーが発生しました");
		}
	}

	/**
	 * 郵便番号に紐づく住所を取得し、結果をDBに保存する
	 * 
	 * @param zipcode
	 * @return
	 * @throws IOException
	 */
	public ZipcodeDataDto saveAddress(AddressEntity addressForm) throws IOException {
		try {
			// 取得処理実行
			AddressDtoResponse dtoResponse = zipcodeRepository.searchAddress(addressForm);
			if(Objects.isNull(dtoResponse.getResults())) {
				return new ZipcodeDataDto("指定された郵便番号はありませんでした", "", "", "", "", "", addressForm.getZipcode());
			}
			ZipcodeDataDto dto = new ZipcodeDataDto(dtoResponse.getResults().get(0).getAddress1(),
					dtoResponse.getResults().get(0).getAddress2(), dtoResponse.getResults().get(0).getAddress3(),
					dtoResponse.getResults().get(0).getKana1(), dtoResponse.getResults().get(0).getKana2(),
					dtoResponse.getResults().get(0).getKana3(), dtoResponse.getResults().get(0).getZipcode());
			// 保存処理
			zipcodeDbRepository.saveZipcode(dto);
			return dto;
		} catch (IOException e) {
			throw new IOException("Serviceクラスでエラーが発生しました");
		}
	}
	
	/**
	 * DBに保存されているデータをすべて取得する
	 * @return
	 * @throws IOException
	 */
	public List<Zipcode> getList() throws IOException {
		return zipcodeDbRepository.getList();
	}
}
