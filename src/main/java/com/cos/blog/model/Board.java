package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity //테이블 생성하기 위한 어노테이션! 빼먹지 말자!!
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터 사용시
	private String content; //섬머노트 라이브러리 사용 - <html>태그가 섞여서 디자인이 됨. 데이터량 증가
	
	private int count; //조회수
	
	//FetchType.EAGER : board 정보를 클릭하면 user 정보를 무조건 가져온다.
	@ManyToOne(fetch = FetchType.EAGER) //Many - Board , One - User //종속 관계 표현
	@JoinColumn(name = "userId")
	private User user; 
	
	//DB는 Object를 저장할 수 없다. 
	//FK, 자바는 오브젝트를 저장할 수 있으나 DB로 전환하면서 충돌남.
	//FetchType.LAZY : 필요시에만 정보를 가져온다. (지연로딩)
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //mappedBy 연관관계의 주인이 아니다. (난 FK가 아니에요) DB에 컬럼을 만들지 마세요
	@JsonIgnoreProperties({"board"}) //무한 참조 방지; <Reply>가 실행될때 board 호출을 제외한다.
	@OrderBy("id desc")
	private List<Reply> replys;
	
	@CreationTimestamp //데이터가 insert, create 될때 자동으로 현재시간 들어감.
	private Timestamp createDate;
	
}