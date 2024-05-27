package com.example.naram.service;

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
}