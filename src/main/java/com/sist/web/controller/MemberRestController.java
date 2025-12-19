package com.sist.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import com.sist.web.vo.*;

import jakarta.servlet.http.HttpSession;

import com.sist.web.service.*;
@RestController
@RequiredArgsConstructor
/*
 * 	@Repository : Mapping (데이터베이스) => SQL
 * 		|	
 *  @Service
 *  	| => SQL문장을 모아서 처리
 *  @Controller / @RestController
 *  	|
 *  DispatcherServlet => ViewResolver => HTML/JSP
 * 
 * 
 */
public class MemberRestController {
	private final MemberService mService;
	
	@GetMapping("/member/login_vue/")
	public MemberVO member_login_vue(@RequestParam("id")String id, @RequestParam("pwd")String pwd,HttpSession session)
	{
		MemberVO vo = mService.memberLogin(id, pwd);
		if(vo.getMsg().equals("YES"))
		{
			session.setAttribute("id", vo.getId());
			session.setAttribute("sex", vo.getSex());
			session.setAttribute("address", vo.getAddress());
			session.setAttribute("name", vo.getName());
			
		}
		return vo;
	}
	@GetMapping("/member/logout_vue/")
	public String member_logout_vue(HttpSession session)
	{
		String res = "YES";
		session.invalidate();	
		return res;
	}
}
