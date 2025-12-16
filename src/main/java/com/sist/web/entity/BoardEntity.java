package com.sist.web.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
// save() , delete()
// findByNo(int no) WHERE no=1
// findByName(String name)
// findByNameLike
// 단점 : JOIN / SubQuery를 지원하지 않는다
@Data
@Entity(name="board")
public class BoardEntity {
	@Id
	private int no;
	
	private int hit;
	private String name,subject,content,pwd;
	private Date regdate;
}
	