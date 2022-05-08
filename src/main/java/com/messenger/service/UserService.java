package com.messenger.service;

import com.messenger.exception.EntityNotFoundException;
import com.messenger.model.Image;
import com.messenger.model.User;
import com.messenger.repository.UserRepository;
import com.messenger.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationFacade authenticationFacade;
    private final ImageService imageService;

    public User findById(String id) {
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

    public List<User> findByName(String name, Pageable pageable) {
        return userRepository.findAllByName(name, pageable);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User updateName(String newName) {
        User user = findById(authenticationFacade.getUserId());

        user.setName(newName);

        return save(user);
    }

    public User updateDescription(String newDescription) {
        User user = findById(authenticationFacade.getUserId());

        user.setDescription(newDescription);

        return save(user);
    }

    public User updateImage(MultipartFile newImage) throws IOException {
        User user = findById(authenticationFacade.getUserId());

        Image image = imageService.save(newImage);
        user.setImageId(image.getId());

        return save(user);
    }
}
