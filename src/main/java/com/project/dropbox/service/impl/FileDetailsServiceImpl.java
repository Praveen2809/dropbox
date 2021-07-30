package com.project.dropbox.service.impl;

import com.project.dropbox.model.FileDetails;
import com.project.dropbox.repository.FileRepository;
import com.project.dropbox.service.FileDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileDetailsServiceImpl implements FileDetailsService {
    private FileRepository fileRepository;

    @Autowired
    public FileDetailsServiceImpl(FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }

    @Override
    public FileDetails findByFileIdAndMerchantId(Long fileId, String merchantId) {
        return fileRepository.findByFileIdAndMerchantId(fileId, merchantId);
    }

    @Override
    public List<FileDetails> findByCheckSum(String checkSum) {
        return fileRepository.findByCheckSum(checkSum);
    }

    @Override
    public List<FileDetails> findByMerchantId(String merchantId){
        return fileRepository.findByMerchantId(merchantId);
    }

    public void addFileDetails(String filePath, String fileName, String merchantId, String documentType, long size, String checksum){
        FileDetails fileDetails = new FileDetails();
        fileDetails.setFilePath(filePath);
        fileDetails.setFileName(fileName);
        fileDetails.setMerchantId(merchantId);
        fileDetails.setDocumentType(documentType);
        fileDetails.setFileSize(size);
        fileDetails.setCheckSum(checksum);
        fileRepository.save(fileDetails);
    }
}
