package com.sist.web.service;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.MemberMapper;
import com.sist.web.vo.MemberVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	private final MemberMapper mMapper;
	
	
	@Override
	public MemberVO memberLogin(String id, String pwd) {
		MemberVO vo = new MemberVO();
		
		int count = mMapper.memberIdCount(id);
		if(count==0)
		{
			vo.setMsg("NOID");
		}
		else
		{
			MemberVO dbvo = mMapper.memberInfoData(id);
			if(dbvo.getPwd().equals(pwd))
			{
				vo = dbvo;
				vo.setMsg("YES");
			}
			else
			{
				vo.setMsg("NOPWD");
			}
		}
		return vo;
	}
}
