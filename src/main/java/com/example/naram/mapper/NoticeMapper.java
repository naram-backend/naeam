package com.example.naram.mapper;

import com.example.naram.domain.dto.NoticeDto;
import com.example.naram.domain.dto.NoticeFileDto;
import com.example.naram.domain.vo.NoticeDetailVo;
import com.example.naram.domain.vo.SearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 공지사항 Mapper
 * 홍재헌
 */
@Mapper
public interface NoticeMapper {
    //공지사항 새글 작성
    public void createNotice(NoticeDto noticeDto);
    
    //공지사항 파일 업로드
    public void uploadFileNotice(NoticeFileDto noticeFileDto);

    //공지사항 전체 조회
    public List<NoticeDetailVo> viewNotice(@Param("searchVo") SearchVo searchVo);

    //전체 공지사항 수
    public int getTotalNotice(@Param("searchVo") SearchVo searchVo);

    //공지사항 상세정보
    public NoticeDetailVo viewDetailNotice(Long noticeNumber);

    //공지사항 조회수 증가
    public void noticeCount(Long noticeNumber);

    //공지사항 수정
    public void updateNotice(NoticeDto noticeDto);

    //공지사항 파일 수정
    public void updateFileNotice(NoticeFileDto noticeFileDto);
    
    //공지사항 삭제
    public void deleteNotice(Long noticeNumber);

}
