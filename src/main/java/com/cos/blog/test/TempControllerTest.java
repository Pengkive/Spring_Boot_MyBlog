package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //해당 경로 이하에 있는 파일을 리턴하는 어노테이션
public class TempControllerTest {

	//http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		//파일 리턴 기본경로 : src/main/resources/static
		//리턴명 : /home.html
		//풀경로 : src/main/resources/static/home.html
		return "/home.html"; 
	}
	
	@GetMapping("/temp/img")
	public String tempImg() {
		return "/img.jpg";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//jsp 파일은 동적 파일이므로 static 폴더에서 작업x
		//	=> src/main/webapp/WEB-INF/views 폴더를 생성하여 그 곳에서 처리해야함.
		//웹서버인 아파치 대신 톰캣이 컴파일해서 웹브라우저에 전달한다.
		
		//prefix:/WEB-INF/views/   (컨트롤러가 리턴할때 앞에 붙는 경로)
		//suffix:.jsp	(뒤에 붙는 확장자)
		//return "/test.jsp"; 인 경우
		//풀네임:/WEB-INF/views//test.jsp.jsp 로 인식된다.
		
		return "test";
	}
	
}