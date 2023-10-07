package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.dto.UserDto;
import by.pashkevich.mikhail.entity.User;
import by.pashkevich.mikhail.mapper.UserMapper;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/sign-up")
    public UserDto signUp(@RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        user = userService.create(user);
        return userMapper.toDto(user);
    }

    @GetMapping("/jwt/{jwt}")
    public String getJwt(@PathVariable String jwt) {
        return jwt;
    }
}
