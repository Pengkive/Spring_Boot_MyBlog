package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//@Getter
//@Setter

@Data //getter, setter 두개 다 쓰고 싶을때
@NoArgsConstructor
public class Member {

	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder //builer pattern 을 사용하면 생성자의 값 순서 상관없이 데이터 입력 가능
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	
	
}