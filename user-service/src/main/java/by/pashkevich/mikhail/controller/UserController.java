package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.mapper.UserMapper;
import by.pashkevich.mikhail.model.dto.UserDto;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @GetMapping("/auth/{jwt}")
    public UserDto getUser(@PathVariable String jwt) {
        return userMapper.toDto(userService.getByJwt(jwt));
    }

    @GetMapping("/jwt/{jwt}")
    public String getJwt(@PathVariable String jwt) {
        return jwt;
    }
}
