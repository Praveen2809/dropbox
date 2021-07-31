package com.project.dropbox.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.core.io.Resource;

@Data
@AllArgsConstructor
public class FileDownloadDto {
    Resource resource;
    String secretKey;
}
