package com.sist.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.sist.web.vo.RecipeDetailVO;
import com.sist.web.vo.RecipeVO;

@Mapper
@Repository
public interface RecipeMapper {
	@Select("SELECT no,poster,title,chef FROM recipe WHERE no IN ((SELECT no FROM recipe) INTERSECT (SELECT no FROM recipedetail)) ORDER BY no ASC "
			+ "OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<RecipeVO> recipeListData(int start);
	
	@Select("SELECT CEIL(COUNT(*)/12.0) FROM recipe WHERE no IN ((SELECT no FROM recipe) INTERSECT (SELECT no FROM recipedetail))")
	public int recipeTotalPage();
	
	@Update("UPDATE recipe SET hit=hit+1 WHERE no=#{no}")
	public void recipeHitIncrement(int no);
	
	@Select("SELECT * FROM recipeDetail WHERE no = #{no}")
	public RecipeDetailVO recipeDetailData(int no);
	
	@Select("SELECT no, title, hit, chef "
			+ "FROM (SELECT no , title , hit, chef, "
			+ "ROW_NUMBER() OVER (PARTITION BY chef ORDER BY hit DESC) as rn "
			+ "FROM recipe) "
			+ "WHERE rn = 1 "
			+ "ORDER BY hit DESC "
			+ "FETCH FIRST 10 ROWS ONLY")
	public List<RecipeVO> recipeTop10();
	
	
	@Select("SELECT no,poster,title,chef FROM recipe WHERE REGEXP_LIKE(chef,#{chef}) AND no IN ((SELECT no FROM recipe) INTERSECT (SELECT no FROM recipedetail)) ORDER BY no ASC OFFSET #{start} ROWS FETCH NEXT 12 ROWS ONLY")
	public List<RecipeVO> recipeChefListData(@Param("start")int start,@Param("chef")String chef);

	@Select("SELECT CEIL(COUNT(*)/12.0) FROM recipe WHERE REGEXP_LIKE(chef,#{chef}) AND no IN ((SELECT no FROM recipe) INTERSECT (SELECT no FROM recipedetail))")
	public int recipeChefTotalPage(String chef);
}
