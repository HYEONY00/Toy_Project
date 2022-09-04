package com.study.board.service;

import com.study.board.dto.BoardDto;
import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired // 알아서 읽어옴
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository){
        this.boardRepository = boardRepository;
    }
    @Transactional
    public void write(BoardDto boardDto, MultipartFile file) throws Exception{ // 글 작성 처리
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files"; // 저장할 경로
        UUID uuid = UUID.randomUUID(); // random으로 이름을 생성
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        boardDto.setFilename(fileName); // 저장된 파일 이름
        boardDto.setFilepath("/files/" + saveFile); // 저장된 파일의 경로와 이름
        this.boardRepository.save(boardDto.toEntity()).getId();
    }

    public Page<BoardDto> boardList(Pageable pageable){ // 게시글 리스트 처리
        return this.boardRepository.findAll(pageable).map(BoardDto::new);
    }

    public Page<BoardDto> boardSearchList(String searchKeyword, Pageable pageable){
        return this.boardRepository.findByTitleContaining(searchKeyword, pageable).map(BoardDto::new);
    }
    //특정 게시글 불러오기
    public BoardDto boardView(Integer id){
        Board board = this.boardRepository.findById(id).get();
        BoardDto boardDto = new BoardDto(board);
        return boardDto;
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id){
        this.boardRepository.deleteById(id);
    }

    @Transactional
    public int updateCount(Integer id) {
        return this.boardRepository.updateCount(id);
    }
}