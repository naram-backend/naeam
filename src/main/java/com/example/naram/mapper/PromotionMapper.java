package com.example.naram.mapper;

import com.example.naram.domain.dto.PromotionDto;
import com.example.naram.domain.dto.PromotionFileDto;
import com.example.naram.domain.vo.PrUploadVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PromotionMapper {
//    게시글 등록
    public void prUpload(PromotionDto promotionDto);
//    게시글 파일
    public void prFileUpload(PromotionFileDto promotionFileDto);
}
