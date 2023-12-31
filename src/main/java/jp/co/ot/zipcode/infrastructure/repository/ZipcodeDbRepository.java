package jp.co.ot.zipcode.infrastructure.repository;

import java.util.List;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import jp.co.ot.zipcode.domain.model.Zipcode;
import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;
import jp.co.ot.zipcode.presentation.request.UpdateAddressListRequestQuery;

@Repository
@Mapper
public interface ZipcodeDbRepository {
	
	/**
	 * 郵便番号に紐づく情報をDBに保存する
	 * @param dto
	 * @param id
	 */
	void saveZipcode(@Param("dto") List<ZipcodeDataDto> dto);
	
	/**
	 * DBのデータをすべて取得する
	 * @return
	 */
	List<Zipcode> getList();

	
	void putList(String id, @Valid UpdateAddressListRequestQuery query);
	
}
