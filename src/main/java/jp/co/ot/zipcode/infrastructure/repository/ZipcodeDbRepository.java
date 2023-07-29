package jp.co.ot.zipcode.infrastructure.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;

@Repository
@Mapper
public interface ZipcodeDbRepository {
	
	/**
	 * 郵便番号に紐づく情報をDBに保存する
	 * @param dto
	 * @param id
	 */
	public void saveZipcode(@Param("dto") ZipcodeDataDto dto);
	
}
