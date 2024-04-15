package com.example.naram.controller;

import com.example.naram.domain.dto.PromotionDto;
import com.example.naram.domain.dto.PromotionFileDto;
import com.example.naram.domain.vo.PrUploadVo;
import com.example.naram.service.PromotionService;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pr/*")
@Slf4j
public class PromotionController {
    private final PromotionService promotionService;

    @Autowired
    private Storage storage;

    @Value("${gcp.storage.bucket}")
    private String bucketName;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadPost(@RequestBody PrUploadVo pr) {
//      파일 디코딩
        String fileData = pr.getFileData();
        byte[] decodedBytes = Base64.getDecoder().decode(fileData.substring(fileData.indexOf(",") + 1));

//      파일 클라우드 스토리지에 저장
        String origin = pr.getFileName();
        String fileName = UUID.randomUUID().toString() + "-" + origin;
        String blobName = "file/" + fileName;
        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, decodedBytes);
        String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;

        PromotionDto promotionDto = new PromotionDto();
        promotionDto.setUserNumber(pr.getUserNumber());
        promotionDto.setPromotionTitle(pr.getPromotionTitle());
        promotionDto.setPromotionContent(pr.getPromotionContent());
        log.info("promotionDto == {}",promotionDto);

        PromotionFileDto promotionFileDto = new PromotionFileDto();
        promotionFileDto.setFileUrl(fileUrl);
        promotionFileDto.setFileName(origin);
        log.info("promotionFileDto == {}",promotionFileDto);

        try{
            promotionService.prUpload(promotionDto,promotionFileDto);
            return ResponseEntity.ok("{\"message\": \"게시글 등록 성공!!\"}");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"게시글 등록 실패\"}");
        }

    }
}
