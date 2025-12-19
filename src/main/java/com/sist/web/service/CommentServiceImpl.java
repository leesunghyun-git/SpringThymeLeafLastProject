package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.CommentMapper;
import com.sist.web.vo.CommentVO;

import lombok.RequiredArgsConstructor;

@Service // BI => 여러개 DAO 통합해서 사용 , SQL문장 통합
@RequiredArgsConstructor 
public class CommentServiceImpl implements CommentService{
	// 필요한 객체를 스프링으로부터 가지고 온다 => @Autowired / getBean()
	// private 변수에 접근이 가능 (OOP를 무시) => 가급적이면 생성자에서 값을 주입
	// 리플렉션 Class.forName()
	private final CommentMapper cMapper;
	
	@Override
	public void commentDelete(int no) {
		// TODO Auto-generated method stub
		cMapper.commentDelete(no);
	}
	@Override
	public void commentInsert(CommentVO vo) {
		// TODO Auto-generated method stub
		cMapper.commentInsert(vo);
	}
	@Override
	public List<CommentVO> commentListData(int cno, int type) {
		// TODO Auto-generated method stub
		return cMapper.commentListData(cno, type);
	}
	@Override
	public void commentUpdate(String msg, int no) {
		// TODO Auto-generated method stub
		cMapper.commentUpdate(msg, no);
	}
}
	