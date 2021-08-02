package com.project.dropbox.controller;

import com.project.dropbox.dto.FileResponseDto;
import com.project.dropbox.service.FileHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping(value = "/v1")
public class FileHandlerController {

    @Autowired
    FileHandlerService fileHandlerService;

    @PostMapping(path = "/upload", produces = "application/json")
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("merchantId") String merchantId,
                                     @RequestParam("docType") String docType) throws Exception {
        fileHandlerService.uploadFile(file, merchantId, docType);
        return  ResponseEntity.ok().body("File uploaded successfully");
    }

    @GetMapping(path = "/download", produces = "application/json")
    public ResponseEntity downloadFile(@RequestParam("merchantId") String merchantId, @RequestParam("fileId") Long fileId,
                                       HttpServletRequest request) throws Exception {
        Resource resource = fileHandlerService.downLoadFile(merchantId, fileId);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping(path = "/all", produces = "application/json")
    public ResponseEntity getFiles(@RequestParam("merchantId") String merchantId) throws Exception {
        List<FileResponseDto> fileResponseDtos = fileHandlerService.getFiles(merchantId);
        return  ResponseEntity.ok().body(fileResponseDtos);
    }
}
