package by.pashkevich.mikhail.service;

import by.pashkevich.mikhail.model.User;
import by.pashkevich.mikhail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void create(User user) {
        //create user
    }

    public User getByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(() -> {
            throw new UnsupportedOperationException("Not implemented yet!");
        });
    }
}
