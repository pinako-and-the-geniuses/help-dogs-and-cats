package com.ssafy.a302.global.util;

import com.ssafy.a302.global.constant.ErrorMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    public String storeFile(MultipartFile file, String path) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename().toLowerCase();
        String storeFilename = createStoreFilename(originalFilename);
        file.transferTo(new File(getFullPath(storeFilename, path)));

        return storeFilename;
    }

    public void removeFile(String storeFilename, String path) throws IOException {
        try {
            File file = new File(path + File.separator + storeFilename);
            if (file.exists()) {
                if (!file.delete()) {
                    throw new IOException();
                }
            } else {
                throw new IllegalArgumentException(ErrorMessage.BAD_REQUEST);
            }
        } catch (IOException e) {
            throw new IOException(ErrorMessage.UNAVAILABLE);
        }
    }

    private String createStoreFilename(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    public String getFullPath(String filename, String path) {
        return path + File.separator + filename;
    }
}
