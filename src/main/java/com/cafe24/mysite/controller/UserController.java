package com.cafe24.mysite.controller;


import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cafe24.mysite.repository.vo.UserVo;
import com.cafe24.mysite.service.UserService;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping("/user")
public class UserController {
	public UserController() {
		System.out.println("UserController constructor");
	}
	
	@Autowired
	private UserService userService;
	

	
	
	@RequestMapping(value="/join",method=RequestMethod.GET)
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}
	
	//@ResponseBody
	@RequestMapping(value="/join",method=RequestMethod.POST)
	public String join(@Valid @ModelAttribute UserVo userVo,
			BindingResult result,
			Model model) {
		if(result.hasErrors()) {
			model.addAttribute(result.getModel());
			//System.out.println(result.getModel());
			return "user/join";
		}
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}
	
	@RequestMapping(value="/joinsuccess")
	public String joinSuccess(@ModelAttribute UserVo userVo) {
		return "/user/joinsuccess";
	}
	
	@RequestMapping(value="/login" ,method=RequestMethod.GET)
	public String login() {
		return "/user/login";
	}
	
	//Dao는 기술침투 
	/*@RequestMapping(value="/login" ,method=RequestMethod.POST)
	public String login(@RequestParam(value="email",required=true,defaultValue="")String email,
			@RequestParam(value="password",required=true,defaultValue="")String password,
			HttpSession session,Model model) {
		
		UserVo authUser = userService.getUser(new UserVo(email, password));
		if(authUser==null) {
			model.addAttribute("result","fail");
			return "user/login";
		}
		//세션처리
		session.setAttribute("authUser",authUser);
		
		return "redirect:/";
	}*/
	
	//ModelAndView 객체 이용
	@RequestMapping(value="/auth",method=RequestMethod.POST)
	public ModelAndView login(@RequestParam(value="email",required=true,defaultValue="")String email,
			@RequestParam(value="password",required=true,defaultValue="")String password) {
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/");
		
		
		return mv;
	}
	
	
	
	@RequestMapping("/logout")
	public String logout() {
		return "redirect:/";
	}
	
	
	@RequestMapping(value="/update",method=RequestMethod.GET)
	public String update(
			@AuthUser UserVo authUser,
			HttpSession session,Model model) {
		
		//UserVo userVo = (UserVo)session.getAttribute("authUser");
		if(authUser==null) {
			return "redirect:/";
		}
		model.addAttribute("userVo", authUser);
		
		return "/user/update";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@ModelAttribute UserVo userVo,Model model) {
		//POST로 받은 값 넣기
		userService.update(userVo);
		model.addAttribute("result", "success");
		
		return "/user/update";
		
	}
	
	
	/*
	@ExceptionHandler(UserDaoException.class)
	public String handleUserDaoException() {
		return "error/exception";
	}*/

}
