package com.messenger.service;

import com.messenger.dto.mapper.UserMapper;
import com.messenger.dto.model.UserDto;
import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.Image;
import com.messenger.model.User;
import com.messenger.repository.UserRepository;
import com.messenger.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ImageService imageService;
    private final UserMapper userMapper;

    public UserDto getUserDtoById(Long id) {
        return userMapper.createFrom(findById(id));
    }

    public UserDto getUserDtoByUser(User user) {
        return userMapper.createFrom(user);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with id: %s was not found", id))
        );
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException(String.format("User with username: %s was not found", username))
        );
    }

    public boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public Page<UserDto> findByName(String name, Pageable pageable) {
        Page<User> users = userRepository.findAllByName(name, pageable);
        return users.map(userMapper::createFrom);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public UserDto updateName(String newName) {
        User user = findById(authenticationFacade.getUserId());

        user.setName(newName);

        return userMapper.createFrom(save(user));
    }

    public UserDto updateDescription(String newDescription) {
        User user = findById(authenticationFacade.getUserId());

        user.setDescription(newDescription);

        return userMapper.createFrom(save(user));
    }

    public UserDto updateImage(MultipartFile newImage) {
        User user = findById(authenticationFacade.getUserId());

        Image image = imageService.save(newImage);
        user.setImageId(image.getId());

        return userMapper.createFrom(save(user));
    }
}
