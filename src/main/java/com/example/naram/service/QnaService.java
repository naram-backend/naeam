package com.example.naram.service;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.dto.NoticeFileDto;
import com.example.naram.domain.dto.QnaDto;
import com.example.naram.domain.dto.QnaFileDto;
import com.example.naram.domain.vo.Criteria;
import com.example.naram.domain.vo.NoticeDetailVo;
import com.example.naram.domain.vo.QnaDetailVo;
import com.example.naram.domain.vo.SearchVo;
import com.example.naram.mapper.NoticeMapper;
import com.example.naram.mapper.QnaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaMapper qnaMapper;

//    문의사항 작성
@Transactional
public void qnaUpload (QnaDto qnaDto, QnaFileDto qnaFileDto) throws Exception{
    log.info("문의사항 등록 서비스");
    try {
        log.info("qnaDto = {}",qnaDto);
        log.info("qnaFileDto = {}", qnaFileDto);
        log.info("========================");
        log.info("qnaUpload");
        qnaMapper.createQna(qnaDto);
        log.info("qnaFileUpload");
        qnaMapper.uploadFileQna(qnaFileDto);
        log.info("========================");
    } catch (Exception e){
        log.error("문의사항 등록 중 오류 발생 : {}",e.getMessage());
        e.printStackTrace(); // 예외 스택 트레이스 출력
        throw new Exception("문의사항 등록 실패");
    }
}
    
//    문의사항 조회
    public List<QnaDetailVo> listQna(Criteria criteria, SearchVo searchVo){
        log.info("===============문의사항 조회 서비스 실행 !!");
        List<QnaDetailVo> qnaList = qnaMapper.viewQna(criteria, searchVo);
        if (qnaList == null) {
            throw new IllegalArgumentException("문의사항 조회 서비스 실패!!");
        }
        return qnaList;
    }

//    문의사항 상세정보
    public QnaDetailVo viewDetailQna(Long qnaNumber){
        return qnaMapper.viewDetailQna(qnaNumber);
    }

//    문의사항 조회수
    public void updateCount(Long qnaNumber){
        if (qnaNumber == null) {
            throw new IllegalArgumentException("문의사항 번호 누락!");
        }
        qnaMapper.qnaCount(qnaNumber);
    }

//    문의사항 글 갯수 조회
    public int getTotalCount(SearchVo searchVo){
        log.info("===============문의사항 글 갯수 조회 서비스 실행 !!");
        if (searchVo == null) {
            throw new IllegalArgumentException("문의사항 글 갯수 조회 서비스 실패!!");
        }
        return qnaMapper.getTotalQna(searchVo);
    }

//    문의사항　수정
    @Transactional
    public QnaDetailVo updateQna(QnaDto qnaDto){
        log.info("===============문의사항 수정 서비스 실행 !!");
        if (qnaDto == null) {
            throw new IllegalArgumentException("문의사항 수정 서비스 실패!!");
        }
        qnaMapper.updateQna(qnaDto);
        return qnaMapper.viewDetailQna(qnaDto.getQnaNumber());
    }

//    문의사항 삭제
    public void deleteQna(Long qnaNumber){
        log.info("===============문의사항 삭제 서비스 실행 !!");
        if (qnaNumber == null) {
            throw new IllegalArgumentException("문의사항 삭제 서비스 실패!!");
        }
        qnaMapper.deleteQna(qnaNumber);
    }

}
