package jp.co.ot.zipcode.infrastructure.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import jp.co.ot.zipcode.domain.model.Zipcode;
import jp.co.ot.zipcode.domain.model.response.ZipcodeDataDto;

//@SpringBootTest
//@RunWith(SpringRunner.class)
public class ZipcodeDbRepositoryTest {

	@Autowired
	private ZipcodeDbRepository sut;
	
//    @Test
    public void saveZipcode_DBに値が保存されていること() throws IOException { 
    	List<ZipcodeDataDto> dtoList = new ArrayList();
    	ZipcodeDataDto dto = new ZipcodeDataDto("大阪府", "大阪市", "北区", "ｵｵｻｶﾌ", "ｵｵｻｶｼ", "ｷﾀｸ", "5300001");
    	dtoList.add(dto);
    	sut.saveZipcode(dtoList);
  }
    
//    @Test
    public void getList_DBから値と取り出せること() throws IOException { 
    	List<Zipcode> zipcodes = sut.getList();
    	// DBに沢山データある為、結果を目視確認する
    	for(Zipcode zipcode : zipcodes) {
    		System.out.println(zipcode);
    	}
    }
}
