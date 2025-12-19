package com.sist.web.controller;
// 브라우저로 전송
import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.vo.*;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import com.sist.web.service.*;
/*
 * 		HttpServletRequest
 * 		
 * 		=> 클래스 캡슐화
 * 		class Model
 * 		{
 * 			HttpServletRequset request => DispatcherServlet
 * 			public void addAttribute(String key, Object obj)
 * 			{
 * 				request.setAttribute(key,obj)
 * 			}
 * 		}
 * 		--------------------
 * 		@Controller
 * 		@RestController
 * 		-------------------- DispatcherServlet => 사용자 요청 처리는 Controller에서만
 * 
 * 		한개 page =  @RequestParam("page")
 * 		VO 단위 => @ModelAttribute("vo")
 * 		{		=> JSON => 객체로변환 @RequestBody 
 * 			no:1,
 * 			name:'',
 * 			...
 * 		}
 * 
 * 		@ResponseBody @RestController
 * 			|				|
 * 			-----------------
 * 				| 다른 언어로 값을 전송
 * 		메소드 => 승격 => 연산자
 * 		free / malloc / 
 * 		  |		  |
 * 		 delete	 new 
 * 
 * 	XML => 변경 (어노테이션)
 *   | 자바스크립트 : JSON
 */
@Controller
@RequestMapping("/recipe/")
@RequiredArgsConstructor
public class RecipeController {
	private final RecipeService rService;
	
	@GetMapping("list")
	public String Recipe_list(@RequestParam(name="page",required = false)String page,Model model)
	{
		if(page==null)
			page="1";
		int curPage=Integer.parseInt(page);
		int rowSize = 12;
		int start = (curPage-1)*rowSize;
		int totalPage= rService.recipeTotalPage();
		List<RecipeVO> list = rService.recipeListData(start);
		final int BLOCK = 10;
		int startPage= ((curPage-1)/BLOCK*BLOCK)+1;
		int endPage= ((curPage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalPage)
			endPage=totalPage;
		model.addAttribute("list", list);
		model.addAttribute("curPage", curPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("curCat", "recipe");
		model.addAttribute("main_html", "recipe/list");
		return "main/main";
	}
	@GetMapping("detail")
	public String recipe_detail(@RequestParam("no")int no,Model model,HttpSession session)
	{
		RecipeDetailVO vo = rService.recipeDetailData(no);
		model.addAttribute("vo", vo);
		// 댓글
		List<String> mList=new ArrayList<>();
		List<String> nList=new ArrayList<>();
		String[] datas = vo.getFoodmake().split("\n");
		for(String s:datas)
		{
			StringTokenizer st = new StringTokenizer(s,"^");
			mList.add(st.nextToken());
			nList.add(st.nextToken());
		}
		String id = (String)session.getAttribute("id");
		if(id==null)
		{
			model.addAttribute("sessionId", "");
		}
		else
		{
			model.addAttribute("sessionId", id);
		}
		model.addAttribute("mList", mList);
		model.addAttribute("nList", nList);		
		model.addAttribute("curCat", "recipe");
		model.addAttribute("main_html", "recipe/detail");
		return "main/main";
	}
	@GetMapping("chef_list")
	public String recipe_chef_list(@RequestParam(name="page",required = false)String page,@RequestParam("chef")String chef,Model model)
	{
		if(page==null)
			page="1";
		int curPage=Integer.parseInt(page);
		int rowSize = 12;
		int start = (curPage-1)*rowSize;
		int totalPage= rService.recipeChefTotalPage(chef);
		List<RecipeVO> list = rService.recipeChefListData(start, chef);
		final int BLOCK = 10;
		int startPage= ((curPage-1)/BLOCK*BLOCK)+1;
		int endPage= ((curPage-1)/BLOCK*BLOCK)+BLOCK;
		if(endPage>totalPage)
			endPage=totalPage;
		model.addAttribute("list", list);
		model.addAttribute("curPage", curPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("chef", chef);
		model.addAttribute("curCat", "recipe");	
		model.addAttribute("main_html", "recipe/chef_list");
		return "main/main";
	}
	
}
