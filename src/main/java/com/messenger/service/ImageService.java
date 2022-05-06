package com.messenger.service;

import com.messenger.dto.mapper.ImageMapper;
import com.messenger.dto.model.ImageDto;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.Image;
import com.messenger.repository.ImageRepository;
import com.messenger.security.AuthenticationFacade;
import com.messenger.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final AuthenticationFacade authenticationFacade;

    public Image findById(String id) {
        return imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image was not found"));
    }

    public ImageDto getInfo(String id) {
        Image image = findById(id);
        return imageMapper.createFrom(image);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public Image save(MultipartFile image) throws IOException {
        Image newImage = new Image();

        newImage.setName(image.getOriginalFilename());
        newImage.setType(image.getContentType());
        newImage.setImageBytes(ImageUtility.compressImage(image.getBytes()));
        newImage.setOwnerId(authenticationFacade.getUserId());

        return save(newImage);
    }

    public void delete(String id) {
        imageRepository.deleteById(id);
    }
}
