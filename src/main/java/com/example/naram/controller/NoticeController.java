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
//      파일 디코딩
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

        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setUserNumber(nt.getUserNumber());
        noticeDto.setNoticeTitle(nt.getNoticeTitle());
        noticeDto.setNoticeContent(nt.getNoticeContent());
        log.info("noticeDto == {}",noticeDto);

        NoticeFileDto noticeFileDto = new NoticeFileDto();
        noticeFileDto.setFileUrl(fileUrl);
        noticeFileDto.setFileName(origin);
        log.info("noticeFileDto == {}",noticeFileDto);

        try{
            noticeService.noticeUpload(noticeDto,noticeFileDto);
            return ResponseEntity.ok("{\"message\": \"공지사항 등록 성공!!\"}");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"공지사항 등록 실패\"}");
        }

    }

    //  공지사항 리스트 조회 컨트롤러
    @GetMapping("/list/{page}")
    public Map<String, Object> viewNotice(@PathVariable("page")int page, SearchVo searchVo){
        log.info("===============공지사항 리스트 조회 컨트롤러 실행 !!");
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(15);
        PageVo pageVo = new PageVo(noticeService.getTotalCount(searchVo), criteria);
        List<NoticeDetailVo> noticeList = noticeService.listNotice(criteria, searchVo);

        Map<String, Object> noticeMap = new HashMap<>();
        noticeMap.put("pageVo", pageVo);
        noticeMap.put("noticeList", noticeList);
        log.info("===============공지사항 리스트 조회 컨트롤러 결과 !! {}", noticeList);

        return noticeMap;
    }

    // 공지사항 상세정보
    @GetMapping(value={"/noticeDetail","/noticeConfig"})
    public void selectNoticeDetail(@RequestParam(name = "noticeNumber")Long noticeNumber, Model model){
        log.info("===============공지사항 상세정보 컨트롤러 실행 !! {}", noticeNumber);
        noticeService.updateCount(noticeNumber);
        NoticeDetailVo noticeDetail = noticeService.viewDetailNotice(noticeNumber);
        log.info("===============공지사항 상세정보 컨트롤러 결과 !! {}", noticeDetail);
        model.addAttribute("noticeDetail", noticeDetail);
    }

    // 공지사항 수정 컨트롤러
    @PostMapping("/noticeConfig")
    public RedirectView noticeUpdate(@RequestBody NoticeDto noticeDto){
        noticeService.updateNotice(noticeDto);
        return new RedirectView("/list");
    }

    // 공지사항 삭제
    @GetMapping("/delete")
    public RedirectView noticeDelete(@RequestParam Long noticeNumber){
        noticeService.deleteNotice(noticeNumber);
        return new RedirectView("/list");
    }

}
