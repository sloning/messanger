package com.messenger.dto.mapper;

import com.messenger.dto.model.RegisterDto;
import com.messenger.dto.model.UserDto;
import com.messenger.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public User createFrom(RegisterDto registerDto) {
        User user = new User();

        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        user.setDescription(registerDto.getDescription());
        user.setPublicKey(registerDto.getPublicKey());
        user.setImageId(registerDto.getImageId());

        return user;
    }

    public UserDto createFrom(User user) {
        UserDto userDto = new UserDto();

        userDto.setName(user.getName());
        userDto.setDescription(user.getDescription());
        userDto.setEmail(user.getUsername());
        userDto.setImageId(user.getImageId());
        userDto.setPublicKey(user.getPublicKey());
        userDto.setId(user.getId());

        return userDto;
    }

    public List<UserDto> createFrom(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(createFrom(user));
        }
        return userDtos;
    }

    //TODO придумать как возвращать нормальный Page. Потому что этот является калом.
    public Page<UserDto> createPageFrom(List<User> users) {
        return new PageImpl<>(createFrom(users));
    }
}
