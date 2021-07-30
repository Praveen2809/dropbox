package com.project.dropbox.repository;

import com.project.dropbox.model.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileDetails, Long> {
    FileDetails findByFileIdAndMerchantId(Long fileId, String merchantId);
    List<FileDetails> findByCheckSum(String checkSum);
    List<FileDetails> findByMerchantId(String merchantId);
}
