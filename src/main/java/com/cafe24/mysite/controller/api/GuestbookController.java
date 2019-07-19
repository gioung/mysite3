package com.cafe24.mysite.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cafe24.mysite.dto.JSONResult;
import com.cafe24.mysite.repository.vo.GuestbookVo;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.service.GuestbookService2;

@RestController("guestbookAPIController")
@RequestMapping(value="/api/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService; //서비스가 없는 상황을가정한 예제
	@Autowired
	private GuestbookService2 guestbookService2;
	
	//test할때 실제 코드를 넣고 하는게 나음.
	@RequestMapping(value="/list/{lastNo}", method=RequestMethod.GET)
	public JSONResult list(@PathVariable(value="lastNo")long lastNo) {
		List<GuestbookVo> list = guestbookService.getContentList(lastNo);
		return JSONResult.success(list);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public JSONResult add(@RequestBody GuestbookVo guestbookVo) {
		guestbookService.writeContent(guestbookVo);
		return JSONResult.success(guestbookVo);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public JSONResult delete(@RequestBody Map<String, Object> map) {
		Long no = guestbookService2.deleteContents((Long)map.get("no"), (String)map.get("password"));
		return JSONResult.success(no);
	}
	
}


