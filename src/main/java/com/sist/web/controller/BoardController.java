package com.sist.web.controller;
import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.dao.*;
import com.sist.web.entity.BoardEntity;

@Controller
@RequiredArgsConstructor
public class BoardController {
	private final BoardRepository bDao;
	
	@GetMapping("/board/list")
	public String board_list(@RequestParam(name="page",required = false)String page,Model model)
	{
		if (page==null)
			page ="1";
		int curPage = Integer.parseInt(page);
		int rowSize= 10;
		int start=((curPage-1)*rowSize)+1;
		int end = curPage*rowSize;
		
		List<BoardVO> list=bDao.boardListData(start,end);
		int count =(int)bDao.count();
		int totalPage= (int)(Math.ceil(count/10.0));
		
		model.addAttribute("list", list);
		model.addAttribute("curPage", curPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("main_html", "board/list");
		model.addAttribute("curCat", "board");
		return "main/main"; 
	}
	@GetMapping("/board/insert")
	public String board_insert(Model model)
	{
		model.addAttribute("main_html", "board/insert");
		model.addAttribute("curCat", "board");
		return "main/main"; 
	}
	@PostMapping("/board/insert_ok")
	public String board_insert_ok(@ModelAttribute("vo")BoardEntity vo)
	{
		vo.setHit(0);
		vo.setRegdate(new Date());
		vo.setNo(bDao.getMax());
		bDao.save(vo);
		return "redirect:/board/list";
	}
	@GetMapping("/board/detail")
	public String board_detail(@RequestParam("no")int no,Model model)
	{
		BoardEntity vo = bDao.findByNo(no);
		// 조회수 증가
		vo.setHit(vo.getHit()+1);
		bDao.save(vo);
		vo=bDao.findByNo(no);
		model.addAttribute("vo", vo);
		model.addAttribute("main_html", "board/detail");
		model.addAttribute("curCat", "board");
		return "main/main";
	}
	@GetMapping("/board/update")
	public String board_update(@RequestParam("no")int no,Model model)
	{
		BoardUpdateVO vo = bDao.boardUpdateData(no);
		model.addAttribute("vo", vo);
		model.addAttribute("main_html", "board/update");
		model.addAttribute("curCat", "board");
		return "main/main";
	}
	@PostMapping("/board/update_ok")
	public String board_udpate_ok(@ModelAttribute("vo")BoardEntity vo,Model model)
	{
		
		BoardEntity dbVO=bDao.findByNo(vo.getNo());
		String result="no";
		if(vo.getPwd().equals(dbVO.getPwd()))
		{
			vo.setNo(vo.getNo());
			vo.setHit(dbVO.getHit());
			bDao.save(vo);
			result="yes";
		}
		model.addAttribute("no", vo.getNo());
		model.addAttribute("res", result);
		return "board/update_ok";
	}
	@GetMapping("/board/delete")
	public String board_delete(@RequestParam("no")int no,Model model)
	{
		model.addAttribute("no", no);
		model.addAttribute("main_html", "board/delete");
		model.addAttribute("curCat", "board");
		return "main/main";
	}
	@PostMapping("/board/delete_ok")
	public String board_delete_ok(@RequestParam("no")int no,@RequestParam("pwd")String pwd,Model model)
	{
		BoardEntity vo=bDao.findByNo(no);
		String res="no";
		if(pwd.equals(vo.getPwd()))
		{
			res="yes";
			bDao.delete(vo);
		}
		model.addAttribute("res", res);
		return "board/delete_ok";
	}
}
