package com.sist.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.FoodService;
import com.sist.web.vo.FoodVO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class FoodController {
	private final FoodService fService;
	
	@GetMapping("/food/list")
	public String food_list(@RequestParam(name="page",required = false)String page,Model model)
	{
		if (page==null)
			page ="1";
		int curPage = Integer.parseInt(page);
		List<FoodVO> list = fService.foodListData(curPage);
		final int rowSize = 12;
		int start = (curPage-1)*rowSize;
		final int BLOCK = 10;
		int totalPage= fService.foodTotalPage();
		int startPage= ((curPage-1)/BLOCK*BLOCK)+1;
		int endPage=((curPage-1)/BLOCK*BLOCK)+BLOCK;
		if (endPage>totalPage)
			endPage=totalPage;
		model.addAttribute("curPage", curPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("list", list);
		model.addAttribute("curCat", "food");
		model.addAttribute("main_html", "food/list");
		return "main/main";
	}
	@GetMapping("/food/detail")
	public String food_detail(@RequestParam("fno")int fno,Model model,HttpSession session)
	{
		FoodVO vo = fService.foodDetailData(fno);
		String id = (String)session.getAttribute("id");
		if(id==null)
		{
			model.addAttribute("sessionId", "");
		}
		else
		{
			model.addAttribute("sessionId", id);
		}
		model.addAttribute("vo", vo);
		model.addAttribute("curCat", "food");
		model.addAttribute("main_html", "food/detail");
		return "main/main";
	}
}
