package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.data.jpa.domain.AbstractAuditable_.createdDate;

@Controller
public class BoardController {
    @Autowired
    private BoardService boardService;
    @GetMapping("/board/write") // localhost:8080/board/write
    public String boardWriteForm(){
        return "boardwrite"; // 어떤 html 파일로 돌려줄건가
    }

    @PostMapping("/board/writepro")
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{

        boardService.write(board, file);
        model.addAttribute("message", "글 작성이 완료되었습니다.");
//        model.addAttribute("message", "글 작성을 실패하였습니다.");
//        model.addAttribute("searchUrl", "/board/list");
        model.addAttribute("searchUrl", "/");
        return "message";
    }

    @GetMapping("/")
    public String boardList(Model model,
                            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, String searchKeyword){
        Page<Board> list = null;
        if(searchKeyword == null){
            list = boardService.boardList(pageable);
        }else{
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        if (list.getTotalElements() == 0){ // 검색결과 없으면 /board/list
            model.addAttribute("message", "검색 결과가 없습니다.");
//            model.addAttribute("searchUrl", "/board/list");
            model.addAttribute("searchUrl", "/");
            return "message";
        }
        int nowPage = list.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, list.getTotalPages());
        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }
    @GetMapping("/board/view") // localhost:8080/board/view?id = 1
    public String boardView(Model model, Integer id){
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);
//        return "redirect:/board/list";
        return "redirect:/";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){
        model.addAttribute("board", boardService.boardView(id));
        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, MultipartFile file) throws Exception{
        Board boardTemp = boardService.boardView(id); // 기준에 있는 객체 불러온
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent()); // 새로 작성한 내용을 적용
        boardService.write(boardTemp, file);
//        return "redirect:/board/list";
        return "redirect:/";
    }
}