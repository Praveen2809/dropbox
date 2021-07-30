package com.project.dropbox.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.nio.file.Path;

@Entity
@Table(name = "file_details")
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@Data
public class FileDetails{
    @Id
    @SequenceGenerator(name = "sequenceGenerator",sequenceName = "HIBERNATE_SEQUENCE",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "sequenceGenerator")
    Long fileId;

    private String filePath;

    private String fileName;

    private String merchantId;

    private String documentType;

    private Long fileSize;

    private String checkSum;
}
