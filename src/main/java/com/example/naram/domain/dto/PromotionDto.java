package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class PromotionDto {
    private Long promotionNumber;
    private Long userNumber;
    private String promotionTitle;
    private String promotionContent;
    private int promotionView;
    private String promotionRegisterDate;
}
