package com.artsiomshshshsk.mypapershopapp.controller;


import com.artsiomshshshsk.mypapershopapp.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@Tag(name = "Images", description = "Images API")
@RequestMapping("/images")
public record ImagesController(ImageService imageService) {

    private static final String IMAGE_DIRECTORY = "src/main/resources/static/images";

    @GetMapping(value = "/{imageId}", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Get an image by id")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<byte[]> getImageById(@PathVariable String imageId) throws IOException {
        MediaType mediaType = MediaType.IMAGE_PNG;
        if(imageId.endsWith(".jpg") || imageId.endsWith(".jpeg")) {
            mediaType = MediaType.IMAGE_JPEG;
        }
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageService.getImage(IMAGE_DIRECTORY, imageId));
    }
}
