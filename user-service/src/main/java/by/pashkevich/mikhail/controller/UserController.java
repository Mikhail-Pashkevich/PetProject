package by.pashkevich.mikhail.controller;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    //make it permit all
    @PostMapping("/sign-up")
    public void signUp(User user) {
        userService.create(user);

    }

    @GetMapping("/sign-out")
    public void signOut() {
    }
}
