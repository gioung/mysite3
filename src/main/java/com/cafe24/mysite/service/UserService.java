package com.cafe24.mysite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.UserDao;
import com.cafe24.mysite.repository.vo.UserVo;

@Service
public class UserService {
	public UserService() {
		System.out.println("UserService constructora");
	}
	
	@Autowired
	private UserDao userDao;
	
	public Boolean existEmail(String email) {
		UserVo userVo=userDao.get(email);
		return userVo != null;
	}
	
	public boolean join(UserVo userVo) {
		return userDao.insert(userVo);
	}
	
	
	public UserVo getUser(Long userNo) {
		return userDao.get(userNo);
	}
	public UserVo getUser(UserVo userVo) {
		return userDao.get(userVo.getEmail(), userVo.getPassword());
	}
	
	public int update(UserVo userVo) {
		return userDao.update(userVo);
	}
	

	

}
