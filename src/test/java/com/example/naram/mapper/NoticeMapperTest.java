package com.example.naram.mapper;

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
class NoticeMapperTest {
    @Autowired
    NoticeMapper noticeMapper;

    @Test
    @DisplayName("공지사항 등록")
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
        noticeMapper.createNotice(noticeDto);
    }

    @Test
    @DisplayName("전체 공지사항 정보")
//    @Disabled
    void viewNotice() {
        Criteria criteria = new Criteria();
        SearchVo searchVo = new SearchVo();

        criteria.setAmount(15);
        criteria.setPage(0);
        
        searchVo.setCate("noticeUser");
        searchVo.setKeyword("관");

        List<NoticeDetailVo> list = noticeMapper.viewNotice(criteria, searchVo);
        for (NoticeDetailVo noticeDetailVo : list) {
            log.info("가져온 공지사항 정보 : {}", noticeDetailVo);
        }

    }

    @Test
    @DisplayName("전체 공지사항 갯수")
//    @Disabled
    void viewNoticeCount() {
        SearchVo searchVo = new SearchVo();
        searchVo.setCate("noticeTitle");
        searchVo.setKeyword("123");
        int getSearchCount = noticeMapper.getTotalNotice(searchVo);
        log.info("가져온 공지사항 갯수 : {}", getSearchCount);
    }

    @Test
    @DisplayName("공지사항 세부정보")
//    @Disabled
    void viewDetailNotice() {
        NoticeDetailVo noticeDetailVo = noticeMapper.viewDetailNotice(10L);
        log.info("공지사항 세부정보 : {}", noticeDetailVo);
    }

    @Test
    @DisplayName("공지사항 수정")
//    @Disabled
    void updateNotice() {
        NoticeDto noticeUpdateVo = new NoticeDto();
        noticeUpdateVo.setNoticeNumber(8L);
        noticeUpdateVo.setNoticeTitle("수정됐나123");
        noticeUpdateVo.setNoticeContent("내용도123");
        noticeMapper.updateNotice(noticeUpdateVo);
    }

    @Test
    @DisplayName("공지사항 삭제")
//    @Disabled
    void deleteNotice() {
        noticeMapper.deleteNotice(7L);
    }


}