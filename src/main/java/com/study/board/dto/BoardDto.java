package com.study.board.dto;

import com.study.board.entity.Board;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;
    private LocalDateTime date;
    private String writer;
    private Integer view_cnt;


    public Board toEntity(){
        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .filepath(filepath)
                .writer(writer)
                .view_cnt(0)
                .build();
        return board;
    }
    @Builder
    public BoardDto(Integer id, String title, String content, String filename, String filepath, String writer, LocalDateTime date, Integer view_cnt){
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.writer = writer;
        this.date = date;
        this.view_cnt = view_cnt;
    }
    @Builder
    public BoardDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.filename = board.getFilename();
        this.filepath = board.getFilepath();
        this.writer = board.getWriter();
        this.date = board.getDate();
        this.view_cnt = board.getView_cnt();
    }
}