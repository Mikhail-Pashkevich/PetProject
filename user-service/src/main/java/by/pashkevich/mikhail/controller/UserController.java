package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping("/sign-in")
    public Principal signIn(Principal user) {
        return user;
    }

    @PostMapping("/sign-up")
    public void signUp(@RequestBody User user) {
        userService.create(user);
    }
}
