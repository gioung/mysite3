package com.cafe24.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.repository.vo.BoardVo;

@Repository
public class BoardDao {
	
	
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(BoardVo vo) {
		return (1==sqlSession.insert("board.insert", vo));
	}
	
	public long getMaxGroupid() {
		long maxGroupid = sqlSession.selectOne("board.getMaxGroupid");
		
		return maxGroupid;
	}
	
	public List<BoardVo> getList(){
		List<BoardVo> result = sqlSession.selectList( "board.getList");
		return result;
	}

	public BoardVo getBoard(long no) {
		BoardVo boardVo = sqlSession.selectOne("board.getBoard",no);
		return boardVo;
	}
	
	public List<BoardVo> getPageList(int pageNo, int numberListPerPage) {
		Map<String,Integer> pageInfoList = new HashMap<>();
		pageInfoList.put("pageNo", pageNo);
		pageInfoList.put("numberListPerPage",numberListPerPage);
		List<BoardVo> list = sqlSession.selectList("board.getPageList", pageInfoList);
		
		return list;
	}

	public boolean update(BoardVo boardVo) {
		int correct = sqlSession.update("board.update", boardVo);
		return (correct==1);
		
	}

	public boolean delete(long no) {
		int correct = sqlSession.delete("board.delete", no);
		return (correct==1);
	}

	public void updateOrderNo(BoardVo replyvo) {
		sqlSession.update("board.updateOrderNo", replyvo);
		
	}

	public int getCount() {
		
		return sqlSession.selectOne("board.getCount");
	}

	
	
	
}
