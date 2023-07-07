package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/jwt/{jwt}")
    public String getJwt(@PathVariable String jwt) {
        return jwt;
    }

    @PostMapping
    public void signUp(@RequestBody User user) {
        userService.create(user);
    }
}
