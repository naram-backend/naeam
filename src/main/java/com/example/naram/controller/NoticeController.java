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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/notice/*")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    //  공지사항 등록 POST 매핑
    @PostMapping("/noticeRegist")
    public ResponseEntity<?> serviceReviewReply(@RequestBody NoticeDto noticeDto){
        log.info("===============공지사항 등록 컨트롤러 실행 !! {}", noticeDto);
        noticeService.createNotice(noticeDto);
        return ResponseEntity.ok("");
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
