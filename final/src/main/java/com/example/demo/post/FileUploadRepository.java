package com.example.demo.post;

import com.example.demo.post.domain.FileUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
    FileUpload save(FileUpload fileUpload);//?
}
