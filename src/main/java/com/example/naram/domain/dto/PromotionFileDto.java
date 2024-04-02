package com.example.naram.domain.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class PromotionFileDto {
    private Long promotionFileNumber;
    private Long promotionNumber;
    private String promotionUrl;
    private String promotionDate;
}
