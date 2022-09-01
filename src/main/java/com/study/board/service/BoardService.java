package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
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
    @Transactional
    public void write(Board board, MultipartFile file) throws Exception{ // 글 작성 처리
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files"; // 저장할 경로
        UUID uuid = UUID.randomUUID(); // random으로 이름을 생성
        String fileName = uuid + "_" + file.getOriginalFilename();
        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        board.setFilename(fileName); // 저장된 파일 이름
        board.setFilepath("/files/" + fileName); // 저장된 파일의 경로와 이름
        boardRepository.save(board);
    }

    public Page<Board> boardList(Pageable pageable){ // 게시글 리스트 처리
        return boardRepository.findAll(pageable);
    }

    //특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
        // board 생성자
        // board dto 생성자 넘겨서
        // return dto
    }

    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){
        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }
}