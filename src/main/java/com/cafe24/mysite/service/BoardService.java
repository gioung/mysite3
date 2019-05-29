package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.repository.vo.BoardVo;
import com.cafe24.mysite.repository.vo.UserVo;


@Service
public class BoardService {
	@Autowired
	private BoardDao boardDao;
	
	public boolean writeContent(BoardVo vo) {
		List<BoardVo> list = boardDao.getList();
		long maxGroupid=0;
		
		if(list.size()>0)
			maxGroupid = boardDao.getMaxGroupid();
		
		vo.setGroup_no(maxGroupid);
		vo.setOrder_no(1L);
		vo.setDepth(0);
		return boardDao.insert(vo);
	}
	
	public int getContentCount() {
		return boardDao.getCount();
	}
	
	public List<BoardVo> getContentList(int pageNo,int NumberListPerPage) {
		return boardDao.getPageList(pageNo,NumberListPerPage);
	}

	public BoardVo getBoard(long no) {
		return boardDao.getBoard(no);
	}

	public boolean update(BoardVo boardVo) {
		return boardDao.update(boardVo);
		
	}

	public boolean delete(long no) {
		return boardDao.delete(no);
		
	}

	public void writeReply(BoardVo replyvo) {
		
		//현재 답글을 달 게시물의 정보 가져오기
		BoardVo vo = boardDao.getBoard(replyvo.getNo());
		//댓글의 조건 : group no = 부모 댓글의 group no
		long group_no = vo.getGroup_no();
		replyvo.setGroup_no(group_no);
		//order no = 부모의 order_no+1
		long order_no = vo.getOrder_no()+1;
		replyvo.setOrder_no(order_no);
		//depth = 부모의 depth+1
		long depth = vo.getDepth()+1;
		replyvo.setDepth(depth);
		
		//order_no를 update하기
		boardDao.updateOrderNo(replyvo);
		
		//System.out.println(replyvo);
		
		//위조건을 만족하는 BoardVo 객체를 insert 하기 
		boardDao.insert(replyvo);
		
	}

}
