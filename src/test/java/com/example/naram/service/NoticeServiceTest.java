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
        noticeDto.setNoticeTitle("test123");
        noticeDto.setNoticeContent("testcontent123");
        noticeDto.setNoticeView(0);
        noticeDto.setHiring(false);
        noticeDto.setImportant(false);
        noticeDto.setNoticeView(0);
        noticeDto.setUserNumber(2L);
        noticeService.createNotice(noticeDto);
    }

    @Test
    @DisplayName("게시글 조회")
//    @Disabled
    void viewNotice() {
        Criteria criteria = new Criteria();
        SearchVo searchVo = new SearchVo();
        criteria.setPage(0);
        criteria.setAmount(15);
        searchVo.setKeyword("테");
        searchVo.setCate("noticeUser");
        List<NoticeDetailVo> list = noticeService.viewNotice(criteria, searchVo);

        for (NoticeDetailVo noticeDetailVo : list ) {
            log.info("가져온 공지사항 정보 : {}", noticeDetailVo);
        }
    }



}