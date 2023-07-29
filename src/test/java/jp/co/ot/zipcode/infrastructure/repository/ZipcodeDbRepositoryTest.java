package jp.co.ot.zipcode.infrastructure.repository;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class ZipcodeDbRepositoryTest {

	@Autowired
	private ZipcodeDbRepository sut;
	
    @Test
    @Ignore
    public void saveZipcode_200を受け取った場合_DBに値が保存されていること() throws IOException { 
    	ZipcodeDataDto dto = new ZipcodeDataDto("大阪府", "大阪市", "北区", "ｵｵｻｶﾌ", "ｵｵｻｶｼ", "ｷﾀｸ", "5300001");
    	sut.saveZipcode(dto);
  }

}
