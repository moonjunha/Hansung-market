package com.hansungmarket.demo.entity.board;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "board_image")
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "stored_file_name")
    private String storedFileName;

    @Column(name = "stored_file_path")
    private String storedFilePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private Board board;

    @Builder
    private BoardImage(String originalFileName, String storedFileName, String storedFilePath, Board board) {
        this.originalFileName = originalFileName;
        this.storedFileName = storedFileName;
        this.storedFilePath = storedFilePath;
        this.board = board;
    }

//    public void setBoard(Board board) {
//        this.board = board;
//    }
}
