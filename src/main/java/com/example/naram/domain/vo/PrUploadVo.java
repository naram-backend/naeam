package com.example.naram.domain.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
public class PrUploadVo {
    private Long userNumber;
    private String promotionTitle;
    private String promotionContent;
    private String fileName;
    private String fileData;
}
