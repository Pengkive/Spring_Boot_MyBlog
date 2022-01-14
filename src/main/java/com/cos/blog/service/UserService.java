package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. = IoC(제어의 역전)를 해준다. = 메모리를 대신 띄워준다.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional //import 확인!
	public void 회원가입(User user) {
		String rawPassword = user.getPassword();	//비밀번호 원문
		String encPassword = encoder.encode(rawPassword); //해쉬화
		user.setPassword(encPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}
	
	@Transactional
	public void 회원수정(User user) {
		User persistance = userRepository.findById(user.getId())
							.orElseThrow(()->{
								return new IllegalArgumentException("회원 찾기 실패: 해당 회원을 찾을 수 없습니다.");
							});//영속화 완료
				
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());
	}
	
	
}//UserService