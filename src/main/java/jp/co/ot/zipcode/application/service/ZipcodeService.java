package jp.co.ot.zipcode.application.service;

import java.io.IOException;
import java.util.ArrayList;
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
	public List<ZipcodeDataDto> searchAddress(AddressEntity addressForm) throws IOException {
		try {
			List<ZipcodeDataDto> dtoList = new ArrayList<>();

			AddressDtoResponse dtoResponse = zipcodeRepository.searchAddress(addressForm);
			if(Objects.isNull(dtoResponse.getResults())) {
				dtoList.add(new ZipcodeDataDto("指定された郵便番号はありませんでした", "", "", "", "", "", addressForm.getZipcode()));
				return dtoList;
			}
			for(int i = 0; i < dtoResponse.getResults().size(); i++) {
				ZipcodeDataDto dto = new ZipcodeDataDto(dtoResponse.getResults().get(i).getAddress1(),
						dtoResponse.getResults().get(i).getAddress2(), dtoResponse.getResults().get(i).getAddress3(),
						dtoResponse.getResults().get(i).getKana1(), dtoResponse.getResults().get(i).getKana2(),
						dtoResponse.getResults().get(i).getKana3(), dtoResponse.getResults().get(i).getZipcode());
				dtoList.add(dto);
			}
			return dtoList;
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
	public List<ZipcodeDataDto> saveAddress(AddressEntity addressForm) throws IOException {
		try {
			List<ZipcodeDataDto> dtoList = new ArrayList<>();

			AddressDtoResponse dtoResponse = zipcodeRepository.searchAddress(addressForm);
			if(Objects.isNull(dtoResponse.getResults())) {
				dtoList.add(new ZipcodeDataDto("指定された郵便番号はありませんでした", "", "", "", "", "", addressForm.getZipcode()));
				return dtoList;
			}
			
			for(int i = 0; i < dtoResponse.getResults().size(); i++) {
				ZipcodeDataDto dto = new ZipcodeDataDto(dtoResponse.getResults().get(i).getAddress1(),
						dtoResponse.getResults().get(i).getAddress2(), dtoResponse.getResults().get(i).getAddress3(),
						dtoResponse.getResults().get(i).getKana1(), dtoResponse.getResults().get(i).getKana2(),
						dtoResponse.getResults().get(i).getKana3(), dtoResponse.getResults().get(i).getZipcode());
				dtoList.add(dto);
			}
			// 保存処理
			zipcodeDbRepository.saveZipcode(dtoList);
			return dtoList;
		} catch (IOException e) {
			throw new IOException("Serviceクラスでエラーが発生しました");
		}
	}
	
	/**
	 * DBに保存されているデータをすべて取得する
	 * @return
	 * @throws IOException 
	 */
	public List<Zipcode> getList() throws Exception {
		try {
			return zipcodeDbRepository.getList();
		} catch (Exception e) {
			throw new Exception("Serviceクラスでエラーが発生しました");
		}
	}
}
