package com.cafe24.mysite.controller;



import com.cafe24.security.Auth;

@Auth(role=Auth.Role.ADMIN)
public class AdminController {

	    //리퀘스트 매핑 만들고 
		public String site() {
			return "";
		}
}
