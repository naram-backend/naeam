package com.example.naram.controller;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.dto.NoticeFileDto;
import com.example.naram.domain.dto.PromotionDto;
import com.example.naram.domain.dto.PromotionFileDto;
import com.example.naram.domain.vo.*;
import com.example.naram.service.NoticeService;
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
@RequestMapping("/notice/*")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @Autowired
    private Storage storage;

    @Value("${gcp.storage.bucket}")
    private String bucketName;

    //  공지사항 등록 POST 매핑
    @PostMapping("/upload")
    public ResponseEntity<?> uploadNotice(@RequestBody NtUploadVo nt){
        NoticeFileDto noticeFileDto = new NoticeFileDto();
//      파일 디코딩
        try {
            String fileData = nt.getFileData();
            byte[] decodedBytes = Base64.getDecoder().decode(fileData.substring(fileData.indexOf(",") + 1));
            //      파일 클라우드 스토리지에 저장
            String origin = nt.getFileName();
            String fileName = UUID.randomUUID().toString() + "-" + origin;
            String blobName = "notice/file/" + fileName;
            BlobId blobId = BlobId.of(bucketName, blobName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            Blob blob = storage.create(blobInfo, decodedBytes);
            String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;

            noticeFileDto.setFileUrl(fileUrl);
            noticeFileDto.setFileName(origin);
            log.info("noticeFileDto == {}",noticeFileDto);
        } catch (NullPointerException e) {
            noticeFileDto = null;
        }

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setUserNumber(nt.getUserNumber());
        noticeDto.setNoticeTitle(nt.getNoticeTitle());
        noticeDto.setNoticeContent(nt.getNoticeContent());
        log.info("noticeDto == {}",noticeDto);

        try{
            noticeService.noticeUpload(noticeDto,noticeFileDto);
            return ResponseEntity.ok("{\"message\": \"공지사항 등록 성공!!\"}");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"공지사항 등록 실패\"}");
        }

    }

    //  공지사항 리스트 조회 컨트롤러
    @GetMapping("/list")
    public ResponseEntity<?> viewNotice(SearchVo searchVo){
        log.info("===============공지사항 리스트 조회 컨트롤러 실행 !!");
        try {
            List<NoticeDetailVo> noticeList = noticeService.listNotice(searchVo);
            log.info("===============공지사항 상세정보 컨트롤러 결과 !! {}", noticeList);
            return ResponseEntity.ok(noticeList);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"공지사항 불러오기 실패\"}");
        }

    }

    // 공지사항 상세정보
    @GetMapping("/detail")
    public ResponseEntity<?> selectNoticeDetail(@RequestParam(name = "noticeNumber")Long noticeNumber){
        log.info("===============공지사항 상세정보 컨트롤러 실행 !! {}", noticeNumber);
        try {
            noticeService.updateCount(noticeNumber);
            NoticeDetailVo noticeDetail = noticeService.viewDetailNotice(noticeNumber);
            if (noticeDetail == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"null 값\"}");
            }
            log.info("===============공지사항 상세정보 컨트롤러 결과 !! {}", noticeDetail);
            return ResponseEntity.ok(noticeDetail);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"공지사항 불러오기 실패\"}");
        }
    }

    // 공지사항 수정 컨트롤러
    @PostMapping("/noticeConfig")
    public ResponseEntity<?> noticeUpdate(@RequestBody NtUploadVo nt){
        NoticeFileDto noticeFileDto = new NoticeFileDto();
//      파일 디코딩
        try {
            String fileData = nt.getFileData();
            byte[] decodedBytes = Base64.getDecoder().decode(fileData.substring(fileData.indexOf(",") + 1));
            //      파일 클라우드 스토리지에 저장
            String origin = nt.getFileName();
            String fileName = UUID.randomUUID().toString() + "-" + origin;
            String blobName = "notice/file/" + fileName;
            BlobId blobId = BlobId.of(bucketName, blobName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            Blob blob = storage.create(blobInfo, decodedBytes);
            String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;

            noticeFileDto.setFileUrl(fileUrl);
            noticeFileDto.setFileName(origin);
            noticeFileDto.setNoticeNumber(nt.getNoticeNumber());
            log.info("try 문에 있는 noticeFileDto == {}",noticeFileDto);
        } catch (NullPointerException e) {
            noticeFileDto = null;
        }

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setUserNumber(nt.getUserNumber());
        noticeDto.setNoticeNumber(nt.getNoticeNumber());
        noticeDto.setNoticeTitle(nt.getNoticeTitle());
        noticeDto.setNoticeContent(nt.getNoticeContent());
        log.info("try 문에 없는 noticeFileDto == {}",noticeFileDto);
        log.info("noticeDto == {}",noticeDto);

        try{
            NoticeDetailVo noticeDetailVo = noticeService.noticeUpdate(noticeDto,noticeFileDto);
            return ResponseEntity.ok(noticeDetailVo);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"공지사항 수정 실패\"}");
        }
    }

    //공지사항 수정
    @GetMapping("/noticeConfig")
    public ResponseEntity<?> noticeUpdateView(@RequestParam(name = "noticeNumber")Long noticeNumber){
        log.info("===============공지사항 수정 확인 컨트롤러 실행 !! {}", noticeNumber);
        try {
            NoticeDetailVo noticeDetail = noticeService.viewDetailNotice(noticeNumber);
            if (noticeDetail == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"null 값\"}");
            }
            log.info("===============공지사항 수정 확인 컨트롤러 결과 !! {}", noticeDetail);
            return ResponseEntity.ok(noticeDetail);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"공지사항 수정 불러오기 실패\"}");
        }
    }

    // 공지사항 삭제
    @GetMapping("/delete")
    public ResponseEntity<?> noticeDelete(@RequestParam Long noticeNumber){
        try {
            NoticeDetailVo noticeDetailVo = noticeService.viewDetailNotice(noticeNumber);
            if (noticeDetailVo == null) {
                throw new IllegalArgumentException("삭제할 공지사항이 존재하지 않습니다.");
            }
            noticeService.deleteNotice(noticeNumber);
            log.info("===============공지사항 삭제 완료 !!");
            return ResponseEntity.ok("{\"message\": \"공지사항 삭제 완료\"}");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"공지사항 삭제 실패\"}");
        }
    }

}
