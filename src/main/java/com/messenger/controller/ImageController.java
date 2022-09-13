package com.messenger.controller;

import com.messenger.dto.model.ImageDto;
import com.messenger.dto.model.Response;
import com.messenger.model.Image;
import com.messenger.service.ImageService;
import com.messenger.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping
    public Response<Image> uploadImage(@RequestParam("image") MultipartFile image) {
        return new Response<>(imageService.save(image));
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Image image = imageService.getById(id);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(image.getType()))
                .body(ImageUtility.decompressImage(image.getImageBytes()));
    }

    @GetMapping("/info/{id}")
    public Response<ImageDto> getInfo(@PathVariable Long id) {
        return new Response<>(imageService.getInfo(id));
    }

    @DeleteMapping("/{id}")
    public Response<Void> deleteImage(@PathVariable Long id) {
        imageService.delete(id);
        return new Response<>("Image was successfully deleted");
    }
}
