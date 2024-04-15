package com.example.naram.service;

import com.example.naram.domain.dto.PromotionDto;
import com.example.naram.domain.dto.PromotionFileDto;
import com.example.naram.domain.vo.PrUploadVo;
import com.example.naram.mapper.PromotionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PromotionService {
    private final PromotionMapper promotionMapper;

//    게시글 등록
    @Transactional
    public void prUpload (PromotionDto promotionDto, PromotionFileDto promotionFileDto) throws Exception{
        log.info("게시글 등록 서비스");
        try {
            log.info("promotionDto = {}",promotionDto);
            log.info("promotionFileDto = {}", promotionFileDto);
            log.info("========================");
            log.info("prUpload");
            promotionMapper.prUpload(promotionDto);
            log.info("prFileUpload");
            promotionMapper.prFileUpload(promotionFileDto);
            log.info("========================");
        } catch (Exception e){
            log.error("게시글 등록중 오류 발생 : {}",e.getMessage());
            e.printStackTrace(); // 예외 스택 트레이스 출력
            throw new Exception("게시글 등록 실패");
        }
    }
}
