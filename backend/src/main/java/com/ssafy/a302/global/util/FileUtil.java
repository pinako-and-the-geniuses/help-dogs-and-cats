package com.ssafy.a302.global.util;

import com.ssafy.a302.global.message.ErrorMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${path.images}")
    private String filePath;

    public String storeFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        file.transferTo(new File(getFullPath(storeFilename)));

        return storeFilename;
    }

    public void removeProfileImage(String storeFilename) throws IOException {
        try {
            File file = new File(filePath + "profile" + File.separator + storeFilename);
            if (!file.delete()) {
                throw new IOException();
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

    public String getFullPath(String filename) {
        return filePath + "profile" + File.separator + filename;
    }
}
