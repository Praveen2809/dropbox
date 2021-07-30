package com.project.dropbox.service;

import com.project.dropbox.model.FileDetails;

import java.util.List;

public interface FileDetailsService {
    FileDetails findByFileIdAndMerchantId(Long fileId, String merchantId);
    List<FileDetails> findByCheckSum(String checkSum);
    void addFileDetails(String filePath, String fileName, String merchantId, String documentType, long size, String checksum);
    List<FileDetails> findByMerchantId(String merchantId);
}
