package com.example.scm_system.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryImage upload(MultipartFile multipartFile) throws IOException;
}
