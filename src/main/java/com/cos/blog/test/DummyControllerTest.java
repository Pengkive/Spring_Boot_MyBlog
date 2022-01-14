package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


//html 파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입(DI)
	private UserRepository userRepository;	

	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
		  userRepository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {
			//모든 exception의 부모에 해당하는 Exception을 사용해도 되지만
			//없는 데이터에 대한 exception 체크를 위해 정확하게 EmptyResultDataAccessException를 사용함.
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다. id: "+id;
		}
	
		return "삭제되었습니다. id: "+id;
	}
	
	//email, password 변경
	//json 데이터로 입력하려면 @RequestBody 어노테이션 필수!

	//save 함수는 id를 전달하지 않으면 insert를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
	@Transactional //함수 종료시에 자동 commit 이 됨.
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		System.out.println("id: "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("email: "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
//		// 수정해야하는 항목만 update되고 기존 항목은 유지된다.
//		userRepository.save(user);
		
		//더티 체킹
		return user;
	}

	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	//한페이지당 2건의 데이터를 리턴
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id",direction = Sort.Direction.DESC)Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable); //모든 정보 리턴
		
		List<User> users = pagingUser.getContent(); //content 내용만 페이징 후 리턴
		
		return users;
	}
	
	//{id} 주소로 파라미터 전달 받을 수 있음
	//http://localhost:8000/blog/dummy/user/5
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
	//Optional<T> findById(ID id);
	//타입이 Optional인 이유:
	//user/5 를 찾으면 내가 DB에서 못찾아오게 되면 user 가 null이 될 것 아냐?
	//그럼 return null 이 리턴이 되자나.. 그럼 프로그램에 문제가 있지 않겠니?
	//Optional로 너의 User 객체를 감싸서 가져 올테니 null인지 아닌지 판단해서 return 해!

		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				
				return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
			}

		});
		
//참고) 위의 식을 람다식으로 표현
//		User user = userRepository.findById(id).orElseThrow(()-> {
//				return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
//		});
		
		//요청 - > 웹 브라우저
		//user 객체  = 자바 오브젝트
		//변환(웹브라우저가 이해할 수 있는 데이터 ) 하려면 -> json(Gson라이브러리) 있어야함
		//but, 스프링 부트 = MessageConverter라는 애가 응답시에 자동 작동
		//만약에 자바 오브젝트를 리턴하게 되면 MessageConverter 가 Jackson 라이브러리를 호출해서
		//user 오브젝트를 json으로 변환하여 브라우저에게 던져준다.
		return user;
	}
		
	
	
	
	//http://localhost:8000/blog/dummy/join (요청)
	//http의 body에 username, password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) {//Object로 받을때
//	public String join(String username, String password, String email) {//파라미터로 값을 받을때, key=value(약속된 규칙)
		
		System.out.println("id: "+user.getId());
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		System.out.println("role: "+user.getRole());
		System.out.println("createDate: "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입 완료!";
	}
	
}