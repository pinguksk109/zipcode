package jp.co.ot.zipcode.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.ot.zipcode.domain.model.request.AddressEntity;
import jp.co.ot.zipcode.domain.model.response.AddressDto;
import jp.co.ot.zipcode.infrastructure.repository.ZipcodeRepository;

@Service
public class ZipcodeService {

	@Autowired
	private ZipcodeRepository zipcodeRepository;
	
	/**
	 * 郵便番号に紐づく住所を取得する
	 * @param zipcode
	 * @return
	 * @throws IOException
	 */
	public AddressDto searchAddress(AddressEntity addressForm) throws IOException {
		try {
			// 取得処理実行
			return zipcodeRepository.searchAddress(addressForm);
		} catch (IOException e) {
			throw new IOException("Repositoryからデータを取得するところでエラーが発生しました");
		}
	}
}
