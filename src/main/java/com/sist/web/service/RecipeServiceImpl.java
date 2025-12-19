package com.sist.web.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.sist.web.mapper.RecipeMapper;
import com.sist.web.vo.RecipeDetailVO;
import com.sist.web.vo.RecipeVO;

import lombok.RequiredArgsConstructor;
/*
 * 		VO ===> Mapper (SQL) ==> Service에서 구현 ======> Controller  =====> HTML/JSP
 * 				  |					|						|
 * 														데이터베이스에서 읽은 값을 브라우저
 * 															| Spring에서 처리
 * 																@Controller
 * 																	| ThymeLeaf / JSP
 * 																	| router
 * 															| Spring에서 데이터 전송
 * 																@RestController
 * 																	| Vue / React / Next
 * 															=> JS / TS
 * 															  ---------
 * 																일반 데이터형은 동일
 * 																Object / Array
 * 																			| List => []
 * 																| VO, Map => {}
 * 																	   | []
														 * 				class A 
														 * 				{
														 * 					int no;
														 * 					String name,sex,address;
														 * 				}
														 * 				TS
														 * 				interface A => type A
														 * 				{
														 * 					no:int,
														 * 					name:string,
														 * 					sex:string,
														 * 					address:string
														 * 				}
														 * 				JS
														 * 				{no:1,name:'',sex:'',address=''} => JSON

 * 				순수하게 SQL	SQL여러개 모아서 하나의 기능 수행		
 * 							=> hitIncrement
 * 
 * 
 */
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{
	private final RecipeMapper rMapper;
	@Override
		public List<RecipeVO> recipeListData(int start) {
			// TODO Auto-generated method stub
			return rMapper.recipeListData(start);
		}
	@Override
	public int recipeTotalPage() {
		// TODO Auto-generated method stub
		return rMapper.recipeTotalPage();
	}
	@Override
	public RecipeDetailVO recipeDetailData(int no) {
		// TODO Auto-generated method stub
		rMapper.recipeHitIncrement(no);
		return rMapper.recipeDetailData(no);
	}
	@Override
	public List<RecipeVO> recipeTop10() {
		// TODO Auto-generated method stub
		return rMapper.recipeTop10();
	}
	@Override
	public List<RecipeVO> recipeChefListData(int start, String chef) {
		// TODO Auto-generated method stub
		return rMapper.recipeChefListData(start, chef);
	}
	@Override
	public int recipeChefTotalPage(String chef) {
		// TODO Auto-generated method stub
		return rMapper.recipeChefTotalPage(chef);
	}
}
