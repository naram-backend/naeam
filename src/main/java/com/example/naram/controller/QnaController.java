package com.example.naram.controller;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.dto.NoticeFileDto;
import com.example.naram.domain.dto.QnaDto;
import com.example.naram.domain.dto.QnaFileDto;
import com.example.naram.domain.vo.*;
import com.example.naram.service.NoticeService;
import com.example.naram.service.QnaService;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;

@Controller
@Slf4j
@RequestMapping("/qna/*")
@RequiredArgsConstructor
public class QnaController {
    private final QnaService qnaService;

    @Autowired
    private Storage storage;

    @Value("${gcp.storage.bucket}")
    private String bucketName;

    //  공지사항 등록 POST 매핑
    @PostMapping("/upload")
    public ResponseEntity<?> uploadQna(@RequestBody QnaUploadVo qn){
        QnaFileDto qnaFileDto = new QnaFileDto();
//      파일 디코딩
        try {
            String fileData = qn.getFileData();
            byte[] decodedBytes = Base64.getDecoder().decode(fileData.substring(fileData.indexOf(",") + 1));
            //      파일 클라우드 스토리지에 저장
            String origin = qn.getFileName();
            String fileName = UUID.randomUUID().toString() + "-" + origin;
            String blobName = "qna/file/" + fileName;
            BlobId blobId = BlobId.of(bucketName, blobName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            Blob blob = storage.create(blobInfo, decodedBytes);
            String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;

            qnaFileDto.setFileUrl(fileUrl);
            qnaFileDto.setFileName(origin);
            log.info("noticeFileDto == {}",qnaFileDto);
        } catch (NullPointerException e) {
            qnaFileDto = null;
        }

        QnaDto qnaDto = new QnaDto();
        qnaDto.setUserNumber(qn.getUserNumber());
        qnaDto.setQnaTitle(qn.getQnaTitle());
        qnaDto.setQnaContent(qn.getQnaContent());
        log.info("qnaDto == {}",qnaDto);

        try{
            qnaService.qnaUpload(qnaDto,qnaFileDto);
            return ResponseEntity.ok("{\"message\": \"문의사항 등록 성공!!\"}");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"문의사항 등록 실패\"}");
        }

    }

    //  문의사항 리스트 조회 컨트롤러
    @GetMapping("/list")
    public ResponseEntity<?> viewQna(SearchVo searchVo){
        log.info("===============문의사항 리스트 조회 컨트롤러 실행 !!");
        try {
            List<QnaDetailVo> qnaList = qnaService.listQna(searchVo);
            log.info("===============문의사항 상세정보 컨트롤러 결과 !! {}", qnaList);
            return ResponseEntity.ok(qnaList);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"문의사항 불러오기 실패\"}");
        }
    }

    // 문의사항 상세정보
    @GetMapping("/detail")
    public ResponseEntity<?> selectQnaDetail(@RequestParam(name = "qnaNumber")Long qnaNumber){
        log.info("===============문의사항 상세정보 컨트롤러 실행 !! {}", qnaNumber);
        try {
            qnaService.updateCount(qnaNumber);
            QnaDetailVo qnaDetailVo = qnaService.viewDetailQna(qnaNumber);
            if (qnaDetailVo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"null 값\"}");
            }
            log.info("===============문의사항 상세정보 컨트롤러 결과 !! {}", qnaDetailVo);
            return ResponseEntity.ok(qnaDetailVo);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"문의사항 불러오기 실패\"}");
        }
    }

    // 문의사항 수정 컨트롤러
    @PostMapping("/qnaConfig")
    public ResponseEntity<?> qnaUpdate(@RequestBody QnaUploadVo qn){
        QnaFileDto qnaFileDto = new QnaFileDto();
//      파일 디코딩
        try {
            String fileData = qn.getFileData();
            byte[] decodedBytes = Base64.getDecoder().decode(fileData.substring(fileData.indexOf(",") + 1));
            //      파일 클라우드 스토리지에 저장
            String origin = qn.getFileName();
            String fileName = UUID.randomUUID().toString() + "-" + origin;
            String blobName = "qna/file/" + fileName;
            BlobId blobId = BlobId.of(bucketName, blobName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            Blob blob = storage.create(blobInfo, decodedBytes);
            String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;

            qnaFileDto.setFileUrl(fileUrl);
            qnaFileDto.setFileName(origin);
            qnaFileDto.setQnaNumber(qnaFileDto.getQnaNumber());
            log.info("try 문에 있는 qnaFileDto == {}",qnaFileDto);
        } catch (NullPointerException e) {
            qnaFileDto = null;
        }

        QnaDto qnaDto = new QnaDto();
        qnaDto.setUserNumber(qn.getUserNumber());
        qnaDto.setQnaNumber(qn.getQnaNumber());
        qnaDto.setQnaTitle(qn.getQnaTitle());
        qnaDto.setQnaContent(qn.getQnaContent());
        log.info("try 문에 없는 qnaFileDto == {}",qnaFileDto);
        log.info("qnaDto == {}",qnaDto);

        try{
            QnaDetailVo qnaDetailVo = qnaService.updateQna(qnaDto,qnaFileDto);
            return ResponseEntity.ok(qnaDetailVo);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"문의사항 수정 실패\"}");
        }
    }
    
    //공지사항 수정
    @GetMapping("/noticeConfig")
    public ResponseEntity<?> noticeUpdateView(@RequestParam(name = "qnaNumber")Long qnaNumber) {
        log.info("===============문의사항 수정 확인 컨트롤러 실행 !! {}", qnaNumber);
        try {
            QnaDetailVo qnaDetailVo = qnaService.viewDetailQna(qnaNumber);
            if (qnaDetailVo == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"null 값\"}");
            }
            log.info("===============공지사항 수정 확인 컨트롤러 결과 !! {}", qnaDetailVo);
            return ResponseEntity.ok(qnaDetailVo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"공지사항 수정 불러오기 실패\"}");
        }
    }

    // 문의사항 삭제
    @GetMapping("/delete")
    public ResponseEntity<?> qnaDelete(@RequestParam Long qnaNumber){
        try {
            QnaDetailVo qnaDetailVo = qnaService.viewDetailQna(qnaNumber);
            if (qnaNumber == null) {
                throw new IllegalArgumentException("삭제할 문의사항이 존재하지 않습니다.");
            }
            qnaService.deleteQna(qnaNumber);
            log.info("===============문의사항 삭제 완료 !!");
            return ResponseEntity.ok("{\"message\": \"문의사항 삭제 완료\"}");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"문의사항 삭제 실패\"}");
        }
    }

}
