package com.project.dropbox.service.impl;

import com.project.dropbox.config.AppConfig;
import com.project.dropbox.dto.FileDownloadDto;
import com.project.dropbox.dto.FileResponseDto;
import com.project.dropbox.exception.FileNotPresentException;
import com.project.dropbox.exception.FileStorageException;
import com.project.dropbox.exception.InvalidFileException;
import com.project.dropbox.model.FileDetails;
import com.project.dropbox.service.CommonFileService;
import com.project.dropbox.service.FileDetailsService;
import com.project.dropbox.service.FileHandlerService;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class FileHandlerServiceImpl implements FileHandlerService {

    private AppConfig appConfig;
    private CommonFileService commonFileService;
    private FileDetailsService fileDetailsService;
    private final Path fileStorageLocation;

    @Autowired
    public FileHandlerServiceImpl(AppConfig appConfig, CommonFileService commonFileService, FileDetailsService fileDetailsService){
        this.appConfig = appConfig;
        this.commonFileService =  commonFileService;
        this.fileDetailsService = fileDetailsService;
        this.fileStorageLocation = Paths.get(appConfig.getUploadDirectory())
                .toAbsolutePath().normalize();
    }

    @Override
    @Transactional
    public void uploadFile(MultipartFile file, String merchantId, String docType)  {
        validateUploadFile(file);
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = this.fileStorageLocation.resolve(originalFileName);
        // check if already any file exists
        try {
            String checksum = commonFileService.getChecksumString(file);
            List<FileDetails> fileDetailsList = fileDetailsService.findByCheckSum(checksum);
            if (fileDetailsList == null || fileDetailsList.size() == 0) {
                Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            } else {
                targetLocation = Paths.get(fileDetailsList.get(0).getFilePath());
            }
            fileDetailsService.addFileDetails(targetLocation.toString(), originalFileName, merchantId, docType, file.getSize(), checksum);
        }catch (Exception e){
            throw new FileStorageException("Something went wrong");
        }
    }

    @Override
    public Resource downLoadFile(String merchantId, Long fileId) {
        validateDownloadFile(fileId, merchantId);
        FileDetails fileDetails = fileDetailsService.findByFileIdAndMerchantId(fileId, merchantId);
        if(fileDetails == null){
            throw new FileNotPresentException("File is not present");
        }
        Resource resource;
        Path filePath = Paths.get(fileDetails.getFilePath());
        try {
            resource = new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            throw new FileStorageException("Something went wrong");
        }
        return resource;
    }

    @Override
    public List<FileResponseDto> getFiles(String merchantId) {
        List<FileDetails> fileDetails = fileDetailsService.findByMerchantId(merchantId);
        return commonFileService.createFileResposne(fileDetails);
    }

    public void validateDownloadFile(Long fileId, String merchantId){
        if(fileId == null){
            throw new InvalidFileException("file name is missing");
        }
        if(merchantId == null || merchantId.isEmpty()){
            throw new InvalidFileException("merchantId is missing");
        }
    }

    public void validateUploadFile(MultipartFile file){
        if(file ==  null || file.isEmpty() || file.getSize() == 0L){
            throw new InvalidFileException("File is not valid");
        }
        if(file.getSize() > appConfig.getMaxFileSize()){
            throw new InvalidFileException("File size is more than expected");
        }
    }
}
