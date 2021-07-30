package com.project.dropbox.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AppConfig {
    @Value("${file.max.size}")
    private Long maxFileSize;

    @Value("${file.upload.directory}")
    private String uploadDirectory;

}
