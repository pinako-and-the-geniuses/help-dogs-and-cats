package com.ssafy.a302.domain.image.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    @Value("${path.saved.files.images.board}")
    private String imageFileSavePath;

    @Value("${path.access.files.images.board}")
    private String imageFileAccessPath;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("에디터용 이미지 저장 테스트 - 성공")
    void saveImageFileSuccess() throws IOException {
        /**
         * 테스트 데이터 세팅
         */
        String googleImageUrl = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_light_color_272x92dp.png";
        URL url = new URL(googleImageUrl);

        File baseProfileImageFile = new File("test.png");
        ImageIO.write(ImageIO.read(url), "png", baseProfileImageFile);

        MultipartFile testImageFile = new MockMultipartFile(
                "imageFile",
                baseProfileImageFile.getName(),
                "image/png",
                new FileInputStream(baseProfileImageFile));

        assertThat(baseProfileImageFile.delete()).isTrue();

        String savedImageFilePath = imageService.saveImageFile(testImageFile);
        int indexOf = savedImageFilePath.lastIndexOf("/");
        String storeFilename = savedImageFilePath.substring(indexOf + 1);
        assertThat(savedImageFilePath).isEqualTo(imageFileAccessPath + "/" + storeFilename);

        File file = new File(imageFileSavePath + File.separator + storeFilename);
        assertThat(file.delete()).isTrue();
        assertThat(file.exists()).isFalse();
    }
}