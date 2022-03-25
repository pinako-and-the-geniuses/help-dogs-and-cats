package com.ssafy.a302.domain.image.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    String saveImageFile(MultipartFile imageFile) throws IOException;
}
