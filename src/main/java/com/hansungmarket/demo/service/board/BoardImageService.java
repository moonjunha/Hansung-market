package com.hansungmarket.demo.service.board;

import com.hansungmarket.demo.dto.board.BoardImageDto;
import com.hansungmarket.demo.entity.board.Board;
import com.hansungmarket.demo.entity.board.BoardImage;
import com.hansungmarket.demo.repository.board.BoardImageRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BoardImageService {
    private final BoardImageRepository boardImageRepository;

    // 이미지 저장될 경로
    @Value("${image-path}")
    private String imagePath;

    // id로 파일 경로 가져오기
    @Transactional(readOnly = true)
    public String getImagePath(Long id) {
        BoardImage image = boardImageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
        return image.getStoredFilePath() + File.separator + image.getStoredFileName();
    }

    // 이미지 저장(파일, DB)
    @Transactional
    public void create(Board board, MultipartFile image) throws IOException {
        // 원래 이름
        String imageName = image.getOriginalFilename();
        UUID uuid = UUID.randomUUID();
        // 저장될 이름
        String storedImageName = uuid + "_" + imageName;

        File saveFile = new File(imagePath, storedImageName);
        // 파일 저장
        image.transferTo(saveFile);

        BoardImage boardImage = BoardImage.builder()
                .originalFileName(imageName)
                .storedFileName(storedImageName)
                .storedFilePath(imagePath)
                .board(board)
                .build();

        boardImageRepository.save(boardImage);
    }
    
    // 실제 이미지 파일 삭제
    public void deleteFile(BoardImage image) {
        File deleteFile = new File(image.getStoredFilePath(), image.getStoredFileName());
        deleteFile.delete();
    }

    @Transactional(readOnly = true)
    public BoardImageDto searchBoardImage(Long id) {
        BoardImage boardImage = boardImageRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("이미지를 찾을 수 없습니다."));
        return new BoardImageDto(boardImage);
    }

    // 해당 경로의 이미지 byte 가져오기
    @Transactional(readOnly = true)
    public byte[] getByteImage(String imagePath) throws IOException {
        InputStream imageStream = new FileInputStream(imagePath);

        // byte 로 이미지 인코딩
        byte[] imageByteArray = IOUtils.toByteArray(imageStream);
        imageStream.close();

        return imageByteArray;
    }

    // 해당 경로의 이미지 리소스 가져오기
    @Transactional(readOnly = true)
    public Resource getImageResource(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);

        return new InputStreamResource(Files.newInputStream(path));
    }
}
