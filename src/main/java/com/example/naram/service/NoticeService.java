package com.example.naram.service;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.dto.NoticeFileDto;
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
public void noticeUpload (NoticeDto noticeDto, NoticeFileDto noticeFileDto) throws Exception{
    log.info("공지사항 등록 서비스");
    try {
        log.info("noticeDto = {}",noticeDto);
        log.info("noticeFileDto = {}", noticeFileDto);
        log.info("========================");
        log.info("ntUpload");
        noticeMapper.createNotice(noticeDto);
        log.info("ntFileUpload");
        noticeMapper.uploadFileNotice(noticeFileDto);
        log.info("========================");
    } catch (Exception e){
        log.error("공지사항 등록 중 오류 발생 : {}",e.getMessage());
        e.printStackTrace(); // 예외 스택 트레이스 출력
        throw new Exception("공지사항 등록 실패");
    }
}
    
//    공지사항 조회
    public List<NoticeDetailVo> listNotice(SearchVo searchVo) throws Exception{
        log.info("===============게시물 조회 서비스 실행 !!");
        try{
            List<NoticeDetailVo> noticeList = noticeMapper.viewNotice(searchVo);
            return noticeList;
        }catch (Exception e){
            log.error("게시글 불러오기 중 오류 발생 : {}",e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new Exception("게시물 조회 서비스 실패");
        }
    }

//    공지사항 상세정보
    public NoticeDetailVo viewDetailNotice(Long noticeNumber) throws Exception{
        try{
            NoticeDetailVo noticeDetailVo = noticeMapper.viewDetailNotice(noticeNumber);
            return noticeDetailVo;
        }catch (Exception e){
            log.error("게시글 불러오기 중 오류 발생 : {}",e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new Exception("게시글 불러오기 실패");
        }
    }

//    공지사항 조회수
    public void updateCount(Long noticeNumber) throws Exception{
        try{
            noticeMapper.noticeCount(noticeNumber);
        }catch (Exception e){
            log.error("공지사항 조회수 불러오기 중 오류 발생 : {}",e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new Exception("공지사항 번호 누락!");
        }
    }

//    공지사항 글 갯수 조회
    public int getTotalCount(SearchVo searchVo) throws Exception{
        log.info("===============공지사항 글 갯수 조회 서비스 실행 !!");
        try{
            return noticeMapper.getTotalNotice(searchVo);
        }catch (Exception e){
            log.error("공지사항 글 갯수 조회 중 오류 발생 : {}",e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new Exception("공지사항 글 갯수 조회 서비스 실패!!");
        }
    }

//    공지사항　수정
    @Transactional
    public NoticeDetailVo noticeUpdate(NoticeDto noticeDto, NoticeFileDto noticeFileDto) throws Exception{
        log.info("===============공지사항 수정 서비스 실행 !!");
        try {
            log.info("noticeDto = {}",noticeDto);
            log.info("noticeFileDto = {}", noticeFileDto);
            log.info("========================");
            log.info("ntUpload");
            noticeMapper.updateNotice(noticeDto);
            log.info("ntFileUpload");
            noticeMapper.updateFileNotice(noticeFileDto);
            log.info("========================");
            return noticeMapper.viewDetailNotice(noticeDto.getNoticeNumber());
        } catch (Exception e){
            log.error("공지사항 등록 중 오류 발생 : {}",e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new Exception("공지사항 수정 서비스 실패");
        }
    }

//    공지사항 삭제
    public void deleteNotice(Long noticeNumber) throws Exception{
        log.info("===============공지사항 삭제 서비스 실행 !!");
        try{
            noticeMapper.deleteNotice(noticeNumber);
        }catch (Exception e){
            log.error("공지사항 삭제 중 오류 발생 : {}",e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new Exception("공지사항 삭제 실패");
        }
    }

}
