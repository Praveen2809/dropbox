package com.project.dropbox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponseDto {
    Long fileId;
    String fileName;
    String merchantId;
    String docType;
    Long fileSize;
}
