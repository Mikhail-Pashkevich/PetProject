package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.entity.User;
import by.pashkevich.mikhail.exception.NotFoundException;
import by.pashkevich.mikhail.repository.UserRepository;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getByUsername(String username) {
        return userRepository.findByLoginWithRoles(username).orElseThrow(() ->
                new NotFoundException("Can't find user by current email: " + username)
        );
    }
}
