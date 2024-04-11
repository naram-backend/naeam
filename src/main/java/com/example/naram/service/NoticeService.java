package com.example.naram.service;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.vo.Criteria;
import com.example.naram.domain.vo.NoticeDetailVo;
import com.example.naram.domain.vo.SearchVo;
import com.example.naram.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;

//    공지사항 작성
    @Transactional
    public void createNotice(NoticeDto noticeDto){
        log.info("===============공지사항 작성 서비스 실행 !!");
        if (noticeDto == null) {
            throw new IllegalArgumentException("공지사항 작성 서비스 실패!!");
        }
        noticeMapper.createNotice(noticeDto);
        NoticeDetailVo noticeDetailVo = noticeMapper.viewDetailNotice(noticeDto.getNoticeNumber());
        log.info("공지사항 작성 서비스 결과 : {}", noticeDetailVo);
    }
    
//    공지사항 조회
    public List<NoticeDetailVo> listNotice(Criteria criteria, SearchVo searchVo){
        log.info("===============게시물 조회 서비스 실행 !!");
        List<NoticeDetailVo> noticeList = noticeMapper.viewNotice(criteria, searchVo);
        if (noticeList == null) {
            throw new IllegalArgumentException("게시물 조회 서비스 실패!!");
        }
        return noticeList;
    }

//    공지사항 상세정보
    public NoticeDetailVo viewDetailNotice(Long noticeNumber){
        return noticeMapper.viewDetailNotice(noticeNumber);
    }

//    공지사항 조회수
    public void updateCount(Long noticeNumber){
        if (noticeNumber == null) {
            throw new IllegalArgumentException("공지사항 번호 누락!");
        }
        noticeMapper.noticeCount(noticeNumber);
    }

//    공지사항 글 갯수 조회
    public int getTotalCount(SearchVo searchVo){
        log.info("===============공지사항 글 갯수 조회 서비스 실행 !!");
        if (searchVo == null) {
            throw new IllegalArgumentException("공지사항 글 갯수 조회 서비스 실패!!");
        }
        return noticeMapper.getTotalNotice(searchVo);
    }

//    공지사항　수정
    @Transactional
    public NoticeDetailVo updateNotice(NoticeDto noticeDto){
        log.info("===============공지사항 수정 서비스 실행 !!");
        if (noticeDto == null) {
            throw new IllegalArgumentException("공지사항 수정 서비스 실패!!");
        }
        noticeMapper.updateNotice(noticeDto);
        return noticeMapper.viewDetailNotice(noticeDto.getNoticeNumber());
    }

//    공지사항 삭제
    public void deleteNotice(Long noticeNumber){
        log.info("===============공지사항 삭제 서비스 실행 !!");
        if (noticeNumber == null) {
            throw new IllegalArgumentException("공지사항 삭제 서비스 실패!!");
        }
        noticeMapper.deleteNotice(noticeNumber);
    }

}
