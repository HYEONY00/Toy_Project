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
    private LocalDateTime date;


    public Board toEntity(){
        Board board = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .filename(filename)
                .filepath(filepath)
                .build();
        return board;
    }
    @Builder
    public BoardDto(Integer id, String title, String content, String filename, String filepath, LocalDateTime date){
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
        this.date = date;
    }
    @Builder
    public BoardDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.filename = board.getFilename();
        this.filepath = board.getFilepath();
        this.date = board.getDate();
    }
}
