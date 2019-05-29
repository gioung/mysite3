package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.repository.vo.GuestbookVo;
import com.cafe24.mysite.repository.vo.UserVo;
import com.cafe24.mysite.service.GuestbookService;
import com.cafe24.mysite.service.UserService;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	
	@RequestMapping( "" )
	public String index( Model model ,HttpSession session){
		List<GuestbookVo> list = guestbookService.getContentList();
		model.addAttribute( "list", list );
		return "guestbook/list";
	}
	
	@RequestMapping( value="/delete/{no}", method=RequestMethod.GET )
	public String deleteform( @PathVariable( "no" ) Long no, Model model,HttpSession session ){
		model.addAttribute( "no", no );
		return "guestbook/deleteform";
	}
	
	@RequestMapping( value="/delete", method=RequestMethod.POST )
	public String delete( @ModelAttribute GuestbookVo vo,HttpSession session){
		System.out.println( vo );
		guestbookService.deleteContent( vo );
		return "redirect:/guestbook";
	}
	
	@RequestMapping( value="/add", method=RequestMethod.POST )
	public String add( @ModelAttribute GuestbookVo vo,HttpSession session) {
		guestbookService.writeContent( vo );
		return "redirect:/guestbook";
	}

}