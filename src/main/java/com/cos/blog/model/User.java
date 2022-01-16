package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//ORM - Java(다른언어도 가능) Object -> 테이블로 매핑해주는 기술
//@DynamicInsert //insert 할 때, 값이 null인 컬럼은 제외 후 입력 
@Entity //User 클래스가 MySQL에 테이블이 생성된다.
public class User {
	
	@Id //primary key
	@GeneratedValue(strategy=GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다
	private int id; //시퀸스, auto_increment
	
	@Column(nullable=false, length = 100, unique = true)
	private String username; //아이디
	
	@Column(nullable=false, length = 100)//123456=> 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable=false, length = 50)
	private String email;
	
	//@ColumnDefault("user")
	//private String role; //ADMIN, USER 권한 설정 //(Enum을 쓰는게 좋다. 도메인 설정, 오타방지)
	
	@Enumerated(EnumType.STRING) //DB에게 Enum type을 알려줘야 한다.
	private RoleType role; //Enum 사용 => ADMIN, USER 값만 들어올 수 있다
	
	private String oauth; //kakao, google
	
	@CreationTimestamp //시간이 자동으로 입력
	private Timestamp createDate;

}
//***** yml 파일에서 설정 확인할것. ******//
//jpa? Java ORM 기술에 대한 API 표준 명세
//jpa:
//open-in-view: true
//hibernate:
//  ddl-auto: create <- 테이블 만들때만 create, 만들고 난 후는 update로 바꿔주기
//naming:
//    physical-strategy: org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
// (PhysicalNamingStrategyStandardImpl => 변수명 그대로 테이블 컬럼 생성
//	SpringPhysicalNamingStrategy => 변수명이 카멜 표기법일시, _를 넣어서 컬럼명 생성됨
//	예:userName - user_name)
//  use-new-id-generator-mappings: false  
//=> false: jpa의 기본 전략을 따라가지 않겠다.
//show-sql: true  
//=> console에 sql 보여준다
//properties:
//  hibernate.format_sql: true
//=> sql 정렬 가능, 예쁘게 나온당


// ddl-auto: => 테이블 생성 옵션
//  create : 실행할때마다 테이블 생성
//  update : 테이블에 값 업데이트
//  create-drop : Create and then destroy the schema at the end of the session.
//  validate : Validate the schema, make no changes to the database.
//  none : ddl-auto off
