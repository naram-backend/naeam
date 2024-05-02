package com.example.naram.controller;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@RestController
@Slf4j
public class ImgController {
    @Autowired
    private Storage storage;

    @Value("${gcp.storage.bucket}")
    private String bucketName;

    @PostMapping("/prImg")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("확인");
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String blobName = "promotion/img/" + fileName;
        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, file.getBytes());
        String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;
        log.info("imageUrl = {}",imageUrl);
        return ResponseEntity.ok(imageUrl);
    }

    @PostMapping("/ntImg")
    public ResponseEntity<String> uploadNoticeImage(@RequestParam("file") MultipartFile file) throws IOException {
        log.info("확인");
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        String blobName = "notice/img/" + fileName;
        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, file.getBytes());
        String imageUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;
        log.info("imageUrl = {}",imageUrl);
        return ResponseEntity.ok(imageUrl);
    }
}
