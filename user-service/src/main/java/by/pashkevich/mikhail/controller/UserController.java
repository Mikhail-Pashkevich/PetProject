package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.dto.UserDto;
import by.pashkevich.mikhail.entity.User;
import by.pashkevich.mikhail.mapper.UserMapper;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;

    @PostMapping("/sign-in")
    public String signIn(@RequestBody User user) {
        return userService.getJwt(user);
    }

    @GetMapping("/auth")
    public UserDto getUser(@AuthenticationPrincipal String jwt) {
        return userMapper.toDto(userService.getByJwt(jwt));
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        userService.create(user);
    }
}
