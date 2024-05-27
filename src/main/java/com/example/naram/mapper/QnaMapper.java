package com.example.naram.mapper;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.dto.NoticeFileDto;
import com.example.naram.domain.dto.QnaDto;
import com.example.naram.domain.dto.QnaFileDto;
import com.example.naram.domain.vo.Criteria;
import com.example.naram.domain.vo.NoticeDetailVo;
import com.example.naram.domain.vo.QnaDetailVo;
import com.example.naram.domain.vo.SearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 문의사항 Mapper
 * 홍재헌
 */
@Mapper
public interface QnaMapper {
    //문의사항 새글 작성
    public void createQna(QnaDto qnaDto);

    //문의사항 전체 조회
    public List<QnaDetailVo> viewQna(@Param("criteria") Criteria criteria, @Param("searchVo") SearchVo searchVo);

    //전체 문의사항 수
    public int getTotalQna(@Param("searchVo") SearchVo searchVo);

    //문의사항 조회수 증가
    public void qnaCount(Long qnaNumber);

    //문의사항 파일 업로드
    public void uploadFileQna(QnaFileDto qnaFileDto);

    //문의사항 상세정보
    public QnaDetailVo viewDetailQna(Long qnaNumber);

    //문의사항 수정
    public void updateQna(QnaDto qnaDto);

    //문의사항 삭제
    public void deleteQna(Long qnaNumber);
}
