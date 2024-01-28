package com.artsiomshshshsk.mypapershopapp.service;


import com.artsiomshshshsk.mypapershopapp.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ImageServiceTest {


    @InjectMocks
    private ImageService imageService;

    @Mock
    private MultipartFile multipartFile;
    @Test
    void getImageNotFound() {
        String imageDirectory = "nonexistentDirectory";
        String imageName = "nonexistentImage.jpg";

        assertThrows(NotFoundException.class, () -> imageService.getImage(imageDirectory, imageName));
    }


    @Test
    void saveImageToStorage() throws IOException {
        String uploadDirectory = "/Users/artsiom.shablinski/Documents/tests";
        String originalFilename = "postgres.jpg";
        String uniqueFileName = "07a04aa8-14fb-471e-a3ae-00d460e41fa8.jpg";

        when(multipartFile.getOriginalFilename()).thenReturn(originalFilename);
        when(multipartFile.getInputStream()).thenReturn(new ByteArrayInputStream(new byte[0]));

        String result = imageService.saveImageToStorage(uploadDirectory, multipartFile);

        assertNotNull(result);
        assertFalse(result.startsWith(uniqueFileName.substring(0, 36))); // Checking if the result starts with the unique UUID

        Path filePath = Paths.get(uploadDirectory, result);
        assertTrue(Files.exists(filePath));
    }

    @Test
    void deleteImageNotFound() throws IOException {
        String imageDirectory = "nonexistentDirectory";
        String imageName = "nonexistentImage.jpg";

        assertEquals("Failed", imageService.deleteImage(imageDirectory, imageName));
    }
}
