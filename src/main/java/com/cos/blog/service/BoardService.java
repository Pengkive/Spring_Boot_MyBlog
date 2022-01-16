package com.cos.blog.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReplyRepository replyRepositoy;
	
	@Transactional
	public void 글쓰기(Board board, User user) { //title, content
		System.out.println("Board 정보 : title, content = " + board);
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly=true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	@Transactional(readOnly=true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		//수정하려면 영속화를 먼저 해야한다.
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 수정하기 실패: 아이디를 찾을 수 없습니다.");
				});//영속화 완료
		
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		
		//해당 함수가 종료되면(Service 종료) 트랙젝션이 종료되고 더티체킹(자동업데이트,DB flush)이 일어난다.
		
	}
	
	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) { //title, content
		
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패: 게시글 아이디를 찾을 수 없습니다.");
				});//영속화 완료
		User user = userRepository.findById(replySaveRequestDto.getUserId())
				.orElseThrow(()->{
					return new IllegalArgumentException("댓글 쓰기 실패: 해당 아이디를 찾을 수 없습니다.");
				});//영속화 완료
		
		Reply reply = Reply.builder()
					.user(user)
					.board(board)
					.content(replySaveRequestDto.getContent())
					.build();
		
		replyRepositoy.save(reply);
	}
	
	@Transactional
	public void 댓글삭제(int replyId) {
		replyRepositoy.deleteById(replyId);
	}
	
	
}