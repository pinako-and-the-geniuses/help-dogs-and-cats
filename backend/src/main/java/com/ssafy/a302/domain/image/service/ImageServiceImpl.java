package com.ssafy.a302.domain.image.service;

import com.ssafy.a302.global.constant.ErrorMessage;
import com.ssafy.a302.global.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

    private final FileUtil fileUtil;

    @Value("${path.saved.files.images.board}")
    private String boardImageSavePath;

    @Value("${path.access.files.images.board}")
    private String boardImageAccessPath;

    @Override
    public String saveImageFile(MultipartFile imageFile) throws IOException {
        String storeFilename = null;
        try {
            storeFilename = fileUtil.storeFile(imageFile, boardImageSavePath);
            if (storeFilename == null) {
                throw new IOException();
            }
        } catch (IOException e) {
            throw new IOException(ErrorMessage.UNAVAILABLE);
        }

        return boardImageAccessPath + "/" + storeFilename;
    }

    @Override
    public void removeImageFile(String imageFilename) throws IOException {
        fileUtil.removeFile(imageFilename, boardImageSavePath);
    }
}
