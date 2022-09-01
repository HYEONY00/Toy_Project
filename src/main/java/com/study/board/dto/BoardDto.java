package com.study.board.dto;

import com.study.board.entity.Board;
import lombok.*;
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
//    private String createdDate;


    public Board toEntity(){
        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .filepath(filepath)
//                .createdDate(createdDate)
                .build();
        return board;
    }
    @Builder
    public BoardDto(Integer id, String title, String content, String filename, String filepath, String createdDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
//        this.createdDate = createdDate;
    }
    @Builder
    public BoardDto(Board board){
        this.id = board.getId();
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
//        this.createdDate = createdDate;
    }
}
