package com.cafe24.mysite.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.vo.GuestbookVo;


@Service
public class GuestbookService2 {

	public List<GuestbookVo> getContentsList(long no) {
		
		  GuestbookVo first = new GuestbookVo(no, "user1","12345","test1","2019-0710");
		  GuestbookVo second = new GuestbookVo(no,"user2","12345","test2","2019-0710");
		  
		  List<GuestbookVo> list = new ArrayList<GuestbookVo>(); 
		  list.add(first);
		  list.add(second); 
		 
		
		return list;
	}

	public GuestbookVo addContents(GuestbookVo guestbookVo) {
		guestbookVo.setNo(10L);
		guestbookVo.setReg_date("2019-07-10 00:00:00");
		return null;
	}

	public Long deleteContents(Long no, String password) {
		// 서비스를 만드는게 아닌 테스트용이므로
		return no;
	}

}
