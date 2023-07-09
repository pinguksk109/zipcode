package jp.co.ot.zipcode.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public String searchAddress(String zipcode) throws IOException {
		try {
			// 取得処理実行
			return zipcodeRepository.searchAddress(zipcode);
		} catch (IOException e) {
			return("郵便番号の取得でエラーが発生しました");
		}
	}
}
