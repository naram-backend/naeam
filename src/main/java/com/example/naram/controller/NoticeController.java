package com.example.naram.controller;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.vo.Criteria;
import com.example.naram.domain.vo.NoticeDetailVo;
import com.example.naram.domain.vo.PageVo;
import com.example.naram.domain.vo.SearchVo;
import com.example.naram.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    //  게시물 등록 POST 매핑
    @PostMapping("")
    public ResponseEntity<?> serviceReviewReply(@RequestBody NoticeDto noticeDto){
        log.info("===============게시물 등록 컨트롤러 실행 !! {}", noticeDto);
        noticeService.createNotice(noticeDto);
        return ResponseEntity.ok("");

    }

    //  게시물 조회 컨트롤러
    @GetMapping("/notice/{page}")
    public Map<String, Object> viewNotice(@PathVariable("page")int page, SearchVo searchVo){
        log.info("===============게시물 조회 컨트롤러 실행 !!");
        Criteria criteria = new Criteria();
        criteria.setPage(page);
        criteria.setAmount(15);
        PageVo pageVo = new PageVo(noticeService.getTotalCount(searchVo), criteria);
        List<NoticeDetailVo> noticeList = noticeService.viewNotice(criteria, searchVo);

        Map<String, Object> noticeMap = new HashMap<>();
        noticeMap.put("pageVo", pageVo);
        noticeMap.put("adminUserList", noticeList);
        log.info("===============게시물 조회 컨트롤러 실행 !! {}", noticeList);

        return noticeMap;
    }




}
