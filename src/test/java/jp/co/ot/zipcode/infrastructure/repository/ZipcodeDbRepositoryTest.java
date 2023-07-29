package jp.co.ot.zipcode.infrastructure.repository;

import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ZipcodeDbRepositoryTest {

	@Autowired
	private ZipcodeDbRepository sut;
	
    @Test
//  @Ignore
    public void saveZipcode_200を受け取った場合_DBに値が保存されていること() throws IOException { 
    	ZipcodeDataDto dto = new ZipcodeDataDto("大阪府", "大阪市", "北区", "ｵｵｻｶﾌ", "ｵｵｻｶｼ", "ｷﾀｸ", "5300001");
    	sut.saveZipcode(dto, UUID.randomUUID().toString());
  }

}
