package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class PrDetailVo {
    private String promotionTitle;
    private String promotionContent;
    private int promotionView;
    private String promotionRegisterDate;
    private String fileName;
    private String fileUrl;
    private String userName;
}
