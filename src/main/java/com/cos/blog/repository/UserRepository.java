package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

//DAO
//자동으로 bean 등록이 된다.
//@Repository //생략 가능하다.
//JpaRepository 를 extends 하는것만으로도 모든 CRUD 가능.
public interface UserRepository extends JpaRepository<User, Integer>{
	//SELECT * FROM user WHERE username = ?;
	Optional<User> findByUsername(String username);
	
}

// 참고
//JpaRepository<T, ID> 
//Type Parameters :
//<T> the domain type the repository manages
//<ID> the type of the id of the entity the repository manages
//JpaRepository 안에 List<T> findAll(); 있다.
// => User 테이블의 모든행을 리턴하라는 의미.
// => 모든 행은 CRUD, FindByID.. 가능 


//Jpa  Naming 쿼리 전략
//select * from user where username = ? and password = ?;

//User findByUsernameAndPassword(String username, String password);

//native query, Jpa Naming 쿼리와 동일한 작용함
//@Query(value="select * from user where username = ? and password = ?;", nativeQuery = true)
//User login(String username, String password);
