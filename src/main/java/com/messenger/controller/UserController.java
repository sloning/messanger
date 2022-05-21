package com.messenger.controller;

import com.messenger.dto.model.PasswordChangeDto;
import com.messenger.dto.model.Response;
import com.messenger.dto.model.UserDto;
import com.messenger.service.AuthService;
import com.messenger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    @GetMapping("/{id}")
    public Response<UserDto> findUserById(@PathVariable String id) {
        return new Response<>(userService.getUserDtoById(id));
    }

    @GetMapping("/name/{name}")
    public Response<Page<UserDto>> findUserByName(@PathVariable String name, Pageable pageable) {
        return new Response<>(userService.findByName(name, pageable));
    }

    @PutMapping("/password")
    public Response<Map<String, String>> updatePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto) {
        return new Response<>(authService.updatePassword(passwordChangeDto));
    }

    @PutMapping("/name")
    public Response<UserDto> updateName(@RequestBody String newName) {
        return new Response<>(userService.updateName(newName));
    }

    @PutMapping("/description")
    public Response<UserDto> updateDescription(@RequestBody String newDescription) {
        return new Response<>(userService.updateDescription(newDescription));
    }

    @PutMapping("/image")
    public Response<UserDto> updateImage(@RequestParam("image") MultipartFile newImage) {
        return new Response<>(userService.updateImage(newImage));
    }
}
