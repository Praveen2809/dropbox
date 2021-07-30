package com.project.dropbox.service.impl;

import com.project.dropbox.dto.FileResponseDto;
import com.project.dropbox.model.FileDetails;
import com.project.dropbox.service.CommonFileService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommonFileServiceImpl implements CommonFileService {

    public String getChecksumString(MultipartFile file) throws IOException {
        return DigestUtils.sha256Hex(new ByteArrayInputStream(file.getBytes()));
    }

    public List<FileResponseDto> createFileResposne(List<FileDetails> fileDetailsList){
        List<FileResponseDto> fileResponseDtoList = new ArrayList<>();
        for(FileDetails fileDetails: fileDetailsList){
            FileResponseDto fileResponseDto = new FileResponseDto(fileDetails.getFileId(), fileDetails.getFileName(),
                    fileDetails.getMerchantId(), fileDetails.getDocumentType(), fileDetails.getFileSize());
            fileResponseDtoList.add(fileResponseDto);
        }
        return fileResponseDtoList;
    }
}
