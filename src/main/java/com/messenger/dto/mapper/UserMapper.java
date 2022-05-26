package com.messenger.dto.mapper;

import com.messenger.dto.model.RegisterDto;
import com.messenger.dto.model.UserDto;
import com.messenger.model.Image;
import com.messenger.model.User;
import com.messenger.service.ImageService;
import com.messenger.util.ImageUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public final ImageService imageService;

    public User createFrom(RegisterDto registerDto) {
        User user = new User();

        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setDescription(registerDto.getDescription());
        user.setImageId(registerDto.getImageId());

        return user;
    }

    public UserDto createFrom(User user) {
        UserDto userDto = new UserDto();

        userDto.setName(user.getName());
        userDto.setDescription(user.getDescription());
        userDto.setUsername(user.getUsername());
        userDto.setImage(getImage(user));
        userDto.setId(user.getId());

        return userDto;
    }

    private byte[] getImage(User user) {
        if (user.getImageId() == null) {
            return null;
        }
        Optional<Image> optionalImage = imageService.findById(user.getImageId());
        return optionalImage.map(image -> ImageUtility.decompressImage(image.getImageBytes())).orElse(null);
    }
}
