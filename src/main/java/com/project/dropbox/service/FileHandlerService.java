package com.project.dropbox.service;

import com.project.dropbox.dto.FileDownloadDto;
import com.project.dropbox.dto.FileResponseDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public interface FileHandlerService {
    void uploadFile(MultipartFile file, String merchantId, String docType) throws IOException;
    Resource downLoadFile(String merchantId, Long fileId) throws MalformedURLException;
    List<FileResponseDto> getFiles(String merchantId);
}
