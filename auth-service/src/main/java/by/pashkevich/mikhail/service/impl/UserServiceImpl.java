package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.repository.UserRepository;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getByLogin(String login) {
        return userRepository.findByLoginWithRoles(login).orElseThrow(() ->
                new EntityNotFoundException("Can't find user by current email: " + login)
        );
    }
}
