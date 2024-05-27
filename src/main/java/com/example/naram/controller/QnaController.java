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
    public ResponseEntity<?> uploadQna(@RequestBody QnaUploadVo qnaUploadVo){
//      파일 디코딩
        String fileData = qnaUploadVo.getFileData();
        byte[] decodedBytes = Base64.getDecoder().decode(fileData.substring(fileData.indexOf(",") + 1));

//      파일 클라우드 스토리지에 저장
        String origin = qnaUploadVo.getFileName();
        String fileName = UUID.randomUUID().toString() + "-" + origin;
        String blobName = "qna/file/" + fileName;
        BlobId blobId = BlobId.of(bucketName, blobName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        Blob blob = storage.create(blobInfo, decodedBytes);
        String fileUrl = "https://storage.googleapis.com/" + bucketName + "/" + blobName;

        QnaDto qnaDto = new QnaDto();
        qnaDto.setUserNumber(qnaUploadVo.getUserNumber());
        qnaDto.setQnaTitle(qnaUploadVo.getQnaTitle());
        qnaDto.setQnaContent(qnaUploadVo.getQnaContent());
        log.info("qnaDto == {}",qnaDto);

        QnaFileDto qnaFileDto = new QnaFileDto();
        qnaFileDto.setFileUrl(fileUrl);
        qnaFileDto.setFileName(origin);
        log.info("qnaFileDto == {}",qnaFileDto);

        try{
            qnaService.qnaUpload(qnaDto, qnaFileDto);
            return ResponseEntity.ok("{\"message\": \"문의사항 등록 성공!!\"}");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"문의사항 등록 실패\"}");
        }

    }

    //  문의사항 리스트 조회 컨트롤러
    @GetMapping("/list/{page}")
    public Map<String, Object> viewQna(@PathVariable("page")int page, SearchVo searchVo){
        log.info("===============문의사항 리스트 조회 컨트롤러 실행 !!");
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(15);
        PageVo pageVo = new PageVo(qnaService.getTotalCount(searchVo), criteria);
        List<QnaDetailVo> qnaList = qnaService.listQna(criteria, searchVo);

        Map<String, Object> qnaMap = new HashMap<>();
        qnaMap.put("pageVo", pageVo);
        qnaMap.put("qnaList", qnaList);
        log.info("===============문의사항 리스트 조회 컨트롤러 결과 !! {}", qnaList);

        return qnaMap;
    }

    // 문의사항 상세정보
    @GetMapping(value={"/qnaDetail","/qnaConfig"})
    public void selectQnaDetail(@RequestParam(name = "qnaNumber")Long qnaNumber, Model model){
        log.info("===============문의사항 상세정보 컨트롤러 실행 !! {}", qnaNumber);
        qnaService.updateCount(qnaNumber);
        QnaDetailVo qnaDetailVo = qnaService.viewDetailQna(qnaNumber);
        log.info("===============문의사항 상세정보 컨트롤러 결과 !! {}", qnaDetailVo);
        model.addAttribute("qnaDetail", qnaDetailVo);
    }

    // 문의사항 수정 컨트롤러
    @PostMapping("/qnaConfig")
    public RedirectView qnaUpdate(@RequestBody QnaDto qnaDto){
        qnaService.updateQna(qnaDto);
        return new RedirectView("/list");
    }

    // 문의사항 삭제
    @GetMapping("/delete")
    public RedirectView qnaDelete(@RequestParam Long qnaNumber){
        qnaService.deleteQna(qnaNumber);
        return new RedirectView("/list");
    }

}
