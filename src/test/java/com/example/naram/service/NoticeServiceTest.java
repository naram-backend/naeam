package com.example.naram.service;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.vo.Criteria;
import com.example.naram.domain.vo.NoticeDetailVo;
import com.example.naram.domain.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class NoticeServiceTest {
    @Autowired
    NoticeService noticeService;

    @Test
    @DisplayName("게시글 등록")
//    @Disabled
    void createNotice() {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNoticeTitle("test");
        noticeDto.setNoticeContent("test");
        noticeDto.setNoticeView(0);
        noticeDto.setHiring(false);
        noticeDto.setImportant(false);
        noticeDto.setNoticeView(0);
        noticeDto.setUserNumber(2L);
        noticeService.updateNotice(noticeDto);
    }

    @Test
    @DisplayName("게시글 조회")
//    @Disabled
    void listNotice() {
        SearchVo searchVo = new SearchVo();
        searchVo.setKeyword("");
        searchVo.setCate("noticeUser");
        List<NoticeDetailVo> list = null;
        try {
            list = noticeService.listNotice(searchVo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (NoticeDetailVo noticeDetailVo : list ) {
            log.info("가져온 공지사항 정보 : {}", noticeDetailVo);
        }
    }

    @Test
    @DisplayName("게시글 수정")
//    @Disabled
    void updateNotice() {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNoticeNumber(8L);
        noticeDto.setNoticeTitle("수정 서비스 테스트");
        noticeDto.setNoticeContent("수정 서비스 테스트 내용입니다");
        NoticeDetailVo updateNotice = noticeService.updateNotice(noticeDto);
        log.info("수정된 정보 : {}", updateNotice);
    }



}