package jp.co.ot.zipcode.application.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			return new ZipcodeDataDto(dtoResponse.getResults().get(0).getAddress1(),
					dtoResponse.getResults().get(0).getAddress2(), dtoResponse.getResults().get(0).getAddress3(),
					dtoResponse.getResults().get(0).getKana1(), dtoResponse.getResults().get(0).getKana2(),
					dtoResponse.getResults().get(0).getKana3(), dtoResponse.getResults().get(0).getZipcode());
		} catch (IOException e) {
			throw new IOException("Repositoryからデータを取得するところでエラーが発生しました");
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
			ZipcodeDataDto dto = new ZipcodeDataDto(dtoResponse.getResults().get(0).getAddress1(),
					dtoResponse.getResults().get(0).getAddress2(), dtoResponse.getResults().get(0).getAddress3(),
					dtoResponse.getResults().get(0).getKana1(), dtoResponse.getResults().get(0).getKana2(),
					dtoResponse.getResults().get(0).getKana3(), dtoResponse.getResults().get(0).getZipcode());
			// 保存処理
			zipcodeDbRepository.saveZipcode(dto, UUID.randomUUID().toString());
			return dto;
		} catch (IOException e) {
			throw new IOException("Serviceクラスでエラーが発生しました");
		}
	}
}
