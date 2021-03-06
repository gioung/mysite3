package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.GuestbookDao;
import com.cafe24.mysite.repository.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookDao guestbookDao;
	
	public List<GuestbookVo> getContentList() {
		return guestbookDao.getList();
	}
	
	public List<GuestbookVo> getContentList(Long lastno) {
		
		return guestbookDao.getList(lastno);
	}
	
	public boolean deleteContent( GuestbookVo vo ){
		return 1 == guestbookDao.delete( vo );
	}
	
	public boolean writeContent( GuestbookVo vo ) {
		int count = guestbookDao.insert(vo);
		//System.out.println(vo.getNo());
		return count == 1;
	}
}