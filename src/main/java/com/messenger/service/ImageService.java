package com.messenger.service;

import com.messenger.dto.mapper.ImageMapper;
import com.messenger.dto.model.ImageDto;
import com.messenger.exception.BadRequestException;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.Image;
import com.messenger.repository.ImageRepository;
import com.messenger.security.AuthenticationFacade;
import com.messenger.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final AuthenticationFacade authenticationFacade;

    public Image getById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Image was not found"));
    }

    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    public ImageDto getInfo(Long id) {
        Image image = getById(id);
        return imageMapper.createFrom(image);
    }

    public Image save(Image image) {
        return imageRepository.save(image);
    }

    public Image save(MultipartFile image) {
        Image newImage = new Image();

        newImage.setName(image.getOriginalFilename());
        newImage.setType(image.getContentType());
        try {
            newImage.setImageBytes(ImageUtility.compressImage(image.getBytes()));
        } catch (Exception e) {
            throw new BadRequestException("Image can not be saved");
        }
        newImage.setOwnerId(authenticationFacade.getUserId());

        return save(newImage);
    }

    public void delete(Long id) {
        imageRepository.deleteById(id);
    }
}
