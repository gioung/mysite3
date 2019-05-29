package com.cafe24.mysite.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

	@RequestMapping({"/","/main"})
	public String main() {
		return "/main/index";
	}
	
	/*@RequestMapping("/hello")
	@ResponseBody
	public String hello() {
		return "안녕하세요!";
	}
	
	@RequestMapping("/hello2")
	@ResponseBody
	public Map<String,String> hello2(){
		Map<String,String> map = new HashMap<>();
		map.put("이름", "남기웅");
		map.put("나이", "27");
		map.put("직업", "프로그래머");
		
		return map;
	}*/
	
	@RequestMapping("/nologin")
	public String nologin() {
		return "/main/nologin";
	}
	
	
	
}
