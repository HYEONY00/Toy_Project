package com.study.board.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity // table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class Board{ // Board table에 관한 정보
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String filename;
    private String filepath;

//    private String createdDate;

    @Builder
    public Board(Integer id, String title, String content, String filename, String filepath, String createdDate){
        this.id = id;
        this.title = title;
        this.content = content;
        this.filename = filename;
        this.filepath = filepath;
//        this.createdDate = createdDate;
    }
}