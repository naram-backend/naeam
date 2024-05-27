package com.example.naram.mapper;

import com.example.naram.domain.dto.QnaDto;
import com.example.naram.domain.vo.Criteria;
import com.example.naram.domain.vo.QnaDetailVo;
import com.example.naram.domain.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class QnaMapperTest {
    @Autowired
    QnaMapper qnaMapper;

    @Test
    @DisplayName("문의사항 등록")
//    @Disabled
    void createQna() {
        QnaDto qnaDto = new QnaDto();
        qnaDto.setQnaTitle("test123");
        qnaDto.setQnaContent("testcontent123");
        qnaDto.setQnaPrivate(false);
        qnaDto.setQnaView(0);
        qnaDto.setQnaAnswer(false);
        qnaDto.setUserNumber(5L);
        qnaMapper.createQna(qnaDto);
    }
    
    @Test
    @DisplayName("전체 문의사항 정보")
    void viewQna() {
        SearchVo searchVo = new SearchVo();

        searchVo.setCate("qnaContent");
        searchVo.setKeyword("te");

        List<QnaDetailVo> list = qnaMapper.viewQna(searchVo);
        for (QnaDetailVo qnaDetailVo : list) {
            log.info("가져온 공지사항 정보 : {}", qnaDetailVo);
        }

    }

    @Test
    @DisplayName("전체 문의사항 갯수")
    void viewQnaCount() {
        SearchVo searchVo = new SearchVo();
        searchVo.setCate("qnaTitle");
        searchVo.setKeyword("");
        int getSearchCount = qnaMapper.getTotalQna(searchVo);
        log.info("가져온 공지사항 갯수 : {}", getSearchCount);
    }

    @Test
    @DisplayName("문의사항 세부정보")
    void viewDetailQna() {
        QnaDetailVo qnaDetailVo = qnaMapper.viewDetailQna(3L);
        log.info("문의사항 세부정보 : {}", qnaDetailVo);
    }

    @Test
    @DisplayName("문의사항 수정")
    void updateQna() {
        QnaDto qnaDto = new QnaDto();
        qnaDto.setQnaNumber(3L);
        qnaDto.setQnaTitle("수정 테스트");
        qnaDto.setQnaContent("내용 수정");
        qnaMapper.updateQna(qnaDto);
    }

    @Test
    @DisplayName("문의사항 삭제")
    void deleteQna() {
        qnaMapper.deleteQna(5L);
    }

}