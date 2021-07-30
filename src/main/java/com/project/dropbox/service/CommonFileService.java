package com.project.dropbox.service;


import com.project.dropbox.dto.FileResponseDto;
import com.project.dropbox.model.FileDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CommonFileService {
    String getChecksumString(MultipartFile file) throws IOException;
    List<FileResponseDto> createFileResposne(List<FileDetails> fileDetailsList);
}
