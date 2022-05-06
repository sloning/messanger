package com.messenger.dto.mapper;

import com.messenger.dto.model.ImageDto;
import com.messenger.model.Image;
import com.messenger.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageMapper {

    private final UserRepository userRepository;

    public ImageDto createFrom(Image image) {
        ImageDto imageDto = new ImageDto();

        imageDto.setId(image.getId());
        imageDto.setName(image.getName());
        imageDto.setType(image.getType());
        imageDto.setOwnerId(image.getOwnerId());
        imageDto.setOwnerName(userRepository.findById(image.getOwnerId()).get().getName());

        return imageDto;
    }
}
