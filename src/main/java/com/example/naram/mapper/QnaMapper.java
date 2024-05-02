package com.example.naram.mapper;

import com.example.naram.domain.dto.QnaDto;
import com.example.naram.domain.dto.QnaFileDto;
import org.apache.ibatis.annotations.Mapper;

/**
 * 문의글 Mapper
 * 24.04.30
 * 홍재헌
 */
@Mapper
public interface QnaMapper {
    //문의글 새글 작성
    public void createQna(QnaDto qnaDto);

    //문의글 파일 업로드
    public void uploadFileQna(QnaFileDto qnaFileDto);



}
