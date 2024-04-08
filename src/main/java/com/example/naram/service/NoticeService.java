package com.example.naram.service;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.vo.Criteria;
import com.example.naram.domain.vo.NoticeDetailVo;
import com.example.naram.domain.vo.SearchVo;
import com.example.naram.mapper.NoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeMapper noticeMapper;

//    공지사항 작성
    public void createNotice(NoticeDto noticeDto){
        noticeMapper.createNotice(noticeDto);
        log.info("공지사항 작성 서비스 결과 : {}", noticeDto);
    }
    
//    공지사항 조회
    public List<NoticeDetailVo> viewNotice(Criteria criteria, SearchVo searchVo){
        log.info("===============게시물 조회 서비스 실행 !!");
        List<NoticeDetailVo> noticeList = noticeMapper.viewNotice(criteria, searchVo);
        if (noticeList == null) {
            throw new IllegalArgumentException("게시물 조회 서비스 실패!!");
        }
        return noticeList;
    }

//    공지사항 글 갯수 조회
    public int getTotalCount(SearchVo searchVo){
        return noticeMapper.getTotalNotice(searchVo);
    }

//    공지사항　수정
    public NoticeDetailVo updateNotice(NoticeDto noticeDto){
        noticeMapper.updateNotice(noticeDto);
        return noticeMapper.viewDetailNotice(noticeDto.getNoticeNumber());
    }

}
