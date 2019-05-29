package com.cafe24.mysite.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.repository.vo.BoardVo;
import com.cafe24.mysite.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	BoardService boardservice;
	private final static int NUMBER_LIST_PERPAGE=5;
	private final String REDIRECT = "redirect:/board/1";
	
	@RequestMapping("/{pageNo}")
	public String list(@PathVariable("pageNo")int pageNo, Model model,String search) {
		
		int listCount = boardservice.getContentCount();
		System.out.println("listCount = " + listCount);
		int pageCount = listCount/NUMBER_LIST_PERPAGE;
		System.out.println("pageCount = " + pageCount);
		int remainder = (int)(listCount%NUMBER_LIST_PERPAGE);
		if(remainder > 0)
			pageCount++;
		
		if(pageNo < 1 || pageNo > pageCount) {
			pageNo = 1;
		}
		List<BoardVo> list = boardservice.getContentList(pageNo, NUMBER_LIST_PERPAGE);
		model.addAttribute("list", list);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageCount", pageCount);
		
		System.out.println(pageCount);
		return "board/list";
	}
	
	//@RequestMapping("")
	
	
	
	@RequestMapping(value="/write",method=RequestMethod.GET)
	public String write() {
		
		return "board/write";
		
	}
	
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardVo) {
		
		boardservice.writeContent(boardVo);
		return REDIRECT;
	}
	
	@RequestMapping(value="/view/{no}",method=RequestMethod.GET)
	public String view(@PathVariable("no") long no,Model model) {
		//Interceptor가 세션 유저 no값과 글 작성자 no값을 비교
		
		//no 값을 받아서 해당 게시물의 번호와 내용이 있는 BoardVo객체 받아오기
		BoardVo boardVo = boardservice.getBoard(no);
		//model 속성에 BoardVo객체 추가
		model.addAttribute("boardVo", boardVo);
		//view.jsp로 값 리턴
		return "board/view";
	}
	
	@RequestMapping(value="/update/{no}",method=RequestMethod.GET)
	public String update(@PathVariable("no") long no,Model model) {
		//인터셉터로 세션 유저가 현재 글작성자인지 판단
		
		//현재 board_no에 해당하는 BoardVo 객체 리턴
		BoardVo boardVo = boardservice.getBoard(no);
		
		//model 객체를 이용해 setAttribute
		model.addAttribute("boardVo",boardVo);
		
		//view 리턴
		return "board/update";
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(@ModelAttribute BoardVo boardVo ,
			Model model) {
		//수정 쿼리 실행 후 view 리턴
		boardservice.update(boardVo);
		model.addAttribute("result", "success");
		return "board/view";
	}
	
	@RequestMapping(value="/delete/{no}",method=RequestMethod.GET)
	public String delete(@PathVariable("no") long no) {
		//인터셉터로 세션 유저가 현재 글작성자인지 판단
		
		//글번호 delete 쿼리 실행
		boardservice.delete(no);
		//redirect:/board
		return REDIRECT;
	}
	
	@RequestMapping(value="/reply/{no}",method=RequestMethod.GET)
	public String reply(@PathVariable("no") long no,Model model) {
		BoardVo boardVo = new BoardVo();
		boardVo.setNo(no);
		model.addAttribute("boardVo", boardVo);
		return "board/reply";
	}
	
	@RequestMapping(value="/reply",method=RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo replyVo) {
		System.out.println(replyVo);
		//댓글 insert 
		boardservice.writeReply(replyVo);
		//board로 redirect
		return REDIRECT;
	}
	
	
	
	
	
}
