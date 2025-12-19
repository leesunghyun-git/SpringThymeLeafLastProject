package com.sist.web.commons;

import org.springframework.web.bind.annotation.ControllerAdvice;
// @Controller에서 발생 예외를 처리 = 공통 기반
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class CommonsException {
	@ExceptionHandler(Exception.class)
	public void exception(Exception ex)
	{
		System.out.println("======== Exception 발생 ===========");
		ex.printStackTrace();
	}
	// 예외 + 에러 (수정이 불가능한 에러)
	@ExceptionHandler(Throwable.class)
	public void throwable(Throwable ex)
	{
		System.out.println("=========== Throwable 발생 ==========");
		ex.printStackTrace();
	}
}
