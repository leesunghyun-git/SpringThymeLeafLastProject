package com.sist.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sist.web.entity.BoardEntity;
import com.sist.web.vo.BoardUpdateVO;
import com.sist.web.vo.BoardVO;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer>{
	// 상세보기 : 메소드
	public BoardEntity findByNo(int no);
	// SELECT * FROM board WHERE no=1
	/*
	 * 	findByNameContains => %name%
	 * 
	 */
	// update, insert , deletem ,count
	@Query(value="SELECT no,subject,name,TO_CHAR(regdate,'yyyy-mm-dd') as dbday,hit,num "
			+ "FROM (SELECT no,subject,name,regdate,hit,ROWNUM as num "
			+ "FROM (SELECT no,subject,name,regdate,hit "
			+ "FROM board_2 ORDER BY no DESC)) "
			+ "WHERE num BETWEEN :start AND :end" , nativeQuery = true)
	// nativeQuery => SQL 문장을 그대로 적용 JPQL로 	
	public List<BoardVO> boardListData(@Param("start")Integer start, @Param("end") Integer end);
	
	@Query(value="SELECT NVL(MAX(no)+1,1) FROM board_2" , nativeQuery = true)
	public int getMax();
	
	// 수정 데이터 읽기
	@Query(value="SELECT no,subject,name,TO_CHAR(content) as content FROM board_2 WHERE no=:no",nativeQuery = true)
	public BoardUpdateVO boardUpdateData(@Param("no")int no);
	// 수정 => save()
	// 삭제 => delete()
	// 총갯수 => count()
	// 전체 데이터 => findAll()
	// WHERE => finyBy컬럼명오라클명령문
	// 상세보기 => findByNo() => findByFno() = 
	//  		 ------ WHERE no   WHERE fno 
}
