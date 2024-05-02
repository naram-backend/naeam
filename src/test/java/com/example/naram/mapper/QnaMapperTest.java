package com.example.naram.mapper;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.dto.QnaDto;
import com.example.naram.domain.vo.Criteria;
import com.example.naram.domain.vo.NoticeDetailVo;
import com.example.naram.domain.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
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
    @DisplayName("문의글 등록")
//    @Disabled
    void createQna() {
        QnaDto qnaDto = new QnaDto();
        qnaDto.setUserNumber(2L);
        qnaDto.setQnaTitle("문의글 제목 테스트");
        qnaDto.setQnaContent("문의글 내용 테스트");
        qnaDto.setQnaPrivate(true);
        qnaDto.setQnaAnswer(false);
        qnaMapper.createQna(qnaDto);
    }

}